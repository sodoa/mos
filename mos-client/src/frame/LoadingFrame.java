package frame;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import request.HttpHelper;

import com.sun.awt.AWTUtilities;

import config.ConfigUtils;
import constant.Constants;

public class LoadingFrame extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8236850107130802140L;
	private static LoadingFrame loadingFrame;

	public static LoadingFrame instance() {
		if (loadingFrame == null)
			loadingFrame = new LoadingFrame();
		return loadingFrame;
	}

	public LoadingFrame() {
		super("一一生物科技");
		setSize(320, 233);
		loadingFrame = this;
		//setUndecorated(true);
		//setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		//AWTUtilities.setWindowOpaque(this, false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/logo2.png")).getImage());
		setVisible(true);

		String ip = ConfigUtils.getProperty(Constants.ip);
		String port = ConfigUtils.getProperty(Constants.port);

		boolean defaultInit = false;
		if (ip == null || ip.trim().length() == 0) {
			ip = Constants.default_ip;
			defaultInit = true;
		}
		if (port == null || port.trim().length() == 0) {
			port = Constants.default_port;
			defaultInit = true;
		}

		HttpHelper.init(ip, port);
		
		if (!HttpHelper.isConnect()) {
			JOptionPane.showMessageDialog(null, "网络连接异常，请检查网络设置");
		}

		if (defaultInit) {
			ConfigUtils.setProperty(Constants.ip, ip);
			ConfigUtils.setProperty(Constants.port, port);
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		ImageIcon bg = new ImageIcon(this.getClass().getResource(
				"/images/logo2.png"));
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public void run() {
		instance();
	}
}
