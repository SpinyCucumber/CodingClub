package engine_v01.assets;

//Extension of the Shape2D class designed to speed up processing by using a minimal amount of
//axes. Also contains an convenience constructor which takes the center and the dimensions
public class Rectangle extends Shape2D {

	public Rectangle(Vector2D c, Vector2D hd) {
		super(c, corners(c, hd));
	}
	
	@Override
	public Vector2D[] axes() {
		Vector2D[] axes = new Vector2D[2];
		for(int i = 0; i < 2; i++) {
			Vector2D p1 = vertices[i];
			Vector2D p2 = vertices[i == 2 ? 0 : i + 1];
			Vector2D edge = p1.subtract(p2);
			Vector2D normal = edge.perpendicular();
			axes[i] = normal.normalize();
		}
		return axes;
	}
	
	private static Vector2D[] corners(Vector2D c, Vector2D hd) {
		Vector2D p = hd.perpendicular();
		return new Vector2D[]{c.subtract(hd), c.subtract(p), c.add(hd), c.add(p)};
	}

}
