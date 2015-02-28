package engine_v01.assets;

import engine_v01.assets.World.Entity;

/**
 * Same as {@link Entity} class, but with health
 */
public class Creature extends Entity {

	protected float health;
	
	public Creature(World world, Shape shape, Vec2 vector, Animation texture, boolean stretch, float mass, float rest, float s_friction, float d_friction) {
		world.super(shape, vector, texture, stretch, mass, rest, s_friction, d_friction);
	}

}
