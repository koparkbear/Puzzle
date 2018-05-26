package com.java.puzzle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

class Algorithms {

	private static LinkedList<Node> sons(Node parent, Node objective, LinkedList<Node> visited) {

		LinkedList<Node> sons = new LinkedList<Node>();
		int[][] movements = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
		for (int k = 0; k < 4; k++) {
			int vertical = parent.zero_height + movements[k][0];
			int horizontal = parent.zero_width + movements[k][1];

			if (vertical >= 0 && vertical < objective.matrix.length && horizontal >= 0
					&& horizontal < objective.matrix[0].length) {
				Node son = Node.son(parent);

				son.matrix[son.zero_height][son.zero_width] = son.matrix[vertical][horizontal];
				son.matrix[vertical][horizontal] = 0;
				son.zero_height = vertical;
				son.zero_width = horizontal;

				boolean flag = true;
				for (Node test : visited) {
					if (Node.compare(son, test, objective.matrix.length, objective.matrix[0].length)) {
						flag = false;
						break;
					}
				}
				if (flag) {
					Node.score(son, objective);
					visited.addFirst(son);
					sons.addFirst(son);
				}
			}
		}
		return sons;
	}

	public static void greedy(Node source, Node objective, ArrayList<Node> result) {
		PriorityQueue<Node> queue = new PriorityQueue<>();
		LinkedList<Node> visited = new LinkedList<>();
		queue.add(source);
		Node alfa;
		do {
			alfa = queue.poll();
			if (Node.compare(alfa, objective, source.matrix.length, source.matrix[0].length)) {
				break;
			}
			queue.addAll(sons(alfa, objective, visited));
		} while (!queue.isEmpty());

		Node.returnArray(alfa, result);
	}

	public static void shuffle(int[][] matrix, int n) {
		Random random = new Random();

		for (int count = 0; count < n;) {
			for (int i = 0; i < matrix.length; i++)
				for (int j = 0; j < matrix[0].length; j++)
					if (matrix[i][j] == 0) {
						int r = random.nextInt(4);
						if (r == 0 && j > 0) {
							matrix[i][j] = new Integer(matrix[i][j - 1]);
							matrix[i][j - 1] = 0;
							count++;
						}
						if (r == 1 && j < matrix.length - 1) {
							matrix[i][j] = new Integer(matrix[i][j + 1]);
							matrix[i][j + 1] = 0;
							count++;
						}
						if (r == 2 && i > 0) {
							matrix[i][j] = new Integer(matrix[i - 1][j]);
							matrix[i - 1][j] = 0;
							count++;
						}
						if (r == 3 && i < matrix.length - 1) {
							matrix[i][j] = new Integer(matrix[i + 1][j]);
							matrix[i + 1][j] = 0;
							count++;
						}
					}

		}
	}

	public static int[][] fillData(int size) {
		int[][] martrix = new int[size][size];

		for (int i = 0, count = 1; i < size; i++) {
			for (int j = 0; j < size; j++, count++) {
				martrix[i][j] = count;
				if (count == size * size) {
					martrix[i][j] = 0;
				}
			}
		}
		return martrix;

	}

	public static int zeroHeight(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					return i;
				}
			}
		}
		return 0;
	}

	public static int zeroWidth(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					return j;
				}
			}
		}
		return 0;
	}
	public static int nHeight(int[][] matrix,int n) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == n) {
					return i;
				}
			}
		}
		return 0;
	}

	public static int nWidth(int[][] matrix,int n) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == n) {
					return j;
				}
			}
		}
		return 0;
	}

}
