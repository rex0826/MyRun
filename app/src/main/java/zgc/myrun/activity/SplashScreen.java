package zgc.myrun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import zgc.myrun.R;


public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 4000; //延迟三秒
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this,RunActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }

}
