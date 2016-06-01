package com.hema.myplayer.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hema.myplayer.ui.MainActivity;

import java.util.Stack;

/**
 * @ClassName: AppManager
 * @Description: 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author liuyongzheng
 * @date 2014-3-18 下午2:29:15
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public static void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	/**
	 * 结束所有界面，跳转到登录界面
	 */
//	public static void finishAllAndLaunchLogin(Context context) {
//		for (int i = activityStack.size()-1, size = 0; i >=size; i--) {
//			if (null != activityStack.get(i)) {
//				if (activityStack.get(i).getClass().equals(MainActivity.class)) {
//					Intent intent = new Intent();// 新建Intent
//					intent.setClass(activityStack.get(i), FDLoginActivity.class);
//					activityStack.get(i).startActivityForResult(intent, 1);// 显示界面
//					return;
//				}else {
//					activityStack.get(i).finish();
//				}
//			}
//		}
//	}
	/**
	 * 结束所有界面，跳转到主界面
	 * 第二个参数为选择主页面的哪个Fragment,-1为不使用
	 */
	public static void finishAllAndLaunchMain(Context context,int number) {
		for (int i = activityStack.size()-1, size = 0; i >=size; i--) {
			if (null != activityStack.get(i)) {
				if (activityStack.get(i).getClass().equals(MainActivity.class)) {
					if (number!=-1) {
						if (changelayout!=null) {
							changelayout.callBack(number);
						}
					}
					return;
				}else {
					activityStack.get(i).finish();
				}
			}
		}
	}
	private static MainChangeLayoutListener changelayout=null;

	public void setMainChangeLayoutListener(MainChangeLayoutListener KeyListener) {
		this.changelayout = KeyListener;
	};

	public interface MainChangeLayoutListener {
		void callBack(int number);
	};
	/**
	 * 结束所有界面，跳转到主界面
	 */
	public static void finishAllAndLaunchMain(Context context) {
		for (int i = activityStack.size()-1, size = 0; i >=size; i--) {
			if (null != activityStack.get(i)) {
				if (activityStack.get(i).getClass().equals(MainActivity.class)) {
					return;
				}else {
					activityStack.get(i).finish();
				}
			}
		}
	}
	
	/**
	 * 结束所有界面，跳转到指定页面
	 */
	public static void finishAllAndLaunchOther(Context context,Class cla) {
		for (int i = activityStack.size()-1, size = 0; i >=size; i--) {
			if (null != activityStack.get(i)) {
				if (activityStack.get(i).getClass().equals(MainActivity.class)) {
					Intent intent = new Intent();// 新建Intent
					intent.setClass(activityStack.get(i), cla);
					activityStack.get(i).startActivity(intent);// 显示界面
					return;
				}else {
					activityStack.get(i).finish();
				}
			}
		}
	}
	
	/**
	 * 结束所有界面，跳转到指定页面
	 */
	public static void finishAllAndLaunchOther(Context context,Class cla,Bundle bundle) {
		for (int i = activityStack.size()-1, size = 0; i >=size; i--) {
			if (null != activityStack.get(i)) {
				if (activityStack.get(i).getClass().equals(MainActivity.class)) {
					Intent intent = new Intent();// 新建Intent
					intent.putExtras(bundle);
					intent.setClass(activityStack.get(i), cla);
					activityStack.get(i).startActivity(intent);// 显示界面
					return;
				}else {
					activityStack.get(i).finish();
				}
			}
		}
	}
	/**
	 * 结束到指定页面
	 */
	public static void finishToActivity(Context context,Class cla) {
		for (int i = activityStack.size()-1, size = 0; i >=size; i--) {
			if (null != activityStack.get(i)) {
				if (activityStack.get(i).getClass().equals(cla)) {
//					Intent intent = new Intent();// 新建Intent
//					intent.setClass(activityStack.get(i), cla);
//					activityStack.get(i).startActivity(intent);// 显示界面
					return;
				}else {
					activityStack.get(i).finish();
				}
			}
		}
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}