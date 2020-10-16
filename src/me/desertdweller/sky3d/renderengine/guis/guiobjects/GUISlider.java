package me.desertdweller.sky3d.renderengine.guis.guiobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import me.desertdweller.sky3d.renderengine.Window;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.AxisType;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.ConstraintCompiler;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.EdgeType;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.MaximumPositionConstraint;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.MousePosConstraint;

public class GUISlider extends GUIObject{
	private GUIButton button;
	private AxisType type;
	private MousePosConstraint mouseConstraint;

	public GUISlider(GUIObject parent, int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUIButton button, AxisType type, Window window) {
		super(parent, texture, position, scale, opacity, cornerRadius, colour);
		button.setParentGUI(this);
		button.addConstraint(new MaximumPositionConstraint(EdgeType.BOTTOM, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.TOP, 1));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.LEFT, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.RIGHT, 1));
		this.button = button;
		this.type = type;
		mouseConstraint = new MousePosConstraint(type, button, window);
	}
	
	public GUISlider(GUIObject parent, int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUITransition transition, GUIButton button, AxisType type, Window window) {
		super(parent, texture, position, scale, opacity, cornerRadius, colour, transition);
		button.setParentGUI(this);
		button.addConstraint(new MaximumPositionConstraint(EdgeType.BOTTOM, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.TOP, 1));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.LEFT, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.RIGHT, 1));
		this.button = button;
		this.type = type;
		mouseConstraint = new MousePosConstraint(type, button, window);
	}
	
	public GUISlider(int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUIButton button, AxisType type, Window window) {
		super(texture, position, scale, opacity, cornerRadius, colour);
		button.setParentGUI(this);
		button.addConstraint(new MaximumPositionConstraint(EdgeType.BOTTOM, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.TOP, 1));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.LEFT, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.RIGHT, 1));
		this.button = button;
		this.type = type;
		mouseConstraint = new MousePosConstraint(type, button, window);
	}
	
	public GUISlider(int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUITransition transition, GUIButton button, AxisType type, Window window) {
		super(texture, position, scale, opacity, cornerRadius, colour, transition);
		button.setParentGUI(this);
		button.addConstraint(new MaximumPositionConstraint(EdgeType.BOTTOM, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.TOP, 1));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.LEFT, 0));
		button.addConstraint(new MaximumPositionConstraint(EdgeType.RIGHT, 1));
		this.button = button;
		this.type = type;
		mouseConstraint = new MousePosConstraint(type, button, window);
	}
	
	public float getValue() {
		if(type == AxisType.X) {
			return button.getPosition().x;
		}else {
			return button.getPosition().y;
		}
	}
	
	public void updateConstraints() {
		Vector4f newPos = ConstraintCompiler.compile(constraints, new Vector4f(this.getPosition().y, this.getScale().x, this.getScale().y, this.getPosition().x));
		this.setPosition(new Vector2f(newPos.w, newPos.x));
		this.setScale(new Vector2f(newPos.y, newPos.z));
		
		
		
		if(button.mouseHeld && !button.hasConstraintType(mouseConstraint)) {
			button.addConstraintToBeginning(mouseConstraint);
		}else if(!button.mouseHeld && button.hasConstraintType(mouseConstraint)) {
			button.removeConstraint(mouseConstraint);
		}
		
	}
}
