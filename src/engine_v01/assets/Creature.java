package engine_v01.assets;

import engine_v01.assets.World.Entity;
import engine_v01.assets.World.EntityType;

/**
 * Same as {@link Entity} class, but with health
 */
public class Creature extends Entity {
	
	public static class CreatureType extends EntityType {
		
		public float health;
		
		public CreatureType(Animation texture, boolean stretch, float mass, float rest, float s_friction, float d_friction, float health) {
			super(texture, stretch, mass, rest, s_friction, d_friction);
			this.health = health;
		}
		
	}
	
	protected float health;
	
	public Creature(World world, Shape shape, Vec2 vector, CreatureType type) {
		world.super(shape, vector, type);
		this.health = type.health;
	}

}
