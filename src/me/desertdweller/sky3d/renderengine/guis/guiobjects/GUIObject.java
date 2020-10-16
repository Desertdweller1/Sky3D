package me.desertdweller.sky3d.renderengine.guis.guiobjects;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import me.desertdweller.sky3d.input.ControlType;
import me.desertdweller.sky3d.input.Controls;
import me.desertdweller.sky3d.renderengine.Window;
import me.desertdweller.sky3d.renderengine.guis.GUITexture;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.Constraint;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints.ConstraintCompiler;
import toolbox.Maths;

public class GUIObject extends GUITexture implements Cloneable{

	private GUIObject parent;
	private float cornerRadius;
	private float targetOpacity;
	private float opacity;
	private Vector3f colour = new Vector3f(0,0,0);
	private GUITransition transition;
	protected boolean mouseHovered = false;
	protected boolean mouseHeld = false;
	protected ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	
	public GUIObject(int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour) {
		super(texture, position, scale);
		this.cornerRadius = cornerRadius;
		this.targetOpacity = opacity;
		this.colour = colour;
	}

	public GUIObject(int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUITransition transition) {
		super(texture, position, scale);
		this.cornerRadius = cornerRadius;
		this.targetOpacity = opacity;
		this.colour = colour;
		this.transition = transition;
	}
	
	public GUIObject(GUIObject parent, int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour) {
		super(texture, position, scale);
		this.cornerRadius = cornerRadius;
		this.targetOpacity = opacity;
		this.colour = colour;
		this.parent = parent;
	}

	public GUIObject(GUIObject parent, int texture, Vector2f position, Vector2f scale, float opacity, float cornerRadius, Vector3f colour, GUITransition transition) {
		super(texture, position, scale);
		this.cornerRadius = cornerRadius;
		this.targetOpacity = opacity;
		this.colour = colour;
		this.transition = transition;
		this.parent = parent;
	}
	
	
	public void updateConstraints() {
		Vector4f newPos = ConstraintCompiler.compile(constraints, new Vector4f(this.getPosition().y, this.getScale().x, this.getScale().y, this.getPosition().x));
		this.setPosition(new Vector2f(newPos.w, newPos.x));
		this.setScale(new Vector2f(newPos.y, newPos.z));
	}
	
	public void transition(double delta) {
		if(transition == null) {
			updateConstraints();
			if(parent != null && targetOpacity > parent.getOpacity()) {
				opacity = parent.getOpacity();
			}else {
				opacity = targetOpacity;
			}
			return;
		}
		
		if(transition.getWaitTime() > 0) {
			transition.setWaitTime(transition.getWaitTime() - delta);
			if(transition.getWaitTime() < 0) {
				delta = -transition.getWaitTime();
			}else {
				delta = 0;
			}
		}
		
		transition.update(delta);
		if(transition.getTimeLeft() < 0)
			delta = transition.getTimeLeft() + delta;
		
		Vector2f deltaPosition = new Vector2f();
		transition.getPositionRate().mul((float) delta, deltaPosition);
		this.getPosition().add(deltaPosition);
		
		Vector2f deltaScale = new Vector2f();
		transition.getScaleRate().mul((float) delta, deltaScale);
		this.getScale().add(deltaScale);
		
		Vector3f deltaColour = new Vector3f();
		transition.getScaleRate().mul((float) delta, deltaScale);
		this.getScale().add(deltaScale);
		colour.add(deltaColour);
		
		targetOpacity += transition.getOpacityRate()*delta;
		
		cornerRadius += transition.getCornerRadiusRate()*delta;
		
		if(transition.getTimeLeft() <= 0)
			transition = transition.getFollowingTransition();

		if(parent != null && targetOpacity > parent.getOpacity()) {
			opacity = parent.getOpacity();
		}else {
			opacity = targetOpacity;
		}
	}
	
	public Vector2f getMouseRelative(Window window) {
		double mouseX = Controls.getMousePos().x;
		double mouseY = window.getHeight() - Controls.getMousePos().y;
		//Turning the mouse pos into a number between 0-2
		mouseX = (mouseX/window.getWidth())*2;
		mouseY = (mouseY/window.getHeight())*2;
		Vector2f positionOffset = new Vector2f(1,1);
//		if(this instanceof GUIButton)
//			System.out.println("Relative Pos Y - " + getRelativePosition().y + "  Relative Scale Y - " + getRelativeScale().y);
		positionOffset.x = (getRelativePosition().x + 1) / getRelativeScale().x;
		positionOffset.y = (getRelativePosition().y + 1) / getRelativeScale().y;
//		if(this instanceof GUIButton)
//			System.out.println("Offsets: X- " + positionOffset.x + "  Y- " + positionOffset.y);
		mouseX = mouseX / getRelativeScale().x;
		mouseY = mouseY / getRelativeScale().y;
		mouseX -= positionOffset.x - 1;
		mouseY -= positionOffset.y - 1;
//		if(this instanceof GUIButton)
//			System.out.println("Mouse coords in relation: X- " + mouseX + "  Y- " + mouseY);
		return new Vector2f((float) mouseX - 1, ((float) mouseY - 1));
	}
	
	public void checkForMouseInteraction(Window window, Controls controls) {
		Vector2f mouse = getMouseRelative(window);
		mouse.add(new Vector2f(1,1));
		

//		if(this instanceof GUIButton)
//			System.out.println("Mouse coords in relation: X- " + mouse.x + "  Y- " + mouse.y);
		
		Vector4f sideVec = Maths.toSideVector(new Vector4f(getPosition().y, getScale().x, getScale().y, getPosition().x));
		
//		if(this instanceof GUIButton) {
//			System.out.println((double) mouse.x + "  " + (double) mouse.y);
//			System.out.println("w: " + (double) sideVec.w);
//			System.out.println("x: " + (double) sideVec.x);
//			System.out.println("y: " + (double) sideVec.y);
//			System.out.println("z: " + (double) sideVec.z);
//		}
		
		if(!controls.controlIsHeld(ControlType.MOUSE_1)) {
			if((mouse.x < sideVec.x && mouse.x > sideVec.w) && (mouse.y < sideVec.z && mouse.y > sideVec.y)) {
				if(!mouseHovered) {
					mouseHovered = true;
					onMouseHover();
				}
				if(mouseHeld) {
					onMouseGoodRelease();
					mouseHeld = false;
				}
			}else {
				if(mouseHovered) {
					mouseHovered = false;
					if(mouseHeld) {
						onMouseBadRelease();
					}else {
						onMouseUnHover();
					}
					mouseHeld = false;
				}
			}
		}else if(mouseHovered && !mouseHeld) {
			mouseHeld = true;
			onMouseClicked();
		}
	}
	
	protected void onMouseHover() {
		
	}
	
	protected void onMouseUnHover() {
		
	}
	
	protected void onMouseClicked() {
		
	}
	
	protected void onMouseBadRelease() {
		
	}
	
	protected void onMouseGoodRelease() {
		
	}
	
	public Vector2f getGlobalScale() {
		Vector2f scale = new Vector2f(1,1);
		GUIObject curParent = this;
		while(curParent != null) {
			scale = new Vector2f(scale.x*curParent.getScale().x, scale.y*curParent.getScale().y);
			curParent = (GUIObject) curParent.parent;
		}
		return scale;
	}
	
	public Vector2f getGlobalScale(float x, float y) {
		Vector2f scale = new Vector2f(x,y);
		GUIObject curParent = this;
		while(curParent != null) {
			scale = new Vector2f(scale.x*curParent.getScale().x, scale.y*curParent.getScale().y);
			curParent = (GUIObject) curParent.parent;
		}
		return scale;
	}
	
	public Vector2f getGlobalPosition() {
		Vector2f position = new Vector2f(0,0);
		GUIObject curParent = this;
		while(curParent != null) {
			position.add(new Vector2f(curParent.getPosition().x * curParent.getRelativeScale().x, curParent.getPosition().y * curParent.getRelativeScale().y));
			
			curParent = curParent.parent;
		}
		return position;
	}
	
	public Vector2f getGlobalPosition(float x, float y) {
		Vector2f position = new Vector2f(x,y);
		GUIObject curParent = this;
		while(curParent != null) {
			position.add(new Vector2f(curParent.getPosition().x * curParent.getRelativeScale().x, curParent.getPosition().y * curParent.getRelativeScale().y));
			
			curParent = curParent.parent;
		}
		return position;
	}
	
	public Vector2f getRelativeScale() {
		Vector2f scale = new Vector2f(1,1);
		GUIObject curParent = parent;
		while(curParent != null) {
			scale = new Vector2f(scale.x*curParent.getScale().x, scale.y*curParent.getScale().y);
			curParent = curParent.parent;
		}
		return scale;
	}
	
	public Vector2f getRelativePosition() {
		Vector2f position = new Vector2f(0,0);
		GUIObject curParent = parent;
		while(curParent != null) {
			position.add(new Vector2f(curParent.getPosition().x * curParent.getRelativeScale().x, curParent.getPosition().y * curParent.getRelativeScale().y));
			curParent = curParent.parent;
		}
		return position;
	}
	
	public void addConstraintToBeginning(Constraint constraint) {
		List<Constraint> newConstList = constraints;
		newConstList.add(0, constraint);
		constraints = (ArrayList<Constraint>) newConstList;
	}
	
	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}
	
	public void removeConstraint(Constraint constraint) {
		constraints.remove(constraint);
	}
	
	public boolean hasConstraintType(Constraint constraintCheck) {

		for(Constraint constraint : constraints) {
			if(constraint.getClass().equals( constraintCheck.getClass())) {
				return true;
			}
		}
		return false;
	}

	public float getCornerRadius() {
		return cornerRadius;
	}

	public void setCornerRadius(float cornerRadius) {
		this.cornerRadius = cornerRadius;
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
	
	public GUIObject getParentGUI() {
		return parent;
	}
	
	public void setParentGUI(GUIObject parent) {
		this.parent = parent;
	}
	
}
