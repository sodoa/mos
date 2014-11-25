package frame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JLabel;

import config.ConfigUtils;
import constant.Constants;

import java.awt.FlowLayout;

public class ButtomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5419654428856923445L;
	
	private JLabel jblCompany;
	
	private JLabel jblPhone;
	
	private JLabel jblSite;
	private JLabel jblVersion;

	public ButtomPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, 735,
				26);
		
		jblCompany = new JLabel(ConfigUtils.getProperty(Constants.SYS_TITLE,""));
		jblVersion = new JLabel("版本："+ConfigUtils.getProperty(Constants.CLIENT_VERSION,""));
		jblSite = new JLabel("网站："+ConfigUtils.getProperty(Constants.WEB_SITE,""));
		jblPhone = new JLabel("服务电话："+ConfigUtils.getProperty(Constants.SERVICE_PHONE,""));
		
		add(jblCompany);
		add(jblSite);
		add(jblPhone);
		add(jblVersion);
	}
	
}
