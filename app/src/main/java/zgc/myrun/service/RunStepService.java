package zgc.myrun.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import zgc.myrun.common.Constants;
import zgc.myrun.step.RunStepDetector;

/**
 * Created by Administrator on 2016/6/25.
 */
public class RunStepService extends Service {
    public static Boolean FLAG = false;

    private SensorManager mSensorManager;
    //private StepDetector detector;

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;

    private RunStepDetector detector;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FLAG = true;
        detector = new RunStepDetector(this);
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(detector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        mPowerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);

        //mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");

        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Constants.TAG);
        mWakeLock.acquire();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        FLAG = false;
        if (detector != null) {
            mSensorManager.unregisterListener(detector);
        }

        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }
}
