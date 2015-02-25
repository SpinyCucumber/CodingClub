package engine_v01.assets;

//Extension of the Shape2D class designed to speed up processing by using a minimal amount of
//axes. Also contains an convenience constructor which takes the center and the dimensions
public class Rectangle extends Shape {

	public Rectangle(Vec2 c, Vec2 hd) {
		super(corners(c, hd));
	}
	
	@Override
	public Vec2[] axes() {
		Vec2[] axes = new Vec2[2];
		for(int i = 0; i < 2; i++) {
			Vec2 p1 = vertices[i];
			Vec2 p2 = vertices[i == 2 ? 0 : i + 1];
			Vec2 edge = p1.subtract(p2);
			Vec2 normal = edge.perpendicular();
			axes[i] = normal.normalize();
		}
		return axes;
	}
	
	private static Vec2[] corners(Vec2 c, Vec2 hd) {
		Vec2 p = hd.reflection();
		return new Vec2[]{c.subtract(hd), c.subtract(p), c.add(hd), c.add(p)};
	}

}
