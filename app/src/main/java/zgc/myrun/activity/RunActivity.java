package zgc.myrun.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import zgc.myrun.R;
import zgc.myrun.db.DaoMaster;
import zgc.myrun.db.DaoSession;
import zgc.myrun.db.bean.LoginInfo;
import zgc.myrun.db.dao.LoginInfoDao;
import zgc.myrun.step.RunStepDetector;

/**
 * Created by Administrator on 2016/6/26.
 */
public class RunActivity extends Activity {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListener myListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    MapView mMapView;
    BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位

    private TextView step_counter;
    private TextView time_counter;
    private int total_step = 0;

    private boolean isRun = false;
    private long timer = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            countStep();
            step_counter.setText(total_step + "步");
            time_counter.setText(time_counter +"分钟");
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_run);

        LinearLayout llStart=(LinearLayout)findViewById(R.id.llStart);
        LinearLayout llStop=(LinearLayout)findViewById(R.id.llPause);
        llStart.setVisibility(View.VISIBLE);
        llStop.setVisibility(View.GONE);
        Button btnStart=(Button)findViewById(R.id.buttonStartRun);
        btnStart.setOnClickListener(listenerStart);
        setupDatabase();
        // 获取 NoteDao 对象
        getLoginInfoDao();
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapRun);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型

        option.setScanSpan(1000);
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的
        mLocClient.setLocOption(option);
        mLocClient.start();

        Bundle extras = getIntent().getExtras();
        isRun = extras.getBoolean("run");
        step_counter = (TextView)findViewById(R.id.totalStep);
        time_counter = (TextView)findViewById(R.id.totalTime);
    }
    private LoginInfoDao getLoginInfoDao(){
        return daoSession.getLoginInfoDao();
    }
    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "run-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    Button.OnClickListener listenerStart = new Button.OnClickListener(){//创建监听对象
        public void onClick(View v){



            //RunActivity.this.finish();
            QueryBuilder<LoginInfo> qb = getLoginInfoDao().queryBuilder();
            List lst = qb.list();
            if(lst.size()>0){
                LinearLayout llStart=(LinearLayout)findViewById(R.id.llStart);
                LinearLayout llStop=(LinearLayout)findViewById(R.id.llPause);
                llStart.setVisibility(View.GONE);
                llStop.setVisibility(View.VISIBLE);
            }else{
                Intent mainIntent = new Intent(RunActivity.this,RegisterActivity.class);
                RunActivity.this.startActivity(mainIntent);
            }
        }

    };
    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void init() {

        countDistance();
        countStep();

    }

    private void countDistance() {

    }
    private void countStep() {
        if (RunStepDetector.CURRENT_SETP % 2 == 0) {
            total_step = RunStepDetector.CURRENT_SETP;
        } else {
            total_step = RunStepDetector.CURRENT_SETP +1;
        }
    }
    private String getFormatTime(long time) {
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;


        String strSecond = ("00" + second)
                .substring(("00" + second).length() - 2);

        String strMinute = ("00" + minute)
                .substring(("00" + minute).length() - 2);

        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

        return strHour + ":" + strMinute + ":" + strSecond;

    }
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals(getString(R.string.zero)) ? getString(R.string.double_zero)
                : distanceStr;
    }
}
