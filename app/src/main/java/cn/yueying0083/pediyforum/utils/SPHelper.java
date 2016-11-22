package cn.yueying0083.pediyforum.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

@SuppressLint("WorldReadableFiles")
public class SPHelper {

	/*
	 * 获取指定文件和字段的字符串
	 */
	public static String getString(Context context, String filename, String field) {
		return getString(context, filename, field, null);
	}
	
	/*
	 * 获取指定文件和字段的字符串，可以设置默认值
	 */
	public static String getString(Context context, String filename, String field, String defaultValue) {
		SharedPreferences setting = context.getSharedPreferences(filename, Context.MODE_WORLD_READABLE);
		String uid = setting.getString(field, defaultValue);
		return uid;
	}
	
	/*
	 * 获取所有
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getAll(Context context, String filename) {
		SharedPreferences setting = context.getSharedPreferences(filename, Context.MODE_WORLD_READABLE);
		Map<String, String> all = null;
		try {
			all = (Map<String, String>)setting.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
	
	/*
	 * 清空文件下的所有
	 */
	public static void clearFile(Context context, String filename) {
		SharedPreferences setting = context.getSharedPreferences(filename, Context.MODE_WORLD_READABLE);
		setting.edit().clear().commit();
	}
	
	/*
	 * 设置指定文件和字段的字符串
	 */
	public static void setString(Context context, String filename, String field, String value) {
		if (TextUtils.isEmpty(value)) {
			removeString(context, filename, field);
			return;
		}
		SharedPreferences setting = context.getSharedPreferences(filename, Context.MODE_WORLD_READABLE);
		setting.edit().putString(field, value).commit();
	}
	
	/*
	 * 删除字段
	 */
	public static void removeString(Context context, String filename, String field) {
		SharedPreferences setting = context.getSharedPreferences(filename, Context.MODE_WORLD_READABLE);
		setting.edit().remove(field).commit();
	}
	
	/*
	 * 还是BASE64靠谱，用UTF-8二进制长度会变，用ISO长度不会变，但是有时候EOF异常
	 */
	public static void setSerializableObj(Context context, String filename, String field, Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			String base64String = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
			setString(context, filename, field, base64String);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Serializable getSerializableObj(Context context, String filename, String field) {
		try {
			String base64String = getString(context, filename, field);
			if (TextUtils.isEmpty(base64String)) {
				return null;
			}
			byte[] base64Bytes = Base64.decode(base64String.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Serializable)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
