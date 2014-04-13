package com.volpc.androidtracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class DownloadService extends IntentService {
	private int result = Activity.RESULT_CANCELED;
	public static final String URL = "urlpath";
	public static final String FILENAME = "filename";
	public static final String FILEPATH = "filepath";
	public static final String RESULT = "result";
	public static final String NOTIFICATION = "com.volpc.androidtracker";

	public DownloadService() {
		super("DownloadService");
		// TODO Auto-generated constructor stub
	}

	// will be called asynchonously by android
	@Override
	protected void onHandleIntent(Intent intent) {
		String urlPath = intent.getStringExtra(URL);
		String fileName = intent.getStringExtra(FILENAME);
		File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				fileName);
		if (output.exists()) {
			output.delete();
		}

		InputStream stream = null;
		FileOutputStream fos = null;

		try {
			java.net.URL url = new URL(urlPath);
			stream = url.openConnection().getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);

			fos = new FileOutputStream(output.getPath());
			int next = -1;
			while ((next = reader.read()) != -1) {
				fos.write(next);
			}
			result = Activity.RESULT_OK;
		} catch (Exception e) {
			Log.println(Log.DEBUG, "Error", e.toString());
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					Log.println(Log.DEBUG, "Stream close", e.toString());
				}
			}
		}
		publishResults(output.getAbsolutePath(),result);

	}
	private void publishResults(String outputPath, int result){
		Intent intent = new Intent(NOTIFICATION);
		intent.putExtra(FILEPATH, outputPath);
		intent.putExtra(RESULT, result);
		sendBroadcast(intent);
	}
}
