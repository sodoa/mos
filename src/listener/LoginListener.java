package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import request.HttpHelper;
import request.ResponseCallBack;
import security.Md5PwdEncoder;
import base.DataMap;
import config.ConfigUtils;
import frame.ImageButton;
import frame.LoginFrame;
import frame.MainFrame;

public class LoginListener extends MouseAdapter implements ActionListener {

	private JTextField jtfUserName;
	private JPasswordField jpfPassword;
	private JButton ensure;
	private JButton cancel;

	public LoginListener(JTextField jtfUserName, JPasswordField jpfPassword,
			JButton ensure, JButton cancel) {
		this.jtfUserName = jtfUserName;
		this.jpfPassword = jpfPassword;
		this.ensure = ensure;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ensure) {
			if (jtfUserName.getText().equals("")
					|| jpfPassword.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "�������û������룡", "��ʾ",
						JOptionPane.ERROR_MESSAGE);

			} else  {
				final String username = jtfUserName.getText();
				final String password = jpfPassword.getText();
				login(username,password);
			} 
		} else if (e.getSource() == cancel) {
			System.exit(0);
		}
	}

	public void login(final String username,final String password) {

		DataMap loginMap = new DataMap();
		loginMap.put("FUNID", "MOS.03.01");
		loginMap.put("username", username);
		loginMap.put("password",
				new Md5PwdEncoder().encodePassword(password));

		HttpHelper.post(loginMap, new ResponseCallBack() {

			@Override
			public void call(DataMap resultMap) {
				int result = resultMap.getInt("result");

				String msg = "";
				switch (result) {
				case 1:
					msg = "�û�������";
					break;
				case 2:
					msg = "�������";
					break;
				case 3:
					msg = "�ʺű�����";
					break;
				case 5:
					msg = "�û���ɾ��";
					break;
				case 0:
					msg = "��¼�ɹ�";
					break;
				default:
					msg = "��¼ʧ�ܣ�����ϵ����Ա";
					break;
				}
				if (result != 0) {
					JOptionPane.showMessageDialog(null, msg, "��ʾ",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String sessionid = resultMap.getString("SESSIONID");
					ConfigUtils.setProperty("sessionid", sessionid);
					ConfigUtils.setProperty("username", username);
					ConfigUtils.setProperty("password", password);
					
					LoginFrame.instance().setVisible(false);
					MainFrame.instance().open();
				}
			}
		});
	}
}
