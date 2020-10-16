package me.desertdweller.sky3d.core;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_INVALID_ENUM;
import static org.lwjgl.opengl.GL11.GL_INVALID_OPERATION;
import static org.lwjgl.opengl.GL11.GL_INVALID_VALUE;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_OUT_OF_MEMORY;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STACK_OVERFLOW;
import static org.lwjgl.opengl.GL11.GL_STACK_UNDERFLOW;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import me.desertdweller.sky3d.input.Controls;
import me.desertdweller.sky3d.renderengine.Loader;
import me.desertdweller.sky3d.renderengine.MasterRenderer;
import me.desertdweller.sky3d.renderengine.Window;
import me.desertdweller.sky3d.renderengine.font.renderer.TextMaster;
import me.desertdweller.sky3d.renderengine.guis.GUIRenderer;

public class GameLoop {
	
	private int addedLagTime = 0;
	
	private boolean debugMode = true;
	private boolean running = true;
	private Game gameClass;
	private int ticks = 0;
	private Window window;
	private Controls controls;
	private Loader loader;
	private MasterRenderer renderer;
	private GUIRenderer guiRenderer;

	public GameLoop(Game gameClass, int w, int h) {
		this.gameClass = gameClass;
		
		//GLFW Initializations
		if(!glfwInit())
			throw new IllegalStateException("Not able to initialize GLFW.");
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		window = new Window(w, h);
		window.setFullscreen(false);
		if(gameClass.getName() == null) {
			window.createWindow("Game");
		}else {
			window.createWindow(gameClass.getName());
		}
		controls = new Controls();
		window.initControls(controls);

		GL.createCapabilities();
		//camera = new Camera((int) wSize.x, (int) wSize.y);
		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
		
		loader = new Loader();
		renderer = new MasterRenderer(window, loader);
		guiRenderer = new GUIRenderer(loader);
	}
	
	
	public void run() {
		System.out.println("Debug: " + debugMode);
		long lastTime = System.nanoTime();
		long lastTickTime = System.currentTimeMillis();
		int tps = 60; //Rate of the game ticks per second.
		double ns = 1000000000 / tps;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			while(delta >=1) {
				long miliNow = System.currentTimeMillis();
				gameClass.gameTick((miliNow - lastTickTime)/1000d);
				delta--;
				ticks++;
				lastTickTime = miliNow;
			}
			if(running) {
				//camera.setSize((int) Math.round(wSize.x), (int) Math.round(wSize.y)); 
				gameClass.renderTick();
				TextMaster.render();
				window.swapBuffers();
				GLFW.glfwPollEvents();
				
				if(addedLagTime > 0) {
					double temptime = System.currentTimeMillis();
					while(temptime + addedLagTime > System.currentTimeMillis()) {
						
					}
				}
				
				long error = glGetError();
				if(error == GL_INVALID_ENUM) {
					System.out.println("GL invalid enum");
				}else if(error == GL_INVALID_VALUE){
					System.out.println("GL invalid value");
				}else if(error == GL_INVALID_OPERATION){
					System.out.println("GL invalid operation");
				}else if(error == GL_OUT_OF_MEMORY){
					System.out.println("GL out of memory!");
				}else if(error == GL_STACK_UNDERFLOW){
					System.out.println("GL stack underflow");
				}else if(error == GL_STACK_OVERFLOW){
					System.out.println("GL stack overflow");
				}else if(error != GL_NO_ERROR){
					System.out.println("GL error!");
				}
			}
			lastTime = now;
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if(debugMode) {
					System.out.println("FPS: " + frames);
					System.out.println("TPS: " + ticks);
				}
				ticks = 0;
				frames = 0;
			}
			if(glfwWindowShouldClose(window.getWindow())) {
				running = false;
			}
		}
		TextMaster.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		glfwTerminate();
	}
	
	
	public void stop() {
		running = false;
	}
	
	public Loader getLoader() {
		return loader;
	}
	public MasterRenderer getRenderer() {
		return renderer;
	}
	public Controls getControls() {
		return controls;
	}
	public GUIRenderer getGUIRenderer() {
		return guiRenderer;
	}
	public Window getWindow() {
		return window;
	}
}
