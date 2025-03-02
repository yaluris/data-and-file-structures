package tuc.ece.cs201.hashtable;

import tuc.ece.cs201.interfaces.Structure;
import tuc.ece.cs201.linkedlist.List;

public class Hash implements Structure {  //Hash table (data structure) implementing insert and search
	private List[] table;  //An array of singly linked lists
	private static final int TABLE_SIZE = 100;  //The size of the hash table is set to 100 for better comparison of the structures
	private static final int N = 262144;  //x and y take values from 0 to N-1, N = 2^18 = 262144
	
	public Hash() {  //Initialize the table and the lists in each position of the table
		table = new List[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++) {
			table[i] = new List();
		}
	}
	
	public List[] getTable() {
		return table;
	}

	public void setTable(List[] table) {
		this.table = table;
	}

	public int getHash(int x, int y) {  //Calculate position Hash(x, y) of the table
		return (int)(((long)x*N + y)%TABLE_SIZE);
	}
	
	public void insert(int x, int y) {  //Each point (x, y) is inserted as the last node of the list starting with position Hash(x, y) of the table
		int tableIndex = getHash(x, y);
		table[tableIndex].insert(x, y);
	}
	
	public boolean search(int x, int y) {  //Search for a point (x, y) in the hash table. Includes functionality to count number of comparisons
		int tableIndex = getHash(x, y);
		if (table[tableIndex].search(x, y))
			return true;  //The point was found
		else
			return false;  //The point was not found
	}
	
}
