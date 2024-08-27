package lab4;

public class test_IntervalClass {
	public static void main(String[] args) {
		The_Interval_Var_7 obj1 = new The_Interval_Var_7();
		The_Interval_Var_7 obj2 = new The_Interval_Var_7(5, 10);
		The_Interval_Var_7 obj3 = new The_Interval_Var_7(2, 5);
		
		System.out.println("Interval 1: " + obj1);
		System.out.println("Interval 2: " + obj2);
		System.out.println("Interval 3: " + obj3 + "\n");
		
		System.out.println("Interval 1 length: " + obj1.length());
		System.out.println("Interval 2 length: "  + obj2.length() + "\n");
		
		obj1.offset(3);
		System.out.println("Interval 1 after an offset of 3 to the right: " + obj1);
		obj2.offset(-4);
		System.out.println("Interval 2 after an offset of 4 to the left: " + obj2 + "\n");
		
		obj1.compression(1.2);
		System.out.println("Interval 1 after stretching x1.2: " + obj1);
		obj2.compression(0.5);
		System.out.println("Interval 2 after compression x0.5: " + obj2 + "\n");
		
		System.out.println("Interval 1 equals interval 3: " + obj1.equals(obj3));
		System.out.println("Interval 2 equals interval 3: " + obj2.equals(obj3) + "\n");
		
		System.out.println("Sum of interval 1 and interval 3: " + obj1.sum(obj3));
		System.out.println("Difference between interval 2 and interval 3: " + obj2.difference(obj3) + "\n");
	}
}
