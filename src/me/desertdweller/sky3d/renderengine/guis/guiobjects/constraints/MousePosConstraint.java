package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector2f;
import org.joml.Vector4f;

import me.desertdweller.sky3d.renderengine.Window;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.GUIObject;

public class MousePosConstraint extends Constraint{
	AxisType type;
	GUIObject parentGUI;
	Window window;
	
	public MousePosConstraint(AxisType type, GUIObject parentGUI, Window window) {
		this.type = type;
		this.parentGUI = parentGUI;
		this.window = window;
	}
	
	@Override
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		Vector2f mouse = parentGUI.getMouseRelative(window);
		mouse.add(new Vector2f(1, 1));
		
		float length;
		switch(type) {
		case Y:
			length = edgePosVector.z - edgePosVector.y;
			edgePosVector.y = (float) (mouse.y - (length / 2));
			edgePosVector.z = (float) (mouse.y + (length / 2));
			break;
		case X:
			length = edgePosVector.x - edgePosVector.w;
			edgePosVector.w = (float) (mouse.x - (length / 2));
			edgePosVector.x = (float) (mouse.x + (length / 2));
			break;
		}
		return edgePosVector;
	}
}
