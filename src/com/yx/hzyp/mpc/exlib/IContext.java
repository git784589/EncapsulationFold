package com.yx.hzyp.mpc.exlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

/**
 * 全局接口对象
 * 
 * @author Tips
 * 
 */
public interface IContext {
	public static final String STRING$EMPTY_LEN = " ";
	public static final String STRING$EMPTY = "";
	public static final String MAN = "男";
	public static final String W_MAN = "女";
	public static final String AND = "AND", OR = "OR", NOT = "NOT";
	public static final String EXG = "\\d{15}(\\d{3}|(\\d{2}[Xx]))?";// 验证身份证位数的有效性

	public enum Gander {
		男, 女
	}

	/**
	 * 拷贝DB到APP文件夹下
	 * 
	 * @author Tips
	 * 
	 */
	public class CopyDB {

		/**
		 * 根据路径复制文件
		 * 
		 * @param resourcePath
		 * @param targetPath
		 * @param o
		 * @param ctx
		 */
		public static void copy(String resourcePath, String targetPath,
				boolean o, Context ctx) {
			if (TextUtils.isEmpty(resourcePath)
					|| TextUtils.isEmpty(targetPath)) {
				return;
			}
			if (resourcePath.equals(targetPath))
				return;
			File resource = new File(resourcePath);
			File target = new File(targetPath);
			if (resource.exists()) {
				if (o) {
					target.delete();
				}
				copyFile(resourcePath, targetPath, ctx);

			}
		}

		/**
		 * 复制单个文件
		 * 
		 * @param oldPath
		 *            String 原文件路径 如：c:/fqf.txt
		 * @param newPath
		 *            String 复制后路径 如：f:/fqf.txt
		 * @return boolean
		 */
		public static void copyFile(String oldPath, String newPath, Context ctx) {
			try {

				File oldfile = new File(oldPath);
				if (oldfile.exists()) { // 文件存在时
					FileInputStream input = new FileInputStream(oldfile);
					FileOutputStream out = new FileOutputStream(newPath);
					copy(input, out, ctx);
				}
			} catch (Exception e) {
				System.out.println("复制单个文件操作出错");
				e.printStackTrace();

			}

		}

		/**
		 * 文件复制
		 * 
		 * @param inStream
		 * @param fs
		 * @param ctx
		 */
		public static void copy(InputStream inStream, FileOutputStream fs,
				Context ctx) {
			byte[] buffer = new byte[1444];
			int bytesum = 0;
			int byteread = 0;
			try {
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(bytesum);
			}
		}

		/**
		 * 删除文件
		 * 
		 * @param filename
		 *            文件名
		 * @param filepath
		 *            文件路径
		 * @return
		 */
		public boolean fileDelete(String filename, String filepath) {
			boolean bflag = false;
			File file;
			try {
				file = new File(filepath + filename);
				file.delete();
				bflag = true;
			} catch (Exception e) {
			}
			return bflag;
		}

		/**
		 * 检查并建立文件夹sFilePath
		 * 
		 * @param sFilePath
		 *            目录路径
		 * @param sExt
		 *            目录分隔符
		 */

		public void createFilePath(String sFilePath, String sExt) {
			try {
				File fileWT = new File(sFilePath);
				if (fileWT.exists() == false) {
					int nPos = 0;
					if ((nPos = sFilePath.lastIndexOf(sExt)) > 0) {
						createFilePath(sFilePath.substring(0, nPos), sExt);
					}
					fileWT.mkdir();
				}
				fileWT = null;
			} catch (Exception e) {
			}
		}

		/**
		 * 读文件
		 * 
		 * @param filename
		 *            为文件路径
		 * @return
		 */
		@SuppressWarnings("resource")
		public String fileRead(String filename) {
			String fileBody = "";
			try {
				FileInputStream fr = new FileInputStream(filename);
				int n = fr.available();
				byte b[] = new byte[n];
				fr.read(b);
				fileBody = new String(b, 0, b.length);
			} catch (Exception e) {
				fileBody = "";
			}
			return fileBody;
		}

		/**
		 * 写文件
		 * 
		 * @param filename
		 *            文件路径
		 * @param filebody
		 * @return
		 */
		public boolean fileWrite(String filename, String filebody) {
			boolean bflag = false;
			PrintWriter file = null;
			try {
				file = new PrintWriter(new FileWriter(filename, false), true);
				file.println(filebody);
				file.flush();
				bflag = true;
			} catch (Exception e) {
			} finally {
				file.close();
			}
			return bflag;
		}

		/**
		 * 把字符串数组用指定的连接符连成字符串
		 * 
		 * @param array
		 *            :数组
		 * @param link
		 *            :连接符
		 * @return
		 */
		public String join(String[] array, String link) {
			if (array == null || array.length == 0) {
				return "";
			}
			StringBuffer result = new StringBuffer(array[0]);
			for (int i = 1; i < array.length; i++) {
				result.append(link + array[i]);
			}
			return result.toString();
		}

		public static void copyTrue(String resourcePath, String targetPath,
				Context ctx) {
			copy(resourcePath, targetPath, true, ctx);
		}

		public static void copyFalse(String resourcePath, String targetPath,
				Context ctx) {
			copy(resourcePath, targetPath, false, ctx);
		}
	}

	public class IContextImpl implements IContext {
		/**
		 * 将数组转换成字符串
		 * 
		 * @param <T>:注意泛型规范
		 * 
		 * @param arrays
		 *            :字符串类型,主要是字符串类型
		 * @return:元素列表
		 */
		public static <T> String convertString(T[] arrays) {
			return convertString(arrays, ",");
		}

		/**
		 * 将数组转换成字符串
		 * 
		 * @param <T>:注意泛型规范
		 * 
		 * @param arrays
		 *            :字符串类型,主要是字符串类型
		 * @return:元素列表
		 */
		public static <T> String convertString(T[] arrays, String flag) {
			return (Arrays.toString(arrays).replaceAll("\\[|\\]|\\s+", "")
					.replaceAll("[,]", flag == null ? "," : flag));
		}

		/**
		 * 验证字符串类型对象是否为空
		 * 
		 * @param msg
		 * @return
		 */
		public static boolean isEmpty(String msg) {
			return msg == null || msg.trim().length() <= 0;
		}

		public static boolean isEmpty$Null(String msg) {
			return msg == null || msg.trim().length() <= 0
					|| msg.equalsIgnoreCase("null");
		}

		public static boolean isNotEmpty$Null(String msg) {
			return isEmpty$Null(msg) == false;
		}

		/**
		 * 验证任何类型对象是否为空,包括基本数据类型
		 * 
		 * @param msg
		 * @return
		 */
		public static <T> boolean isEmpty(T msg) {
			return isEmpty(msg, false);
		}

		/**
		 * 验证任何类型对象是否为空,包括基本数据类型
		 * 
		 * @param msg
		 * @return
		 */
		public static <T> boolean isEmptyAndNull(T msg) {
			return isEmpty(msg, true);
		}

		/**
		 * 验证任何类型对象是否非空,包括基本数据类型
		 * 
		 * @param msg
		 * @return
		 */
		public static <T> boolean isNotEmpty(T msg) {
			return isEmpty(msg, true) == false;
		}

		/**
		 * 取得一个字符串的值,如果为空的话则设定默认值
		 * 
		 * @param msg
		 * @param defaultValue
		 * @return
		 */
		public static String getValue(String msg, String defaultValue) {
			String value = STRING$EMPTY;
			if (TextUtils.isEmpty(msg) == false) {
				value = msg.trim();
			} else {
				value = defaultValue;
			}
			return value;
		}

		public static String getValue(String msg) {
			String value = STRING$EMPTY;
			if (isEmpty(msg) == false) {
				value = msg.trim();
			}
			return value;
		}

		public static String getValue(Object msg) {
			String value = STRING$EMPTY;
			if (isEmpty(msg) == false) {
				value = msg.toString().trim();
			}
			return value;
		}

		public static <T extends BigDecimal> int getValueInt(Object msg) {
			int value = 0;
			if (isNotEmpty(msg)) {
				if (msg instanceof Integer) {
					value = Integer.parseInt(msg.toString());
				}
			}
			return value;
		}

		/**
		 * 验证任何类型是否空指针,及empty空间的方法
		 * 
		 * @param msg
		 * @param isEmpty
		 * @return
		 */
		public static <T> boolean isEmpty(T msg, boolean isEmpty) {
			if (msg == null) {
				return msg == null;
			} else if (msg instanceof String) {
				return isEmpty(msg.toString());
			} else if (msg.getClass().isArray()) {
				return isEmpty ? (msg == null || Array.getLength(msg) <= 0)
						: msg == null;
			} else if (msg instanceof Collection<?>) {
				Collection<?> c = (Collection<?>) msg;
				return isEmpty ? (msg == null || c.isEmpty()) : msg == null;
			} else if (msg instanceof Map<?, ?>) {
				Map<?, ?> c = (Map<?, ?>) msg;
				return isEmpty ? (msg == null || c.isEmpty()) : msg == null;
			} else {
				return msg == null;
			}
		}

		/**
		 * 根据表达式验证数据类型
		 * 
		 * @deprecated
		 * @param msg
		 * @param isEmpty
		 * @return
		 */
		public static <T> boolean isNotEmpty(T msg, boolean isEmpty, String reg) {
			return isEmpty(msg.toString(), false)
					&& msg.toString().matches(reg);
		}
	}
}
