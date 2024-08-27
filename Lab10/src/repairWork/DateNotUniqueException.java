package repairWork;

import java.util.Date;

public class DateNotUniqueException extends Exception{

	private static final long serialVersionUID = 1L;

	public DateNotUniqueException(Date date) {
		super(new String("Date is not unique: " + Bill.dateFormatter.format(date)));
	}
}
