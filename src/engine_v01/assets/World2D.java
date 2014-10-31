package engine_v01.assets;

import java.util.ArrayDeque;

//The plug-in for 2D physics. Everything physics-related starts here and ends here, no exceptions
public class World2D {
	
	//A generic Entity2D class, which has bounds (a shape), and a direction / speed (vector)
	public class Entity2D {
		
		//Utilizes Shape2D and Vector2D classes, which include some complex maths. Check for
		//yourself if you want to see what's happening behind the scenes
		protected Shape2D shape;
		protected Vector2D vector;
		
		//Apply physics, which means applying gravity and resistance from the world
		protected void update(int delta) {
			vector = vector.add(gravity).product(resistance);
			shape.translate(vector);
		}
		
		public void remove() {
			//"Delete" this entity. Java Garbage collection will delete the memory
			entities.remove(this);
		}
		
		public Entity2D(Shape2D shape, Vector2D vector) {
			this.shape = shape;
			this.vector = vector;
			//Add this entity to the entity list so it can get updated with the world
			entities.add(this);
		}
		
	}
	
	public void update(int delta) {
		for(Entity2D entity : entities) {
			entity.update(delta);
		}
		//Might do something with gravity here, not sure
	}
	
	private ArrayDeque<Entity2D> entities = new ArrayDeque<Entity2D>();
	private Vector2D gravity;
	//Resistance when moving through the air. Space has 0 resistance
	private float resistance;
	
	public World2D(Vector2D gravity, float resistance) {
		this.gravity = gravity;
		this.resistance = resistance;
	}
	
}
