package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public class EdgeConstraint extends Constraint{
	EdgeType type;

	public EdgeConstraint(float distance, EdgeType type) {
		this.distance = distance;
		this.type = type;
	}

	@Override
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		float length;
		switch(type) {
		case LEFT:
			length = edgePosVector.x - edgePosVector.w;
			edgePosVector.w = distance;
			edgePosVector.x = distance + length;
			break;
		case RIGHT:
			length = edgePosVector.x - edgePosVector.w;
			edgePosVector.w = 2 - distance - length;
			edgePosVector.x = 2 - distance;
			break;
		case BOTTOM:
			length = edgePosVector.z - edgePosVector.y;
			edgePosVector.y = distance;
			edgePosVector.z = distance + length;
			break;
		case TOP:
			length = edgePosVector.z - edgePosVector.y;
			edgePosVector.y = 2 - distance - length;
			edgePosVector.z = 2 - distance;
			break;
		}
 		return edgePosVector;
	}
}
