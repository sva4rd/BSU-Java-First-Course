	package stage2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Connector {

	private File file;

	public File getFile() {
		return file;
	}

	public Connector(String filename) {
		this.file = new File(filename);
	}

	public Connector(File file) {
		this.file = file;
	}
	
	public void writeDeal(Customer customer, Constructor mainConstructor, Constructor[] brigade) 
			throws IOException{
		assert(customer != null && mainConstructor != null && brigade != null);
		FileOutputStream fos = new FileOutputStream(file);
		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(customer);
			oos.writeObject(mainConstructor);
			oos.writeInt(brigade.length);
			for (int i = 0; i < brigade.length; i++) {
				oos.writeObject(brigade[i]);
			}
			oos.flush();
		}
	}
	
	public ReadingData readDeal() throws IOException, ClassNotFoundException{
		
		Customer customer;
		Constructor mainConstructor;
		Constructor[] brigade;
		
		FileInputStream fis = new FileInputStream(file);
		try (ObjectInputStream oin = new ObjectInputStream(fis)) {
			
			customer = (Customer) oin.readObject();
			mainConstructor = (Constructor) oin.readObject();
			
			int length = oin.readInt();
			brigade = new Constructor[length];
			for (int i = 0; i < length; i++) {
				brigade[i] = (Constructor) oin.readObject();
			}
		}
		
		return new ReadingData(customer, mainConstructor, brigade);
		
	}

}
