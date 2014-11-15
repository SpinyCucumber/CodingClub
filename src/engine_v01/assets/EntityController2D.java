package engine_v01.assets;

import org.lwjgl.input.Keyboard;

import engine_v01.assets.World2D.Entity2D;
import engine_v01.assets.World2D.EntityInterface;

public class EntityController2D implements EntityInterface {
	
	private float speed;
	
	@Override
	public void run(Entity2D entity, int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) entity.vector.x += speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) entity.vector.x -= speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) entity.vector.y += speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) entity.vector.y -= speed;
	}
	
	public EntityController2D(float speed) {
		this.speed = speed;
	}

}
