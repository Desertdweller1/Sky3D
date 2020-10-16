package me.desertdweller.sky3d.renderengine.guis.guiobjects;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import me.desertdweller.sky3d.input.Controls;
import me.desertdweller.sky3d.renderengine.Window;

public class GUIContainer extends GUIObject{
	protected List<GUIObject> guis = new ArrayList<GUIObject>();
	
	
	public GUIContainer(GUIObject parent, Vector2f position, Vector2f scale, float opacity, GUITransition transition) {
		super(parent, -1, position, scale, opacity, 0f, new Vector3f(0,0,0), transition);
	}
	
	public GUIContainer(GUIObject parent, Vector2f position, Vector2f scale, float opacity) {
		super(parent, -1, position, scale, opacity, 0f, new Vector3f(0,0,0));
	}
	
	public void update(double delta) {
		this.transition(delta);
		for(GUIObject gui : guis) {
			gui.transition(delta);
			if(gui instanceof GUIContainer)
				((GUIContainer) gui).update(delta);
		}
		this.handle();
	}
	
	public void handle() {
		
	}
	
	public List<GUIObject> updateForRender(Controls controls, Window window) {
		for(GUIObject gui : guis) {
			if(gui instanceof GUIContainer) {
				((GUIContainer) gui).updateForRender(controls, window);
			}else {
				gui.checkForMouseInteraction(window, controls);
			}
		}
		return guis;
	}
}
