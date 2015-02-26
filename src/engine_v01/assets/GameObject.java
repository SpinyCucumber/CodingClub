package engine_v01.assets;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

//The game will be initialized from this class. The initialization/boot process will
//include creating the world, starting the timer, loading resources, initializing OpenGL, etc.
public class GameObject extends Thread {
	
	private int width, height, lastTime;
	private World world;
	
	public GameObject(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	private int getTime() {
		return (int) (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}
	
	public void start() {
		
		try {
			
			Display.setTitle("Game");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.create();
			
			glClearColor(0.5f, 0.5f, 1, 1);
			glEnable(GL_TEXTURE_2D);

			glOrtho(0, width, 0, height, 1, -1);
		
			int t2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/image.png")).getTextureID(),
					t1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/Playable/Dragon/Dragon.png")).getTextureID();
			
			world = new World(new Vec2(0, -0.01f), 0.01f);
			world.new Entity(new Rectangle(new Vec2(500, 100), new Vec2(500, 100)), new Vec2(0, 0), t1, 0, 0);
			
			lastTime = getTime();

			while(!Display.isCloseRequested()) {
				
				int time = getTime(), delta = time - lastTime;
				lastTime = time;
				
				while(Mouse.next()) {
					if(!Mouse.getEventButtonState()) continue;
					world.new Entity(new Rectangle(Vec2.fromMousePosition(), new Vec2(40, 40)), new Vec2(0, 0), t2, 1, 0.5f);
				}
				
				world.update(delta);
				
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				world.draw();
			
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
