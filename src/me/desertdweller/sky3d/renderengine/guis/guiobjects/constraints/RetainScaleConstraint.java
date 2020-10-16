package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public class RetainScaleConstraint extends Constraint{

	float targetScale;
	AxisType type;
	
	public RetainScaleConstraint(float targetScale, AxisType type) {
		this.targetScale = targetScale*2;
		this.type = type;
	}
	
	@Override
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		if(type == AxisType.X) {
			float scaleDifference = targetScale - (edgePosVector.x - edgePosVector.w);
			edgePosVector.w = edgePosVector.w - (scaleDifference / 2);
			edgePosVector.x = edgePosVector.x + (scaleDifference / 2);
		}else {
			float scaleDifference = targetScale - (edgePosVector.z - edgePosVector.y);
			edgePosVector.y = edgePosVector.y - (scaleDifference / 2);
			edgePosVector.z = edgePosVector.z + (scaleDifference / 2);
		}
		return edgePosVector;
	}

}
