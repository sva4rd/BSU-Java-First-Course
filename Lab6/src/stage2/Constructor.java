package stage2;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import stage2.Customer.ArgException;
import stage2.Customer.MethodException;

public class Constructor implements Serializable{

	private static final long serialVersionUID = -3731214660271581626L;

	private static int workID = 1;

	private boolean mainConstructor;

	private String[] termsOfReference = null;

	private int termsOfReferenceID;
	private int constructorID;
	
	private final Date creationDate = new Date();

	public Constructor() {
		mainConstructor = true;
		constructorID = 0;
	}

	private Constructor(int idx, int jobID) {
		mainConstructor = false;
		constructorID = idx;
		termsOfReferenceID = jobID;
	}

	public void setTermsOfReference(String...args) throws ArgException, MethodException{

		if (args.length < 1)
			throw new ArgException("there are no terms of reference");

		if (termsOfReference != null)
			throw new MethodException("the constructor already has terms of reference");

		termsOfReference = new String[args.length];
		for (int i = 0; i < args.length; ++i) 
			termsOfReference[i] = args[i];

		termsOfReferenceID = workID;
		workID++;
	}
	
	
	private Double exchangeRate(double money) {

		if (AppLocale.get().equals(new Locale("be", "BY"))) 
			return money * 2.54;
		else if (AppLocale.get().equals(new Locale("ru", "RU"))) 
			return money * 61.5;
		else
			return money * 0.87;
	}

	public String giveDesignCost() throws MethodException{

		if (!mainConstructor || termsOfReference == null)
			throw new MethodException("it is impossible to execute the method");
		
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(AppLocale.get());
		return currencyFormatter.format(exchangeRate(termsOfReference.length * 25));
	}

	public String giveConstructionCost() throws MethodException{

		if (!mainConstructor || termsOfReference == null)
			throw new MethodException("it is impossible to execute the method");

		int cost = 0;
		for (int i = 0; i < termsOfReference.length; ++i)
			cost += termsOfReference[i].length() * 75;

		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(AppLocale.get());
		return currencyFormatter.format(exchangeRate(cost));
	}
	
	public String getCreationDate() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(
				DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
		String dateOut = dateFormatter.format(creationDate);
		return dateOut;
	}

	public Constructor[] makeBrigade() throws MethodException{

		if (!mainConstructor || termsOfReference == null)
			throw new MethodException("it is impossible to execute the method");

		Constructor[] brigade = new Constructor[termsOfReference.length];
		for (int i = 0; i < brigade.length; ++i) {
			brigade[i] = new Constructor(i+1, termsOfReferenceID);
		}

		return brigade;
	}

	public String toString() {

		String str;
		if (mainConstructor)
		{
			str = AppLocale.getString(AppLocale.mainConstructor) + ":\n\t" +
					AppLocale.getString(AppLocale.termsOfReference)+":\n";
			if (termsOfReference != null) {
				for (int i = 0; i < termsOfReference.length; ++i) 
					str += "\t\t" + termsOfReference[i] + "\n";
				try {
					str += "\t" + AppLocale.getString(AppLocale.designCost) + ": " + giveDesignCost();
					str += "\n\t" + AppLocale.getString(AppLocale.constructionCost) + ": " 
							+ giveConstructionCost() + "\n\t";
				}
				catch(Exception ex) {
					str = "\nconversion is not possible";
				}
			}
			else
				str += "\t\t" + AppLocale.getString(AppLocale.noTask) + "\n\t";
		}
		else
			str = "\t" + AppLocale.getString(AppLocale.brigade_member) + " " + 
			Integer.toString(constructorID) + " — ";
		
		str += AppLocale.getString(AppLocale.creation) + ": " 
				+ getCreationDate();

		return str;
	}
}
