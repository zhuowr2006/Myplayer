package com.hema.myplayer.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * Activity常用工具方法
 * 
 * @author zwr
 */
public class ActivityTools {
	/**
	 * 获取Activity名字
	 * 
	 * @param cla
	 *            需要的页面
	 */
	public static String ActivityName(Class cla) {
		return cla.getSimpleName();
	}

	/**
	 * Activity跳转
	 * 
	 * @param ac
	 *            当前页面
	 * @param cla
	 *            需要跳转的页面
	 */
	public static void skipActivity(Activity ac, Class cla) {
		Intent intent = new Intent(ac, cla);
		ac.startActivity(intent);
	}

	/**
	 * Activity跳转
	 * 
	 * @param ac
	 *            当前页面
	 * @param cla
	 *            需要跳转的页面
	 */
	public static void skipActivity(Activity ac, Class cla, Bundle bundle) {
		Intent intent = new Intent(ac, cla);
		intent.putExtras(bundle);
		ac.startActivity(intent);
	}

	/**** Activity跳转并关闭跳转界面 ***/
	public static void skipActivityFinish(Activity ac, Class cla) {
		Intent intent = new Intent(ac, cla);
		ac.startActivity(intent);
		ac.finish();
	}

	/**** ActivityForResult跳转界面 ***/
	public static void skipActivityForResult(Activity ac, Class cla,
			int requestCode) {
		Intent intent = new Intent(ac, cla);
		ac.startActivityForResult(intent, requestCode);
	}

	// /**
	// * 结束所有界面，跳转到登录界面
	// */
	// public static void finishAllAndLaunchLogin(Context context) {
	// ActivityManagerCommon managerCommon =
	// ActivityManagerCommon.getScreenManager();
	// managerCommon.clearStack();
	// // Stack<Activity> stack = managerCommon.getStackActivity();
	// // for(Activity activity:stack){
	// // activity.finish();
	// // }
	// Intent intent = new Intent(context,LoginActivity.class);
	// context.startActivity(intent);
	// }

	// /**
	// * 显示Toast
	// *
	// * @param msg 内容
	// */
	// public static void showToast(Context context,String msg){
	// Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	// }
	//
	/**
	 * 弹出软键盘
	 * 
	 */
	public static void showKeyboard(final Context context) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				((InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 1000);
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * false 不存在 true 存在
	 */
	public static boolean isSDCardExist() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 网络连接是否可用
	 */
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivityManager) {
			try {
				NetworkInfo networkInfo[] = connectivityManager
						.getAllNetworkInfo();

				if (null != networkInfo) {
					for (NetworkInfo info : networkInfo) {
						if (info.getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			} catch (NullPointerException e) {
				return false;
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
			} catch (RuntimeException e) {
				// TODO: handle exception
			}
		}
		return false;
	}

	/**
	 * 截屏操作
	 * 
	 * 把图片保存到SD卡
	 */
	// public static void screenView(View view,String fileName) throws
	// Exception{
	// Bitmap sCoverBitmap =
	// Bitmap.createBitmap(view.getWidth(),view.getHeight(), Config.ARGB_8888);
	// Canvas canvas = new Canvas(sCoverBitmap);
	// view.draw(canvas);
	//
	// FileOutputStream fos = null;
	// File dirFile = new File(Constant.Common.YOUJIAOPIC_PATH);
	//
	// if(!dirFile.exists()){
	// dirFile.mkdir();
	// }
	// File file = new File(Constant.Common.YOUJIAOPIC_PATH,fileName);
	// fos = new FileOutputStream(file);
	// if (null != fos) {
	// sCoverBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
	// fos.flush();
	// fos.close();
	// }
	// }

	/** 判断当前网络连接类型 **/
	public static boolean isNetWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivityManager) {
			if (connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) { // WIFI连接
				return true;
			}
		}
		return false;
	}

	/** 关闭Dialog **/
	public static void disMissDialog(ProgressDialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}

	/**
	 * 使用电话功能来判断 如是没有电话功能则为ture，有电话功能则为false
	 * 
	 * 这里的PhoneType在SDK里有如下几种： 
	 * PHONE_TYPE_NONE 手机制式未知 
	 * PHONE_TYPE_GSM  手机制式为GSM，移动和联通 
	 * PHONE_TYPE_CDMA 手机制式为CDMA，电信 
	 * PHONE_TYPE_SIP  网络电话
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isTabletDevice(Context mContext) {
		TelephonyManager telephony = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_NONE) {// 没有电话功能
			return true;
		} else {// 有电话功能
			return false;
		}
	}
}
