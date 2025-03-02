package tuc.ece.cs201.maxheap;

import tuc.ece.cs201.multicounter.MultiCounter;

public class DynamicMaxHeap {
	public static class Node {  //node class (static nested class) that defines Heap node
		private int key;
		private Node parent, left, right;
		
        public Node(int key) {
            this.key = key;
            parent = left = right = null;
        }  
    }
	private Node root;
	private int size;
	
	public DynamicMaxHeap() {
		root = null;
		size = 0;
	}
	
	private boolean isLeaf(Node n) {
		return MultiCounter.increaseCounter(8) && n.left == null;  //The right child can only be present if the left child is also present
	}
	
	private void swap(Node n1, Node n2) {
		int tmp = n1.key;
		n1.key = n2.key;
		n2.key = tmp;
    }
	
	private Node getParentOfRightMost(Node rootNode, int numOfNodes) {  //Returns the parent of the right most child in a tree with numberOfNodes nodes
		Node current = rootNode;
		String asBinary = Integer.toBinaryString(numOfNodes);  //Binary of numOfNodes without leading zeros
		for (int currentPos = 1; MultiCounter.increaseCounter(6) && MultiCounter.increaseCounter(8) && currentPos < asBinary.length() - 1; currentPos++) {  //Iterate from 2nd bit up to second to last bit
			if (MultiCounter.increaseCounter(6) && MultiCounter.increaseCounter(8) && asBinary.charAt(currentPos) == '0')
				current = current.left;
			else
				current = current.right;
			MultiCounter.increaseCounter(6, 2);  //For the assignment of current and currentPos++
			MultiCounter.increaseCounter(8, 2);
		}
		MultiCounter.increaseCounter(6, 3);  //For the assignment of current, asBinary and currentPos
		MultiCounter.increaseCounter(8, 3);
		return current;
	}
	
	public void insert(int key) {
		if (MultiCounter.increaseCounter(6) && root == null) {  //Heap is empty
			root = new Node(key);
			size++;
			MultiCounter.increaseCounter(6, 6);
			return;
		}
		Node newNode = new Node(key);
		size++;
		Node current = getParentOfRightMost(root, size);
		MultiCounter.increaseCounter(6, 7);
		if (MultiCounter.increaseCounter(6) && current.left == null) {
			current.left = newNode;
			newNode.parent = current;
		} else {
			current.right = newNode;
			newNode.parent = current;
		}
		Node tmp = newNode;
		MultiCounter.increaseCounter(6, 3);
		while (MultiCounter.increaseCounter(6) && tmp.parent != null && MultiCounter.increaseCounter(6) && tmp.key > tmp.parent.key) {
			swap(tmp, tmp.parent);
			tmp = tmp.parent;
			MultiCounter.increaseCounter(6, 4);
		}
	}
	
	public int removeMax() {
		if (MultiCounter.increaseCounter(8) && root == null)
			throw new IllegalArgumentException("The Heap is empty");
        int maxKey = root.key;
        Node current = getParentOfRightMost(root, size);
        if (MultiCounter.increaseCounter(8) && current.right == null) {
        	root.key = current.left.key;
        	current.left = null;
        } else {
        	root.key = current.right.key;
        	current.right = null;
        }
        size--;
        MultiCounter.increaseCounter(8, 5);  //For the assignment of maxKey, current, root.key, current.left/right and size--
        maxHeapify(root);
        return maxKey;
	}
	
	private void maxHeapify(Node n) {
		if (isLeaf(n))
			return;
		if (MultiCounter.increaseCounter(8) && n.key < n.left.key || (MultiCounter.increaseCounter(8) && n.right != null && MultiCounter.increaseCounter(8) && n.key < n.right.key)) {
			if (MultiCounter.increaseCounter(8) && n.left.key > n.right.key) {
				swap(n, n.left);
				maxHeapify(n.left);
			} else {
				swap(n, n.right);
				maxHeapify(n.right);	
			}
			MultiCounter.increaseCounter(8, 3);  //For swap
		}
	}
	
	public void buildHeap(int[] keys) {
		Node[] nodes = new Node[keys.length];
		for (int i = 0; i < keys.length; i++) {
			nodes[i] = new Node(keys[i]);
		}
		for (int i = 0; i < keys.length; i++) {
			nodes[i].parent = nodes[(i - 1)/2];
			if (i < keys.length/2) {
				nodes[i].left = nodes[2*i + 1];
				if (2*i + 2 < keys.length)
					nodes[i].right = nodes[2*i + 2];
			}
		}
		root = nodes[0];  //Tree created
		size = keys.length;
		for (int i = (size/2) - 1; i >= 0; i--) {
			maxHeapify(nodes[i]);
		}
	}
	
	public void print() {
		for (int i = 2; i <= size; i += 2) {
			Node current = getParentOfRightMost(root, i);
			System.out.print("Parent: " + current.key);
			System.out.print(", Left child: " + current.left.key);
			if (current.right != null)
				System.out.print(", Right child: " + current.right.key);
			System.out.println();
		}
	}
	
	public int getSize() {
		return size;
	}
	
}
