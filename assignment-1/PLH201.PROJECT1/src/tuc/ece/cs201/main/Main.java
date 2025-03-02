package tuc.ece.cs201.main;
import java.io.IOException;
import java.util.Random;
import tuc.ece.cs201.diskdatapage.DataPageHandling;
import tuc.ece.cs201.diskhashtable.DiskHash;
import tuc.ece.cs201.disklinkedlist.DiskList;
import tuc.ece.cs201.hashtable.Hash;
import tuc.ece.cs201.linkedlist.List;
import tuc.ece.cs201.multicounter.MultiCounter;
import tuc.ece.cs201.interfaces.Structure;

public class Main {

	public static void main(String[] args) throws IOException {
		
		int[] k = {1000, 10000, 30000, 50000, 70000, 100000};
		int[] existingPoint = new int[2];
		int[] existingPoints = new int[200];
		int[] nonExistingPoint = new int[2];
		int[] nonExistingPoints = new int[200];
		DataPageHandling dataPageHandling =  new DataPageHandling();
		
		for (int j = 0; j < k.length; j++) {
			int[] x = intArrayGenerator(0, 262143, k[j]);
			int[] y = intArrayGenerator(0, 262143, k[j]);
			System.out.println("-------------------------------- K = " + k[j] + " --------------------------------");
			
	///////////////////////////////////////////////////////////////////	
			
			List list = new List();
			for (int i = 0; i < k[j]; i++) {
				list.insert(x[i], y[i]);
			}
			for (int i = 0; i < 200; i += 2) {
				existingPoint = getRandomExistingPoint(x, y);
				existingPoints[i] = existingPoint[0];
				existingPoints[i + 1] = existingPoint[1];
			}
			MultiCounter.resetCounter(1);
			for (int i = 0; i < 200; i += 2) {
				list.search(existingPoints[i], existingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for successful List searches: " + MultiCounter.getCount(1)/100);
			
			
			for (int i = 0; i < 200; i += 2) {
				nonExistingPoint = getRandomNonExistingPoint(list, 262143);
				nonExistingPoints[i] = nonExistingPoint[0];
				nonExistingPoints[i + 1] = nonExistingPoint[1];
			}
			MultiCounter.resetCounter(1);
			for (int i = 0; i < 200; i += 2) {
				list.search(nonExistingPoints[i], nonExistingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for failed List searches: " + MultiCounter.getCount(1)/100);
			
	    ///////////////////////////////////////////////////////////////////
			
			Hash hash = new Hash();
			for (int i = 0; i < k[j]; i++) {
				hash.insert(x[i], y[i]);
			}
			MultiCounter.resetCounter(1);
			for (int i = 0; i < 200; i += 2) {
				hash.search(existingPoints[i], existingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for successful Hash searches: " + MultiCounter.getCount(1)/100);
			
			MultiCounter.resetCounter(1);
			for (int i = 0; i < 200; i += 2) {
				hash.search(nonExistingPoints[i], nonExistingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for failed Hash searches: " + MultiCounter.getCount(1)/100);
			
           ////////////////////////////////////////////////////////////////			
		
			dataPageHandling.createFile("fileName.bin");
			
			DiskList diskList = new DiskList();
			for (int i = 0; i < k[j]; i++) {
				diskList.insert(x[i], y[i]);
			}
			MultiCounter.resetCounter(2);
			for (int i = 0; i < 200; i += 2) {
				diskList.search(existingPoints[i], existingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for successful DiskList searches: " + MultiCounter.getCount(2)/100);
			
			MultiCounter.resetCounter(2);
			for (int i = 0; i < 200; i += 2) {
				diskList.search(nonExistingPoints[i], nonExistingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for failed DiskList searches: " + MultiCounter.getCount(2)/100);
			
			
	////////////////////////////////////////////////////
			dataPageHandling.createFile("fileName.bin");
			
			DiskHash diskHash = new DiskHash();
			for (int i = 0; i < k[j]; i++) {
				diskHash.insert(x[i], y[i]);
			}
			MultiCounter.resetCounter(2);
			for (int i = 0; i < 200; i += 2) {
				diskHash.search(existingPoints[i], existingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for successful DiskHash searches: " + MultiCounter.getCount(2)/100);
			
			MultiCounter.resetCounter(2);
			for (int i = 0; i < 200; i += 2) {
				diskHash.search(nonExistingPoints[i], nonExistingPoints[i + 1]);
			}
			System.out.println("Average number of comparisons for failed DiskHash searches: " + MultiCounter.getCount(2)/100);
			
		}
		dataPageHandling.CloseFile("fileName.bin");
	}
	
	public static int[] intArrayGenerator(int startInt, int endInt, int numberOfElements) {
		Random randomGenerator = new Random();
		int[] randomInts = randomGenerator.ints(startInt, endInt).distinct().limit(numberOfElements).toArray();
		return randomInts;
	}
	
	public static int[] getRandomExistingPoint(int[] x, int[] y) {
		Random random = new Random();
		int index = random.nextInt(x.length);
		int[] point = new int[2];
		point[0] = x[index];
		point[1] = y[index];
		return point;
	}
	
	public static int[] getRandomNonExistingPoint(Structure structure, int maxIntNumber) {
		Random random = new Random();
		int x;
		int y;
		do {
			x = random.nextInt(maxIntNumber);
			y = random.nextInt(maxIntNumber);
		} while (structure.search(x, y));
		int[] point = new int[2];
		point[0] = x;
		point[1] = y;
		return point;
	}
	
}
