package com.quick.es.utils;

public class ChinaCi {
	//单词短语
	private final static String[] wordListOne = {"孤独", "自由", "迷惘", "坚强", "绝望", "青春", "迷茫", "光明", "理想", "荒谬"};
	private final static String[] wordListTwo = {"生命", "路", "夜", "天空", "星空", "孩子", "雨", "石头", "鸟", "瞬间", "桥"};
	private final static String[] wordListX = {"正在", "已经", "一直", "无法"};
	private final static String[] wordListThree = {"爱着", "碎灭", "哭泣", "死去", "飞翔", "梦想", "祈祷", "离去", "再见", "埋葬"};

	public static void main(String[] args) {
		for (int i = 0; i <5; i++) {
			System.out.println(generateCi(3));
		}
	}

	public static String generateCi(int clos) {
		int oneLength = wordListOne.length;
		int twoLength = wordListTwo.length;
		int threeLength = wordListThree.length;
		int xLength = wordListX.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < clos; i++) {
			int rand1 = (int) (Math.random() * oneLength);
			int rand2 = (int) (Math.random() * twoLength);
			int rand3 = (int) (Math.random() * threeLength);
			int randx = (int) (Math.random() * xLength);
			sb.append(wordListOne[rand1]).append("的").append(wordListTwo[rand2]).append(wordListX[randx])
					.append(wordListThree[rand3]).append(" ");
		}
		return sb.toString();
	}
}