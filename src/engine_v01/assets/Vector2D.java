package engine_v01.assets;

//This is a utility class which includes many basic and some more complex vector maths.
// Used in other utility classes as well as the entity system.
public class Vector2D {

	public float x, y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	//Convenience constructor used when a vector does not need any values assigned
	public Vector2D() {}
	
	//Derive a vector from an angle using sine/cosine functions
	public static Vector2D fromAngle(float a) {
		return new Vector2D((float) Math.sin(a), (float) Math.cos(a));
	}
	
	public Vector2D add(Vector2D b) {
		return new Vector2D(x + b.x, y + b.y);
	}
	
	public Vector2D subtract(Vector2D b) {
		return new Vector2D(x - b.x, y - b.y);
	}
	
	//Rotate the vector around the origin using dot products and cross products
	public Vector2D rotate(Vector2D b) {
		return new Vector2D(crossProduct(b), dotProduct(b));
	}
	
	//Scale the vector using a scalar
	public Vector2D product(float s) {
		return new Vector2D(x * s, y * s);
	}
	
	//Find the vector perpendicular to itself, using the formula -y / x
	public Vector2D perpendicular() {
		return new Vector2D(-y, x);
	}
	
	//Reverse the vector's direction
	public Vector2D negate() {
		return new Vector2D(-x, -y);
	}	
	
	//Normalize the vector by dividing both its values by its length
	public Vector2D normalize() {
		float length = length();
		return new Vector2D(x / length, y / length);
	}
	
	//Get the dot product of two vectors by adding together the product of their x values and y values
	public float dotProduct(Vector2D b) {
		return x * b.x + y * b.y;
	}
	
	//Get the cross product (similar to dot product) by subtracting the cross-products (Think of a fraction)
	public float crossProduct(Vector2D b) {
		return x * b.y - y * b.x;
	}
	
	//Calculate the length using the pythagorean theorem.
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	//Find the angle between the vector and the x-axis using trigonometry.
	public float direction() {
		return (float) Math.atan2(y, x);
	}
	
	public float overlap(Vector2D b) {
		return Math.min(y, b.y) - Math.max(x, b.x);
	}

}
