import java.util.*;


public class Data {
	int arraySize = 500;
	Random random = new Random();
	ArrayList<Integer> array = new ArrayList<Integer>();
	
	public Data() {
		randomizeArray();
	}
	
	public void randomizeArray() {
		for(int i = 0; i<arraySize; i++) {
			array.add(random.nextInt(500) + 1);
		}
	}
	
	public ArrayList<Integer> getArray(){
		return array;
	}
}
