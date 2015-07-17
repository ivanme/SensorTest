package com.yu.ivanyu.sensortest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;


//*/
public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isMyServiceRunning(SensorService.class))
        {
            Intent intent = new Intent(MainActivity.this, SensorService.class);
            stopService(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, SensorService.class);
            startService(intent);
            finish();
        }
    }




    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}

/*/
public class MainActivity extends WearableActivity
        implements SensorEventListener
{

    private SensorManager mSensorManager;
    private Sensor mRotationSensor;
    private PowerManager.WakeLock wakeLock;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub)
            {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        wakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelock");
        wakeLock.acquire();


        setAmbientEnabled();


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        String output = String.format("X:%s Y:%s Z:%s at %s", event.values[0], event.values[1], event.values[2], event.timestamp );
        Log.v("Value", output);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        wakeLock.release();
    }


    @Override
    public void onEnterAmbient(Bundle ambientDetails)
    {
        super.onEnterAmbient(ambientDetails);
        Log.v("Value", "Entering ambient");
    }

    @Override
    public void onExitAmbient()
    {
        super.onExitAmbient();
        Log.v("Value", "Exiting ambient");
    }


}

//*/


