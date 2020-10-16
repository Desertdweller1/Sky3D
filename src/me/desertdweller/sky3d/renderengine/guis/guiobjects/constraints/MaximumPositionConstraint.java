package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public class MaximumPositionConstraint extends Constraint{
	EdgeType type;
	
	public MaximumPositionConstraint(EdgeType type, float distance) {
		this.type = type;
		this.distance = distance*2;
	}

	@Override
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		float length;
		switch(type) {
		case RIGHT:
			length = edgePosVector.x - edgePosVector.w;
			if(edgePosVector.x > distance) {
				edgePosVector.x = distance;
				edgePosVector.w = distance - length;
			}
			break;
		case LEFT:
			length = edgePosVector.x - edgePosVector.w;
			if(edgePosVector.w < distance) {
				edgePosVector.w = distance;	
				edgePosVector.x = edgePosVector.w + length;
			}
			break;
		case TOP:
			length = edgePosVector.z - edgePosVector.y;
			if(edgePosVector.z > distance) {
				edgePosVector.z = distance;
				edgePosVector.y = distance - length;	
			}
			break;
		case BOTTOM:
			length = edgePosVector.z - edgePosVector.y;
			if(edgePosVector.y < distance) {
				edgePosVector.y = distance;	
				edgePosVector.z = edgePosVector.y + length;
			}
			break;
		}
		return edgePosVector;
	}
}
