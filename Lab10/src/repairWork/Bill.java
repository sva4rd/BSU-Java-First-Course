package repairWork;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Bill implements Serializable{
	
	private static final long serialVersionUID = 610366388238212568L;
	
	static DateFormat dateFormatter = DateFormat.getDateInstance(
			DateFormat.DEFAULT, Locale.GERMAN);
	
	private int houseNumber;
	private int roomNumber;
	private String address;
	private String name;
	private Date billDate;
	private int billSum;
	private int penya;
	private int overdue;
	
	public Date getBillDate() {
		return billDate;
	}
	
	public int getHouseNumber() {
		return houseNumber;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public int getOverdue() {
		return overdue;
	}
	
	
	public Bill() {}
	
	private static GregorianCalendar curCalendar = new GregorianCalendar();
	
	public boolean validBillDate() {
		return billDate.after(new GregorianCalendar(1970, 0, 0).getTime()) 
				&& (billDate.before(curCalendar.getTime())
				|| billDate.equals(curCalendar.getTime()));
	}
	
	public boolean validNumber(int num) {
		return num >= 0;
	}
	
	public boolean validName(String name) {
		return (!name.isEmpty() && name != null);
	}
	
	public boolean validPenya() {
		return (penya >= 0 && penya < 100);
	}

	public Bill(int hNum, int rNum, String ad, String n, Date date, int sum, int pen, int over) throws IOException {
		
		this.houseNumber = hNum;
		this.roomNumber = rNum;		
		this.address = ad;
		this.name = n;			
		this.billDate = date;
		this.billSum = sum;
		this.penya = pen;
		this.overdue = over;
	}
	
	public static boolean nextRead(Scanner fin, PrintStream out) {
		return nextRead("House number", fin, out);
	}

	private static boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
		out.print(prompt);
		out.print(": ");
		return fin.hasNextLine();
	}

	@SuppressWarnings("deprecation")
	public static Bill read(Scanner fin, PrintStream out) throws IOException,
			NumberFormatException {
		String str;
		
		Bill bill = new Bill();
		bill.houseNumber = Integer.parseInt(fin.nextLine().trim());
		if (bill.validNumber(bill.houseNumber) == false) {
			throw new IOException("Invalid House number: " + bill.houseNumber);
		}
		
		
		if (!nextRead("Room number", fin, out)) {
			return null;
		}
		bill.roomNumber = Integer.parseInt(fin.nextLine().trim());
		if (bill.validNumber(bill.roomNumber) == false) {
			throw new IOException("Invalid Room number: " + bill.roomNumber);
		}
		
		
		if (!nextRead("Address", fin, out)) {
			return null;
		}
		bill.address = fin.nextLine().trim();
		if (bill.validName(bill.address) == false) {
			throw new IOException("Invalid Address: " + bill.address);
		}
		
		
		if (!nextRead("Name", fin, out)) {
			return null;
		}
		bill.name = fin.nextLine().trim();
		if (bill.validName(bill.name) == false) {
			throw new IOException("Invalid Name: " + bill.name);
		}
		
		
		if (!nextRead("Bill date", fin, out)) {
			return null;
		}
		str = fin.nextLine().trim();
		StringTokenizer st = new StringTokenizer(str, ".");
		if (st.countTokens() != 3) {
			throw new IOException("Invalid Date: " + str);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()));
		cal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));		
		bill.billDate = cal.getTime();
		bill.billDate.setHours(0);
		bill.billDate.setMinutes(0);
		bill.billDate.setSeconds(0);
		if (bill.validBillDate() == false) {
			throw new IOException("Invalid Bill date: " + bill.billDate);
		}
		
		
		if (!nextRead("Bill sum", fin, out)) {
			return null;
		}
		bill.billSum = Integer.parseInt(fin.nextLine().trim());
		if (bill.validNumber(bill.billSum) == false) {
			throw new IOException("Invalid Bill sum: " + bill.billSum);
		}
		
		
		if (!nextRead("Penya", fin, out)) {
			return null;
		}
		bill.penya = Integer.parseInt(fin.nextLine().trim());
		if (bill.validPenya() == false) {
			throw new IOException("Invalid Penya: " + bill.penya);
		}
		
		
		if (!nextRead("Overdue", fin, out)) {
			return null;
		}
		bill.overdue = Integer.parseInt(fin.nextLine().trim());
		if (bill.validNumber(bill.overdue) == false) {
			throw new IOException("Invalid Overdue: " + bill.overdue);
		}
		
		
		return bill;
	}
	
	public String toString() {
		return new String(
			"House number: " + houseNumber + "||" +
					"Room number: " + roomNumber + "||" +
					"Address: " + address + "||" +
					"Name: " + name + "||" +
					"Bill date: " + dateFormatter.format(billDate) + "||" +
					"Bill sum: " + billSum + "$||" + 
					"Penya: " + penya + "%||" +
					"Overdue: " + overdue);
	}
}
