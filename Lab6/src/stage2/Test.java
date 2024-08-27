package stage2;

import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import stage2.Customer.ArgException;
import stage2.Customer.MethodException;


public class Test {
	
	static Locale createLocale(String[] args) {
		if (args.length == 2) {
			return new Locale(args[0], args[1]);
		} 
		else if (args.length == 4) {
			return new Locale(args[2], args[3]);
		}
		return null;
	}

	static void setupConsole(String[] args) {
		if (args.length >= 2) {
			if (args[0].equals("-encoding")) {
				try {
					System.setOut(new PrintStream(System.out, true, args[1]));
				} 
				catch (UnsupportedEncodingException ex) {
					System.err.println("Unsupported encoding: " + args[1]);
					System.exit(1);
				}
			}
		}
	}

	public static Constructor[] makeDeal(Customer customer, Constructor mainConstructor) 
			throws ArgException, MethodException {

		mainConstructor.setTermsOfReference(customer.giveTermsOfReference());
		customer.setBill(mainConstructor.giveDesignCost());
		return mainConstructor.makeBrigade();
	}

	public static void main(String[] args) {

		try {
			setupConsole(args);
			Locale loc = createLocale(args);
			if (loc == null) {
				System.err.println("Invalid argument(s)\n"
						+ "Syntax: [-encoding ENCODING_ID] language country\n"
						+ "Example: -encoding Cp855 be BY");
				System.exit(1);
			}
			AppLocale.set(loc);
			Connector con = new Connector(new File("buro_stage2.dat"));
			Customer customer = new Customer(Customer.WorkType.KITCHEN,
					Customer.WorkType.BATHROOM,
					Customer.WorkType.BEDROOM,
					Customer.WorkType.TOILET,
					Customer.WorkType.HALLWAY);
			Constructor mainConstructor = new Constructor();
			Constructor[] brigade1 = makeDeal(customer, mainConstructor);

			con.writeDeal(customer, mainConstructor, brigade1);

			ReadingData data = con.readDeal();

			Customer readingCustomer = data.getCustomer();
			Constructor readingMainConstructor = data.getMainConstructor();
			Constructor[] readingBrigade1 = data.getBrigade();

			System.out.println(readingCustomer);
			System.out.println();
			System.out.println(readingMainConstructor);
			System.out.println();
			System.out.println(AppLocale.getString(AppLocale.brigade)+": ");
			for (Constructor x : readingBrigade1)
				System.out.println(x);

		} 
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
