package engine_v01.assets;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

import engine_v01.assets.Shape.CollisionResult;
import static org.lwjgl.opengl.GL11.*;

//The plug-in for 2D physics. Everything physics-related starts here and ends here, no exceptions
public class World {
	
	public class Entity {
		
		//Utilizes Shape2D and Vector2D classes, which include some complex maths. Check for
		//yourself if you want to see what's happening behind the scenes
		protected Shape shape, texCoords;
		protected Vec2 velocity;
		protected float mass, i_mass, rest;
		private Texture texture;
		
		protected void remove() {
			//"Delete" this entity. Java Garbage Collection will erase the memory
			entities.remove(this);
		}
		
		protected void draw() {
			texture.bind();
			glBegin(GL_POLYGON);
			for(int i = 0; i < shape.vertices.length; i++) {
				texCoords.vertices[i].glTexCoord();
				shape.vertices[i].glVertex();
			}
			glEnd();
		}
		
		public Entity(Shape shape, Vec2 vector, Texture texture, boolean stretch, float mass, float rest) {
			this.shape = shape;
			this.velocity = vector;
			this.texture = texture;
			this.mass = mass;
			this.i_mass = mass == 0 ? 0 : 1 / mass;
			this.rest = rest;
			Vec2 min = shape.min(), d = stretch ? shape.max().subtract(min) : new Vec2(texture.getImageWidth(), texture.getImageHeight());
			Vec2[] texCoords = new Vec2[shape.vertices.length];
			for(int i = 0; i < shape.vertices.length; i++) texCoords[i] = shape.vertices[i].subtract(min).divide(d);
			this.texCoords = new Shape(texCoords);
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
			entity1.velocity = n.dot(entity1.velocity) < 0 ? new Vec2(0, 0) : n;
			for(int i2 = i1 + 1; i2 < entities.size(); i2++) {
				Entity entity2 = entities.get(i2);
				CollisionResult r = entity1.shape.checkCollision(entity2.shape);
				if(r == null) continue;
				float d = entity2.velocity.subtract(entity1.velocity).dot(r.normal);
				if(d > 0) continue;
				float tm = entity1.i_mass + entity2.i_mass;
				if(tm == 0) continue;
				float e = Math.min(entity1.rest, entity2.rest), j = -(1 + e) * d / tm;
				Vec2 i = r.normal.scale(j), c = r.normal.scale(r.depth / tm);
				entity1.shape.translate(c.scale(-entity1.i_mass));
				entity2.shape.translate(c.scale(entity2.i_mass));
				entity1.velocity = entity1.velocity.subtract(i.scale(entity1.i_mass));
				entity2.velocity = entity2.velocity.add(i.scale(entity2.i_mass));
			}
		}
	}
	
	public void draw() {
		for(Entity entity : entities) entity.draw();
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
