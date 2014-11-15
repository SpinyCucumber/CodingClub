package engine_v01.assets;

import java.util.ArrayList;
import java.util.List;

import engine_v01.assets.Shape2D.CollisionResult;

//The plug-in for 2D physics. Everything physics-related starts here and ends here, no exceptions
public class World2D {
	
	//A generic Entity2D class, which has bounds (a shape), and a direction / speed (vector)
	public class Entity2D {
		
		//Utilizes Shape2D and Vector2D classes, which include some complex maths. Check for
		//yourself if you want to see what's happening behind the scenes
		public Shape2D shape;
		public Vector2D vector;
		public float rotation;
		//The ID of the OpenGL texture to be rendered
		public boolean physics;
		private EntityInterface[] interfaces;
		
		public void remove() {
			//"Delete" this entity. Java Garbage Collection will erase the memory
			entities.remove(this);
		}
		
		public Entity2D(Shape2D shape, Vector2D vector, float rotation, boolean physics, EntityInterface...interfaces) {
			this.shape = shape;
			this.vector = vector;
			this.rotation = rotation;
			this.interfaces = interfaces;
			this.physics = physics;
			entities.add(this);
		}
		
		public Entity2D(Shape2D shape, Vector2D vector, float rotation, EntityFormat format) {
			this(shape, vector, rotation, format.physics, format.interfaces);
		}
		
	}
	
	//An interface that allows for modification of entities
	public interface EntityInterface {
		
		void run(Entity2D entity, int delta);
		
	}
	
	//Format for entity creation
	public static class EntityFormat {
		
		private boolean physics;
		private EntityInterface[] interfaces;
		
		public EntityFormat(boolean physics, EntityInterface...interfaces) {
			this.physics = physics;
			this.interfaces = interfaces;
		}
		
	}
	
	//Apply physics to entities and check for collisions using delta timing
	public void update(int delta) {
		for(int i1 = 0; i1 < entities.size(); i1++) {
			Entity2D entity1 = entities.get(i1);
			for(EntityInterface i : entity1.interfaces) i.run(entity1, delta);
			entity1.shape.translate(entity1.vector.scale(delta));
			entity1.shape.rotate(entity1.shape.center(), entity1.rotation);
			if(entity1.physics) entity1.vector = entity1.vector.add(gravity).scale(resistance);
			entity1.rotation *= resistance;
			for(int i2 = i1 + 1; i2 < entities.size(); i2++) {
				Entity2D entity2 = entities.get(i2);
				CollisionResult r = entity1.shape.checkCollision(entity2.shape);
				if(r != null) {
					entity1.shape.translate(r.normal1.scale(r.depth1));
					if(entity1.physics) {
						float a = entity1.shape.center().subtract(r.center).direction();
						entity1.rotation += Math.pow(a - r.normal1.direction(), Math.PI);
						entity1.vector = r.normal1.rotate(entity1.vector);
					}
					if(entity2.physics) {
						float a = entity2.shape.center().subtract(r.center).direction();
						entity2.rotation += Math.pow(a - r.normal2.direction(), Math.PI);
						entity2.vector = entity2.vector.rotate(r.normal2);
					}
				}
			}
		}
	}

	private List<Entity2D> entities = new ArrayList<Entity2D>();
	private Vector2D gravity;
	//Resistance when moving through the air. Space has 0 resistance
	private float resistance;
	
	public World2D(Vector2D gravity, float resistance) {
		this.gravity = gravity;
		this.resistance = resistance;
	}
	
}
