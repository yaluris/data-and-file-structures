package tuc.ece.cs201.binarysearchtree;

import tuc.ece.cs201.multicounter.MultiCounter;

public class StaticBST {
	private int[][] array;
	private int avail;  //Pointer to the next available position (column) to be used in the next element insertion
	private int root;
	private static final int INFO = 0;  //First row contains the value of the element
	private static final int LEFT = 1;  //Second row contains the position (column) of the left child
	private static final int RIGHT = 2;  //Third row contains the position (column) of the right child OR it is used to implement the stack with the available positions (when there is no element)
	
	public StaticBST(int numOfElements) {
		array = new int[3][numOfElements];  //3 rows and numOfElements columns. 3*N instead of N*3 because Java creates an object for each row, which will delay the implementation
		for (int i = 0; i < numOfElements; i++) {  //Initialize the BST to be empty
			array[INFO][i] = Integer.MIN_VALUE;  //Use -2147483648 as null
			array[LEFT][i] = -1;  //Use -1 as null
			array[RIGHT][i] = i + 1;  //Each position (column) points to the next available one using the third row
		}
		array[RIGHT][numOfElements - 1] = -1;  //No available  position (column) after the last one
		avail = 0;  //First available position (column)
		root = -1;  //The BST is empty, so there is no root
	}
	
	public void insert(int key) {  //Insert a new distinct key in the BST
		root = insertRecursive(root, key);
	}
	
	private int insertRecursive(int root, int key) {
//		if (avail == -1)  //No available position (column). This condition will affect the measurements
//			throw new IllegalArgumentException("The BST is full");
		if (MultiCounter.increaseCounter(1) && root == -1) {  //Insert the first key of the BST
			avail = array[RIGHT][0];
			array[INFO][0] = key;
			array[RIGHT][0] = -1;  //New element has no children
			root = 0;
			MultiCounter.increaseCounter(1, 4);
		} else if (MultiCounter.increaseCounter(1) && key < array[INFO][root]) {  //New element's key is smaller than the root's key, so it goes to the left subtree
			if (MultiCounter.increaseCounter(1) && array[LEFT][root] == -1) {  //Root element has no left child
				array[LEFT][root] = avail;  //New element becomes the left child of the root
				array[INFO][avail] = key;
				avail = array[RIGHT][avail];  //Change avail to point to the next available position (column)
				array[RIGHT][array[LEFT][root]] = -1;  //New element has no children
				MultiCounter.increaseCounter(1, 4);
			} else
				insertRecursive(array[LEFT][root], key);
		} else if (MultiCounter.increaseCounter(1) && key > array[INFO][root]) {  //New element's key is greater than the root's key, so it goes to the right subtree
			if (MultiCounter.increaseCounter(1) && array[RIGHT][root] == -1) {  //Root element has no right child
				array[RIGHT][root] = avail;  //New element becomes the right child of the root
				array[INFO][avail] = key;
				avail = array[RIGHT][avail];  //Change avail to point to the next available position (column)
				array[RIGHT][array[RIGHT][root]] = -1;  //New element has no children
				MultiCounter.increaseCounter(1, 4);
			} else
				insertRecursive(array[RIGHT][root], key);
	    }
		return root;  //root = 0
	}
	
	public boolean search(int key) {
		return searchRecursive(root, key);
	}
	
	private boolean searchRecursive(int root, int key) {
		if (root == -1)  //The BST is empty
			return false;  //The key was not found
		else if (key == array[INFO][root])
			return true;  //The key was found
		else if (key < array[INFO][root])
			return searchRecursive(array[LEFT][root], key);
		else
			return searchRecursive(array[RIGHT][root], key);
	}
	
	public void delete(int key) {
		root = deleteRecursive(root, key);
	}
	
	private int deleteRecursive(int root, int key) {
		if (MultiCounter.increaseCounter(3) && root == -1)
			return root;  //The key was not found
		if (MultiCounter.increaseCounter(3) && key < array[INFO][root]) {
			array[LEFT][root] = deleteRecursive(array[LEFT][root], key);
			MultiCounter.increaseCounter(3);
		} else if (MultiCounter.increaseCounter(3) && key > array[INFO][root]) {
			array[RIGHT][root] = deleteRecursive(array[RIGHT][root], key);
			MultiCounter.increaseCounter(3);
		} else {  //The key was found (key == array[INFO][root])
			if (MultiCounter.increaseCounter(3) && array[LEFT][root] == -1)
				return array[RIGHT][root];
			else if (MultiCounter.increaseCounter(3) && array[RIGHT][root] == -1)
				return array[LEFT][root];
			array[INFO][root] = getMinKey(array[RIGHT][root]);
			array[RIGHT][root] = deleteRecursive(array[RIGHT][root], array[INFO][root]);
			MultiCounter.increaseCounter(3, 2);
		}
		return root;  //root = 0
	}
	
	private int getMinKey(int root) {
		int minKey = array[INFO][root];
		MultiCounter.increaseCounter(3);
		while (MultiCounter.increaseCounter(3) && array[LEFT][root] != -1) {
			root = array[LEFT][root];
			minKey = array[INFO][root];
			MultiCounter.increaseCounter(3, 2);
		}
		return minKey;
	}
	
	public void inorder() {
		inorderRecursive(root);
		System.out.println();
	}
	
	private void inorderRecursive(int root) {
		if (root != -1) {
			inorderRecursive(array[LEFT][root]);
			System.out.print(array[INFO][root] + " ");
			inorderRecursive(array[RIGHT][root]);
		}
	}
	
}
