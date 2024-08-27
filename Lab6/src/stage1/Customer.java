package stage1;

import java.io.Serializable;


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
			return "Kitchen";
		else if (type == WorkType.BATHROOM)
			return "Bathroom";
		else if (type == WorkType.BEDROOM)
			return "Bedroom";
		else if (type == WorkType.TOILET)
			return "Toilet";
		else if (type == WorkType.HALLWAY)
			return "Hallway";
		else if (type == WorkType.STORE_ROOM)
			return "Store room";
		else if (type == WorkType.LIVING_ROOM)
			return "Living room";
		else
			return "Office";
	}
	

	private String[] termsOfReference = null;

	private String bill;

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

		if (bl != null && bl.charAt(bl.length()-1) == '$')
			bill = bl;
		else
			throw new ArgException("wrong bill");
	}

	public String toString() {

		String str;

		str = "Customer:\n\tTerms of Reference:\n";
		for (int i = 0; i < termsOfReference.length; ++i) 
			str += "\t\t" + termsOfReference[i] + "\n";

		if (bill != null)
			str += "\tDesign bill: " + bill;
		else
			str += "\tDidn't get the bill";

		return str;
	}
}
