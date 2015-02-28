package engine_v01.assets;

public abstract class Animation {

	protected int frame, length;
	
	public abstract void texCoord(Vec2 texCoord);
	
	public Animation(int length) {
		this.length = length;
	}
	
}
