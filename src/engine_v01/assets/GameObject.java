package engine_v01.assets;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

//The game will be initialized from this class. The initialization/boot process will
//include creating the world, starting the timer, loading resources, initializing OpenGL, etc.
public class GameObject extends Thread {
	
	private int width, height, lastTime;
	private World2D world;
	
	public GameObject(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	private int getTime() {
		return (int) (Sys.getTime() / Sys.getTimerResolution());
	}
	
	public void start() {
		
		try {
			
			Display.setTitle("Game");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.create(new PixelFormat(0,0,0,0));        
        
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
			glEnable(GL_TEXTURE_2D);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
 
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, width, height, 0, 1, -1);
			glMatrixMode(GL_MODELVIEW);
		
			TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/image.png"));
			world = new World2D(new Vector2D(0, 0), 0.99f);
			world.new Entity2D(new Rectangle(new Vector2D(50, 50), new Vector2D(10, 10)), new Vector2D(0, 0), true);
			
			lastTime = getTime();

			while(!Display.isCloseRequested()) {
				
				int time = getTime(), delta = time - lastTime;
				lastTime = time;
				
				world.update(delta);
				
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
				Display.update();
				Display.sync(60);
			
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			Display.destroy();
			System.exit(1);
		}
		
	}
	
	public static void main(String[] args) {
		new GameObject(1000, 700).start();
	}
	
}
