package lab5;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


public abstract class Triangle implements Comparable<Triangle>, Iterable<String>{

	// argument exception
	public static class ArgException extends Exception {
		private static final long serialVersionUID = 1L;

		ArgException(String arg) {
			super("Invalid argument: " + arg);
		}
	}

	protected String[] data = null;

	private void setData(double... args) throws ArgException{

		if (args.length != 3 || args[0] < 0 || args[1] < 0 || args[2] < 0) {
			throw new ArgException("3 arguments must be greater than zero!");
		}

		if (args[2] >= 180) {
			throw new ArgException("the angle must be less than 180 degrees!");
		}

		data = new String[3];
		for (int i = 0; i < 3; ++i) {
			data[i] = Double.toString(args[i]);
		}
	}

	public Triangle(double... args) throws ArgException{
		setData(args);
	}

	private boolean argsCheck(String...args){

		if (args[0] == null || args[1]  == null || args[2] == null)
			return false;

		try { 
			if (args.length != 3 || Double.parseDouble(args[0]) < 0 
					|| Double.parseDouble(args[1]) < 0 
					|| Double.parseDouble(args[2]) < 0
					|| Double.parseDouble(args[2]) >= 180) {
				return false;
			}
		} 
		catch (NumberFormatException e) { 
			return false;
		}

		return true;
	}

	public Triangle (String str) throws ArgException {

		if (str == null) {
			throw new ArgException("null pointer passed for str");
		}

		String[] args = str.split("\\|");
		if (argsCheck(args))
			setData(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		else
			throw new ArgException("wrong sides or angle");
	}

	public Triangle(String... args) throws ArgException{

		if (argsCheck(args))
			setData(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		else
			throw new ArgException("sides must be greater than zero, angle must be greater than zero and less than 180");
	}




	public double Area() {

		return 0.5 * Double.parseDouble(data[0]) * Double.parseDouble(data[1]) 
				* Math.sin(Double.parseDouble(data[2]));
	}

	public double Perimeter() {

		double a = Double.parseDouble(data[0]);
		double b = Double.parseDouble(data[1]);
		double angle = Double.parseDouble(data[2]);

		double c =  Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(angle));
		return a + b + c;
	}

	public String getDataString (int idx) {
		if (idx >= data.length || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		return data[idx];
	}

	public double getDataDouble (int idx) {
		if (idx >= data.length || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		return Double.parseDouble(data[idx]);
	}



	@Override
	public Iterator<String> iterator() {

		return new Iterator<String>() {

			private int iterator_idx = -1;

			@Override
			public boolean hasNext() {
				return iterator_idx < (data.length - 1);
			}

			@Override
			public String next() {
				if (iterator_idx < (data.length - 1)) {
					return data[++iterator_idx];
				} 
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}


	@Override
	public int compareTo(Triangle cy) {
		if (Area() > cy.Area())
			return 1;
		else if (Area() < cy.Area())
			return -1;
		else 
			return 0;
	}





	private static String format(Iterable<String> what) {
		String result = "";
		int idx = 0;
		for (String str : what) {
			result += String.format(dataFormat[idx++], str);
		}
		return result;
	}

	public static String format() {
		return Triangle.format(Arrays.asList(Triangle.dataNames));
	}

	public static String format(Triangle cn) {
		return Triangle.format(((Iterable<String>) cn));
	}





	public static final String[] dataNames = { 
			"First side", 
			"Second side", 
			"Angle"
	};


	public static final String[] dataFormat = { 
			"%-25s", 
			"%-25s", 
			"%-15s"
	};

	public static String getDataName(int sortBy) {
		if (sortBy >= dataNames.length || sortBy < 0) {
			throw new IndexOutOfBoundsException();
		}
		return Triangle.dataNames[sortBy];
	}



	@Override
	public String toString() {
		if (data == null) {
			return " | | ";
		}
		String res = data[0];
		for (int i = 1; i < data.length; i++) {
			res += "|" + data[i];
		}
		return res;
	}

}
