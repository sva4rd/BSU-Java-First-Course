package repairWork;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;



public class FieldIndexes implements Serializable{

	private static final long serialVersionUID = -7897189366606956967L;

	private TreeMap<String, Vector<Long>> map;

	public FieldIndexes() {
		map = new TreeMap<String, Vector<Long>>();
	}

	public String[] getKeys(Comparator<String> comp) {
		String[] result = map.keySet().toArray(new String[0]);
		Arrays.sort(result, comp);
		return result;
	}

	public void put(String key, long value) {
		Vector<Long> keyData = map.get(key);
		if (keyData == null) {
			keyData = new Vector<Long>();
		}
		keyData.add(value);
		map.put(key, keyData);
	}

	public boolean contains(String key) {
		return map.containsKey(key);
	}

	public Long[] get(String key) {
		return map.get(key).toArray(new Long[0]);
	}
}
