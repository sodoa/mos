package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import request.HttpHelper;
import request.ResponseCallBack;
import base.DataMap;
import java.awt.Color;
import java.awt.Font;

public class IdentifyDialog extends JDialog {
	/**
	 * BookingDialog
	 */
	private static final long serialVersionUID = -2103628463004265908L;
	private static IdentifyDialog bookingDialog;
	public JTextField jtfProductCode, jtfIdcard;
	public JLabel lblTipLabel;

	public static IdentifyDialog instance() {
		if (bookingDialog == null)
			bookingDialog = new IdentifyDialog();
		return bookingDialog;
	}

	public IdentifyDialog() {
		super(MainFrame.instance(), "消费认证", true);
		getContentPane().setLayout(null);
		setResizable(false);
		setSize(400, 350);

		bookingDialog = this;
		JLabel jlbPhoneNumber = new JLabel("\u4EA7\u54C1\u7F16\u7801\uFF1A");
		JLabel jlbDuration = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\uFF1A");
		jtfProductCode = new JTextField();
		jtfIdcard = new JTextField();
		final JButton jbtIdentify = new JButton("\u6D88\u8D39\u8BA4\u8BC1");
		JButton jbtCancel = new JButton("取消");
		jlbPhoneNumber.setBounds(60, 129, 90, 35);
		jlbDuration.setBounds(60, 75, 90, 35);
		jtfProductCode.setBounds(140, 129, 190, 35);
		jtfIdcard.setBounds(140, 75, 190, 35);
		jbtIdentify.setBounds(60, 198, 90, 40);
		jbtCancel.setBounds(156, 198, 90, 40);
		JButton jbtRegist = new JButton("\u6CE8\u518C\u4F1A\u5458");
		jbtRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IdentifyDialog.instance().dispose();
				RegisterDialog.instance().open();
			}
		});
		jbtRegist.setBounds(257, 198, 90, 40);
		getContentPane().add(jbtRegist);

		jbtIdentify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String productcode = jtfProductCode.getText();
				String idcard = jtfIdcard.getText();
				if (productcode == null || productcode.trim().length() == 0
						|| idcard == null || idcard.trim().length() == 0) {
					lblTipLabel.setText("身份证，产品编号不能为空！");
					return;
				}
				
				DataMap map = new DataMap();
				map.put("idcard", idcard);
				map.put("code", productcode);
				map.put("FUNID", "MOS.02.02");
				
				HttpHelper.post(map, new ResponseCallBack(){
					@Override
					public void call(DataMap resultMap) {
						int result = resultMap.getInt("result");
						String message = resultMap.getString("message");
						if(result == 0){
							lblTipLabel.setText("消费认证成功！");
							IdentifyHistoryPanel.instance().refresh();
							jtfProductCode.setText("");
							jtfProductCode.setFocusable(true);
						}
						else{
							lblTipLabel.setText(message);
						}
						
					}});;
			}
		});
		
		jtfIdcard.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					jbtIdentify.doClick();
				}
			}
		});
		
		jtfProductCode.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyText(event.getKeyCode()).compareToIgnoreCase(
						"Enter") == 0) {
					jbtIdentify.doClick();
				}
			}
		});		

		jbtCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IdentifyDialog.instance().dispose();
			}
		});
		jtfProductCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		getContentPane().add(jlbPhoneNumber);
		getContentPane().add(jlbDuration);
		getContentPane().add(jtfProductCode);
		getContentPane().add(jtfIdcard);
		getContentPane().add(jbtIdentify);
		getContentPane().add(jbtCancel);
		
		lblTipLabel = new JLabel("");
		lblTipLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		lblTipLabel.setForeground(Color.RED);
		lblTipLabel.setBounds(61, 22, 270, 33);
		getContentPane().add(lblTipLabel);

		setLocationRelativeTo(null);
	}

	public void open() {
		jtfProductCode.setText("");
		jtfIdcard.setText("");
		lblTipLabel.setText("");
		setVisible(true);
	}
}