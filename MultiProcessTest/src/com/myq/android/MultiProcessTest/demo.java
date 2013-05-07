package com.myq.android.MultiProcessTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class demo extends Activity {

	private static final String TAG = "------------";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "--onCreate--");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "--onStart--");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "--onRestart--");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "--onResume--");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "--onPause--");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "--onStop--");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "--onDestroy--");
	}

}
