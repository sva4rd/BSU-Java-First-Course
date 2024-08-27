package lab5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Equilateral_Triangle extends Triangle{

	private String colorData[] = null;
	
	private void setData(String... args) throws ArgException{

		if (args.length != 2 || args[0] == null || args[1] == null) {
			throw new ArgException("must be two arguments of color!");
		}


		colorData = new String[2];
		for (int i = 0; i < 2; ++i) {
			colorData[i] = args[i];
		}
	}
	
	protected void equilateralCheck()  throws ArgException{
		
		if (!super.data[0].equals(super.data[1]) || !super.data[2].equals("60.0")) {
			throw new ArgException("the triangle must be equilateral!");
		}
	}
	public Equilateral_Triangle(String firstSide, String secondSide, String angle,
			String contour, String pouring) throws ArgException {
		
		super(firstSide, secondSide, angle);
		equilateralCheck();
		setData(contour, pouring);
	}
	
	public Equilateral_Triangle(int firstSide, int secondSide, int angle,
			String contour, String pouring) throws ArgException {
		
		super(firstSide, secondSide, angle);
		equilateralCheck();
		setData(contour, pouring);
	}
	
	public Equilateral_Triangle(String str) throws ArgException {
		
		super(Arrays.copyOf(str.concat(" | | | | ").split("\\|"), 1)[0].toString() + "|" +
				Arrays.copyOfRange(str.concat(" | | | | ").split("\\|"), 1, 2)[0].toString() + "|" +
				Arrays.copyOfRange(str.concat(" | | | | ").split("\\|"), 2, 3)[0].toString());
		
		equilateralCheck();
		String[] args = Arrays.copyOfRange(str.split("\\|"), 3, 5);
		setData(args);
	}
	
	
	
	
	@Override
	public double Area() {
		
		return 0.25 * Double.parseDouble(data[0]) * Double.parseDouble(data[1]) * Math.sqrt(3.0);
	}
	
	@Override
	public double Perimeter() {

		return 3.0 * Double.parseDouble(data[0]);
	}
	
	@Override
	public String getDataString (int idx) {
		if (idx >= super.data.length + 2 || idx < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (idx < super.data.length)
			return super.data[idx];
		else
			return colorData[idx - super.data.length];
	}
	
	
	
	
	@Override
	public Iterator<String> iterator() {

		return new Iterator<String>() {

			private int iterator_idx = -1;

			@Override
			public boolean hasNext() {
				return iterator_idx < (data.length + 1);
			}

			@Override
			public String next() {
				if (iterator_idx < (data.length - 1)) {
					return data[++iterator_idx];
				} 
				
				else if (iterator_idx < (data.length + 1)) {
					return colorData[++iterator_idx - data.length];
				}
				
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	

	public static Comparator<Equilateral_Triangle> getComparator(final int sortBy) {
		if (sortBy >= dataNames.length + 2 || sortBy < 0) {
			throw new IndexOutOfBoundsException();
		}
		return new Comparator<Equilateral_Triangle>() {
			@Override
			public int compare(Equilateral_Triangle c0, Equilateral_Triangle c1) {
				if (sortBy < Equilateral_Triangle.dataNames.length - 2) {
					double a = Double.parseDouble(c0.data[sortBy]);
					double b = Double.parseDouble(c1.data[sortBy]);
					
					if (a > b)
						return 1;
					else if (a < b)
						return -1;
					else 
						return 0;
				}
				else
					return c0.colorData[sortBy - (Equilateral_Triangle.dataNames.length - 2)].compareTo(
							c1.colorData[sortBy - (Equilateral_Triangle.dataNames.length - 2)]);
			}

		};
	}
	
	
	public int compareTo(Equilateral_Triangle cy) {
		if (Area() > cy.Area())
			return 1;
		else if (Area() < cy.Area())
			return -1;
		else return 0;
	}
	
	
	
	
	
	
	private static String format(Iterable<String> what) {
		String result = "";
		int idx = 0;
		for (String str : what) {
			result += String.format(Equilateral_Triangle.dataFormat[idx++], str);
		}
		return result;
	}

	public static String format() {
		return Equilateral_Triangle.format(Arrays.asList(Equilateral_Triangle.dataNames));
	}

	public static String format(Equilateral_Triangle cn) {
		return Equilateral_Triangle.format(((Iterable<String>) cn));
	}
	
	
	
	
	
	public static final String[] dataNames = { 
			"First side", 
			"Second side", 
			"Angle",
			"Outline color",
			"Fill color"
	};


	public static String[] dataFormat = { 
			"%-25s", 
			"%-25s", 
			"%-15s",
			"%-20s",
			"%-20s"
	};
	
	public static String getDataName(int sortBy) {
		if (sortBy >= dataNames.length || sortBy < 0) {
			throw new IndexOutOfBoundsException();
		}
		return Equilateral_Triangle.dataNames[sortBy];
	}
	
	
	
	@Override
	public String toString() {
		if (data == null) {
			return " | | | | ";
		}
		String res = data[0];
		for (int i = 1; i < data.length; i++) {
			res += "|" + data[i];
		}
		
		res += "|" + colorData[0];
		res += "|" + colorData[1];
		return res;
	}

}
