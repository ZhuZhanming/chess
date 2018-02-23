package com.zzm.chess.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

public class IOmanager {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	/**
	 * 避免文件覆盖,末尾加上文字序列"_number"区分
	 */
	private static File newFile(String name, String type) {
		File file = new File(type);
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(type + File.separator + "." + type);
		int i = 1;
		while (file.exists()) {
			file = new File(type + File.separator + name + "_" + (i++) + "." + type);
		}
		return file;
	}

	/**
	 * 判断文件名+对应类型,存在返回File实例,或者返回null
	 */
	private static File exit(String name, String type) {
		File file = null;
		if (name.endsWith("." + type)) {
			file = new File(type + File.separator + name);
		} else {
			file = new File(type + File.separator + name + "." + type);
		}
		if (file.exists())
			return file;
		return null;
	}

	/**
	 * 返回删除的文件信息,没有返回空
	 */
	private static String delete(String name, String type) {
		File file = exit(name, type);
		if (file == null) {
			return null;
		}
		String info = "name:" + file.getName() + ",length:" + file.length() + "b,time:"
				+ sdf1.format(file.lastModified());
		file.delete();
		return info;
	}

	/**
	 * 删除Step
	 */
	public static String deleteStep(String name) {
		String info = delete(name, "step");
		return info;
	}

	/**
	 * 删除Deque
	 */
	public static String deleteDeque(String name) {
		String info = delete(name, "deque");
		return info;
	}

	/**
	 * 返回所有对应的类型文件
	 */
	private static File[] findFile(String type) {
		File dir = new File(type);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().endsWith("." + type);
				}
			});
			return files;
		}
		return null;
	}

	/**
	 * 输出具有的文件名
	 */
	private static void print(File[] files) {
		if (files == null) {
			System.out.println("无");
		} else {
			for (int i = 0; i < files.length;) {
				System.out.print(files[i++].getName() + "  ");
				if (i % 3 == 0) {
					System.out.println();
				}
			}
			System.out.println();
		}
	}

	/**
	 * 输出有哪些step
	 */
	public static void findStep() {
		File[] files = findFile("step");
		System.out.println("过去Step记录:");
		print(files);
	}

	/**
	 * 输出具有哪些deque
	 */
	public static void findDeque() {
		File[] files = findFile("deque");
		System.out.println("过去Deque记录");
		print(files);
	}

	/**
	 * 加载Deque,
	 */
	public static Deque<String> loadDeque(String name) {
		File file = exit(name, "deque");
		if (file == null) {
			System.out.println("IO中未找到文件");
			return null;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String str = null;
			Deque<String> back = new LinkedList<String>();
			while ((str = br.readLine()) != null) {
				back.addLast(str);
			}
			return back;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 保持back,并返回文件名
	 */
	public static String saveDeque(Deque<String> back, String name) {
		if (back.size() == 0)
			return null;
		if ("".equals(name)) {
			name = sdf.format(new Date());
		}
		File file = newFile(name, "deque");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file, "utf-8");
			while (back.size() > 0) {
				pw.println(back.pop());
			}
			pw.flush();
			return file.getName();
		} catch (Exception e) {
			System.out.println("IO方法出错");
			System.out.println(e.getMessage());
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return null;
	}

	/**
	 * 保存当前步,返回保存文件的名字
	 */
	public static String saveStep(Step step, String name) {// yyMMdd
		if ("".equals(name)) {
			name = sdf.format(new Date());
		}
		File file = newFile(name, "step");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(step);
			return file.getName();
		} catch (Exception e) {
			System.out.println("保存当前棋盘失败!");
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 读取一个步骤
	 */
	public static Step loadStep(String name) throws Exception {
		File file = exit(name, "step");
		if (file == null) {
			return null;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			return (Step) ois.readObject();
		} catch (Exception e) {
			System.out.println("读取失败!");
			e.printStackTrace();
			throw e;
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 测试序列化----图片不能序列化
		 */
		// Step step = new Step();
		// try {
		// System.out.println("保存文件名:"+saveStep(step));
		// } catch (Exception e) {
		// System.out.println("失败");
		// e.printStackTrace();
		// }
		/*
		 * 测试能否读取
		 */
		// Scanner scan = new Scanner(System.in);
		// System.out.println("输入名字:");
		// String input = scan.nextLine();
		// try {
		// Step step = loadStep(input);
		// System.out.println(step);
		// } catch (Exception e) {
		// System.out.println("失败");
		// e.printStackTrace();
		// }
	}
}
