package engine_v01.assets;

import java.util.ArrayList;
import java.util.List;

import engine_v01.assets.Shape2D.CollisionResult;

import static org.lwjgl.opengl.GL11.*;

//The plug-in for 2D physics. Everything physics-related starts here and ends here, no exceptions
public class World2D {
	
	//A generic Entity2D class, which has bounds (a shape), and a direction / speed (vector)
	public class Entity2D {
		
		//Utilizes Shape2D and Vector2D classes, which include some complex maths. Check for
		//yourself if you want to see what's happening behind the scenes
		protected Shape2D shape;
		protected Vector2D vector;
		protected float rotation;
		//The ID of the OpenGL texture to be rendered
		private int textureId;
		
		public void remove() {
			//"Delete" this entity. Java Garbage Collection will erase the memory
			entities.remove(this);
		}
		
		public Entity2D(Shape2D shape, Vector2D vector, float rotation, int textureId) {
			this.shape = shape;
			this.vector = vector;
			this.rotation = rotation;
			this.textureId = textureId;
			entities.add(this);
		}
		
	}
	
	//Apply physics to entities and check for collisions using delta timing
	public void update(int delta) {
		for(int i1 = 0; i1 < entities.size(); i1++) {
			Entity2D entity1 = entities.get(i1);
			entity1.vector = entity1.vector.add(gravity).product(resistance);
			entity1.rotation *= resistance;
			entity1.shape.translate(entity1.vector.product(delta));
			entity1.shape.rotate(entity1.rotation);
			for(int i2 = i1 + 1; i2 < entities.size(); i2++) {
				Entity2D entity2 = entities.get(i2);
				CollisionResult r = entity1.shape.checkCollision(entity2.shape);
				if(r != null) {
					entity1.vector.add(r.mtv);
				}
			}
		}
	}
	
	//Render all entities through LWJGL
	public void draw() {
		for(Entity2D entity : entities) {
			glBindTexture(GL_TEXTURE_2D, entity.textureId);
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			entity.shape.vertices[0].glVertex();
			glTexCoord2f(1, 0);
			entity.shape.vertices[1].glVertex();
			glTexCoord2f(1, 1);
			entity.shape.vertices[2].glVertex();
			glTexCoord2f(0, 1);
			entity.shape.vertices[3].glVertex();
			glEnd();
		}
		//Will also render background
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
