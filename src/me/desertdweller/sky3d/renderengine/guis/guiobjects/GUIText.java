package me.desertdweller.sky3d.renderengine.guis.guiobjects;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import me.desertdweller.sky3d.renderengine.font.meshcreator.FontType;
import me.desertdweller.sky3d.renderengine.font.meshcreator.Text;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.Constraint;

public class GUIText{
	private GUIObject parent;
	private float targetOpacity;
	private float opacity;
	private Vector3f colour = new Vector3f(0,0,0);
	private GUITransition transition;
	private Text text;
	protected ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	
	public GUIText(GUIObject parent, Vector2f position, float opacity, Vector3f colour, FontType font, float fontSize, float maxLineLength) {
		text = new Text("", fontSize, font, position, maxLineLength, false);
		this.parent = parent;
		this.targetOpacity = opacity;
		this.colour = colour;
	}
	
	public GUIText(GUIObject parent, Vector2f position, float opacity, Vector3f colour, FontType font, float fontSize, float maxLineLength, GUITransition transition) {
		text = new Text("", fontSize, font, position, maxLineLength, false);
		this.parent = parent;
		this.targetOpacity = opacity;
		this.colour = colour;
		this.transition = transition;
	}

	public GUIObject getParent() {
		return parent;
	}

	public void setParent(GUIObject parent) {
		this.parent = parent;
	}

	public float getTargetOpacity() {
		return targetOpacity;
	}

	public void setTargetOpacity(float targetOpacity) {
		this.targetOpacity = targetOpacity;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public GUITransition getTransition() {
		return transition;
	}

	public void setTransition(GUITransition transition) {
		this.transition = transition;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}

}
