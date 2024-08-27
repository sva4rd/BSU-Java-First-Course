package lab5;

import java.util.Arrays;


public class test_Triangle_classes {

	static void sortAndPrint(Equilateral_Triangle[] pl) {
		// printout in table
		System.out.println("----- Sorted by Area: ");
		Arrays.sort(pl);
		System.out.printf(Equilateral_Triangle.format());
		System.out.println();
		for (Equilateral_Triangle cnt : pl) {
			System.out.println(Equilateral_Triangle.format(cnt));
		}
	}

	static void sortAndPrint(Equilateral_Triangle[] pl, int sortBy) {
		// printout in table
		System.out.println("----- Sorted by: " + Equilateral_Triangle.getDataName(sortBy));
		Arrays.sort(pl, Equilateral_Triangle.getComparator(sortBy));
		System.out.printf(Equilateral_Triangle.format());
		System.out.println();
		for (Equilateral_Triangle cnt : pl) {
			System.out.println(Equilateral_Triangle.format(cnt));
		}
	}

	public static void main(String[] args) {
		try {
			//Create array of contacts:
			Equilateral_Triangle[] pl = new Equilateral_Triangle[4];
			pl[0] = new Equilateral_Triangle("70|70|60|grey|black");
			pl[1] = new Equilateral_Triangle("58|58|60|red|yellow");
			pl[2] = new Equilateral_Triangle(64, 64, 60, "brown", "blue");
			pl[3] = new Equilateral_Triangle("11", "11", "60", "red", "green");
			//Test Comparable:
			sortAndPrint(pl);
			//Test Comparator:
			sortAndPrint(pl, 0);
			sortAndPrint(pl, 1);
			sortAndPrint(pl, 2);
			sortAndPrint(pl, 3);
			sortAndPrint(pl, 4);
			
			// reconstruct object from result of toString();
			System.out.println();
			Equilateral_Triangle c = pl[1];
			System.out.println("Special triangle:\n" + Equilateral_Triangle.format(c));
			c = new Equilateral_Triangle(c.toString());
			System.out.println("Reconstructed triangle:\n" + Equilateral_Triangle.format(c));
			System.out.println("His area: " + c.Area());
			System.out.println("His perimeter: " + c.Perimeter());
			
			// exception test:
			System.out.println();
			new Equilateral_Triangle("Test exception object");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
