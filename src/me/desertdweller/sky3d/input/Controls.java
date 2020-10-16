package me.desertdweller.sky3d.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector2d;

import toolbox.MousePicker;

public class Controls{
	private static Vector2d previousMousePos = new Vector2d(0,0);
	private HashMap<Integer, ControlType> keyMap;
	private HashMap<Integer, ControlType> mouseMap;
	private ArrayList<ControlType> pressedList = new ArrayList<ControlType>();
	
	public static Vector2d getMousePos() {
		return previousMousePos;
	}

	
	public Controls() {
		keyMap = new HashMap<Integer, ControlType>();
		mouseMap = new HashMap<Integer, ControlType>();
		keyMap.put(GLFW_KEY_A, ControlType.A);
		keyMap.put(GLFW_KEY_W, ControlType.W);
		keyMap.put(GLFW_KEY_D, ControlType.D);
		keyMap.put(GLFW_KEY_S, ControlType.S);
		keyMap.put(GLFW_KEY_Q, ControlType.Q);
		keyMap.put(GLFW_KEY_E, ControlType.E);
		keyMap.put(GLFW_KEY_R, ControlType.R);
		keyMap.put(GLFW_KEY_F, ControlType.F);
		keyMap.put(GLFW_KEY_Z, ControlType.Z);
		keyMap.put(GLFW_KEY_X, ControlType.X);
		keyMap.put(GLFW_KEY_LEFT_CONTROL, ControlType.LEFT_CONTROL);
		keyMap.put(GLFW_KEY_LEFT_SHIFT, ControlType.LEFT_SHIFT);
		mouseMap.put(GLFW_MOUSE_BUTTON_LEFT, ControlType.MOUSE_1);
	}
	
	public void mouseMoved(double x, double y) {
		previousMousePos.x = x;
		previousMousePos.y = y;
		MousePicker.update((float) x, (float) y); 
	}

	void keycallback(long win, int key, int scancode, int MOUSE_1, int mods) {
		if(MOUSE_1 == GLFW_PRESS) {
			keyPressed(key);
		}else if(MOUSE_1 == GLFW_RELEASE) {
			keyReleased(key);
		}
	}
	
	public void keyPressed(int key) {
		if(!keyMap.containsKey(key))
			return;
		controlPressed(keyMap.get(key));
	}
	
	public void keyReleased(int key) {
		if(!keyMap.containsKey(key))
			return;
		controlReleased(keyMap.get(key));
	}
	
	public void mousePressed(int button) {
		if(!mouseMap.containsKey(button))
			return;
		controlPressed(mouseMap.get(button));
	}
	
	public void mouseReleased(int button) {
		if(!mouseMap.containsKey(button))
			return;
		controlReleased(mouseMap.get(button));
		
	}
	
	private void controlPressed(ControlType control) {
		if(pressedList.contains(control)) {
			return;
		}
		pressedList.add(control);
	}
	
	private void controlReleased(ControlType control) {
		pressedList.remove(control);
	}
	
	public boolean controlIsHeld(ControlType type) {
		return pressedList.contains(type);
	}
}
