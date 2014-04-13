package com.volpc.androidtracker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView textView;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String string = bundle.getString(DownloadService.FILEPATH);
				int resultCode = bundle.getInt(DownloadService.RESULT);
				if (resultCode == RESULT_OK) {
					Toast.makeText(MainActivity.this,
							"Download complete.  Download URI: " + string,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MainActivity.this, "Download Failed",
							Toast.LENGTH_LONG).show();
					textView.setText("Download Failed");
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView)findViewById(R.id.status);
	}
	protected void onResume(){
		super.onResume();
		registerReceiver(receiver, new IntentFilter(DownloadService.NOTIFICATION));
	}
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
	public void onClick(View view){
		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra(DownloadService.FILENAME, "newFile.html");
		intent.putExtra(DownloadService.URL, "http://www.vogella.com/index.html");
		startService(intent);
		textView.setText("Service Started");
	}

}
