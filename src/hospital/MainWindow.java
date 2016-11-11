package hospital;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	public MainWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(new Dimension(640,480));
		setVisible(true);
	}

}
