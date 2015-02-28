package engine_v01.assets;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class Player extends Creature {
	
	protected float speed = 0.01f;
	private boolean side = true;
	
	public Player(World world, Shape shape, Vec2 vector, Texture texture, boolean stretch, float mass, float rest, float s_friction, float d_friction) {
		super(world, shape, vector, texture, stretch, mass, rest, s_friction, d_friction);
	}
	
	protected void update(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			velocity.x += speed;
			side = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			velocity.x -= speed;
			side = false;
		}
		super.update(delta);
	}
	
	protected Vec2 getTexCoord(int i) {
		Vec2 uv = texCoords.vertices[i];
		return side ? uv : new Vec2(1 - uv.x, uv.y);
	}

}
