package computer;

import java.util.ArrayList;

import board.Board;

public class Computer {
	private int FIND_COUNT;
	private int nowCount;
	private int targetCol;
	
	public Computer() {
		FIND_COUNT = 2;
		nowCount = 0;
		targetCol = 0;
	}
	
	public int getTargetCol() {
		return targetCol;
	}
	
	public void setTargetCol(long m, long o) {
		calculate(m,o);
	}
	
	/**
	 * 空いてるマスから下に4つ以内を探索、それ以外は意味なし
	 * 
	 * 素直に3つ置くと相手が防ぐ前提？
	 * 
	 * 相手が4つそろいそうなところにおく...2つ以上あったら終わり
	 * 
	 * Board class の insert method でクラス変数を変更、そいつを複製してまた検索して的な
	 * 
	 * 
	 * 
	 * */
	private int calculate(long m, long o) {
		nowCount++;
		System.out.println("----- calculate method is called -----");
		System.out.println("nowCount = " + nowCount);
		System.out.println("my board = " + m);
		System.out.println("opp board = " + o);
		System.out.println("");
		long my = m;
		long opp = o;
		ArrayList<Long> list = insertable(my | opp);/**挿入位置の取得*/
		ArrayList<Integer> valueList = new ArrayList<>();/**評価値が一緒の時の列indexのリスト*/
		if (list == null) return 0;
		
		int curr = 0;
		int prev = 0;/**for文内の前回値をとり、最終的な評価値をとる*/
		boolean useValueList = false;
		
		for (int i = 0; i < 7; i++) {
			System.out.println("");
			System.out.println("Insertable list element No." + i + " : bit:" + list.get(i));
			
			if (list.get(i) == 0) {
				System.out.println("Insetable list element is 0. So proceed to next element.");
				continue;
			}
			
			if (i == 0) {
				if (nowCount != FIND_COUNT) {
					System.out.println("-- call another calculate for prev");
					prev = calculate(opp, my | list.get(0));
				}
				else prev = evaluate(my, list.get(0), opp);
				valueList.add(1);
				targetCol = 1;
				/*
				prev = evaluate(my, list.get(0));
				valueList.add(1);
				continue;
				*/
			}else {
				if (nowCount != FIND_COUNT) {
					System.out.println("-- call another calculate for curr");
					curr = calculate(opp, my | list.get(i));
				}
				else curr = evaluate(my, list.get(i), opp);
				/*
				curr = evaluate(my, list.get(i));
				*/
			}

			System.out.println("");
			System.out.println("Depth " + nowCount + " Column " + (i+1) + ": prev = " + prev + ", curr = " + curr);
			
			if (i == 0) continue;
			
			switch (bigger(prev, curr)) {
			case 0:
				System.out.println("prev is bigger");
				break;
			case ~0:
				System.out.println("curr is bigger");
				valueList = null;
				valueList = new ArrayList<>();
				useValueList = false;
				prev = curr;
				targetCol = i + 1;
				break;
			default:
				System.out.println("prev and curr is same");				
				valueList.add(i + 1);
				useValueList = true;
				break;
			}
			
			/**
			if (prev > curr) {
				System.out.println("prev is bigger");
				continue;
			}else if (prev < curr) {
				System.out.println("curr is bigger");
				valueList = null;
				prev = curr;
				result = i + 1;
			}else {
				System.out.println("prev and curr is same");
				valueList = new ArrayList<>();
				valueList.add(i + 1);
			}
			*/
		}
		
		if (nowCount % 2 == 0 && prev == Integer.MAX_VALUE) {
			System.out.println("");
			System.out.println("== WILL MEET A CHECKMATE ==");
			System.out.println("prev or curr is MAX_VALUE, so it return with col No." + targetCol);
			nowCount--;
			return prev > curr ? prev : curr;
		}
		
		if (useValueList) {
			if (prev == Integer.MAX_VALUE) return prev;
			int sz = valueList.size();
			int rnd = (int) (Math.random() * sz);
			System.out.println("result has somevalue, so");
			System.out.println("rnd : " + rnd);
			targetCol = valueList.get(rnd);
		}
		
		System.out.println("calculate result is " + prev);
		System.out.println("result is in col No." + targetCol);
		nowCount--;
		return prev;
	}

	private ArrayList<Long> insertable(long flbd) {
		ArrayList<Long> list = new ArrayList<>();
		for (int i = 1; i <= 7; i++) {
			long num = Board.getInsertValue(i, flbd);
			list.add(num);
		}
		return list;
	}
	
	private int bigger(int num1, int num2) {
		int result = 0;
		if (num1 > num2) result = 0;
		else if (num1 < num2) result = ~0;
		else result = 10;
		
		if (nowCount % 2 == 0) {/**最大値さがし*/
			return result;
		}else {/**最小値さがし*/
			return ~result;
		}
	}
	
	/**
	 * 仮に置いた場合の盤面を引き数に、その盤面の評価値を返す。
	 * 
	 * ・駒が4つそろうなら最大値
	 * ・駒が2つそろうなら立っているbit数     3桁目から
	 * ・駒が3つそろうなら立っているbit数*2　　1桁目から
	 * ・
	 * 
	 * 2c 4
	 * ( 駒数 - 1 ) * そろってる数 + 1 = 全部の数
	 * 2の場合
	 * (2-1) * X + 1 = bitcount
	 * X = (bitcount - 1) / (2 - 1)
	 * 
	 * */
	private int evaluate(long my, long add, long opp) {
		long fl = my | add;
		int result = 0;
		int twoStand = 0;/**置いた駒が作る2駒列の数*/
		int threeStand = 0;/**置いた駒が作る3駒列の数*/
		int twbit = 0;
		int thrbit = 0;
		
		if (Board.checkOcurrBit(fl, 4) > 0) return Integer.MAX_VALUE;
		/*if (Board.checkColumnTwo(my, add, opp)) return Integer.MAX_VALUE;*/

		long threeOr = Board.checkOcurrBit(my, 3, "or");
		long twoOr = Board.checkOcurrBit(fl, 2, "or");
		twbit = Board.checkOcurrBit(fl, 2, false);
		thrbit = Board.checkOcurrBit(fl, 3, false);
		
		if ((twoOr & add) > 0) twoStand = Long.bitCount(twoOr) - twbit;
		if ((threeOr & add) > 0) threeStand = (Long.bitCount(threeOr) - thrbit) / 2;
		
		result = twoStand + threeStand;
		
		return result;
	}
	
}
