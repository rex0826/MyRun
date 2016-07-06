package zgc.myrun.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zgc.myrun.R;
import zgc.myrun.common.PhoneInfo;
import zgc.myrun.db.DaoMaster;
import zgc.myrun.db.DaoSession;
import zgc.myrun.db.bean.LoginInfo;
import zgc.myrun.db.dao.LoginInfoDao;

/**
 * Created by Administrator on 2016/6/26.
 */
public class RegisterActivity extends Activity {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnReg = (Button)findViewById(R.id.buttonReg);
        btnReg.setOnClickListener(listenerReg);
        setupDatabase();
        // 获取 NoteDao 对象
        getLoginInfoDao();
        PhoneInfo siminfo = new PhoneInfo(RegisterActivity.this);
        EditText dtPhone =(EditText)findViewById(R.id.editTextPhone);
        dtPhone.setText(siminfo.getNativePhoneNumber());
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
    Button.OnClickListener listenerReg = new Button.OnClickListener() {//创建监听对象
        public void onClick(View v) {

            EditText editTextPhone = (EditText)findViewById(R.id.editTextPhone);
            EditText editTextPsd = (EditText)findViewById(R.id.editTextPassword);
            EditText editTextNickName = (EditText)findViewById(R.id.editTextNickname);
            LoginInfo loginInfo=new LoginInfo(null,editTextPhone.getText().toString(),editTextNickName.getText().toString(),
                    editTextPsd.getText().toString(),null,null);
           getLoginInfoDao().insert(loginInfo);
            Intent mainIntent = new Intent(RegisterActivity.this,RunActivity.class);
            RegisterActivity.this.startActivity(mainIntent);
        }
    };
}
