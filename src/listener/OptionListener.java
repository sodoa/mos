package listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.IdentifyDialog;
import frame.IdentifyHistoryPanel;
import frame.LoginFrame;
import frame.MainFrame;
import frame.RegistHistoryPanel;
import frame.RegisterDialog;

public class OptionListener implements ActionListener {
	JButton jbtScanIdentify;
	JButton jbtIdentifyHistory;
	JButton jbtRegistHistory;
	JButton jbtRegist;
	JButton jbtLock;
	JButton jbtExit;

	public OptionListener(JButton jbtScanIdentify, JButton jbtIdentifyHistory,
			JButton jbtRegistHistory, JButton jbtRegist, JButton jbtLock,
			JButton jbtExit) {
		super();
		this.jbtScanIdentify = jbtScanIdentify;
		this.jbtIdentifyHistory = jbtIdentifyHistory;
		this.jbtRegistHistory = jbtRegistHistory;
		this.jbtRegist = jbtRegist;
		this.jbtLock = jbtLock;
		this.jbtExit = jbtExit;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtScanIdentify) {
			IdentifyDialog.instance().open();
		} else if (e.getSource() == jbtIdentifyHistory) {
			if (MainFrame.instance().centerJPanel != null) {
				MainFrame.instance().getContentPane()
						.remove(MainFrame.instance().centerJPanel);
				;
			}
			MainFrame.instance().centerJPanel = IdentifyHistoryPanel.instance();
			MainFrame.instance().getContentPane()
					.add(IdentifyHistoryPanel.instance(), BorderLayout.CENTER);

			IdentifyHistoryPanel.instance().refresh();
		} else if (e.getSource() == jbtRegistHistory) {
			if (MainFrame.instance().centerJPanel != null) {
				MainFrame.instance().getContentPane()
						.remove(MainFrame.instance().centerJPanel);
				;
			}
			MainFrame.instance().centerJPanel = RegistHistoryPanel.instance();
			MainFrame.instance().getContentPane()
					.add(RegistHistoryPanel.instance(), BorderLayout.CENTER);
			RegistHistoryPanel.instance().refresh();

		} else if (e.getSource() == jbtRegist) {
			RegisterDialog.instance().open();
		} else if (e.getSource() == jbtLock) {
			LoginFrame.instance().open();
			MainFrame.instance().dispose();
		} else if (e.getSource() == jbtExit) {
			System.exit(0);
		}
	}
}
