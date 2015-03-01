package engine_v01.assets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class GLSLProgram {

	private int id;
	private Map<String, Integer> uniformLocations = new HashMap<String, Integer>();
	
	public GLSLProgram(int...shaders) {
		id = glCreateProgram();
		for(int shader : shaders) {
			glAttachShader(id, shader);
		}
		glLinkProgram(id);
	}
	
	public static int loadShader(int type, String location) throws IOException {
		int id = glCreateShader(type);
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(location));
        String line;
        while ((line = reader.readLine()) != null) {
            shaderSource.append(line).append('\n');
        }
        reader.close();
        glShaderSource(id, shaderSource);
        glCompileShader(id);
        if(glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) throw new IOException(glGetShaderInfoLog(id, 1024));
        return id;
	}
	
	private int getUniformLocation(String name) {
		Integer location = uniformLocations.get(name);
		if(location == null) {
			location = glGetUniformLocation(id, name);
			uniformLocations.put(name, location);
		}
		return location;
	}
	
	public void use() {
		glUseProgram(id);
	}
	
	public void delete() {
		glDeleteProgram(id);
	}
	
	public void setUniform(String name, float value) {
		glUniform1f(getUniformLocation(name), value);
	}
	
	public void setUniform(String name, float x, float y) {
		glUniform2f(getUniformLocation(name), x, y);
	}
	
	public void setUniform(String name, float x, float y, float z) {
		glUniform3f(getUniformLocation(name), x, y, z);
	}
	
	public void setUniform(String name, float r, float g, float b, float a) {
		glUniform4f(getUniformLocation(name), r, g, b, a);
	}
	
}
