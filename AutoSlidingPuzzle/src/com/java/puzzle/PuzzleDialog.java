package com.java.puzzle;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;

public class PuzzleDialog extends JDialog {
	String filePath = null;
	private final JPanel contentPanel = new JPanel();

	public PuzzleDialog(PuzzleData puzzleData,JFrame parent) {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		parent.setEnabled(false);
		setResizable(false);	
		setBounds(100, 100, 270, 190);
		getContentPane().setLayout(null);
		contentPanel.setBounds(12, 10, 269, 165);
		contentPanel.setBackground(SystemColor.control);
		contentPanel.setBorder(
				new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(57, 58, 53, 19);
		contentPanel.add(spinner);
		spinner.setModel(new SpinnerNumberModel(3, 3, 99, 1));
		spinner.setFont(new Font("굴림", Font.PLAIN, 15));

		ButtonGroup btnGroup = new ButtonGroup();
		
		JRadioButton rdbtnHard = new JRadioButton("hard");
		rdbtnHard.setBounds(189, 29, 51, 23);
		contentPanel.add(rdbtnHard);

		JRadioButton rdbtnNomarl = new JRadioButton("nomarl");
		rdbtnNomarl.setBounds(114, 29, 65, 23);
		contentPanel.add(rdbtnNomarl);

		JRadioButton rdbtnEasy = new JRadioButton("easy");
		rdbtnEasy.setSelected(true);
		rdbtnEasy.setBounds(57, 29, 53, 23);
		contentPanel.add(rdbtnEasy);
		
		btnGroup.add(rdbtnEasy);
		btnGroup.add(rdbtnNomarl);
		btnGroup.add(rdbtnHard);

		JLabel lblNewLabel = new JLabel("\uD06C\uAE30");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(0, 61, 57, 15);
		contentPanel.add(lblNewLabel);

		JLabel label = new JLabel("\uB09C\uC774\uB3C4");
		label.setFont(new Font("굴림", Font.PLAIN, 15));
		label.setBounds(0, 33, 57, 15);
		contentPanel.add(label);

		JLabel label_1 = new JLabel("\uC774\uBBF8\uC9C0");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(0, 4, 57, 15);
		contentPanel.add(label_1);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(57, 4, 150, 15);
		contentPanel.add(lblNewLabel_1);

		JButton loadButton = new JButton("...");
		loadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser;

				chooser = new JFileChooser(); // 파일 다이얼로그 생성
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG image","jpg");
				chooser.setFileFilter(filter);

				// 파일 다이얼로그 출력
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) { // 사용자가 창을 강제로 닫았거나 취소 버튼을 누른 경우
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}

				// 사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
				filePath = chooser.getSelectedFile().getPath(); // 파일 경로명을 알아온다.
				lblNewLabel_1.setText(filePath);

			}
		});
		loadButton.setBounds(207, 0, 33, 23);
		contentPanel.add(loadButton);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 100, 240, 40);
			contentPanel.add(buttonPane);
			{
				JButton okButton = new JButton("\uD655\uC778");
				okButton.setBounds(91, 17, 71, 23);
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (filePath == null) {
							JOptionPane.showMessageDialog(null, "이미지를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
						}else {
							try {
								puzzleData.image = ImageIO.read(new File(lblNewLabel_1.getText()));
							} catch (IOException e1) {
								// TODO 자동 생성된 catch 블록
								e1.printStackTrace();
							}
							puzzleData.beta = Algorithms.fillData((int) spinner.getValue());
							puzzleData.alpha = Algorithms.fillData((int) spinner.getValue());
							
							if(rdbtnEasy.isSelected()) {
								Algorithms.shuffle(puzzleData.beta,puzzleData.beta.length*puzzleData.beta.length*2);
							}else if(rdbtnNomarl.isSelected()) {
								Algorithms.shuffle(puzzleData.beta,puzzleData.beta.length*puzzleData.beta.length*8);
							}else if(rdbtnHard.isSelected()) {
								Algorithms.shuffle(puzzleData.beta,puzzleData.beta.length*puzzleData.beta.length*32);
							}
							parent.setSize(puzzleData.image.getWidth()+26, puzzleData.image.getHeight()+200);
							parent.setEnabled(true);
							setVisible(false);
						}
						
					}
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\uCDE8\uC18C");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						parent.setEnabled(true);
						setVisible(false);
					}
				});
				cancelButton.setBounds(169, 17, 71, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
