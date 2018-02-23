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
	 * �����ļ�����,ĩβ������������"_number"����
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
	 * �ж��ļ���+��Ӧ����,���ڷ���Fileʵ��,���߷���null
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
	 * ����ɾ�����ļ���Ϣ,û�з��ؿ�
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
	 * ɾ��Step
	 */
	public static String deleteStep(String name) {
		String info = delete(name, "step");
		return info;
	}

	/**
	 * ɾ��Deque
	 */
	public static String deleteDeque(String name) {
		String info = delete(name, "deque");
		return info;
	}

	/**
	 * �������ж�Ӧ�������ļ�
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
	 * ������е��ļ���
	 */
	private static void print(File[] files) {
		if (files == null) {
			System.out.println("��");
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
	 * �������Щstep
	 */
	public static void findStep() {
		File[] files = findFile("step");
		System.out.println("��ȥStep��¼:");
		print(files);
	}

	/**
	 * ���������Щdeque
	 */
	public static void findDeque() {
		File[] files = findFile("deque");
		System.out.println("��ȥDeque��¼");
		print(files);
	}

	/**
	 * ����Deque,
	 */
	public static Deque<String> loadDeque(String name) {
		File file = exit(name, "deque");
		if (file == null) {
			System.out.println("IO��δ�ҵ��ļ�");
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
	 * ����back,�������ļ���
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
			System.out.println("IO��������");
			System.out.println(e.getMessage());
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return null;
	}

	/**
	 * ���浱ǰ��,���ر����ļ�������
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
			System.out.println("���浱ǰ����ʧ��!");
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
	 * ��ȡһ������
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
			System.out.println("��ȡʧ��!");
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
		 * �������л�----ͼƬ�������л�
		 */
		// Step step = new Step();
		// try {
		// System.out.println("�����ļ���:"+saveStep(step));
		// } catch (Exception e) {
		// System.out.println("ʧ��");
		// e.printStackTrace();
		// }
		/*
		 * �����ܷ��ȡ
		 */
		// Scanner scan = new Scanner(System.in);
		// System.out.println("��������:");
		// String input = scan.nextLine();
		// try {
		// Step step = loadStep(input);
		// System.out.println(step);
		// } catch (Exception e) {
		// System.out.println("ʧ��");
		// e.printStackTrace();
		// }
	}
}
