package engine_v01.assets;

import org.lwjgl.Sys;

//A utility class used to compute the time between frames (called delta-time) in milliseconds
//and the FPS
public class LWJGLTimer {
	
	private long lastTime = getTime(), lastFrame;
	private int frames, fps = getFPS();
	
	//Get the current system time using Java's built-in libraries
	private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
	//Check to see if more than one second has passed and increment the number of frames passed by 1
	public int getFPS() {
		if (getTime() - lastFrame > 1000) {
			fps = frames;
			frames = 0;
			lastFrame += 1000;
		}
		frames++;
		return fps;
	}
	
	//Subtract the last time from the current time
	public int delta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastTime);
        lastTime = currentTime;
        return delta;
    }
	
}
