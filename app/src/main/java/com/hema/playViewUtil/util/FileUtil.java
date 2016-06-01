package com.hema.playViewUtil.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static String sdcardPath = Environment.getExternalStorageDirectory()
			.getPath();

	/**
	 * 用绝对路径创建目录,请使用createDirInSdcard代替
	 * 
	 * @param dir
	 * @return
	 */
	@Deprecated
	public static boolean createDir(String dir) {

		File homedir = new File(dir);

		boolean flag = false;
		if (homedir.exists()) {
			Log.d("FileUtil", "目录已经存在");
			return true;
		} else {
			flag = homedir.mkdirs(); // 创建目录
			if (flag)
				return true;
			else
				return false;
		}
	}

	/**
	 * 在sdcard下面创建目录
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean createDirInSdcard(String dir) {

		if (!haveSdcard()) {
			return false;
		}
		String dirPath = sdcardPath + "/" + dir;

		File homedir = new File(dirPath);

		boolean flag = false;
		if (homedir.exists()) {
			Log.d("FileUtil", "目录已经存在");
			return true;
		} else {
			flag = homedir.mkdirs(); // 创建目录
			if (flag)
				return true;
			else
				return false;
		}

	}

	/**
	 * 创建图片文件到指定目录,请使用saveImageInSdcard代替
	 */
	@Deprecated
	public static boolean saveImage(String dir, String filename, Bitmap bitmap) {
		if (null == bitmap) {
			return false;
		}

		// 目录不存在则创建目录
		File homedir = new File(dir);
		if (!homedir.exists()) {
			if (!homedir.mkdirs()) {
				return false;
			}
		}

		File bitmapFile = new File(dir, filename);
		FileOutputStream bitmapWtriter = null;
		try {
			bitmapWtriter = new FileOutputStream(bitmapFile);
			/*
			 * if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
			 * bitmapWtriter)) { bitmapWtriter.flush(); bitmapWtriter.close();
			 * return true; }
			 */
			bitmapWtriter.flush();
			bitmapWtriter.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建图片文件到sdcard
	 */
	public static boolean saveImageInSdcard(String dir, String urlString,
			Bitmap bitmap) {
		if (null == bitmap) {
			return false;
		}
		if (!haveSdcard()) {
			return false;
		}
		dir = sdcardPath + "/" + dir;

		// 目录不存在则创建目录
		File homedir = new File(dir);
		if (!homedir.exists()) {
			if (!homedir.mkdirs()) {
				return false;
			}
		}

		File bitmapFile = new File(dir, getFileName(urlString));
		FileOutputStream bitmapWtriter = null;
		try {
			bitmapWtriter = new FileOutputStream(bitmapFile);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapWtriter)) {
				bitmapWtriter.flush();
				bitmapWtriter.close();
				return true;
			}
		} catch (FileNotFoundException e) {
			System.out.println("ffdfdfd");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建文件到sdcard
	 */
	public static boolean savefileInSdcard(String dir, String filename,
			byte[] buffer) {
		if (null == buffer) {
			return false;
		}
		if (!haveSdcard()) {
			return false;
		}
		dir = sdcardPath + "/" + dir;

		// 目录不存在则创建目录
		File homedir = new File(dir);
		if (!homedir.exists()) {
			if (!homedir.mkdirs()) {
				return false;
			}
		}

		File file = new File(dir, filename);
		FileOutputStream Wtriter = null;
		try {
			Wtriter = new FileOutputStream(file);
			Wtriter.write(buffer);// 写入buffer数组。如果想写入一些简单的字符，可以将String.getBytes()再写入文件;
			Wtriter.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 得到扩展名 **/
	public static String getExtend(String file) {
		if (file != null && (file.contains("."))) {
			return file.substring(file.lastIndexOf("."));
		}
		return "";
	}

	/** 得到文件名 **/
	public static String getFileName(String path) {
		return path.substring(path.lastIndexOf(File.separator) + 1,
				path.length());
	}

	/**
	 * 根据绝对路径获取图片
	 * 
	 * @param --dir
	 * @return
	 */
	public static Bitmap getImage(String path) {
		return BitmapFactory.decodeFile(path);
	}

	/**
	 * 根据相对路径获取sdcard中的图片
	 * 
	 * @param --dir
	 * @return
	 */
	public static Bitmap getImageInSdcard(String path) {
		String pathName = sdcardPath + "/" + path;
		// System.out.println("根据相对路径获取sdcard中的图片: "+pathName);
		return BitmapFactory.decodeFile(pathName);
	}

	/**
	 * 根据相对路径获取sdcard中图片的缩略图
	 * 
	 * @param filePath
	 *            相对文件路径
	 * @param height
	 *            图片显示高度 height=0时使用默认值80
	 * @return
	 */
	public static Bitmap getThumbnailInSdcard(String filePath, int height) {
		if (0 == height)
			height = 80;
		String path = sdcardPath + "/" + filePath;
		BitmapFactory.Options options = new BitmapFactory.Options();

		// 获取这个图片的宽和高
		options.inJustDecodeBounds = true; // true 则返回bm为空
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空,
																	// 只读大小,不耗内存,
		// 计算缩放比 节省内存
		int be = (int) (options.outHeight / (float) height);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, options);
		if (null == bitmap)
			return null;
		return bitmap;
	}

	/**
	 * 根据绝对路径获取图片的缩略图
	 * 
	 * @param --filePath
	 *            相对文件路径
	 * @param height
	 *            图片显示高度 height=0时使用默认值80
	 * @return
	 */
	public static Bitmap getThumbnail(String path, int height) {
		if (0 == height)
			height = 80;
		BitmapFactory.Options options = new BitmapFactory.Options();

		// 获取这个图片的宽和高
		options.inJustDecodeBounds = true; // true 则返回bm为空
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空,
																	// 只读大小,不耗内存,
		// 计算缩放比 节省内存
		int be = (int) (options.outHeight / (float) height);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Log.d("ia", w + "   " + h);
		return bitmap;
	}

	/**
	 * 获取sdcard下某目录内的图片,不迭代子目录
	 * 
	 * @param dir
	 *            相对于sdcard的目录
	 * @return
	 */
	public static List<String> getImagesInSdcardDir(String dir) {
		/* 设定目前所在路径 */
		List<String> it = new ArrayList<String>();
		File f = new File(sdcardPath + "/" + dir);
		File[] files = f.listFiles();

		/* 将所有文件存入ArrayList中 */
		if (null == files)
			return null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (isImage(file.getPath()))
				it.add(file.getPath());
		}
		return it;
	}

	/**
	 * 获取sdcard下某目录内的图片,包括子目录
	 * 
	 * @param dir
	 *            相对于sdcard的目录
	 * @return
	 */
	public static List<String> getAllImageInSdcardDir(String dir) {
		/* 设定目前所在路径 */
		List<String> it = new ArrayList<String>();
		File f = new File(sdcardPath + "/" + dir);
		File[] files = f.listFiles();

		/* 将所有文件存入ArrayList中 */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (isImage(file.getPath()))
				it.add(file.getPath());
		}
		return it;
	}

	/**
	 * 获取sdcard下面的所有图片
	 * 
	 * @return
	 */
	public static List<String> getAllImageInSdcard() {
		/* 设定目前所在路径 */
		List<String> it = new ArrayList<String>();
		File f = new File(sdcardPath);
		File[] files = f.listFiles();

		/* 将所有文件存入ArrayList中 */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (isImage(file.getPath()))
				it.add(file.getPath());
		}
		return it;
	}

	/**
	 * 删除某个文件夹下的所有文件
	 * 
	 * @param srcDir
	 *            目录地址
	 * @throws IOException
	 */
	public static void deleteDir(String srcDir) throws IOException {
		File file = new File(srcDir);
		if (!file.exists())
			return;

		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory())
				deleteDir(srcDir + "/" + files[i].getName());
			deleteFile(srcDir + "/" + files[i].getName());
		}
		// deleteFile(srcDir);加上，会删除这个文件夹
	}

	/**
	 * 删除某个文件目录
	 * 
	 * @param file
	 *            需要删除的文件
	 */
	public static void deleteFile(File file) {
		if (file != null && file.exists())
			file.delete();
	}

	/**
	 * 用绝对sdcard的路径删除文件
	 * 
	 * @param --dir
	 * @return
	 */
	public static boolean deleteFileInSdcard(String path) {

		File file = new File(sdcardPath + "/" + path);

		boolean flag = false;
		if (file.exists()) {
			flag = file.delete();
			if (flag) {
				Log.d("FileUtil", "文件删除成功");
				return true;
			} else {
				Log.d("FileUtil", "文件删除失败");
				return false;
			}

		} else {
			Log.d("FileUtil", "文件不存在");
			return false;
		}
	}

	/**
	 * 用绝对路径删除文件
	 * 
	 * @param --dir
	 * @return
	 */
	public static boolean deleteFile(String path) {

		File file = new File(path);

		boolean flag = false;
		if (file.exists()) {
			flag = file.delete();
			if (flag) {
				Log.d("FileUtil", "文件删除成功");
				return true;
			} else {
				Log.d("FileUtil", "文件删除失败");
				return false;
			}

		} else {
			Log.d("FileUtil", "文件不存在");
			return false;
		}
	}

	// 读取文本文件中的内容
	public static String ReadTxtFile(String strFilePath) {
		String path = strFilePath;
		String content = ""; // 文件内容字符串
		// 打开文件
		File file = new File(path);
		// 如果path是传递过来的参数，可以做一个非目录的判断
		if (file.isDirectory()) {
			Log.d("TestFile", "The File doesn't not exist.");
		} else {
			try {
				InputStream instream = new FileInputStream(file);
				if (instream != null) {
					InputStreamReader inputreader = new InputStreamReader(
							instream);
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// 分行读取
					while ((line = buffreader.readLine()) != null) {
						content += line + "\n";
					}
					instream.close();
				}
			} catch (FileNotFoundException e) {
				Log.d("TestFile", "The File doesn't not exist.");
			} catch (IOException e) {
				Log.d("TestFile", e.getMessage());
			}
		}
		return content;
	}

	/**
	 * 过滤图片文件,判断传入的文件名是否是图片后缀结尾
	 * 
	 * @param fName
	 * @return
	 */
	public static boolean isImage(String fName) {
		boolean re;

		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/* 按扩展名的类型决定MimeType */
		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	/**
	 * 判断是否插入SDcard
	 * 
	 */
	public static boolean haveSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// ERROR //
	public static byte[] getFileContent(String paramString) {

		return null;
	}

	// 下载文件
	public static void downFile(final String url) {
		new Thread() {
			@Override
			public void run() {
				try {
					int download_precent = 0;
					InputStream is = null;
					long length = 0L;
					// if (Build.VERSION.SDK_INT >= 9) {
					// HttpURLConnection conn = (HttpURLConnection) new URL(
					// url).openConnection();
					// length = conn.getContentLength();
					// is = conn.getInputStream();
					// } else {

					// params[0]代表连接的url
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response = client.execute(get);
					HttpEntity entity = response.getEntity();
					length = entity.getContentLength();
					is = entity.getContent();
					// }
					if (is != null) {
						File rootFile = new File(
								Environment.getExternalStorageDirectory(), "");
						if (!rootFile.exists() && !rootFile.isDirectory())
							rootFile.mkdir();

						File tempFile = new File(
								Environment.getExternalStorageDirectory(),
								"" + url.substring(url.lastIndexOf("/") + 1));
						if (tempFile.exists())
							tempFile.delete();
						tempFile.createNewFile();

						// 已读出流作为参数创建一个带有缓冲的输出流
						BufferedInputStream bis = new BufferedInputStream(is);
						// 创建一个新的写入流，讲读取到的图像数据写入到文件中
						FileOutputStream fos = new FileOutputStream(tempFile);
						// 已写入流作为参数创建一个带有缓冲的写入流
						BufferedOutputStream bos = new BufferedOutputStream(fos);

						int read;
						long count = 0;
						int precent = 0;
						// byte[] buffer = new byte[64];
						byte[] buffer = new byte[1024];
						while ((read = bis.read(buffer)) != -1) {
							bos.write(buffer, 0, read);
							count += read;
							precent = (int) (((double) count / length) * 100);

							// 每下载完成5%就通知任务栏进行修改下载进度
							if (precent - download_precent >= 5) {
								download_precent = precent;
								System.out.println("下载进度--" + download_precent);
							}
						}
						bos.flush();
						bos.close();
						fos.flush();
						fos.close();
						is.close();
						bis.close();
					}

				} catch (ClientProtocolException e) {
					System.out.println("下载文件失败");
				} catch (IOException e) {
					System.out.println("下载文件失败");
				} catch (Exception e) {
					System.out.println("下载文件失败");
				}
			}
		}.start();
	}

}
