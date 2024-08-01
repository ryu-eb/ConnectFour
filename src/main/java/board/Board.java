package board;

import java.util.ArrayList;

public class Board{
	private ArrayList<ArrayList<Integer>> list;
	private long boardNum;
	private final static int MY_NUM = 1;
	private final static int OPP_NUM = 2;
	
	
	/**
	 * コンストラクタ
	 * */
	public Board(){}
	
	public Board(long l) {
		list = new ArrayList<>();
		boardNum = l;
		System.out.println("Before CreateBoard, boardnum : " + boardNum);
		createBoard(MY_NUM);
	}
	
	public Board(long l, String str){
		list = new ArrayList<>();
		boardNum = l;
		System.out.println("Before CreateBoard, boardnum : " + boardNum);
		createBoard(boardNameIntoNum(str));
	}
	
	/**
	 * ゲッター
	 * */
	public ArrayList<ArrayList<Integer>> getBoard(){
		return list;
	}
	
	public ArrayList<Integer> getRow(int i){
		return list.get(i);
	}
	
	public Integer getValue(int i, int j) {
		return list.get(i).get(j);
	}
	
	public long getBoardNum() {
		return boardNum;
	}
	
	/**
	 * メソッド
	 * */
	
	private int boardNameIntoNum(String str) {
		int n = 0;
		switch(str) {
		case "my":
			n = MY_NUM;
			break;
		case "opp":
			n = OPP_NUM;
			break;
		}
		return n;
	}
	
	private void createBoard(int num) {
		
		ArrayList<Integer> row = new ArrayList<>();
		
		for (int i = 41; i >= 0; i--) {
			long mask = powL(2,i);
			if ((boardNum & mask) > 0) row.add(num);
			else row.add(0);
			
			if (i % 7 == 0) {
				list.add(row);
				row = new ArrayList<>();
			}
		}
	}

	public boolean insert(int col, long fullbd) {
		long result = getInsertValue(col,fullbd);
		if (result == 0) {
			return false;
		}
		boardNum = boardNum | result;
		return true;
	}
	
	/**
	 * スタティックメソッド
	 * */
	private static long powL(long root, int idx) {
		if (idx == 0) return 1;
		long result = root;
		for (int i = 1; i < idx; i++) {
			result *= root;
		}
		return result;
	}
	
	public static long getInsertValue(int col, long fullbd) {
		
		long rootmask = powL(2,7 - col);
		long mask = 0;

		/**同列のどの行から値が入ってないか*/
		for (int i = 0; i < 6; i++) {
			
			if (i != 0) mask = rootmask * powL(2, (7 * i));
			else mask = rootmask;

			if ((fullbd & mask) == 0 ) {
				return mask;
			}
		}

		return 0;
	}
	
	public static ArrayList<ArrayList<Integer>> andBoard(Board list, Board other) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			ArrayList<Integer> res = new ArrayList<>();
			for (int j = 0; j < 7; j++) {
				if (list.getValue(i, j) != 0) {
					res.add(list.getValue(i, j));
				}else if (other.getValue(i, j) != 0) {
					res.add(other.getValue(i, j));
				}else {
					res.add(0);
				}
			}
			result.add(res);
		}
		
		return result;
	}
	
	public static long getOrBitBoardNum(Board list, Board other) {
		return (list.getBoardNum() | other.getBoardNum());
	}
	
	public static boolean isTie(long my, long opp) {
		if ( (my | opp) >= (powL(2,42) - 1)) return true;
		else return false;
	}
	
	public static boolean judgeWin(long num) {
		if  (checkOcurrBit(num, 4) > 0) return true;
		else return false;
	}
	
	public static int checkOcurrBit(long num, int ct) {
		return (int) checkOcurrBit(num, ct, true, true, "");
	}
	
	public static long checkOcurrBit(long num, int ct, String andOr) {
		return checkOcurrBit(num, ct, false, false, andOr);
	}
	
	public static int checkOcurrBit(long num, int ct, boolean skip) {
		return (int) checkOcurrBit(num, ct, skip, true, "");
	}
	
	private static long checkOcurrBit(long num, int ct, boolean skip, boolean getBitOc, String andOr) {
		/*System.out.println("-----------checksum---------------");*/
		int cnt = ct - 1;
		long origin = num;
		long mask = num;
		long leftResult = 0;
		long rightResult = 0;
		long upResult = 0;
		long upLeftResult = 0;
		long upRightResult = 0;
		long right = Long.parseLong("111111011111101111110111111011111101111110",2);
		long left = Long.parseLong("011111101111110111111011111101111110111111",2);
		long up = Long.parseLong("0000000111111111111111111111111111111111111111111",2);
		long bitsum = 0;
		long bitsumresult = 0;
		
		/**左への判定*/		
		for (int i = 0; i < cnt; i++) {
			mask = origin & (mask * 2);
			mask = mask & right;
			leftResult = leftResult | mask;
		}
		bitsum = Long.bitCount(mask);
		/*System.out.println("leftResult : " + leftResult);
		System.out.println("bitsum : " + bitsum);*/
		if ((bitsum > 0 && skip)) return bitsum;
		bitsumresult += bitsum;

		/**右への判定*/
		mask = num;
		for (int i = 0; i < cnt; i++) {
			mask = origin & (mask / 2);
			mask = mask & left;
			rightResult = rightResult | mask;
		}
		bitsum = Long.bitCount(mask);
		/*System.out.println("rightResult : " + rightResult);
		System.out.println("bitsum : " + bitsum);*/
		if ((bitsum > 0 && skip)) return bitsum;
		bitsumresult += bitsum;

		/**上への判定*/
		mask = num;
		for (int i = 0; i < cnt; i++) {
			mask = origin & (mask * powL(2, 7));
			mask = mask & up;
			upResult = upResult | mask;
		}
		bitsum = Long.bitCount(mask);
		/*System.out.println("upResult : " + upResult);
		System.out.println("bitsum : " + bitsum);*/
		if ((bitsum > 0 && skip)) return bitsum;
		bitsumresult += bitsum;
		
		/**左ななめ上への判定*/
		mask = num;
		for (int i = 0; i < cnt; i++) {
			mask = origin & (mask * powL(2, 8));
			mask = mask & up & right;
			upLeftResult = upLeftResult | mask;
		}
		bitsum = Long.bitCount(mask);
		/*System.out.println("upLeftResult : " + upLeftResult);
		System.out.println("bitsum : " + bitsum);*/
		if ((bitsum > 0 && skip)) return bitsum;
		bitsumresult += bitsum;
		
		/**右ななめ上への判定*/
		mask = num;
		for (int i = 0; i < cnt; i++) {
			mask = origin & (mask * powL(2, 6));
			mask = mask & up & left;
			upRightResult = upRightResult | mask;
		}
		bitsum = Long.bitCount(mask);
		/*System.out.println("upRightResult : " + upRightResult);
		System.out.println("bitsum : " + bitsum);*/
		if ((bitsum > 0 && skip)) return bitsum;
		bitsumresult += bitsum;

		if (getBitOc) return bitsumresult;
		
		switch (andOr) {
		case "and":
			return leftResult & rightResult & upResult & upLeftResult & upRightResult;
		case "or":
			return leftResult | rightResult | upResult | upLeftResult | upRightResult;
		default:
			return 0;
		}
	}
	

	public static boolean checkColumnTwo(long my, long add, long opp) {
		long mask = 31;
		long root = 6;
		long fl = my | opp;
		long result = 0;
		
		for (int i = 0; i < 3; i++) {
			long times = powL(2,i);
			result = fl & (mask * times);
			if (root * times > result) {
				if ((result | add) != result) return true;
			}
		}
		
		return false;
	}
}
