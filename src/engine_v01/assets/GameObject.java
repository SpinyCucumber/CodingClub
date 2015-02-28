package engine_v01.assets;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

//The game will be initialized from this class. The initialization/boot process will
//include creating the world, starting the timer, loading resources, initializing OpenGL, etc.
public class GameObject extends Thread {
	
	private int width, height, time, lastTime;
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
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			glOrtho(0, width, height, 0, 1, -1);
		
			Texture t1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/material/sandstone.png")),
					t2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/Playable/Knight/Knight_Good.png"));
			
			lastTime = getTime();
			world = new World(new Vec2(0, 0.01f), 0.01f);
			world.new Entity(Rectangle.fromCorners(new Vec2(0, height - 150), new Vec2(width, height)), new Vec2(0, 0), t1, false, 0, 0.3f, 0.9f, 0.99f);
			ArrayList<Vec2> clickPoints = new ArrayList<Vec2>();
			
			while(!Display.isCloseRequested()) {
				
				time = getTime();
				int delta = time - lastTime;
				
				lastTime = time;
				
				while(Mouse.next()) {
					if(!Mouse.getEventButtonState()) continue;
					Vec2 m = new Vec2(Mouse.getX(), height - Mouse.getY());
					switch(Mouse.getEventButton()) {
						case 0 : clickPoints.add(m);
						break;
						case 1 : new Player(world, Rectangle.fromHalfDimension(m, new Vec2(16, 32)), new Vec2(0, 0), t2, true, 1, 0.3f, 0.6f, 0.8f);
						break;
					}
				}
				
				while(Keyboard.next()) {
					if(!Keyboard.getEventKeyState()) continue;
					switch(Keyboard.getEventKey()) {
						case Keyboard.KEY_SPACE : {
							if(clickPoints.size() < 3) continue;
							world.new Entity(new Shape(clickPoints.toArray(new Vec2[clickPoints.size()])), new Vec2(0, 0), t1, false, 0, 0.3f, 0.6f, 0.5f);
							clickPoints.clear();
							break;
						}
						case Keyboard.KEY_C : clickPoints.clear();
						break;
						case Keyboard.KEY_ESCAPE : return;
					}
				}
				
				world.update(delta);
				
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				world.draw();
				
				if(clickPoints.size() > 1) {
					float i = 0.5f + 0.5f * (float) Math.sin(0.003f * time);
					glDisable(GL_TEXTURE_2D);
					glColor4f(0, 0, 0, i);
					glBegin(GL_LINE_LOOP);
					for(Vec2 v : clickPoints) v.glVertex();
					glEnd();
					glColor4f(1, 1, 1, 1);
					glEnable(GL_TEXTURE_2D);
				}
			
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
