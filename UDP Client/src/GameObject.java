
public class GameObject {
	Position pos = new Position();
	int[] rgb = new int[3];
	
	public void setPos(float x, float y) {
		pos.x = x;
		pos.y = y;
	}
	
	public void setX(float x) {
		pos.x = x;
	}
	
	public void setY(float y) {
		pos.y = y;
	}
	
	public float getX() {
		return pos.x;
	}
	
	public float getY() {
		return pos.y;
	}
	
	public void setRGB(int x, int y, int z) {
		rgb[0] = x;
		rgb[1] = y;
		rgb[2] = z;
	}
	
	public int[] getRGB() {
		return rgb;
	}
}
