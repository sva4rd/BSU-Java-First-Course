package stage1;

import java.io.Serializable;

import stage1.Customer.ArgException;
import stage1.Customer.MethodException;

public class Constructor implements Serializable{

	private static final long serialVersionUID = -3731214660271581626L;

	private static int workID = 1;

	private boolean mainConstructor;

	private String[] termsOfReference = null;

	private int termsOfReferenceID;
	private int constructorID;

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

	public String giveDesignCost() throws MethodException{

		if (!mainConstructor || termsOfReference == null)
			throw new MethodException("it is impossible to execute the method");

		return Integer.toString(termsOfReference.length * 25) + "$";
	}

	public String giveConstructionCost() throws MethodException{

		if (!mainConstructor || termsOfReference == null)
			throw new MethodException("it is impossible to execute the method");

		int cost = 0;
		for (int i = 0; i < termsOfReference.length; ++i)
			cost += termsOfReference[i].length() * 75;
		return Integer.toString(cost) + "$";
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
			str = "MainConstructor:\n\tTerms of Reference:\n";
			for (int i = 0; i < termsOfReference.length; ++i) 
				str += "\t\t" + termsOfReference[i] + "\n";
			try {
				str += "\tDesignCosts: " + giveDesignCost();
				str += "\n\tConstructionCost: " + giveConstructionCost();
			}
			catch(Exception ex) {
				str = "\nconversion is not possible";
			}
		}
		else
			str = "\tBrigade member " + Integer.toString(constructorID);

		return str;
	}
}
