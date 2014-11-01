package main;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import engine_v01.assets.LWJGLTimer;
import engine_v01.assets.Rectangle;
import engine_v01.assets.Vector2D;
import engine_v01.assets.World2D;

//The game will be initialized from this class. The initialization/bootup process will
//include creating the world, starting the timer, loading resources, initializing OpenGL, etc.
public class Game {
	
	public static void main(String[] args) {
		
		initGL(800, 600);
		
		Texture texture = null;
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/image.png"));
		} catch(IOException e) {
			e.printStackTrace();
			exit();
		}
		
		//Create timer and world
		LWJGLTimer timer = new LWJGLTimer();
		World2D world = new World2D(new Vector2D(), 0.99f);
		world.new Entity2D(new Rectangle(new Vector2D(400, 300), new Vector2D(60, 60)),
				new Vector2D(), 0, texture.getTextureID());
		
		//Enter main loop
		while(!Display.isCloseRequested()) {
			
			world.update(timer.delta());
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			world.draw();
			
			Display.update();
            Display.sync(60);
			
		}
		
		exit();
		
	}
	
	private static void exit() {
		Display.destroy();
		System.exit(1);
	}
	
	//Initialize the OpenGL environment
	private static void initGL(int width, int height) {
		try {
			Display.setTitle("Game");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.create(new PixelFormat(4,0,0,4));
		} catch(LWJGLException err) {
			err.printStackTrace();
			System.exit(0);
		}             
        
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glEnable(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        glViewport(0, 0, width, height);
 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
}
