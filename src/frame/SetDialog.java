package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import request.HttpHelper;
import config.ConfigUtils;
import constant.Constants;
import listener.AddFoodListener;
import dao.FoodDao;

public class SetDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7928002009191943965L;
	private static SetDialog instance;
	private JTextField jtfIp, jtfPort;

	public static SetDialog instance() {
		if (instance == null)
			instance = new SetDialog();
		return instance;
	}

	public SetDialog() {
		super(MainFrame.instance(), "设置", true);
		setTitle("\u670D\u52A1\u8BBE\u7F6E");
		getContentPane().setEnabled(false);
		getContentPane().setLayout(null);
		setSize(322, 179);
		setLocationRelativeTo(null);
		
		JLabel jlbPrice, jlbNumRest;
		JButton ensure, cancel;
		jlbPrice = new JLabel("\u670D\u52A1IP\uFF1A");
		jlbNumRest = new JLabel("\u670D\u52A1\u7AEF\u53E3\uFF1A");
		jtfIp = new JTextField();
		jtfPort = new JTextField();
		ensure = new JButton("确定");
		cancel = new JButton("取消");
		jlbPrice.setBounds(25, 10, 60, 35);
		jlbNumRest.setBounds(25, 55, 90, 35);
		jtfIp.setBounds(95, 10, 190, 35);
		jtfPort.setBounds(95, 55, 190, 35);
		ensure.setBounds(40, 100, 90, 40);
		cancel.setBounds(198, 100, 90, 40);
		// jtfType.addActionListener(addFoodListener);
		
		
		ensure.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = jtfIp.getText();
				String port = jtfPort.getText();
				if(ip==null||ip.trim().length()==0){
					JOptionPane.showMessageDialog(null, "请输入IP");		
					return;
				}
				if(port==null||port.trim().length()==0){
					JOptionPane.showMessageDialog(null, "请输入端口");		
					return;
				}		
				ip = ip.trim();
				port = port.trim();
				
				ConfigUtils.setProperty(Constants.ip, ip);
				ConfigUtils.setProperty(Constants.port, port);
				HttpHelper.init(ip, port);
				
				JOptionPane.showMessageDialog(null, "设置成功");		
				
				SetDialog.instance.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetDialog.instance.dispose();
			}
		});
		
		getContentPane().add(jlbPrice);
		getContentPane().add(jlbNumRest);
		getContentPane().add(jtfIp);
		getContentPane().add(jtfPort);
		getContentPane().add(ensure);
		getContentPane().add(cancel);
	}

	public void open() {
		
		String port = ConfigUtils.getProperty("port");
		String ip = ConfigUtils.getProperty("ip");
		jtfIp.setText(ip);
		jtfPort.setText(port);
		
		
		setVisible(true);
	}
}
