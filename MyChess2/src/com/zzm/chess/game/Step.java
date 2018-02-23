package com.zzm.chess.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zzm.chess.peice.B;
import com.zzm.chess.peice.C;
import com.zzm.chess.peice.J;
import com.zzm.chess.peice.M;
import com.zzm.chess.peice.P;
import com.zzm.chess.peice.Piece;
import com.zzm.chess.peice.S;
import com.zzm.chess.peice.X;

public class Step implements Serializable {
	private static final long serialVersionUID = 1L;
	public transient static final int START = 0;
	public transient static final int RUNNING = START + 1;
	public transient static final int GAMEOVER = RUNNING + 1;
	public transient static Map<String, Piece> pieceMap = new HashMap<String, Piece>();
	/**
	 * ���������ӵķֲ�״̬
	 */
	private transient int[][] flags;
	/**
	 * key:"/"ʮλ��ʾ��,"%"��λ��ʾ�� value:��Ӧ������
	 */
	private Map<Integer, Piece> map = new HashMap<Integer, Piece>();
	private boolean willOver;// �����ж�
	private boolean redMove;// �췽���ж�
	private int state;
	private int x, y;// ��¼��ʾ��������λ��
	private transient Piece nowPiece;// ѡ�е�����
	static {
		pieceMap.put("C1", new C(Game.BLACK));
		pieceMap.put("C2", new C(Game.RED));
		pieceMap.put("M1", new M(Game.BLACK));
		pieceMap.put("M2", new M(Game.RED));
		pieceMap.put("X1", new X(Game.BLACK));
		pieceMap.put("X2", new X(Game.RED));
		pieceMap.put("S1", new S(Game.BLACK));
		pieceMap.put("S2", new S(Game.RED));
		pieceMap.put("J1", new J(Game.BLACK));
		pieceMap.put("J2", new J(Game.RED));
		pieceMap.put("P1", new P(Game.BLACK));
		pieceMap.put("P2", new P(Game.RED));
		pieceMap.put("B1", new B(Game.BLACK));
		pieceMap.put("B2", new B(Game.RED));
	}

	public Step() {
		Game.startDirection();
	}

	public Step(String str) throws Exception {
		try {
			Step step = IOmanager.loadStep(str);
			initStep(this, step);
		} catch (Exception e) {
			System.out.println("Step���췽�������쳣!");
			throw e;
		}
	}

	public static void initStep(Step st, Step step) {
		st.map = step.map;
		st.redMove = step.redMove;
		st.willOver = step.willOver;
		st.state = step.state;
		st.x = step.x;
		st.y = step.y;
	}

	public int[][] getFlags() {
		return flags;
	}

	public boolean isRedMove() {
		return redMove;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * �ж�ѡ�������Ƿ�Ϸ�. �Ǿ͸���ѡ�����(Ҫ�ƶ�������,�ͷ���λ��)
	 */
	public void selectPiece(int x, int y) {
		int str = x * 10 + y;
		if (map.get(str) != null) {
			boolean flag = false;
			Piece p = map.get(str);
			if (redMove && p.getColor() == Game.RED) {
				flag = true;
			}
			if (!redMove && p.getColor() == Game.BLACK) {
				flag = true;
			}
			if (flag) {
				nowPiece = p;
				this.x = x;
				this.y = y;
				Game.tl.get().getBack().push(this.toString());
				Game.tl.get().getFront().clear();
			}
		}
	}

	/**
	 * �������ӷֲ�����flags����
	 */
	public void upData() {
		flags = new int[10][9];// ��ʼֵȫΪ��
		Set<Entry<Integer, Piece>> c = map.entrySet();
		int key;
		for (Entry<Integer, Piece> e : c) {
			key = e.getKey();
			flags[key / 10][key % 10] = e.getValue().getColor();
		}
	}

	/**
	 * �ƶ�ǰ�������ƶ�����
	 */
	private boolean checkMove(int i, int j) {
		if (nowPiece == null) {
			return false;
		}
		if (nowPiece.getColor() == Game.RED && !redMove) {
			return false;
		}
		if (nowPiece.getColor() == Game.BLACK && redMove) {
			return false;
		}
		if (flags[i][j] == nowPiece.getColor()) {
			return false;
		}
		return true;
	}

	/**
	 * �ƶ��ͳ�����,�Ϸ���ı�����,�������Ƿ���Ϸ�����͸ı��Ƿ񽫾���״̬
	 * 
	 * @param i
	 *            Ҫ�ƶ�����
	 * @param j
	 *            Ҫ�ƶ�����
	 * @return �����Ƿ���Ϸ����
	 */
	public void move(int i, int j) {
		upData();
		if (!checkMove(i, j)) {
			return;
		}
		// ��¼�Ƿ�n���ƶ��ɹ��Ŀ���
		boolean flag = false;
		if (flags[i][j] == 0) {
			flag = nowPiece.run(x, y, i, j);
		} else if (nowPiece instanceof J && map.get(i * 10 + j) instanceof J && betweenNumber(x, y, i, j) == 0) {
			// �Ͻ�����
			flag = true;
		} else {
			flag = nowPiece.eat(x, y, i, j);
		}
		if (flag) {
			moveAction(i * 10 + j);
		}
	}

	/**
	 * �ɹ��ƶ��Ժ�ִ��
	 */
	private void moveAction(int aim) {
		map.remove(x * 10 + y);
		if (map.get(aim) instanceof J) {
			map.put(aim, nowPiece);
			state = GAMEOVER;
			String str = IOmanager.saveDeque(Game.tl.get().getBack(), "");
			System.out.println("��Ϸ����,������Ϸ���Զ�����:" + str);
			Game.gameOverDirection();
			return;
		}
		map.put(aim, nowPiece);
		redMove = !redMove;
		willOver = willOver();
		Game.tl.get().getBack().push(this.toString());
		Game.tl.get().getFront().clear();
	}

	/**
	 * ����Ͻ�λ������
	 */
	public int[] Js() {
		int[] js = new int[2];
		Set<Integer> c = map.keySet();
		int i = 0;
		for (Integer n : c) {
			if (map.get(n) instanceof J) {
				js[i++] = n;
			}
			if (i == 2) {
				break;
			}
		}
		return js;
	}

	/**
	 * �ƶ��ɹ����ж��Ƿ��ܹ�����
	 */
	public boolean willOver() {
		upData();
		int[] js = Js();
		for (int i = 0; i < js.length; i++) {
			for (Entry<Integer, Piece> p : map.entrySet()) {
				if (p.getValue().getColor() != map.get(js[i]).getColor()) {
					int sit = p.getKey();
					if (p.getValue().eat(sit / 10, sit % 10, js[i] / 10, js[i] % 10)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * ������λ�ü����Ӹ���(����һ�����Ϸ���-1)
	 */
	public int betweenNumber(int row, int col, int i, int j) {
		if (row != i && col != j) {
			return -1;
		}
		int max, min, index = 0;
		if (row == i) {
			max = col > j ? col : j;
			min = col > j ? j : col;
			for (min = min + 1; min < max; min++) {
				if (flags[row][min] != 0)
					index++;
			}
		} else {
			max = row > i ? row : i;
			min = row > i ? i : row;
			for (min = min + 1; min < max; min++) {
				if (flags[min][col] != 0) {
					index++;
				}
			}
		}
		return index;
	}

	/**
	 * ����ָ��λ��flags[][]ֵ
	 */
	public int color(int i, int j) {
		return flags[i][j];
	}

	/**
	 * ���������ӵ���Ϣ�ַ�������
	 */
	public static String[] parsePieceStrArr(String stepStr) {
		int index = stepStr.indexOf(",");
		String[] arrs = stepStr.substring(index + 1).split(",");
		return arrs;
	}

	/**
	 * ��������Ϸ״̬ ����λ
	 */
	public static int parseState(String stepStr) {
		return Integer.parseInt(stepStr.substring(2, 3));
	}

	/**
	 * ����������״̬((1:����0:û��)) �ڶ�λ
	 */
	public static int parseWillOver(String stepStr) {
		return Integer.parseInt(stepStr.substring(1, 2));
	}

	/**
	 * ������redMove ��һλ
	 */
	public static int parseRedMove(String stepStr) {
		return Integer.parseInt(stepStr.substring(0, 1));
	}

	/**
	 * ͨ���ַ���������Step����
	 */
	public static Step parseStep(String stepStr) {
		Step step = new Step();
		if (parseRedMove(stepStr) == Game.RED) {
			step.redMove = true;
		} else {
			step.redMove = false;
		}
		if (parseWillOver(stepStr) == 1) {
			step.willOver = true;
		} else {
			step.willOver = false;
		}
		step.state = parseState(stepStr);
		String[] arrs = parsePieceStrArr(stepStr);
		step.x = Integer.parseInt(arrs[0].substring(2, 3));
		step.y = Integer.parseInt(arrs[0].substring(3));
		for (int i = 1; i < arrs.length; i++) {
			step.map.put(Integer.parseInt(arrs[i].substring(2, 4)), pieceMap.get(arrs[i].substring(0, 2)));
		}
		return step;
	}

	/**
	 * �������ݱ���:��һλ��ʾ��˭�ߵ���ɫ,�ڶ�λ�����ж�(1:����0:û��),
	 * ����λ��ʾ��Ϸ״̬,����ÿ��λ��ʾһ��ͼƬ(ǰ��λͼƬ����,����λλ��) ---�ޱ���
	 */
	public String toString() {
		String str = "";
		if (redMove) {
			str += Game.RED;
		} else {
			str += Game.BLACK;
		}
		if (willOver) {
			str += 1;
		} else {
			str += 0;
		}
		str += state;
		str += ",SL" + x + y;
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			str += "," + map.get(key).toString();
			str += key / 10 + "" + key % 10;
		}
		return str;
	}

	/**
	 * �������̵�toString
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new Step());
	}
}
