package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public class CenterConstraint extends Constraint{
	AxisType type;
	
	public CenterConstraint(AxisType type, float distance) {
		this.type = type;
		this.distance = distance*2;
	}
	
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		float length;
		switch(type) {
		case X:
			length = edgePosVector.x - edgePosVector.w;
			
			edgePosVector.w = 1f - (length / 2) + distance;
			edgePosVector.x = 1f + (length / 2) + distance;
			break;
		case Y:
			length = edgePosVector.z - edgePosVector.y;
			edgePosVector.y = 1f - (length / 2) + distance;
			edgePosVector.z = 1f + (length / 2) + distance;
			break;
		}
		return edgePosVector;
	}
}
