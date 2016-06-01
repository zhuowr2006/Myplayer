package com.hema.myplayer.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

	private static Toast toast;

	public static void showShortToast(Context context, String message) {
		if (!StringUtils.isEmpty(message)) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showLongToast(Context context, String message) {
		if (!StringUtils.isEmpty(message)) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}

	public static void showOnceToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}

	public static void showOnceLongToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		} else {
			toast.setText(text);
		}
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
