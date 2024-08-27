package bufferPack;

import javax.swing.*;

import repairWork.Bill;

import java.io.*;
import java.util.ArrayList;

public class Connector {
	
    private File file;

    public Connector(File file) {
        this.file = file;
    }

    public void write(DefaultListModel<Bill> data) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(this.file, "rw")) {
            for(int i=0;i<data.getSize();++i)
                Buffer.writeObject(raf,data.get(i));
        }
    }

    public ArrayList<Bill> read() throws IOException {
        ArrayList<Bill> result = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(this.file, "r")) {
            long position = 0;
            while ((position = raf.getFilePointer()) < raf.length()) {
            	Bill acc = (Bill) Buffer.readObject(raf, position);
                result.add(acc);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}