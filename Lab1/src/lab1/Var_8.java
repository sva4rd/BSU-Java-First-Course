package lab1;
import java.util.Scanner;

public class Var_8 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Input x: ");
		double x = in.nextDouble();
		if ( x >= 1 || x <= -1 ) {
			System.err.println("Invalid argument x");
			in.close();
			System.exit(1);
		}

		System.out.print("Input k: ");
		int k = in.nextInt();
		if ( k <= 1 ) {
			System.err.println("Invalid argument k");
			in.close();
			System.exit(1);
		}

		double epsilon = 1 / Math.pow( 10, k + 1 );
		double result = 0;
		double summand = x;
		int n = 1;
		int evenOrOdd = 1;
		while( Math.abs( summand ) >= epsilon ) {
			if (evenOrOdd % 2 != 0)
				result += summand;
			else
				result -= summand;
			summand = (( summand * n ) * x * x )/( n + 2 );
			n+=2;
			evenOrOdd++;
		}

		String answer = "%." + k + "f\n";
		System.out.printf( "Program result: " + answer, result );
		System.out.printf( "Function result: " + answer, Math.atan( x ));
		in.close();
		System.exit(0);
	}

}
