package mountain;

public class Side {
	private Point a;
	private Point b;

	public Side(Point a, Point b) {
		this.a = a;
		this.b = b;
	}

	public Point getA() {
		return a;
	}

	public Point getB() {
		return b;
	}

	public boolean equals(Object o) {
		if (o instanceof Side) {
			Side other = (Side)o;
			return a == other.getA() && b == other.getB() || b == other.getA() && a == other.getB();
		}
		return false;
	}
	
	public int hashCode() {
		return a.hashCode() + b.hashCode();
		}
}
