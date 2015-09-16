package tspdef;

public class Euc2DPosition {
	public double x;
	public double y;
	
	public Euc2DPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceFrom(Euc2DPosition other) {
		return Math.sqrt((other.x - this.x) * (other.x - this.x)
						+ (other.y - this.y) * (other.y - this.y));
	}
}
