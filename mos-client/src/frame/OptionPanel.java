package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import listener.OptionListener;

public class OptionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1613590806361203632L;
	private static OptionPanel optionPanel;
	
	static public OptionPanel instance(){
		if(optionPanel == null)
			optionPanel = new OptionPanel();
		return optionPanel;
	}

	public OptionPanel() {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		setSize(1366, 150);
		setOpaque(false);
		optionPanel = this;
		ImageButton jbtScanIdentify = new ImageButton("book");
		ImageButton jbtIdentifyHistory = new ImageButton("take");
		ImageButton jbtRegistHistory = new ImageButton("refresh");
		ImageButton jbtRegist = new ImageButton("config");
		ImageButton jbtLock = new ImageButton("lock");
		ImageButton jbtExit = new ImageButton("exit");

		jbtScanIdentify.setToolTipText("   ������֤��");
		jbtIdentifyHistory.setToolTipText("��������ʷ��");
		jbtRegistHistory.setToolTipText("��ע����ʷ��");
		jbtRegist.setToolTipText("���û�ע�ᡡ");
		jbtLock.setToolTipText("��ע����");
		jbtExit.setToolTipText("���˳���");

		jbtScanIdentify.setPreferredSize(new Dimension(80, 80));
		jbtIdentifyHistory.setPreferredSize(new Dimension(80, 80));
		jbtRegistHistory.setPreferredSize(new Dimension(80, 80));
		jbtRegist.setPreferredSize(new Dimension(80, 80));
		jbtLock.setPreferredSize(new Dimension(80, 80));
		jbtExit.setPreferredSize(new Dimension(80, 80));

		OptionListener optionListener = new OptionListener(jbtScanIdentify,
				jbtIdentifyHistory, jbtRegistHistory, jbtRegist, jbtLock, jbtExit);
		jbtScanIdentify.addActionListener(optionListener);
		jbtIdentifyHistory.addActionListener(optionListener);
		jbtRegistHistory.addActionListener(optionListener);
		jbtRegist.addActionListener(optionListener);
		jbtLock.addActionListener(optionListener);
		jbtExit.addActionListener(optionListener);

		add(jbtScanIdentify);
		add(jbtIdentifyHistory);
		add(jbtRegistHistory);
		add(jbtRegist);
		add(jbtLock);
		add(jbtExit);
	}
}
