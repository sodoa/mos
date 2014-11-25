package request;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

	public static SimpleDateFormat yearFomrat = new SimpleDateFormat("yyyy");

	public static SimpleDateFormat monthFomrat = new SimpleDateFormat("MM");

	public static SimpleDateFormat shortFomrat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static SimpleDateFormat middleFomrat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm");

	public static SimpleDateFormat fullFomrat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public static String formatShortDate(Date date) {
		if (date == null) {
			return null;
		}
		return shortFomrat.format(date);
	}

	public static String formatMiddleDate(Date date) {

		return middleFomrat.format(date);
	}

	public static String formatFullDate(Date date) {
		if (date == null) {
			return null;
		}
		return fullFomrat.format(date);
	}

	public static int getDateYear(Date date) {
		if (date == null) {
			return 0;
		}
		return Integer.parseInt(yearFomrat.format(date));
	}

	public static int getDateMonth(Date date) {
		if (date == null) {
			return 0;
		}
		return Integer.parseInt(monthFomrat.format(date));
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String format(String date, String format) {
		if (date == null) {
			return "";
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(new Date(Long.parseLong(date)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}
}
