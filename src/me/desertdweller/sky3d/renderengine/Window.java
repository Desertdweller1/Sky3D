package me.desertdweller.sky3d.renderengine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_STENCIL_BITS;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;

import me.desertdweller.sky3d.input.Controls;


public class Window {
	private long window;
	private int width,height;
	private GLFWVidMode videoMode;
	private boolean fullscreen = false;
	
	public Window(int w, int h) {
		width = w;
		height = h;
		
	}
	
	public void createWindow(String title) {
		glfwWindowHint(GLFW_STENCIL_BITS, 4);
		glfwWindowHint(GLFW_SAMPLES, 4);
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		
		if(window == 0) {
			throw new IllegalStateException("Failed to create new window!");
		}
		
		if(!fullscreen) {
			glfwShowWindow(window);
			videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (videoMode.width() - (int) Math.round(width))/2, (videoMode.height() - (int) Math.round(height))/2);
		}
		
		glfwMakeContextCurrent(window);
		
		glfwSetErrorCallback(new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}
		});
		
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void initControls(Controls controls) {
		glfwSetKeyCallback(window, new GLFWKeyCallback() {
			@Override
			public void invoke(long win, int key, int scancode, int action, int mods) {
				if(action == GLFW_PRESS) {
					controls.keyPressed(key);
				}else if(action == GLFW_RELEASE) {
					controls.keyReleased(key);}}});
		glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallbackI() {
			@Override
			public void invoke(long win, int button, int action, int mods) {
				if(action == GLFW_PRESS) {
					controls.mousePressed(button);
				}else if(action == GLFW_RELEASE) {
					controls.mouseReleased(button);}}});
		glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double x, double y) {
				controls.mouseMoved(x, y);
				
			}});
	}
	
	public long getWindow() {
		return window;
	}
	public void setWindow(long window) {
		this.window = window;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	

}