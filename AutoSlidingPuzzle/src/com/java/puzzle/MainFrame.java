package com.java.puzzle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

public class MainFrame {
	JFrame mainFrame;
	PuzzleData puzzleData;
	Timer t;
	ArrayList<Node> result = new ArrayList<Node>();
	int count = 0;

	public MainFrame() throws IOException {
		mainFrame = new JFrame("Puzzle");
		mainFrame.setSize(500, 500);
		mainFrame.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		init();

		mainFrame.setVisible(true);
	}

	void init() throws IOException {
		puzzleData = new PuzzleData(Algorithms.fillData(4), Algorithms.fillData(4), java.net.URLDecoder.decode(MainFrame.class.getResource("").getPath() ,"UTF-8")+"image.jpg");
		PuzzlePanel puzzlePanel = new PuzzlePanel(puzzleData, mainFrame);
		puzzlePanel.setBounds(0, 0, 1, 1);
		JMenuBar menuBar = new JMenuBar();
		JMenu puzzleMenu = new JMenu("퍼즐");
		JMenuItem newPuzzleMenu = new JMenuItem("새 퍼즐");
		JMenuItem shufflePuzzleMenu = new JMenuItem("퍼즐 섞기");
		JMenuItem autoPuzzleMenu = new JMenuItem("퍼즐 풀기");
		JMenuItem exitMenu = new JMenuItem("종료");
			
		mainFrame.add(puzzlePanel);
		mainFrame.setJMenuBar(menuBar);
		puzzlePanel.repaint();

		menuBar.add(puzzleMenu);
		puzzleMenu.add(newPuzzleMenu);
		puzzleMenu.add(autoPuzzleMenu);
		puzzleMenu.add(shufflePuzzleMenu);
		puzzleMenu.add(exitMenu);

		t=new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(count < result.size()) {
					puzzleData.beta = result.get(count).matrix;
					count++;
					mainFrame.repaint();
				}else {
					count = 0;
					result.clear();
					t.stop();
				}
				
			}
		});
		
		newPuzzleMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("menu - new puzzle menu");
				PuzzleDialog dialog = new PuzzleDialog(puzzleData, mainFrame);
				dialog.setVisible(true);
			}
		});
		autoPuzzleMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Node beta = new Node(puzzleData.beta);
				Node alpha = new Node(puzzleData.alpha);

				// 시간출력
				System.out.println("menu - auto (greedy)");
				System.out.println(">Greedy algorithms call");
				long start = System.currentTimeMillis();
				Algorithms.greedy(beta, alpha, result);
				long end = System.currentTimeMillis();
				System.out.println(">Progress time: " + (end - start) / 1000.0 + "sec"); // 실행 시간 계산 및 출력
				
				t.start();
			}
		});
		shufflePuzzleMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("menu - shuffle");
				Algorithms.shuffle(puzzleData.beta, 30);
				mainFrame.repaint();
			}
		});
		exitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("menu - exit");
				mainFrame.dispose();
			}
		});
	}

	public static void main(String[] args) throws IOException {
		new MainFrame();
	}

}
