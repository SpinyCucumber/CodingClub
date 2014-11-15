package engine_v01.assets;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import engine_v01.assets.World2D.Entity2D;
import engine_v01.assets.World2D.EntityInterface;

public class EntityDrawer2D implements EntityInterface {
	
	private int textureId;
	
	@Override
	public void run(Entity2D entity, int delta) {
		glBindTexture(GL_TEXTURE_2D, textureId);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		entity.shape.vertices[0].glVertex();
		glTexCoord2f(1, 0);
		entity.shape.vertices[1].glVertex();
		glTexCoord2f(1, 1);
		entity.shape.vertices[2].glVertex();
		glTexCoord2f(0, 1);
		entity.shape.vertices[3].glVertex();
		glEnd();
	}
	
	public EntityDrawer2D(int textureId) {
		this.textureId = textureId;
	}

}
