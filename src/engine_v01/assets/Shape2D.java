package engine_v01.assets;

//A more complex geometrical class, usually used detect collisions, but has many applications
//Utilizes the Vector2D class heavily
public class Shape2D {
	
	//The convenience class used to handle collisions, usually discarded immediately
	public class CollisionResult {
		
		public Vector2D mtv, center;
		
		private CollisionResult(Vector2D mtv, Vector2D center) {
			this.mtv = mtv;
			this.center = center;
		}
		
	}
	
	public Vector2D[] vertices;
	public Vector2D center;
	
	public Shape2D(Vector2D center, Vector2D...vertices) {
		this.vertices = vertices;
		this.center = center;
	}
	
	//Move the shape using a vector
	public void translate(Vector2D d) {
		center = center.add(d);
		for(int i = 0; i < vertices.length; i++) {
			vertices[i] = vertices[i].add(d);
		}
	}
	
	public void rotate(float a) {
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
		//How large the overlap is between the two closest sides (Set to a really high value)
		float overlap = Float.MAX_VALUE;
		//Define our variables
		Vector2D smallest = null, c = new Vector2D();
		Vector2D[] axes1 = axes(), axes2 = b.axes();
		//Iterate over axes for each shape
		for (int i = 0; i < axes1.length; i++) {
			//Get the shadows of each shape when projected onto the axis
			Vector2D axis = axes1[i], p1 = project(axis), p2 = b.project(axis);
			//Find the overlap of the shadows
			float ol = p1.overlap(p2);
			//Perform final computations
			if (ol < 0) return null;
			c = c.add(axis.product(p2.x + ol / 4));
			if(ol < overlap) {
				overlap = ol;
				smallest = axis;
			}
		}
		for (int i = 0; i < axes2.length; i++) {
			Vector2D axis = axes2[i], p1 = project(axis), p2 = b.project(axis);
			float ol = p1.overlap(p2);
			if (ol < 0) return null;
			c = c.add(axis.product(p2.x + ol / 4));
			if(ol < overlap) {
				overlap = ol;
				smallest = axis;
			}
		}
		//Compensate for any axis ambiguity
		Vector2D d = b.center.subtract(center);
		if(smallest.dotProduct(d) > 0) smallest = smallest.negate();
		//Return collision result
		return new CollisionResult(smallest.product(overlap), c);
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

