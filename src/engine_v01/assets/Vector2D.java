package engine_v01.assets;

import org.lwjgl.opengl.GL11;

//This is a utility class which includes many basic and some more complex vector maths.
// Used in other utility classes as well as the entity system.
public class Vector2D implements Cloneable {

	public float x, y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
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
	
	public Vector2D midpoint(Vector2D b) {
		return new Vector2D((x + b.x) / 2, (y + b.y) / 2);
	}
	
	//Scale the vector using a scalar
	public Vector2D scale(float s) {
		return new Vector2D(x * s, y * s);
	}
	
	public Vector2D divide(float s) {
		return new Vector2D(x / s, y / s);
	}
	
	//Find the vector perpendicular to itself, using the formula -y / x
	public Vector2D perpendicular() {
		return new Vector2D(-y, x);
	}
	
	public Vector2D reflection() {
		return new Vector2D(-x, y);
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
	
	public Vector2D cross(float scalar) {
		return new Vector2D(y * scalar, x * -scalar);
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
	
	//Plug-in for OpenGL which allows the user to define vertex coordinates using a Vector2D
	public void glVertex() {
		GL11.glVertex2f(x, y);
	}
	
	@Override
	public Vector2D clone() {
		return new Vector2D(x, y);
	}
	
	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
	}

}
