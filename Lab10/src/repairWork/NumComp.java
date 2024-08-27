package repairWork;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;

class NumComp implements Comparator<String>{
	public int compare(String o1, String o2) {
		// right order:
		return Integer.parseInt(o1) - Integer.parseInt(o2);
	}
}
class NumCompReverse implements Comparator<String>{
	public int compare(String o1, String o2) {
		// right order:
		return Integer.parseInt(o2) - Integer.parseInt(o1);
	}
}



class NameComp implements Comparator<String> {
	public int compare(String o1, String o2) {
		// right order:
		return o1.compareTo(o2);
	}
}
class NameCompReverse implements Comparator<String> {
	public int compare(String o1, String o2) {
		// right order:
		return o2.compareTo(o1);
	}
}



class DateComp implements Comparator<String> {
	public int compare(String o1, String o2) {
		
		StringTokenizer st = new StringTokenizer(o1, ".");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));		
		Date d1 = cal.getTime();
		
		st = new StringTokenizer(o2, ".");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));		
		Date d2 = cal.getTime();
		// right order:
		return d1.compareTo(d2);
	}
}
class DateCompReverse implements Comparator<String> {
	public int compare(String o1, String o2) {
		
		StringTokenizer st = new StringTokenizer(o1, ".");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));		
		Date d1 = cal.getTime();
		
		st = new StringTokenizer(o2, ".");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));		
		Date d2 = cal.getTime();
		// right order:
		return d2.compareTo(d1);
	}
}
