package engine_v01.assets;

//A more complex geometrical class, usually used detect collisions, but has many applications
//Utilizes the Vector2D class heavily
public class Shape2D {
	
	//The wrapper class used to handle collisions, usually discarded immediately
	public class CollisionResult {
		
		public Vector2D normal1, normal2, center;
		public float depth1, depth2;
		
		private CollisionResult(Vector2D normal1, Vector2D normal2, float depth1, float depth2, Vector2D center) {
			this.normal1 = normal1;
			this.normal2 = normal2;
			this.depth1 = depth1;
			this.depth2 = depth2;
			this.center = center;
		}
		
	}

	public Vector2D[] vertices;
	
	public Shape2D(Vector2D...vertices) {
		this.vertices = vertices;
	}
	
	//Move the shape using a vector
	public void translate(Vector2D d) {
		for(int i = 0; i < vertices.length; i++) {
			vertices[i] = vertices[i].add(d);
		}
	}
	
	public void rotate(Vector2D center, float a) {
		//Find the rotational vector
		Vector2D d = Vector2D.fromAngle(a);
		//Iterate over vertices
		for(int i = 0; i < vertices.length; i++) {
			//Translate vertex to origin
			Vector2D p = vertices[i].subtract(center);
			//Rotate the vertex (Refer to Vector2D class)
			Vector2D r = p.rotate(d);
			//Translate the vector back
			vertices[i] = center.add(r);
		}
	}
	
	public Vector2D center() {
		Vector2D min = vertices[0].clone(), max = vertices[0].clone();
		for(int i = 1; i < vertices.length; i++) {
			Vector2D vertex = vertices[i];
			if(vertex.x < min.x) min.x = vertex.x;
			if(vertex.y < min.y) min.y = vertex.y;
			if(vertex.x > max.x) max.x = vertex.x;
			if(vertex.y > max.y) max.y = vertex.y;
		}
		return min.midpoint(max);
	}
	
	//Get the axes for testing by normalizing the vectors perpendicular the the edges
	public Vector2D[] axes() {
		Vector2D[] axes = new Vector2D[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			Vector2D p1 = vertices[i];
			Vector2D p2 = vertices[i + 1 == vertices.length ? 0 : i + 1];
			Vector2D edge = p1.subtract(p2);
			Vector2D normal = edge.perpendicular();
			axes[i] = normal.normalize();
		}
		return axes;
	}
	
	//Project the shape onto a 1-dimensional surface, like creating a shadow
	public Vector2D project(Vector2D axis) {
		float min = axis.dotProduct(vertices[0]), max = min;
		for(int i = 1; i < vertices.length; i++) {
			float p = axis.dotProduct(vertices[i]);
			if(p < min) min = p;
			else if(p > max) max = p;
		}
		Vector2D projection = new Vector2D(min, max);
		return projection;
	}

	//Test for collisions using the Seperating-Axis Theorem. The theorem states that if all
	//of the overlaps of the shadows of the shapes when projected onto their individual
	//axes do not overlap, then the shapes are not colliding. This algorithm works well
	//because it test collisions between any kind of shape, as long as the shape is not convex
	public CollisionResult checkCollision(Shape2D b) {
		
		float depth1 = 1000, depth2 = 1000;
		
		Vector2D normal1 = null, normal2 = null, center1 = new Vector2D(0, 0), center2 = new Vector2D(0, 0);
		Vector2D[] axes1 = axes(), axes2 = b.axes();
		
		for(Vector2D axis : axes1) {
			
			Vector2D p1 = project(axis), p2 = b.project(axis);
			float ol = p1.overlap(p2);
			
			if (ol < 0) return null;
			center1 = center1.add(axis.scale(p1.x + ol / 2));
			if(ol < depth1) {
				depth1 = ol;
				normal1 = axis;
			}
			
		}
		
		for(Vector2D axis : axes2) {
			
			Vector2D p1 = project(axis), p2 = b.project(axis);
			float ol = p1.overlap(p2);
			
			if (ol < 0) return null;
			center2 = center2.add(axis.scale(p1.x + ol / 2));
			if(ol < depth2) {
				depth2 = ol;
				normal2 = axis;
			}
			
		}
		
		Vector2D d = b.center().subtract(this.center());
		if(normal1.dotProduct(d) > 0) normal1 = normal1.negate();
		if(normal2.dotProduct(d) > 0) normal2 = normal2.negate();

		return new CollisionResult(normal1, normal2, depth1, depth2, center1.midpoint(center2));
	}

	@Override
	public String toString() {
		String s = "{";
		for(Vector2D v : vertices) {
			s += v.toString() + " ";
		}
		return s + "}";
	}
	
}

