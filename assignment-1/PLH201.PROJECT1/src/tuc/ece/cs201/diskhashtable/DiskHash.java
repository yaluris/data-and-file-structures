package tuc.ece.cs201.diskhashtable;

import tuc.ece.cs201.diskdatapage.*;
import tuc.ece.cs201.disklinkedlist.DiskList;
import tuc.ece.cs201.interfaces.Structure;

public class DiskHash extends DataPageHandling implements Structure {
	private DiskList[] table;
	private static final int TABLE_SIZE = 100;
	private static final int N = 262144;  //x and y take values from 0 to N-1, N = 2^18 = 262144
	
	public DiskHash() {
		table = new DiskList[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++) {
			table[i] = new DiskList();
		}
	}

	public DiskList[] getTable() {
		return table;
	}

	public void setTable(DiskList[] table) {
		this.table = table;
	}
	
	public int getHash(int x, int y) {
		return (int)(((long)x*N + y)%TABLE_SIZE);
	}
	
	public void insert(int x, int y) {
		int tableIndex = getHash(x, y);
		table[tableIndex].insert(x, y);
	}
	
	public boolean search(int x, int y) {
		int tableIndex = getHash(x, y);
		if (table[tableIndex].search(x, y))
			return true;
		else
			return false;
	}
	
}
