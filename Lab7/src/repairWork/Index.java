package repairWork;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class Index implements Serializable, Closeable{

	private static final long serialVersionUID = -7945268573885689341L;

	FieldIndexes houseNumbers;
	FieldIndexes roomNumbers;
	FieldIndexes names;
	FieldIndexes dates;

	public Index() {
		houseNumbers = new FieldIndexes();
		roomNumbers = new FieldIndexes();
		names = new FieldIndexes();
		dates = new FieldIndexes();
	}

	public void test(Bill bill) throws DateNotUniqueException{
		assert (bill != null);
		if (dates.contains(bill.getBillDate().toString())) {
			throw new DateNotUniqueException(bill.getBillDate());
		}
	}

	public void put(Bill bill, long value) throws DateNotUniqueException{
		test(bill);
		houseNumbers.put(Integer.toString(bill.getHouseNumber()), value);
		roomNumbers.put(Integer.toString(bill.getRoomNumber()), value);
		names.put(bill.getName(), value);
		dates.put(bill.getBillDate().toString(), value);
	}

	public static Index load(String name) throws IOException, ClassNotFoundException {
		Index obj = null;
		try {
			FileInputStream file = new FileInputStream(name);
			try (ZipInputStream zis = new ZipInputStream(file)) {
				ZipEntry zen = zis.getNextEntry();
				if (zen.getName().equals(Buffer.zipEntryName) == false) {
					throw new IOException("Invalid block format");
				}
				try (ObjectInputStream ois = new ObjectInputStream(zis)) {
					obj = (Index) ois.readObject();
				}
			}
		} 
		catch (FileNotFoundException e) {
			obj = new Index();
		}
		if (obj != null) {
			obj.save(name);
		}
		return obj;
	}

	private transient String filename = null;

	public void save(String name) {
		filename = name;
	}

	public void saveAs(String name) throws IOException {
		FileOutputStream file = new FileOutputStream(name);
		try (ZipOutputStream zos = new ZipOutputStream(file)) {
			zos.putNextEntry(new ZipEntry(Buffer.zipEntryName));
			zos.setLevel(ZipOutputStream.DEFLATED);
			try (ObjectOutputStream oos = new ObjectOutputStream(zos)) {
				oos.writeObject(this);
				oos.flush();
				zos.closeEntry();
				zos.flush();
			}
		}
	}

	public void close() throws IOException {
		saveAs(filename);
	}
}
