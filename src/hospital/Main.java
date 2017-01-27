package hospital;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.*;

import javax.swing.*;

import hospital.database.DatabaseConnection;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.window.LoginWindow;

public class Main {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable ex) {
				Log.showException(ex);
			}
		});

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {
			// ignore
		}

		System.out.println("initialize...");
		try {
			DatabaseConnection.initialize();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Datenbank konnte nicht geöffnet werden: \n\n" + ex);
			return;
		}

		System.out.println("start simulation...");
		VitalsSimulation.initialize();

		System.out.println("open login window...");
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LoginWindow mw = new LoginWindow();
				mw.setVisible(true);
			}
		});
	}
}
