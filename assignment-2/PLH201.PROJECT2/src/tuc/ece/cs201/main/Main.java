package tuc.ece.cs201.main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import tuc.ece.cs201.binarysearchtree.*;
import tuc.ece.cs201.maxheap.*;
import tuc.ece.cs201.multicounter.MultiCounter;

public class Main {
	static final int NUM_OF_KEYS = 1000000;
	static final int NUM_OF_DELETIONS = 100;
	
	public static void main(String[] args) throws IOException {
		
		int[] keysForInsertion = new int[NUM_OF_KEYS];
		int[] keysForDeletion = new int[NUM_OF_DELETIONS];
		long[] counters = new long[8];
		long startTime;
		long[] elapsedTime = new long[10];
		
		StaticBST sbst = new StaticBST(NUM_OF_KEYS);
		DynamicBST dbst = new DynamicBST();
		StaticMaxHeap smh = new StaticMaxHeap(NUM_OF_KEYS);
		DynamicMaxHeap dmh = new DynamicMaxHeap();
		StaticMaxHeap smh2 = new StaticMaxHeap(NUM_OF_KEYS);
		DynamicMaxHeap dmh2 = new DynamicMaxHeap();
		
		RandomAccessFile raf = new RandomAccessFile("keys_1000000_BE.bin", "rw");
		byte[] buffer = new byte[4*NUM_OF_KEYS];
		raf.seek(0);
		raf.read(buffer);
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
		DataInputStream dis = new DataInputStream(bis);
		for (int i = 0; i < NUM_OF_KEYS; i++) {
			keysForInsertion[i] = dis.readInt();
		}
		
		RandomAccessFile raf2 = new RandomAccessFile("keys_del_100_BE.bin", "rw");
		byte[] buffer2 = new byte[4*NUM_OF_DELETIONS];
		raf2.seek(0);
		raf2.read(buffer2);
		ByteArrayInputStream bis2 = new ByteArrayInputStream(buffer2);
		DataInputStream dis2 = new DataInputStream(bis2);
		for (int i = 0; i < NUM_OF_DELETIONS; i++) {
			keysForDeletion[i] = dis2.readInt();
		}
		
		raf.close();
		raf2.close();
		
		//=========================================== BST insertion
		
		MultiCounter.resetCounter(1);
		MultiCounter.resetCounter(2);
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_KEYS; i++) {
			sbst.insert(keysForInsertion[i]);
		}
		elapsedTime[0] = System.nanoTime() - startTime;
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_KEYS; i++) {
			dbst.insert(keysForInsertion[i]);
		}
		elapsedTime[1] = System.nanoTime() - startTime;
		
		counters[0] = MultiCounter.getCount(1);
		counters[1] = MultiCounter.getCount(2);
		
	    //=========================================== BST deletion
		
		MultiCounter.resetCounter(3);
		MultiCounter.resetCounter(4);
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_DELETIONS; i++) {
			sbst.delete(keysForDeletion[i]);
		}
		elapsedTime[2] = System.nanoTime() - startTime;
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_DELETIONS; i++) {
			dbst.delete(keysForDeletion[i]);
		}
		elapsedTime[3] = System.nanoTime() - startTime;
		
		counters[2] = MultiCounter.getCount(3);
		counters[3] = MultiCounter.getCount(4);
		
		//=========================================== MaxHeap insertion
		
		MultiCounter.resetCounter(5);
		MultiCounter.resetCounter(6);
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_KEYS; i++) {
			smh.insert(keysForInsertion[i]);
		}
		elapsedTime[4] = System.nanoTime() - startTime;
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_KEYS; i++) {
			dmh.insert(keysForInsertion[i]);
		}
		elapsedTime[5] = System.nanoTime() - startTime;
		
		counters[4] = MultiCounter.getCount(5);
		counters[5] = MultiCounter.getCount(6);
		
		//=========================================== MaxHeap deletion
		
		MultiCounter.resetCounter(7);
		MultiCounter.resetCounter(8);
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_DELETIONS; i++) {
			smh.removeMax();
		}
		elapsedTime[6] = System.nanoTime() - startTime;
		
		startTime = System.nanoTime();
		for (int i = 0; i < NUM_OF_DELETIONS; i++) {
			dmh.removeMax();
		}
		elapsedTime[7] = System.nanoTime() - startTime;
		
		counters[6] = MultiCounter.getCount(7);
		counters[7] = MultiCounter.getCount(8);
		
		//=========================================== MaxHeap building
		
		startTime = System.nanoTime();
		smh2.buildHeap(keysForInsertion);
		elapsedTime[8] = System.nanoTime() - startTime;
		
		startTime = System.nanoTime();
		dmh2.buildHeap(keysForInsertion);
		elapsedTime[9] = System.nanoTime() - startTime;
		
		System.out.println("-------------------------------------------BINARYSEARCHTREE---------------------------------------------------");
		System.out.println("                                  *Average number of comparisons*                                             ");
		System.out.println("Average number of comparisons for StaticBST insertion of 1000000 keys: " + counters[0]/NUM_OF_KEYS);
		System.out.println("Average number of comparisons for DynamicBST insertion of 1000000 keys: " + counters[1]/NUM_OF_KEYS);
		System.out.println("Average number of comparisons for StaticBST deletion of 100 keys: " + counters[2]/NUM_OF_DELETIONS);
		System.out.println("Average number of comparisons for DynamicBST deletion of 100 keys: " + counters[3]/NUM_OF_DELETIONS);
		System.out.println("                                            *Total time*                                                      ");		
		System.out.printf("Total time for StaticBST insertion of 1000000 keys (in sec): %f\n", elapsedTime[0]*Math.pow(10, -9));
		System.out.printf("Total time for DynamicBST insertion of 1000000 keys (in sec): %f\n", elapsedTime[1]*Math.pow(10, -9));
		System.out.printf("Total time for StaticBST deletion of 100 keys (in sec): %f\n", elapsedTime[2]*Math.pow(10, -9));
		System.out.printf("Total time for DynamicBST deletion of 100 keys (in sec): %f\n", elapsedTime[3]*Math.pow(10, -9));
		System.out.println("-----------------------------------------------MAXHEAP--------------------------------------------------------");
		System.out.println("                                  *Average number of comparisons*                                             ");
		System.out.println("Average number of comparisons for StaticMaxHeap insertion of 1000000 keys: " + counters[4]/NUM_OF_KEYS);
		System.out.println("Average number of comparisons for DynamicMaxHeap insertion of 1000000 keys: " + counters[5]/NUM_OF_KEYS);
		System.out.println("Average number of comparisons for StaticMaxHeap deletion of 100 keys: " + counters[6]/NUM_OF_DELETIONS);
		System.out.println("Average number of comparisons for DynamicMaxHeap deletion of 100 keys: " + counters[7]/NUM_OF_DELETIONS);
		System.out.println("                                            *Total time*                                                      ");
		System.out.printf("Total time for StaticMaxHeap insertion of 1000000 keys (in sec): %f\n", elapsedTime[4]*Math.pow(10, -9));
		System.out.printf("Total time for DynamicMaxHeap insertion of 1000000 keys (in sec): %f\n", elapsedTime[5]*Math.pow(10, -9));
		System.out.printf("Total time for StaticMaxHeap deletion of 100 keys (in sec): %f\n", elapsedTime[6]*Math.pow(10, -9));
		System.out.printf("Total time for DynamicMaxHeap deletion of 100 keys (in sec): %f\n", elapsedTime[7]*Math.pow(10, -9));
		System.out.printf("Total time for StaticMaxHeap building of 1000000 keys (in sec): %f\n", elapsedTime[8]*Math.pow(10, -9));
		System.out.printf("Total time for DynamicMaxHeap building of 1000000 keys (in sec): %f\n", elapsedTime[9]*Math.pow(10, -9));
		
	}
	
}
