package lab4;


public class The_Interval_Var_7 {

	private int leftBorder;
	private int rightBorder;

	public The_Interval_Var_7() {
		leftBorder = 10;
		rightBorder = 16;
	}

	public The_Interval_Var_7(int l, int r) {
		assert (r >= l);
		leftBorder = l;
		rightBorder = r;
	}

	public int length() {
		return Math.abs(rightBorder - leftBorder) - 1;
	}

	public void offset(int steps) {
		leftBorder += steps;
		rightBorder += steps;
	}

	public void compression(double k) {
		assert (k > 0);
		if (k == 1)
			return;
		
		double l = (double)length();
		
		int changes;
		boolean exception = false;
		double c = ((l * k - l) / 2.0);
		changes = (int)Math.round((l * k - l) / 2.0);
		
		if (Math.abs(changes - c) == 0.5)
			exception = true;
		
		leftBorder -= changes;
		rightBorder += changes;
		
		if (exception && k > 1)
			leftBorder++;
		if (exception && k < 1)
			rightBorder--;
		
	}

	private int sumOfMembers() {
		int sum = 0;
		for (int i = leftBorder + 1; i < rightBorder; i++)
			sum += i;
		return sum;
	}

	public boolean equals(The_Interval_Var_7 obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		
		if (this.sumOfMembers() == obj.sumOfMembers())
			return true;
		return false;
	}
	
	public int sum (The_Interval_Var_7 obj) {
		assert(obj != null);
		
		return this.sumOfMembers() + obj.sumOfMembers();
	}
	
	public int difference (The_Interval_Var_7 obj) {
		assert(obj != null);
		
		return this.sumOfMembers() - obj.sumOfMembers();
	}
	
	public String toString() {
		assert (length() != 0);
		
		StringBuilder res = new StringBuilder();
		for (int i = leftBorder + 1; i < rightBorder; i++) 
				res.append(i + ", ");

		res.delete(res.length() - 2, res.length());
		return res.toString();
	}
}
