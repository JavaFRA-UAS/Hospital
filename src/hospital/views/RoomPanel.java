package hospital.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import hospital.RefreshListener;
import hospital.VitalsSimulation;
import hospital.helper.CustomHeaderRenderer;
import hospital.helper.RefreshableWindow;
import hospital.helper.SearchListener;
import hospital.helper.SearchPanel;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Room;
import hospital.tablemodel.RoomTableModel;
import hospital.window.EditNurseWindow;
import hospital.window.EditRoomWindow;
import hospital.window.MainWindow;
import hospital.window.NewRoomWindow;

public class RoomPanel extends JPanel implements RefreshListener, SearchListener {

	final MainWindow parentWindow;
	final RoomTableModel tableModel;
	final JTable table;
	boolean isInsideMouseEvent;
	boolean hasOpenPopupMenu;

	public RoomPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;
		VitalsSimulation.addRefreshListener(this);
		final RoomPanel that = this;

		tableModel = new RoomTableModel(parentWindow.getCurrentUser());
		table = new JTable(tableModel) {

			private static final long serialVersionUID = 1L;
			DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();

			{
				renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
			}

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				// return column > 0 ? renderRight : renderLeft;
				return renderCenter;
			}
		};
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(new CustomHeaderRenderer(SwingConstants.CENTER));
		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				that.isInsideMouseEvent = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());

				if (r >= 0) {
					final List<Room> rooms = tableModel.getData();
					final Room room = r < rooms.size() ? rooms.get(r) : null;
					if (room != null) {
						JPopupMenu popup = new JPopupMenu();

						JMenuItem item = new JMenuItem("Edit");
						item.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								EditRoomWindow w = new EditRoomWindow(parentWindow);
								w.setRoom(room);
								w.setVisible(true);
							}
						});
						popup.add(item);

						item = new JMenuItem("Delete");
						item.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								room.delete();
								Room.getFactory().save(room);
								parentWindow.refresh();
							}
						});
						popup.add(item);

						popup.addPopupMenuListener(new PopupMenuListener() {
							public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
								that.hasOpenPopupMenu = false;
							}

							public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
								that.hasOpenPopupMenu = false;
							}

							public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
								that.hasOpenPopupMenu = true;
							}
						});
						popup.show(e.getComponent(), e.getX(), e.getY());
					}
				}

				that.isInsideMouseEvent = false;
			}
		});

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
		this.add(new JScrollPane(table), gbc_listRooms);

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

		SearchPanel searchPanel = new SearchPanel();
		searchPanel.addSearchListener(this);
		GridBagConstraints gbc_searchPanel = new GridBagConstraints();
		gbc_searchPanel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_searchPanel.gridx = 0;
		gbc_searchPanel.gridy = 1;
		this.add(searchPanel, gbc_searchPanel);

	}

	void fillList() {
		if (!this.hasOpenPopupMenu && !this.isInsideMouseEvent) {
			tableModel.fireTableDataChanged();
		}
	}

	public synchronized void refresh() {
		fillList();
	}

	@Override
	public void onSearch(String text) {
		tableModel.onSearch(text);
		refresh();
	}
}
