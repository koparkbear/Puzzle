package com.java.puzzle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {
	PuzzleData puzzleData;
	public int width;
	public int height;
	private int image_width;
	private int image_height;
	private double ratio;
	private JFrame parent;

	public PuzzlePanel(PuzzleData puzzleData,JFrame parent) {
		this.puzzleData = puzzleData;

		width = puzzleData.getImage().getWidth();
		height = puzzleData.getImage().getHeight();

		image_width = puzzleData.getImage().getWidth() / puzzleData.alpha[0].length;
		image_height = puzzleData.getImage().getHeight() / puzzleData.alpha.length;

		ratio = 1;
		width = (int) (width / ratio);
		height = (int) (height / ratio);
		image_width = (int) (image_width / ratio);
		image_height = (int) (image_height / ratio);

		this.parent = parent;
	}

	@Override
	protected void paintComponent(Graphics g) {
		width = puzzleData.getImage().getWidth();
		height = puzzleData.getImage().getHeight();

		image_width = puzzleData.getImage().getWidth() / puzzleData.alpha[0].length;
		image_height = puzzleData.getImage().getHeight() / puzzleData.alpha.length;

		ratio = 1;

		width = (int) (width / ratio);
		height = (int) (height / ratio);
		image_width = (int) (image_width / ratio);
		image_height = (int) (image_height / ratio);

		super.paintComponent(g);
		g.setColor(new Color(240, 240, 240));
		for (int i = 0; i < puzzleData.alpha[0].length; i++) {
			for (int j = 0; j < puzzleData.alpha.length; j++) {
				if (puzzleData.beta[i][j] != 0)
					g.drawImage(puzzleData.getImage(), j * image_width, i * image_height,
							(j * image_width + image_width), (i * image_height + image_height),
							(Algorithms.nWidth(puzzleData.alpha, puzzleData.beta[i][j]) * image_width),
							Algorithms.nHeight(puzzleData.alpha, puzzleData.beta[i][j]) * image_height,
							(Algorithms.nWidth(puzzleData.alpha, puzzleData.beta[i][j]) * image_width + image_width),
							(Algorithms.nHeight(puzzleData.alpha, puzzleData.beta[i][j]) * image_height + image_height),
							null);

			}
		}
		Graphics2D g2=(Graphics2D)g;
		g2.setStroke((Stroke) new BasicStroke(2,BasicStroke.CAP_SQUARE,0));
		//g2.setColor(new Color(0,0,0));
		g2.drawRect(1, 1, puzzleData.image.getWidth()-2, puzzleData.image.getHeight()-2);
		//g2.setColor(new Color(0, 0, 0));
		for (int i = 0; i < puzzleData.alpha[0].length; i++) {
			for (int j = 0; j < puzzleData.alpha.length; j++) {
				g2.drawRect(j*image_width, i*image_height, j*image_width+image_width, i*image_height+image_height);
			}
		}
		this.setBounds(10, 10, this.width, this.height);
		parent.setSize(width + 26, height + 74);
	}

}
