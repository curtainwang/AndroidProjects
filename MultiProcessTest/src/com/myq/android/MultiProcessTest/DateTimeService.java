package com.myq.android.MultiProcessTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class DateTimeService extends Service {

	public static final String DATETIME_SERVICE_ACTION = "com.myq.android.MultiProcessTest.DATETIMESERVICE_ACTION";

	private static final String TAG = "--------DateTimeService-------";

	private SimpleDateFormat sdf;

	private final IDateTimeService.Stub stub = new IDateTimeService.Stub() {

		public String getCurrentDateTime(String format) throws RemoteException {
			Log.d("PAGE", " IDateTimeService.Stub ---3");
			return getCurrentDateTimeString(format);
		}
	};

	private synchronized String getCurrentDateTimeString(String format) {
		sdf = new SimpleDateFormat(format);
		final String temp = sdf.format(new Date());
		Log.i(TAG, "getCurrentDateTimeString--" + Thread.currentThread() + "--"
				+ temp);
		return temp;
	}

	public IBinder onBind(Intent arg0) {
		Log.i(TAG, "onBind--" + Thread.currentThread());
		Log.d("PAGE", " onBind ---2");
		return null;// 绑定成功后返回服务端的Binder引用
	}

}
