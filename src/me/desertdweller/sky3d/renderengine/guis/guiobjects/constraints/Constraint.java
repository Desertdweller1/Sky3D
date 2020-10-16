package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public abstract class Constraint {

	float distance;
	
	public abstract Vector4f applyConstraints(Vector4f edgePosVector);
}
