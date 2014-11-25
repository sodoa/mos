package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import listener.TableListener;
import request.DateUtils;
import request.HttpHelper;
import request.ResponseCallBack;
import util.LogUtils;
import util.SimplePage;
import base.DataMap;

import com.alibaba.fastjson.JSONArray;

import config.ConfigUtils;
import constant.Constants;

public class IdentifyHistoryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4090036947302273308L;
	private static IdentifyHistoryPanel mainPanel;
	private DataTable table;
	private JScrollPane jscrolPane;

	private JButton jbtPre;
	private JButton jbtNext;
	private JLabel lblNewLabel;
	
	private JPanel toolsbarPanel;

	private SimplePage spage = new SimplePage();

	public static IdentifyHistoryPanel instance() {
		if (mainPanel == null)
			mainPanel = new IdentifyHistoryPanel();
		return mainPanel;
	}

	public IdentifyHistoryPanel() {
		mainPanel = this;
		setOpaque(false);

		table = new DataTable(null, null);
		setLayout(new BorderLayout());
		jscrolPane = new JScrollPane();
		add(jscrolPane, BorderLayout.CENTER);
		// jscrolPane.setOpaque(false);
		// jscrolPane.getViewport().setOpaque(false);

		jbtPre = new JButton("上一页");
		jbtPre.setBounds(359, 311, 69, 23);
		jbtPre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spage.setPageNo(spage.getPageNo() - 1);
				showIdentifyHistory();
			}
		});

		jbtNext = new JButton("下一页");
		jbtNext.setBounds(430, 311, 69, 23);
		jbtNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (spage.getPageNo() >= spage.getTotalPage()) {
					spage.setPageNo(spage.getTotalPage());
				} else {
					spage.setPageNo(spage.getPageNo() + 1);
				}
				showIdentifyHistory();
			}
		});

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 319, 339, 15);
		
		toolsbarPanel = new JPanel();
		toolsbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		
		toolsbarPanel.add(lblNewLabel);
		toolsbarPanel.add(jbtPre);
		toolsbarPanel.add(jbtNext);
		
		this.add(toolsbarPanel, BorderLayout.NORTH);

	}

	@Override
	public void paint(Graphics g) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(
				"/images/scrollpane.png"));
		Image img = icon.getImage();
		g.drawImage(img, jscrolPane.getX(), jscrolPane.getY(),
				jscrolPane.getWidth(), jscrolPane.getHeight(), this);
		super.paint(g);
	}

	public DataTable getTable() {
		return table;
	}

	public void showIdentifyHistory() {

		final Object[] head = { "产品编号", "姓名", "身份证", "联系方式", "经办人", "消费时间" };

		String username = ConfigUtils.getProperty(Constants.USERNAME);

		DataMap map = new DataMap();
		map.put("userid", username);
		map.put("FUNID", "MOS.04.01");
		map.put("page", spage.getPageNo());
		map.put("rows", spage.getPageSize());

		HttpHelper.post(map, new ResponseCallBack() {
			@Override
			public void call(DataMap resultMap) {
				int result = resultMap.getInt("result");
				String message = resultMap.getString("message");
				if (result == 0) {

					int total = resultMap.getInt("total");
					List<DataMap> list = JSONArray.parseArray(
							resultMap.getString("rows"), DataMap.class);

					Object[][] objs = new Object[list.size()][6];

					for (int i = 0; i < list.size(); i++) {
						DataMap item = list.get(i);
						String productcode = item.getString("productcode");
						String relname = item.getString("relname");
						String idcard = item.getString("idcard");
						String telphone = item.getString("telphone");
						String createuser = item.getString("dealer");
						String createtimeStr = item.getString("createtime");

						String createtime = "";
						try {
							createtime = DateUtils.format(createtimeStr,
									"yyyy-MM-dd hh:mm");
						} catch (Exception e) {
							LogUtils.error(e.getMessage(), e);
						}
						objs[i][0] = productcode;
						objs[i][1] = relname;
						objs[i][2] = idcard;
						objs[i][3] = telphone;
						objs[i][4] = createuser;
						objs[i][5] = createtime;
					}
					showData(objs, head, total);

				} else {
					JOptionPane.showMessageDialog(null, message);
				}
			}
		});

		JMenuItem add = new JMenuItem("    添加房间    ");
		JMenuItem addRooms = new JMenuItem("    批量添加    ");
		JMenuItem delete = new JMenuItem("    删除房间    ");
		JPopupMenu menu = new JPopupMenu();
		menu.add(add);
		menu.add(addRooms);
		menu.add(delete);
		TableListener tableListener = new TableListener(menu);
		table.addMouseListener(tableListener);
		table.addMouseMotionListener(tableListener);
		add.addActionListener(tableListener);
		addRooms.addActionListener(tableListener);
		delete.addActionListener(tableListener);

	}

	public void showData(Object[][] data, Object[] head, int total) {
		table.removeAll();
		table = new DataTable(data, head);
		jscrolPane.setViewportView(table);

		SimplePage spage = new SimplePage();
		spage.setTotalCount(total);

		String label = "第  " + spage.getPageNo() + " 页/共计 "
				+ spage.getTotalPage() + " 页   共计" + total + " 条数据";
		lblNewLabel.setText(label);
	}

	public void refresh() {
		showIdentifyHistory();
	}
}
