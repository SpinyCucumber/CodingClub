package engine_v01.assets;

import org.newdawn.slick.opengl.Texture;

public class TextureAtlas extends Animation {

	private Texture texture;
	private Vec2 dimensions,point;
	
	public TextureAtlas(int length) {
		super(length);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void texCoord(Vec2 texCoord) {
		
	}
	
	@Override
	public void update(int delta) {
		texture.bind();
		int y = (int) Math.floor(frame/dimensions.x);
		point = new Vec2(frame - y*dimensions.x, y);
		super.update(delta);
	}

}
