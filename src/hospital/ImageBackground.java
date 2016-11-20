package hospital;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;

public class ImageBackground {
	
	public static void main(String[] args) {
		
		JFrame F = new JFrame("NameOfTheHospital");
		
		try{
				F.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:/Users/CnX/Desktop/HospitalBackground/Bild1.jpg")))));
				
				
		}catch(IOException e)
		{
					System.out.println("Image does not exists");
		}
		
		F.setResizable(false);
		F.pack();
		F.setVisible(true);
		
	}

}
