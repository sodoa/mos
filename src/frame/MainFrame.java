package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements Runnable {
	/**
	 * MainFrame
	 */
	private static final long serialVersionUID = -5988513125942516733L;
	private static MainFrame mainFrame;
	public  JPanel centerJPanel;
	

	public static MainFrame instance() {
		if (mainFrame == null)
			mainFrame = new MainFrame();
		return mainFrame;
	}

	public MainFrame() {
		setTitle("一一生物消费跟踪系统-客户端");
		mainFrame = this;
		//setUndecorated(true);
		// setAlwaysOnTop(true);
		//setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setMinimumSize(new Dimension(860, 600));
		this.setMaximumSize(new Dimension(860, 600));
		this.setResizable(false);
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		container.add(new OptionPanel(), BorderLayout.NORTH);
		//container.add(new MenuPanel(), BorderLayout.WEST);
		container.add(new ButtomPanel(), BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/icon.png")).getImage());

		((JPanel) this.getContentPane()).setOpaque(false);
		BackgroundPanel background = new BackgroundPanel();
		getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		
		setLocationRelativeTo(null);
	}

	public void open() {
		setVisible(true);
		LoginFrame.instance().setVisible(false);
		//LoginFrame.getLoginDialog().setVisible(false);
	}

	@Override
	public void run() {
		instance();
	}
}
