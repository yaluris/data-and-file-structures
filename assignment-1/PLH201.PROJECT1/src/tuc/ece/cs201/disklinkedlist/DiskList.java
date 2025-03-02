package tuc.ece.cs201.disklinkedlist;

import java.io.IOException;
import java.io.RandomAccessFile;
import tuc.ece.cs201.diskdatapage.*;
import tuc.ece.cs201.interfaces.Structure;
import tuc.ece.cs201.multicounter.MultiCounter;

public class DiskList extends DataPageHandling implements Structure {  //Singly linked list of data pages (file structure) implementing insert and search
	private int firstPage;  //Position of the first page of the list (in bytes). Int instead of long, because we have a limited amount of data
	private int lastPage;  //Position of the last page of the list (in bytes). Int instead of long, because we have a limited amount of data
	
	public DiskList() {  //Initialize the list to be empty
		firstPage = -1;
		lastPage = -1;
	}
	
	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	
	public void insert(int x, int y) {
		try {
			//These conditional statements delay the operation a lot, especially for 100000 points
//			if (x < 0 || x > 262143)
//				throw new IllegalArgumentException("Value of x is out of range");
//			if (y < 0 || y > 262143)
//				throw new IllegalArgumentException("Value of y is out of range");
//			if (search(x, y))
//				throw new IllegalArgumentException("This point has already been inserted");
			if (firstPage == -1) {  //Insert the first point (x, y) into the first page of the list
				int data[] = new int[2];
				data[0] = x;
				data[1] = y;
				RandomAccessFile raf = openFile("fileName.bin");
				DataPage page = new DataPage(data, 1, (int)raf.length(), (int)raf.length() + DataPage.PAGE_SIZE);
				firstPage = (int)raf.length();
				lastPage = (int)raf.length();
				writePage(page);
			} else {
				DataPage page = readPage(lastPage);
				if (page.getNumberOfPoints() < 31) {  //Check if the page is full
					int[] data = page.getData();
					int[] newData = new int[data.length + 2];
					System.arraycopy(data, 0, newData, 0, data.length);  // Copy data buffer to newData of DataPageSize
					newData[data.length] = x;
					newData[data.length + 1] = y;
					RandomAccessFile raf = openFile("fileName.bin");
					DataPage newPage = new DataPage(newData, page.getNumberOfPoints() + 1, lastPage, (int)raf.length());
					writePage(newPage);
				} else {
					RandomAccessFile raf = openFile("fileName.bin");
					DataPage newPage = new DataPage(page.getData(), page.getNumberOfPoints(), lastPage, (int)raf.length());
					int data[] = new int[2];
					data[0] = x;
					data[1] = y;
					DataPage newNextPage = new DataPage(data, 1, (int)raf.length(), (int)raf.length() + DataPage.PAGE_SIZE);
					lastPage = (int)raf.length();
					writePage(newPage);
					writePage(newNextPage);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean search(int x, int y) {  //Includes functionality to count number of disk accesses
		try {
		if (firstPage == -1) {
			return false;
		} else {
			int position = firstPage;
			while (position <= lastPage) {
				MultiCounter.increaseCounter(2);
				DataPage page = readPage(position);
				int numberOfPoints = page.getNumberOfPoints();
				int[] data = page.getData();
				int endOfPage = numberOfPoints*2;
				for (int i = 0; i < endOfPage; i += 2) {
					if (x == data[i] && y == data[i + 1])
						return true;
				}
				position = page.getNextPage();
			}
			return false;
		}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
