package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import listener.LoginListener;
import request.HttpHelper;
import request.ResponseCallBack;
import util.LogUtils;
import base.DataMap;
import config.ConfigUtils;
import constant.Constants;

public class LoginFrame extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7635382088464340846L;
	private static LoginFrame loginFrame;
	private JTextField jtfUserName;
	private JPasswordField jpfPassword;

	public static LoginFrame instance() {
		if (loginFrame == null)
			loginFrame = new LoginFrame();
		return loginFrame;
	}

	public LoginFrame() {
		super("登陆系统");
		setBackground(new Color(0, 204, 204));

		loginFrame = this;

		jtfUserName = new JTextField();
		jpfPassword = new JPasswordField();
		final JButton ensure = new JButton("登录");
		final JButton cancel = new JButton("取消");

		loginFrame.setSize(315, 240);
		loginFrame.getContentPane().setLayout(null);
		loginFrame.setResizable(false);
		//loginFrame.setUndecorated(true);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.getContentPane().add(jtfUserName);
		loginFrame.getContentPane().add(jpfPassword);
		loginFrame.getContentPane().add(ensure);
		loginFrame.getContentPane().add(cancel);
		//loginFrame.getContentPane().add(loginBg);
		//AWTUtilities.setWindowOpaque(loginDialog, false);

		jtfUserName.setBounds(76, 21, 188, 33);
		jpfPassword.setBounds(76, 67, 188, 36);
		ensure.setBounds(38, 113, 77, 33);
		cancel.setBounds(125, 113, 70, 33);

		jtfUserName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		jpfPassword.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		LoginListener loginListener = new LoginListener(jtfUserName,
				jpfPassword, ensure, cancel);
		jtfUserName.addActionListener(loginListener);
		jpfPassword.addActionListener(loginListener);
		ensure.addActionListener(loginListener);
		cancel.addActionListener(loginListener);
		
		JButton button = new JButton("\u8BBE\u7F6E");
		button.setBounds(206, 113, 70, 33);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SetDialog.instance().open();
			}});
		
		getContentPane().add(button);
		
		
		jtfUserName.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					ensure.doClick();
				}
			}
		});		
		
		jpfPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					ensure.doClick();
				}
			}
		});	
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 10, 60, 45);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("\u5BC6 \u7801\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 14));
		label.setBounds(10, 59, 60, 50);
		getContentPane().add(label);
		
		JLabel jlbSite = new JLabel("");
		jlbSite.setBounds(50, 185, 227, 15);
		getContentPane().add(jlbSite);
		
		JLabel jlbPhone = new JLabel("");
		jlbPhone.setBounds(49, 156, 227, 15);
		getContentPane().add(jlbPhone);

		//setSize(screenSize);
		//setUndecorated(true);
		//setBackground(Color.BLACK);
		//setLocationRelativeTo(null);
		//setExtendedState(Frame.MAXIMIZED_BOTH);
		//setMinimumSize(new Dimension(1024, 768));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/icon.png")).getImage());
		
		
		String phone = ConfigUtils.getProperty(Constants.SERVICE_PHONE);
		String site = ConfigUtils.getProperty(Constants.WEB_SITE);	
		
		jlbSite.setText("服务热线："+phone);
		jlbPhone.setText("企业网站："+site);
		
		version();
	}
	
	public void version(){

		DataMap loginMap = new DataMap();
		loginMap.put("FUNID", "MOS.00.02");

		HttpHelper.post(loginMap, new ResponseCallBack() {

			@Override
			public void call(DataMap resultMap) {
				int result = resultMap.getInt("result");
				if(result == 0){
					String service_phone = resultMap.getString("service_phone");
					String web_site = resultMap.getString("web_site");
					String client_version = resultMap.getString("client_version");
					String client_download = resultMap.getString("client_download");
					String sys_title = resultMap.getString("sys_title");
					
					if(client_version == null || client_version.trim().length() ==0 ){
						client_version = "1.0";
					}
					String localVersion = ConfigUtils.getProperty(Constants.CLIENT_VERSION,"1.0");
					
					ConfigUtils.setProperty(Constants.SERVICE_PHONE, service_phone);
					ConfigUtils.setProperty(Constants.WEB_SITE, web_site);
					ConfigUtils.setProperty(Constants.SYS_TITLE, sys_title);
					ConfigUtils.setProperty(Constants.CLIENT_VERSION, client_version);
					ConfigUtils.setProperty(Constants.CLIENT_DOWNLOAD, client_download);
					
					if (Float.parseFloat(client_version) >Float
							.parseFloat(localVersion)) {
						
						JOptionPane.showMessageDialog(null, "有新版本，请先下载更新版本");
						
						try {
							Runtime.getRuntime().exec("cmd.exe /c start iexplore "+client_download);
						} catch (IOException e) {
							LogUtils.error(e.getMessage(), e);
						}
					}
				}

			}
		});
	}

	public void open() {
		jtfUserName.setText("");
		jpfPassword.setText("");
		jtfUserName.requestFocus();

		setVisible(true);
		loginFrame.setVisible(true);
		if(LoadingFrame.instance().isVisible())
			LoadingFrame.instance().dispose();
	}

	public void easeOpacity(float opacity) {
		if (opacity <= 0) {
			opacity = 0;
			return;
		} else if (opacity > 1) {
			opacity = 1;
		}
		//AWTUtilities.setWindowOpacity(loginDialog, opacity);
	}

	public void showMainFrame() {
		MainFrame.instance().open();
	}

	public void exit() {
		System.exit(0);
	}

	@Override
	public void run() {
		instance().open();
	}
}
