package com.java.puzzle;
import java.util.ArrayList;
import java.util.Random;

class Node implements Comparable<Node> {
	int[][] matrix;
	Node parent;
	int zero_height, zero_width;
	int depth;
	int score;

	public Node(int height, int width) {
		matrix = new int[height][width];
		parent = null;
		depth = 0;
	}

	public Node(int[][] arr) {
		matrix = new int[arr.length][arr[0].length];
		parent = null;
		depth = 0;

		for (int j = 0; j < arr.length; j++) {
			for (int i = 0; i < arr[0].length; i++) {
				this.matrix[j][i] = arr[j][i];
				if (this.matrix[j][i] == 0) {
					this.zero_height = j;
					this.zero_width = i;
				}
			}
		}
	}

	public static Node scan(Node alfa, int[][] arr, int depth) {
		for (int j = 0; j < arr.length; j++) {
			for (int i = 0; i < arr[0].length; i++) {
				alfa.matrix[j][i] = arr[j][i];
				if (alfa.matrix[j][i] == 0) {
					alfa.zero_height = j;
					alfa.zero_width = i;
				}
			}
		}
		return alfa;
	}

	public static Node clone(Node original) {
		Node clone = new Node(original.matrix.length, original.matrix[0].length);
		for (int j = 0; j < original.matrix.length; j++) {
			for (int i = 0; i < original.matrix[0].length; i++) {
				clone.matrix[j][i] = original.matrix[j][i];
			}
		}
		clone.zero_height = original.zero_height;
		clone.zero_width = original.zero_width;
		clone.parent = original.parent;
		clone.depth = original.depth;
		return clone;
	}

	public static Node son(Node parent) {

		Node son = clone(parent);
		son.depth = son.depth + 1;
		son.parent = parent;
		return son;
	}

	public static void print(Node node) {
		for (int j = 0; j < node.matrix.length; j++) {
			System.out.print("[");
			for (int i = 0; i < node.matrix.length; i++) {
				System.out.print(node.matrix[j][i] + ",");
			}
			System.out.print("] ");
		}
		System.out.println();
	}

	public static void returnArray(Node objective, ArrayList<Node> result) {
		if (objective.parent != null) {
			returnArray(objective.parent, result);
			result.add(objective);
		}
	}

	public static boolean compare(Node node1, Node node2, int height, int width) {

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (node1.matrix[j][i] != node2.matrix[j][i]) {
					return false;
				}
			}
		}
		return true;
	}

	public int compareTo(Node beta) {
		if (this.score < beta.score) {
			return -1;
		} else if (this.score == beta.score) {
			return 0;
		} else
			return 1;
	}


	public static void score(Node alfa, Node objective) {
		for (int j = 0; j < alfa.matrix.length; j++) {
			for (int i = 0; i < alfa.matrix[0].length; i++) {
				boolean flag = true;
				int aux1 = 0, aux2 = 0;
				for (int m = 0; m < alfa.matrix.length && flag; m++) {
					for (int n = 0; n < alfa.matrix[0].length; n++) {
						if (alfa.matrix[j][i] == objective.matrix[m][n]) {
							flag = false;
							aux1 = m;
							aux2 = n;
							break;
						}
					}
				}
				alfa.score = alfa.score + Math.abs(j - aux1) + Math.abs(i - aux2); //수평상의 거리 + 수직상의 거리
			}
		}
	}

}
