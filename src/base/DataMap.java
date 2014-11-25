package base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DataMap extends HashMap{

	public  DataMap(){
		
	}
	
	public  DataMap(String[] names){
		if (names != null) {
			for (String name : names) {
				this.put(name.toUpperCase().trim(), null);
			}
		}
	}
	
	public  DataMap(Map map) {
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = String.valueOf(map.get(key));
			if (!key.startsWith("##")) {
				this.put(key.toUpperCase(), value);
			}
		}
	}

	public String getString(String key) {
		if (key == null) {
			return null;
		}
		Object value = get(key.toUpperCase());
		if (value == null) {
			return null;
		} else {
			return String.valueOf(value).trim();
		}
	}
	
	public Integer getInt(String key) {
		String value = getString(key);
		if (value == null || value.length() == 0) {
			return null;
		}
		return Integer.parseInt(value);
	}
	
	public Integer getInt(String key,int defValue) {
		String value = getString(key);
		if (value == null || value.length() == 0) {
			return defValue;
		}
		return Integer.parseInt(value);
	}
	
	public Long getLong(String key) {
		String value = getString(key);
		if (value == null || value.length() == 0) {
			return null;
		}
		return Long.parseLong(value);
	}
	
	public Long getLong(String key,long defValue) {
		String value = getString(key);
		if (value == null || value.length() == 0) {
			return defValue;
		}
		return Long.parseLong(value);
	}

	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(String.valueOf(key).toUpperCase());
	}

	@Override
	public Object put(Object key, Object value) {
		return super.put(String.valueOf(key).toUpperCase(), value);
	}

	@Override
	public Object get(Object key) {
		return super.get(String.valueOf(key).toUpperCase());
	}

	@Override
	public Object remove(Object key) {
		if (key != null) {
			return super.remove(String.valueOf(key).toUpperCase());
		}
		return null;
	}
	
	
}
