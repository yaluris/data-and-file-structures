package tuc.ece.cs201.diskdatapage;

public class DataPage {
	private int[] data;  //An array of ints with successive x, y
	private int numberOfPoints;  //Number of points of the current page of the list
	private int position;  //Position of the current page of the list (in bytes). Int instead of long, because we have a limited amount of data
	private int nextPage;  //Position of the next page of the list. Int instead of long, so we can save 31 points on each page without any unused space 
	public static final int PAGE_SIZE = 256;  //Data pages have a fixed size of 256 bytes
	
	public DataPage(int[] data, int numberOfPoints, int position, int nextPage) {
		this.data = data;
		this.numberOfPoints = numberOfPoints;
		this.position = position;
		this.nextPage = nextPage;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	
}
