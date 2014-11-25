package request;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import security.Md5PwdEncoder;
import util.LogUtils;
import base.DataMap;
import config.ConfigUtils;
import constant.Constants;

public class HttpHelper {

	public static String httpaddress = null;

	public static JDialog loadingFrame = null;

	public static void init(String address, String port) {
		httpaddress = "http://" + address + ":" + port + "/"
				+ Constants.SERVER_SUFFIX;
	}

	public static void post(final DataMap asyncMap, final ResponseCallBack call) {
		Thread t = new Thread(){
			public void run() {
				DataMap postParam = asyncMap;

				String sessionid = ConfigUtils.getProperty("sessionid");
				postParam.put("sessionid", sessionid);

				LogUtils.info("post input :" + asyncMap.toString());
				List<NameValuePair> formparams = toNameValues(postParam);
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new HttpPost(httpaddress);

				long start = System.currentTimeMillis();
				httppost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"); 
				httppost.addHeader(new BasicHeader("Cookie","JSESSIONID="+sessionid));
				
				httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);//连接时间20s
				httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);//数据传输时间60s
				
/*				BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",sessionid);
				cookie.setPath("/");
				httpclient.getCookieStore().addCookie(cookie);*/
			
				//httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.ACCEPT_ALL);
				try {
					
					loading();
					
					UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(
							formparams, "UTF-8");
					httppost.setEntity(uefEntity);

					System.out.println("executing request " + httppost.getURI());
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					
				/*	
					
			        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
			        if (cookies.isEmpty()) {
			            System.out.println("None");
			        } else {
			            for (int i = 0; i < cookies.size(); i++) {
			                System.out.println("- " + cookies.get(i).toString());
			            }
			        }
				*/
					if (entity != null) {
						System.out.println("--------------------------------------");
						String msg = EntityUtils.toString(entity, "UTF-8");
						System.out.println("Response content: " + msg);
						System.out.println("--------------------------------------");
						long end = System.currentTimeMillis();
						System.out.println("cost :" + (end - start) * 0.1 / 100);

						DataMap result = JSONUtils.parseObject(msg, DataMap.class);

						LogUtils.info("post output :" + result.toString());

						if (result.getInt("result") == -1) {
							login();
							post(asyncMap, call);
							return;
						}
						
						loaded();
						
						call.call(result);
					}
				} catch (UnsupportedEncodingException e) {
					LogUtils.error(e.getMessage(), e);
					JOptionPane.showMessageDialog(null, "网络连接异常，请检查网络设置");
				} catch (ClientProtocolException e) {
					LogUtils.error(e.getMessage(), e);
					JOptionPane.showMessageDialog(null, "网络连接异常，请检查网络设置");
				} catch (IOException e) {
					LogUtils.error(e.getMessage(), e);
					JOptionPane.showMessageDialog(null, "网络连接异常，请检查网络设置");
				} catch (Exception e) {
					LogUtils.error(e.getMessage(), e);
					JOptionPane.showMessageDialog(null, "网络连接异常，请检查网络设置");
				} finally {
					httpclient.getConnectionManager().shutdown();
					loaded();
				}				
			}
		};
		
		t.start();
	}

	public static List<NameValuePair> toNameValues(Map map) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String val = String.valueOf(map.get(key));
			formparams.add(new BasicNameValuePair(key.toUpperCase(), val));
		}
		return formparams;
	}

	public static void login() {
		final String username = ConfigUtils.getProperty(Constants.USERNAME);
		final String password = ConfigUtils.getProperty(Constants.PASSWORD);

		DataMap loginMap = new DataMap();
		loginMap.put("FUNID", "MOS.03.01");
		loginMap.put("username", username);
		loginMap.put("password", new Md5PwdEncoder().encodePassword(password));

		HttpHelper.post(loginMap, new ResponseCallBack() {

			@Override
			public void call(DataMap resultMap) {
				int result = resultMap.getInt("result");

				String msg = "";
				switch (result) {
				case 1:
					msg = "用户不存在";
					break;
				case 2:
					msg = "密码错误";
					break;
				case 3:
					msg = "帐号被禁用";
					break;
				case 5:
					msg = "用户已删除";
					break;
				case 0:
					msg = "登录成功";
					break;
				default:
					msg = "登录失败，请联系管理员";
					break;
				}
				if (result != 0) {
					JOptionPane.showMessageDialog(null, msg, "提示",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String sessionid = resultMap.getString("SESSIONID");
					ConfigUtils.setProperty("sessionid", sessionid);
					ConfigUtils.setProperty("username", username);
					ConfigUtils.setProperty("password", password);
				}
			}
		});
	}

	// 判断网络状态
	public static boolean isConnect() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("ping " + "www.yiyijt.com");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				LogUtils.info("ping www.yiyijt.com :" + line);
			}
			is.close();
			isr.close();
			br.close();

			LogUtils.info("ping result " + sb);

			if (null != sb && !sb.toString().equals("")) {
				if (sb.toString().indexOf("TTL") > 0) {
					// 网络畅通
					return true;
				} else {
					return false;
				}
			}

			return false;
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
			return false;
		}
	}

	public static void loading() {

		if (loadingFrame == null) {
			loadingFrame = new JDialog();
			final JProgressBar progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			final JPanel contentPane = new JPanel();
			contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
					10));
			contentPane.setLayout(new BorderLayout());
			contentPane.add(new JLabel("正在加载..."), BorderLayout.NORTH);
			contentPane.add(progressBar, BorderLayout.CENTER);
			loadingFrame.setUndecorated(true);
			loadingFrame.setAlwaysOnTop(true);
			loadingFrame.setContentPane(contentPane);
			loadingFrame.pack();
			loadingFrame.setLocationRelativeTo(null);
			loadingFrame.setVisible(true);
		} else {
			loadingFrame.setLocationRelativeTo(null);
			loadingFrame.setVisible(true);
		}
	}

	public static void loaded() {
		if (loadingFrame != null) {
			loadingFrame.dispose();
		}
	}

}
