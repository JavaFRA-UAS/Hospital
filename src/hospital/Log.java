package hospital;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Log {
	public static void showException(final Throwable ex) {
		ex.printStackTrace();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				JOptionPane.showMessageDialog(null, sw.toString());
			}
		});
	}
}
