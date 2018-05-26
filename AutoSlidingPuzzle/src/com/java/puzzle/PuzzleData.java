package com.java.puzzle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PuzzleData {
	int[][] beta;
	int[][] alpha;
	BufferedImage image;
	
	public PuzzleData(int[][] beta, int[][] alpha, String imagePath) throws IOException {
		super();
		this.beta = beta;
		this.alpha = alpha;
		this.image =  ImageIO.read(new File(imagePath));
	}
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	
}
