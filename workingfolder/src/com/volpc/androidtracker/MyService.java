package com.volpc.androidtracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
	public int onStartCommand(Intent intent, int flags, int startId){
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
