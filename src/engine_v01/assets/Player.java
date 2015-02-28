package engine_v01.assets;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.input.Keyboard;

public class Player extends Creature {
	
	protected float speed = 0.03f;
	private boolean side;
	
	public Player(World world, Shape shape, Vec2 vector, Animation texture, boolean stretch, float mass, float rest, float s_friction, float d_friction) {
		super(world, shape, vector, texture, stretch, mass, rest, s_friction, d_friction);
	}
	
	protected void update(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			velocity.x += speed;
			side = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			velocity.x -= speed;
			side = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) velocity.y -= speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) velocity.y += speed;
		super.update(delta);
	}
	
	protected void draw() {
		texture.bind();
		glBegin(GL_POLYGON);
		for(int i = 0; i < shape.vertices.length; i++) {
			Vec2 uv = texCoords.vertices[i];
			texture.getTexCoord(side ? new Vec2(1 - uv.x, uv.y) : uv).glTexCoord();
			shape.vertices[i].glVertex();
		}
		glEnd();
	}

}
