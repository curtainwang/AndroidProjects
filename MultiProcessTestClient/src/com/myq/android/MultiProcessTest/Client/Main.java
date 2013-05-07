package com.myq.android.MultiProcessTest.Client;

import com.myq.android.MultiProcessTest.IDateTimeService;
import com.myq.android.MultiProcessTest.Client.R;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity {
	private static final String TAG = "-----MultiProcessTest.Client  Main-------";
	private IDateTimeService mDateTimeService;
	private TextView mTv;
	private Button mBtn;
	private static final int WHAT = 100;
	private boolean isBinded = false;
	private Spinner mSpinner;
	private ArrayAdapter<String> adapter;
	private String format = "yyyy-MM-dd HH:mm:ss";
	private static final String[] formats = { "yyyy-MM-dd HH:mm:ss",
			"MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "dd/MM HH:mm:ss",
			"HH:mm:ss", "mm:ss" };

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT:
				mTv.setText(getRemoteCurrentTime());
				mTv.invalidate();
				break;
			}
		}
	};

	private Runnable mRunnable = new Runnable() {
		public void run() {
			mHandler.obtainMessage(WHAT).sendToTarget();
			mHandler.postDelayed(mRunnable, 1000);
		}
	};

	private ServiceConnection mServiceConn = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder service) {
			mDateTimeService = IDateTimeService.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName name) {
			mDateTimeService = null;
		}
	};

	private String getRemoteCurrentTime() {
		try {

			return this.mDateTimeService.getCurrentDateTime(format);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return "err";
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "--onCreate--");

		this.setContentView(R.layout.main);
		this.mTv = (TextView) this.findViewById(R.id.tv);

		this.mBtn = (Button) this.findViewById(R.id.bt);

		this.mSpinner = (Spinner) this.findViewById(R.id.spinner);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, formats);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mSpinner.setAdapter(adapter);
	}

	protected void onStart() {
		super.onStart();
		Log.i(TAG, "--onStart--");
	}

	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "--onRestart--");
	}

	protected void onResume() {
		super.onResume();
		Log.i(TAG, "--onResume--");
		this.mTv.setText("MultiProcessTest");
		final Intent intent = new Intent(
				"com.myq.android.MultiProcessTest.DATETIMESERVICE_ACTION");

		this.mSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> adpterView,
							View view, int index, long id) {
						format = formats[index];
					}

					@Override
					public void onNothingSelected(AdapterView<?> adpterView) {
						adpterView.setVisibility(0);
					}
				});

		this.mBtn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				if (!isBinded) {
					bindService(intent, mServiceConn, Service.BIND_AUTO_CREATE);
					mHandler.post(mRunnable);
					isBinded = true;
					mBtn.setText("stop");
					mBtn.invalidate();
				} else {
					mHandler.removeCallbacks(mRunnable);
					unbindService(mServiceConn);
					mTv.setText("MultiProcessTest");
					isBinded = false;
					mBtn.setText("start");
					mBtn.invalidate();
				}
			}
		});
	}

	protected void onPause() {
		Log.i(TAG, "--onPause--");
		super.onPause();
	}

	protected void onStop() {
		Log.i(TAG, "--onStop--");
		super.onStop();
	}

	protected void onDestroy() {
		Log.i(TAG, "--onDestroy--");

		if (isBinded) {
			this.mHandler.removeCallbacks(mRunnable);
			this.unbindService(mServiceConn);
		}
		super.onDestroy();
	}

}