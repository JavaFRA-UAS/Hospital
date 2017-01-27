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
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.tablemodel.NurseTableModel;
import hospital.tablemodel.PatientTableModel;
import hospital.window.EditNurseWindow;
import hospital.window.EditPatientWindow;
import hospital.window.MainWindow;
import hospital.window.NewNurseWindow;

public class NursePanel extends JPanel implements RefreshListener, SearchListener {

	final MainWindow parentWindow;
	final NurseTableModel tableModel;
	final JTable table;
	boolean isInsideMouseEvent;
	boolean hasOpenPopupMenu;

	public NursePanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;
		VitalsSimulation.addRefreshListener(this);
		final NursePanel that = this;

		tableModel = new NurseTableModel();
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
					final List<Nurse> nurses = tableModel.getData();
					final Nurse n = r < nurses.size() ? nurses.get(r) : null;
					if (n != null) {
						JPopupMenu popup = new JPopupMenu();

						JMenuItem item = new JMenuItem("Edit");
						item.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								EditNurseWindow w = new EditNurseWindow(parentWindow);
								w.setNurse(n);
								w.setVisible(true);
							}
						});
						popup.add(item);

						item = new JMenuItem("Delete");
						item.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								n.delete();
								Nurse.getFactory().save(n);
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

		GridBagLayout gbl_panelNurses = new GridBagLayout();
		gbl_panelNurses.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelNurses.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelNurses.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelNurses.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_panelNurses);

		GridBagConstraints gbc_listNurses = new GridBagConstraints();
		gbc_listNurses.gridwidth = 2;
		gbc_listNurses.fill = GridBagConstraints.BOTH;
		gbc_listNurses.gridx = 0;
		gbc_listNurses.gridy = 0;
		this.add(new JScrollPane(table), gbc_listNurses);

		JButton btnNew = new JButton("New Nurse");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewNurseWindow w = new NewNurseWindow(parentWindow);
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
