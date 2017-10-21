import java.io.Serializable;
import java.util.*;

public class Data implements Serializable{
	int arraySize;
	Random random = new Random();
	ArrayList<Integer> array = new ArrayList<Integer>();
	
	public Data(int arraySize) {
		this.arraySize = arraySize;
	}
	
	public Data() {
		setArraySize(500);
		randomizeArray();
	}
	
	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
	}
	
	public void randomizeArray() {
		for(int i = 0; i<arraySize; i++) {
			array.add(random.nextInt(500) + 1);
		}
	}
	
	public void displayContents() {
		int count = 1;
		for(int i: array) {
			System.out.println("value of member " + count + ": " + i);
			count++;
		}
	}
	
	public ArrayList<Integer> getArray(){
		return array;
	}
}
