package hospital.helper;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;

public class SearchPanel extends JPanel {

	private HashSet<SearchListener> listeners = new HashSet<SearchListener>();
	private JTextField textField;

	public SearchPanel() {
		setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		add(textField);
		textField.setColumns(10);

		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				search(textField.getText().trim());
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		JButton btnSubmit = new JButton("Search");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search(textField.getText().trim());
			}
		});
		add(btnSubmit, BorderLayout.EAST);
	}

	public void addSearchListener(SearchListener sl) {
		listeners.add(sl);
	}

	public void search(String s) {
		for (SearchListener sl : listeners) {
			sl.onSearch(s);
		}
	}

}
