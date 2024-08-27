package stage2;

final class ReadingData {
	
	private final Customer cust;
	private final Constructor cons;
	private final Constructor[] brig;
	
	public ReadingData(Customer cu, Constructor co, Constructor[] br) {
		cust = cu;
		cons = co;
		brig = br;
	}
	
	public Customer getCustomer() {
		return cust;
	}
	
	public Constructor getMainConstructor() {
		return cons;
	}
	
	public Constructor[] getBrigade() {
		return brig;
	}
}
