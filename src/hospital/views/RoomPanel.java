package hospital.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;


import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Room;
import hospital.window.EditRoomWindow;
import hospital.window.MainWindow;
import hospital.window.NewRoomWindow;

public class RoomPanel extends JPanel {

	final MainWindow parentWindow;
	final DefaultListModel<Room> listModelRooms;
	final JList<Room> listRooms;

	public RoomPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;

		listModelRooms = new DefaultListModel<Room>();
		listRooms = new JList<Room>();
		listRooms.setModel(listModelRooms);

		GridBagLayout gbl_panelRooms = new GridBagLayout();
		gbl_panelRooms.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelRooms.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelRooms.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelRooms.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_panelRooms);

		GridBagConstraints gbc_listRooms = new GridBagConstraints();
		gbc_listRooms.gridwidth = 2;
		gbc_listRooms.fill = GridBagConstraints.BOTH;
		gbc_listRooms.gridx = 0;
		gbc_listRooms.gridy = 0;
		this.add(listRooms, gbc_listRooms);

		JButton btnNew = new JButton("New Room");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewRoomWindow w = new NewRoomWindow(parentWindow);
				w.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		this.add(btnNew, gbc_btnNew);

		JButton btnDelete = new JButton("Delete Room");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Room p = listRooms.getSelectedValue();
				p.delete();
				Room.getFactory().save(p);
				parentWindow.refresh();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		this.add(btnDelete, gbc_btnDelete);
		listRooms.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				System.out.println(evt.getClickCount());
				if (evt.getClickCount() == 2) {
					EditRoomWindow w = new EditRoomWindow(parentWindow);
					w.setRoom(listRooms.getSelectedValue());
					w.setVisible(true);
				}
			}
		});

	}

	void fillList() {
		Room.getFactory().loadAll();

		listModelRooms.clear();
		for (Room pat : Room.getFactory().list()) {
			System.out.println("load Room: " + pat.toString());
			listModelRooms.addElement(pat);
		}
	}

	public void refresh() {
		fillList();
	}
}
