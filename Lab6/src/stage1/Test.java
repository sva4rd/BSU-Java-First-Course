package stage1;

import java.io.File;

import stage1.Customer.ArgException;
import stage1.Customer.MethodException;


public class Test {

	public static Constructor[] makeDeal(Customer customer, Constructor mainConstructor) 
			throws ArgException, MethodException {

		mainConstructor.setTermsOfReference(customer.giveTermsOfReference());
		customer.setBill(mainConstructor.giveDesignCost());
		return mainConstructor.makeBrigade();
	}

	public static void main(String[] args) {
		try {
			Connector con = new Connector(new File("buro_stage1.dat"));
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
			System.out.println("Brigade: ");
			for (Constructor x : readingBrigade1)
				System.out.println(x);

		} 
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
