package com.yu.ivanyu.sensortest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class SensorService extends Service
        implements SensorEventListener
{


    private PowerManager.WakeLock wakeLock;
    private SensorManager mSensorManager;
    private Sensor mRotationSensor;

    public SensorService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate()
    {
        Log.v("LOG", "service onCreate()");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("LOG", "service onStartCommand()");


        wakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelock");
        wakeLock.acquire();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);


        return Service.START_STICKY;
    }



    @Override
    public void onDestroy() {

        mSensorManager.unregisterListener(this);
        wakeLock.release();
        Log.v("LOG", "service onDestroy()");

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


}
