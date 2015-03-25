package engine_v01.assets;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL14;

public class Framebuffer {
	
	private int id, colorBufferId;
	
	public void bind() {
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, id);
	}
	
	public void bindTexture() {
		glBindTexture(GL_TEXTURE_2D, colorBufferId);
	}
	
	public void delete() {
		glDeleteFramebuffersEXT(id);
	}
	
	public Framebuffer(int width, int height) {
		
		id = glGenFramebuffersEXT();                                         // create a new framebuffer
        colorBufferId = glGenTextures();
        int depthBuffer = glGenRenderbuffersEXT();
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, id);                        // switch to the new framebuffer
        
        // initialize color texture
        glBindTexture(GL_TEXTURE_2D, colorBufferId);                                   // Bind the colorbuffer texture
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);             // make it linear filterd
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);  // Create the texture data
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorBufferId, 0); // attach it to the framebuffer
 
 
        // initialize depth renderbuffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthBuffer);                // bind the depth renderbuffer
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height); // get the data space for it
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depthBuffer); // bind it to the renderbuffer
 
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        
	}
	
	public static void unbind() {
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
	
}
