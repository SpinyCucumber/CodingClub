package engine_v01.assets;

import java.util.ArrayList;
import java.util.List;

import engine_v01.assets.Shape.CollisionResult;

import static org.lwjgl.opengl.GL11.*;

//The plug-in for 2D physics. Everything physics-related starts here and ends here, no exceptions
public class World {
	
	public class Entity {
		
		//Utilizes Shape2D and Vector2D classes, which include some complex maths. Check for
		//yourself if you want to see what's happening behind the scenes
		protected Shape shape;
		protected Vec2 velocity;
		private int texture;
		private float mass, i_mass, rest;
		
		public void remove() {
			//"Delete" this entity. Java Garbage Collection will erase the memory
			entities.remove(this);
		}
		
		public Entity(Shape shape, Vec2 vector, int texture, float mass, float rest) {
			this.shape = shape;
			this.velocity = vector;
			this.texture = texture;
			this.mass = mass;
			this.i_mass = mass == 0 ? 0 : 1 / mass;
			this.rest = rest;
			entities.add(this);
		}
		
	}
	
	//Apply physics to entities and check for collisions using delta timing
	public void update(int delta) {
		for(int i1 = 0; i1 < entities.size(); i1++) {
			Entity entity1 = entities.get(i1);
			entity1.shape.translate(entity1.velocity.scale(delta));
			entity1.velocity = entity1.velocity.add(gravity.scale(entity1.mass));
			Vec2 n = entity1.velocity.subtract(entity1.velocity.scale(airDensity));
			entity1.velocity = n.dotProduct(entity1.velocity) < 0 ? new Vec2(0, 0) : n;
			for(int i2 = i1 + 1; i2 < entities.size(); i2++) {
				Entity entity2 = entities.get(i2);
				CollisionResult r = entity1.shape.checkCollision(entity2.shape);
				if(r == null) continue;
				float d = entity2.velocity.subtract(entity1.velocity).dotProduct(r.normal);
				if(d > 0) continue;
				float e = Math.min(entity1.rest, entity2.rest), j = -(1 + e) * d / (entity1.i_mass + entity2.i_mass);
				Vec2 i = r.normal.scale(j);
				entity1.velocity = entity1.velocity.subtract(i.scale(entity1.i_mass));
				entity2.velocity = entity2.velocity.subtract(i.scale(entity2.i_mass));
			}
		}
	}
	
	public void draw() {
		glBegin(GL_QUADS);
		for(Entity entity : entities) {
			glBindTexture(GL_TEXTURE_2D, entity.texture);
			glTexCoord2f(0, 1);
			entity.shape.vertices[0].glVertex();
			glTexCoord2f(1, 1);
			entity.shape.vertices[1].glVertex();
			glTexCoord2f(1, 0);
			entity.shape.vertices[2].glVertex();
			glTexCoord2f(0, 0);
			entity.shape.vertices[3].glVertex();
		}
		glEnd();
	}

	private List<Entity> entities = new ArrayList<Entity>();
	private Vec2 gravity;
	//Resistance when moving through the air. Space has 0 resistance
	private float airDensity;
	
	public World(Vec2 gravity, float airDensity) {
		this.gravity = gravity;
		this.airDensity = airDensity;
	}
	
}
