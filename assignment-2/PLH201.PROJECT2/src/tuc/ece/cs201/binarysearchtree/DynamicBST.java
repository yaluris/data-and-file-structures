package tuc.ece.cs201.binarysearchtree;

import tuc.ece.cs201.multicounter.MultiCounter;

public class DynamicBST {
	public static class Node {  //node class (static nested class) that defines BST node
		private int key;
		private Node left, right;
		
        public Node(int key) {
            this.key = key;
            left = right = null;
        }
    }
	private Node root;  //BST root node
	
	public DynamicBST() {  //Constructor for BST =>initial empty tree
        root = null;
    }
	
	public void insert(int key) {  //insert a node in BST
        root = insertRecursive(root, key);
    }
	
	private Node insertRecursive(Node root, int key) {  //recursive insert function
        if (MultiCounter.increaseCounter(2) && root == null) {  //tree is empty
            root = new Node(key);
            MultiCounter.increaseCounter(2, 4);  //Fixed increaseCounter function by changing counterIndex to counterIndex - 1
        } else if (MultiCounter.increaseCounter(2) && key < root.key) {  //insert in the left subtree
            root.left = insertRecursive(root.left, key);
            MultiCounter.increaseCounter(2);
        } else if (MultiCounter.increaseCounter(2) && key > root.key) {  //New element's key is greater than the root's key, so it goes to the right subtree
            root.right = insertRecursive(root.right, key);
            MultiCounter.increaseCounter(2);
        }
        return root;  //return pointer
    }
	
	public boolean search(int key) {
		return searchRecursive(root, key);
	}
	
	private boolean searchRecursive(Node root, int key) {
		if (root == null)  //The BST is empty
			return false;  //The key was not found
		else if (key == root.key)
			return true;  //The key was found
		else if (key < root.key)
			return searchRecursive(root.left, key);
		else
			return searchRecursive(root.right, key);
	}
	
	public void delete(int key) {  //delete a node from BST
		root = deleteRecursive(root, key);
	}

	private Node deleteRecursive(Node root, int key) {  //recursive delete function
        if (MultiCounter.increaseCounter(4) && root == null)  //tree is empty
        	return root;
        if (MultiCounter.increaseCounter(4) && key < root.key) {  //traverse left subtree
            root.left = deleteRecursive(root.left, key);
            MultiCounter.increaseCounter(4);
        } else if (MultiCounter.increaseCounter(4) && key > root.key) {  //traverse right subtree
            root.right = deleteRecursive(root.right, key);
            MultiCounter.increaseCounter(4);
        } else {
        	//node with only one child or no child
            if (MultiCounter.increaseCounter(4) && root.left == null)
                return root.right;
            else if (MultiCounter.increaseCounter(4) && root.right == null)
                return root.left;
            // node has two children;
            root.key = getMinKey(root.right);  //get inorder successor (min value in the right subtree)
            root.right = deleteRecursive(root.right, root.key);  // Delete the inorder successor (duplicate)
            MultiCounter.increaseCounter(4, 2);
        }
        return root;
    }
	
	private int getMinKey(Node root) {
        int minKey = root.key;  //initially minval = root
        MultiCounter.increaseCounter(4);
        while (MultiCounter.increaseCounter(4) && root.left != null) {  //find minval
        	minKey = root.left.key;
            root = root.left;
            MultiCounter.increaseCounter(4, 2);
        }
        return minKey;
    }
	
	public void inorder() {
		inorderRecursive(root);
		System.out.println();
	}
	
	private void inorderRecursive(Node root) {  //recursively traverse the BST
        if (root != null) {
        	inorderRecursive(root.left);
            System.out.print(root.key + " ");
            inorderRecursive(root.right);
        }
    }
	
}
