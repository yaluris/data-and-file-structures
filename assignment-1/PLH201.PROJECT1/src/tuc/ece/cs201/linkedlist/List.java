package tuc.ece.cs201.linkedlist;

import tuc.ece.cs201.interfaces.Structure;
import tuc.ece.cs201.multicounter.MultiCounter;

public class List implements Structure {  //Singly linked list (data structure) implementing insert and search
	private Node head;
	private Node tail;
	
	public List() {  //Initialize the list to be empty
		head = null;
		tail = null;
	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public Node getTail() {
		return tail;
	}

	public void setTail(Node tail) {
		this.tail = tail;
	}

	public void insert(int x, int y) {  //Insert a new node at the end of the list
		if (x < 0 || x > 262143)  //x takes values from 0 to N-1, N = 2^18 = 262144
			throw new IllegalArgumentException("Value of x is out of range");
		if (y < 0 || y > 262143)  //y takes values from 0 to N-1, N = 2^18 = 262144
			throw new IllegalArgumentException("Value of y is out of range");
		if (search(x, y))  //No duplicate points allowed
			throw new IllegalArgumentException("This point has already been inserted");	
		Node newNode = new Node(x, y);
		if (head == null) {  //Insert the first node of the list
			head = newNode;
			tail = newNode;
		} else {  //Connect the tail of the list to the next node and insert the next node
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	public boolean search(int x, int y) {  //Search for a point (x, y) in the list. Includes functionality to count number of comparisons
		for (Node current = head; MultiCounter.increaseCounter(1) && current != null; current = current.getNext()) {
			MultiCounter.increaseCounter(1);  //Increase counter because of getNext()
			if (MultiCounter.increaseCounter(1) && x == current.getX() && MultiCounter.increaseCounter(1) && y == current.getY())  //If the first condition returns false, then the next condition won't be evaluated
				return true;  //The point was found
		}
		return false;  //The point was not found
	}
	
}
