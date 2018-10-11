package mountain;

import java.util.HashMap;
import java.util.Map;

import fractal.Fractal;
import fractal.TurtleGraphics;

public class Mountain extends Fractal {
	private Point p1, p2, p3;
	private double dev;
	private Map<Side, Point> sides;
	
	public Mountain(Point p1, Point p2, Point p3, double dev){
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.dev = dev;
		sides = new HashMap<Side, Point>();
	}

	public String getTitle() {
		return "Mountain triangel";
	}
	
	private Point getCenter(Point a, Point b, double dev) {
		if (!sides.containsKey(new Side(a, b))) {
			Point p = new Point(a.getX() + (b.getX() - a.getX()) / 2, a.getY() + (b.getY() - a.getY())/2 + RandomUtilities.randFunc(dev));
			sides.put(new Side(a, b), p);
			return p;
		}
		Side s = new Side(a, b);
		Point p = sides.get(s);
		sides.remove(s);
		return p;
}
	
	public void draw(TurtleGraphics turtle) {
		fractalTriangle(turtle, order, dev, p1, p2 ,p3);
}

	/* 
	 * Recursive method: Draws a recursive line of the triangle. 
	 */
	private void fractalTriangle(TurtleGraphics turtle, int order, double dev, Point a, Point b, Point c) {
		if (order == 0) {
			turtle.moveTo(a.getX(), a.getY());
			turtle.forwardTo(b.getX(), b.getY());
			turtle.forwardTo(c.getX(), c.getY());
			turtle.forwardTo(a.getX(), a.getY());
		} else {
			Point q = getCenter(a, b, dev);
			Point r = getCenter(b, c, dev);
			Point s = getCenter(c, a, dev);

			fractalTriangle(turtle, order - 1, dev / 2, a, q, s);
			fractalTriangle(turtle, order - 1, dev / 2, q, b, r);
			fractalTriangle(turtle, order - 1, dev / 2, r, c, s);
			fractalTriangle(turtle, order - 1, dev / 2, s, q, r);
}
	}

}
