package tuc.ece.cs201.diskdatapage;

import java.io.*;

public class DataPageHandling {
	
	public DataPageHandling() {
		
	}
	
	public boolean createFile(String name) throws IOException {
		try {
			RandomAccessFile raf = new RandomAccessFile(name, "rw");
			raf.setLength(0);
			raf.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public RandomAccessFile openFile(String name) {
		try {
			RandomAccessFile raf = new RandomAccessFile(name, "rw");
			return raf;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public boolean CloseFile(String name) {
		try {
			RandomAccessFile raf = new RandomAccessFile(name, "rw");
			raf.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public void writePage(DataPage page) throws IOException {  // writes given page to disk
		RandomAccessFile raf = openFile("fileName.bin");
		int numberOfPoints = page.getNumberOfPoints();
		int nextPage = page.getNextPage();  //gia to b2
		int[] data = page.getData();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(numberOfPoints);
		dos.writeInt(nextPage);
		int endOfPage = numberOfPoints*2;
		for (int i = 0; i < endOfPage; i++) {
			dos.writeInt(data[i]);
		}
		dos.close();
		byte[] buffer = bos.toByteArray();  // Creates a newly allocated byte array.
		byte[] WriteDataPage = new byte[DataPage.PAGE_SIZE];
		System.arraycopy(buffer, 0, WriteDataPage, 0, buffer.length);  // Copy buffer data to DataPage of DataPageSize
		bos.close();
		raf.seek(page.getPosition());
		raf.write(WriteDataPage);
//		raf.close();
	}
	
	public DataPage readPage(int position) throws IOException {  // reads page size of data from file from position position and constructs a DataPage
		RandomAccessFile raf = openFile("fileName.bin");
		byte[] ReadDataPage = new byte[DataPage.PAGE_SIZE];
		raf.seek(position);
		raf.read(ReadDataPage);
		ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
		DataInputStream dis = new DataInputStream(bis);
		int numberOfPoints = dis.readInt();
		int nextPage = dis.readInt();
		int[] data = new int[numberOfPoints*2];
		int endOfPage = numberOfPoints*2;
		for (int i = 0; i < endOfPage; i++) {
			data[i] = dis.readInt();
		}
//		raf.close();
		DataPage page = new DataPage(data, numberOfPoints, position, nextPage);
		return page;
	}

}
