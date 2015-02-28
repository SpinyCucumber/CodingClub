package engine_v01.assets;

public abstract class Animation {

	protected int frame, length;
	
	public abstract Vec2 getTexCoord(Vec2 texCoord);
	public abstract Vec2 getTextureDimensions();
	public abstract void bind();
	
	public void update(int delta) {
		frame = delta % length;
	}
	
}
