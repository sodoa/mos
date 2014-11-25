package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import request.HttpHelper;
import request.ResponseCallBack;
import base.DataMap;

public class RegisterDialog extends JDialog {
	private static final long serialVersionUID = 5016876596940564305L;
	private static RegisterDialog configDialog;
	private JTextField jtfIdcard;
	private JTextField jtfRealname;
	private JTextField jtfPhone;
	private JPasswordField jtbPassowrd;

	public static RegisterDialog instance() {
		if (configDialog == null)
			configDialog = new RegisterDialog();
		return configDialog;
	}

	public RegisterDialog() {
		super(MainFrame.instance(), "用户注册", true);
		getContentPane().setLayout(null);
		setSize(400, 420);
		setLocationRelativeTo(null);
		configDialog = this;
		JLabel jlbName = new JLabel("\u8EAB\u4EFD\u8BC1\uFF1A");
		JLabel jlbDiscount = new JLabel("\u59D3\u540D\uFF1A");
		JLabel jlbHoursAdded = new JLabel("\u5BC6\u7801\uFF1A");
		JLabel jlbHours = new JLabel("\u624B\u673A\u53F7\u7801\uFF1A");
		final JButton ensure = new JButton("确定");
		JButton cancel = new JButton("取消");

		jlbName.setBounds(32, 35, 85, 30);
		jlbDiscount.setBounds(32, 95, 85, 30);
		jlbHours.setBounds(32, 155, 85, 30);
		jlbHoursAdded.setBounds(32, 220, 85, 30);
		ensure.setBounds(115, 293, 70, 40);
		cancel.setBounds(225, 293, 70, 40);

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterDialog.instance().dispose();
			}
		});
		

		ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String idcard = jtfIdcard.getText();
				String relname = jtfRealname.getText();
				String telphone = jtfPhone.getText();
				String password = jtbPassowrd.getText();

				if (idcard == null || idcard.trim().length() == 0
						|| relname == null || relname.trim().length() == 0
						|| telphone == null || telphone.trim().length() == 0
						|| password == null || password.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "请完善注册信息！");
					return;
				}

				if (idcard.length() > 20) {
					JOptionPane.showMessageDialog(null, "身份证不能超过20个字符！");
					return;
				}

				if (relname.length() > 10) {
					JOptionPane.showMessageDialog(null, "姓名不能超过10个字符！");
					return;
				}

				if (telphone.length() > 20) {
					JOptionPane.showMessageDialog(null, "手机号码不能超过20个字符！");
					return;
				}

				if (password.length() > 20) {
					JOptionPane.showMessageDialog(null, "密码不能超过30个字符！");
					return;
				}

				DataMap map = new DataMap();
				map.put("idcard", idcard.trim().toUpperCase());
				map.put("relname", relname.trim());
				map.put("telphone", telphone.trim());
				map.put("password", password.trim());
				map.put("FUNID", "MOS.02.01");

				HttpHelper.post(map, new ResponseCallBack() {

					@Override
					public void call(DataMap resultMap) {
						int result = resultMap.getInt("result");
						String message = resultMap.getString("message");

						if (result == 0) {
							JOptionPane.showMessageDialog(null, "用户注册成功！");
							RegistHistoryPanel.instance().refresh();
							RegisterDialog.instance().dispose();
						} else {
							JOptionPane.showMessageDialog(null, message);
						}
					}
				});

			}
		});

		getContentPane().add(jlbName);
		getContentPane().add(jlbDiscount);
		getContentPane().add(jlbHours);
		getContentPane().add(jlbHoursAdded);
		getContentPane().add(ensure);
		getContentPane().add(cancel);

		jtfIdcard = new JTextField();
		jtfIdcard.setBounds(115, 36, 213, 30);
		getContentPane().add(jtfIdcard);
		jtfIdcard.setColumns(10);

		jtfRealname = new JTextField();
		jtfRealname.setColumns(10);
		jtfRealname.setBounds(115, 96, 213, 30);
		getContentPane().add(jtfRealname);

		jtfPhone = new JTextField();
		jtfPhone.setColumns(10);
		jtfPhone.setBounds(115, 156, 213, 30);
		getContentPane().add(jtfPhone);

		jtbPassowrd = new JPasswordField();
		jtbPassowrd.setBounds(115, 220, 213, 30);
		getContentPane().add(jtbPassowrd);
		
		
		jtfIdcard.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					ensure.doClick();
				}
			}
		});		
		
		jtfRealname.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					ensure.doClick();
				}
			}
		});			
		
		jtfPhone.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					ensure.doClick();
				}
			}
		});			
		
		jtbPassowrd.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					ensure.doClick();
				}
			}
		});		
		
		
		
	}

	public void open() {
		jtfIdcard.setText("");
		jtfRealname.setText("");
		jtfPhone.setText("");
		jtbPassowrd.setText("");

		setVisible(true);
	}
}
