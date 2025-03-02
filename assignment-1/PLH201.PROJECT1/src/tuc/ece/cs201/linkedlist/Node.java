package tuc.ece.cs201.linkedlist;

public class Node {  //Each list node contains a point (x, y) and a pointer to the next node
	private int x;
	private int y;
	private Node next;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		next = null;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
}
