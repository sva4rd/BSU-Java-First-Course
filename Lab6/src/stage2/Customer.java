package stage2;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;


public class Customer implements Serializable {

	private static final long serialVersionUID = 7316896841130520257L;

	// argument exception
	public static class ArgException extends Exception {
		private static final long serialVersionUID = 1L;

		ArgException(String arg) {
			super("Invalid argument: " + arg);
		}
	}

	public static class MethodException extends Exception {
		private static final long serialVersionUID = 2L;

		MethodException(String arg) {
			super("Invalid act: " + arg);
		}
	}
	
	
	
	public static enum WorkType {
		KITCHEN, BATHROOM, BEDROOM, TOILET, HALLWAY, STORE_ROOM, 
		LIVING_ROOM, OFFICE
	};
	
	private String typeToString(WorkType type) {
		if (type == WorkType.KITCHEN)
			return AppLocale.getString(AppLocale.kitchen);
		else if (type == WorkType.BATHROOM)
			return AppLocale.getString(AppLocale.bathroom);
		else if (type == WorkType.BEDROOM)
			return AppLocale.getString(AppLocale.bedroom);
		else if (type == WorkType.TOILET)
			return AppLocale.getString(AppLocale.toilet);
		else if (type == WorkType.HALLWAY)
			return AppLocale.getString(AppLocale.hallway);
		else if (type == WorkType.STORE_ROOM)
			return AppLocale.getString(AppLocale.storeroom);
		else if (type == WorkType.LIVING_ROOM)
			return AppLocale.getString(AppLocale.living_room);
		else
			return AppLocale.getString(AppLocale.office);
	}

	public String getCreationDate() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(
				DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
		String dateOut = dateFormatter.format(creationDate);
		return dateOut;
	}

	private String[] termsOfReference = null;

	private String bill;
	
	private final Date creationDate = new Date();

	public Customer(WorkType...args) throws ArgException {

		if (args.length < 1) 
			throw new ArgException("the customer does not have the terms of reference");

		termsOfReference = new String[args.length];
		for (int i = 0; i < args.length; ++i) 
			termsOfReference[i] = typeToString(args[i]);
	}

	public String[] giveTermsOfReference() {
		return termsOfReference;
	}

	public void setBill(String bl) throws ArgException, MethodException{

		if (termsOfReference == null)
			throw new MethodException("null pointer customer");

		if (bl != null && (bl.charAt(0) == '£' || bl.charAt(bl.length()-1) == '₽'
				|| bl.substring(bl.length() - 2).equals("Br")))
			bill = bl;
		else
			throw new ArgException("wrong bill");
	}

	public String toString() {

		String str;

		str = AppLocale.getString(AppLocale.customer) + ":\n\t" +
				AppLocale.getString(AppLocale.termsOfReference)+":\n";
		for (int i = 0; i < termsOfReference.length; ++i) 
			str += "\t\t" + termsOfReference[i] + "\n";

		if (bill != null)
			str += "\t" + AppLocale.getString(AppLocale.designBill) + ": " + bill;
		else
			str += "\t" + AppLocale.getString(AppLocale.noBill);
		
		str += "\n\t" + AppLocale.getString(AppLocale.creation) + ": " 
				+ getCreationDate();

		return str;
	}
}
