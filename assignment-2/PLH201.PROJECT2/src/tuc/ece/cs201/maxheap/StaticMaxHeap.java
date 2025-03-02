package tuc.ece.cs201.maxheap;

import tuc.ece.cs201.multicounter.MultiCounter;

public class StaticMaxHeap {
	private int[] heap;  //Pointer to the heap array
	private int size;  //number of elements now in the heap
	private int capacity;  //Maximum size of the heap
	
	public StaticMaxHeap(int capacity) {
		this.capacity = capacity;
		size = 0;
		heap = new int[capacity];
	}
	
	private int parent(int pos) {  //Return parent position
		return (pos - 1)/2;
	}
	
	private int leftChild(int pos) {
		return 2*pos + 1;
	}
	
	private int rightChild(int pos) {
		return 2*pos + 2;
	}
	
	private boolean isLeaf(int pos) {
		return MultiCounter.increaseCounter(7) && (pos >= size/2) && MultiCounter.increaseCounter(7) && (pos < size);
	}
	
	private void swap(int fpos, int spos) {
        int tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }
	
	public void insert(int key) {
		if (MultiCounter.increaseCounter(5) && size == capacity)
			throw new IllegalArgumentException("The Heap is full");
		heap[size] = key;
		int current = size;  //Traverse up and fix violated property
        while (MultiCounter.increaseCounter(5) && heap[current] > heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
            MultiCounter.increaseCounter(5, 4);
        }
        size++;
        MultiCounter.increaseCounter(5, 3);
	}
	
	public int removeMax() {
		if(MultiCounter.increaseCounter(7) && size == 0)
			throw new IllegalArgumentException("The Heap is empty");
        int maxKey = heap[0];
        heap[0] = heap[--size];  //Decrease size by 1 and move the last key at the top of the heap
        MultiCounter.increaseCounter(7, 3);  //For the assignment of maxKey, heap[0] and --size
        maxHeapify(0);
        return maxKey;
	}
	
	private void maxHeapify(int pos) {  //Moves the node at pos down the max-heap until it no longer violates the max-heap property (that is, the node is not smaller than its children)
        if (isLeaf(pos))
        	return;
        if (MultiCounter.increaseCounter(7) && heap[pos] < heap[leftChild(pos)] || (MultiCounter.increaseCounter(7) && rightChild(pos) < size && MultiCounter.increaseCounter(7) && heap[pos] < heap[rightChild(pos)])) {
            if (MultiCounter.increaseCounter(7) && heap[leftChild(pos)] > heap[rightChild(pos)]) {
            	swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            } else {
            	swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
            MultiCounter.increaseCounter(7, 3);  //For swap
        }
	}
	
	public void buildHeap(int[] keys) {
		System.arraycopy(keys, 0, heap, 0, keys.length);
		size = keys.length;
		for (int i = (size/2) - 1; i >= 0; i--) {  //Starting from second to last level
			maxHeapify(i);
		}
	}
	
	public void print() {
		for (int i = 0; i < size/2; i++) {
			System.out.print("Parent: " + heap[i]);
			System.out.print(", Left child: " + heap[leftChild(i)]);
			if (rightChild(i) < size)
				System.out.print(", Right child: " + heap[rightChild(i)]);
			System.out.println();
		}
	}
	
	public int getSize() {
		return size;
	}
	
}
