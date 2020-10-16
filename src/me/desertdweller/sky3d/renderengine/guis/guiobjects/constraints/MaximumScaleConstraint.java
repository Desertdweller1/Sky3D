package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public class MaximumScaleConstraint extends Constraint{

	AxisType type;
	float maxScale;
	
	public MaximumScaleConstraint(AxisType type, float maxScale) {
		this.type = type;
		this.maxScale = maxScale*2;
	}
	
	@Override
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		if(type == AxisType.X && edgePosVector.x - edgePosVector.w > maxScale) {
			float center = (edgePosVector.x + edgePosVector.w)/2;
			edgePosVector.w = center - maxScale/2;
			edgePosVector.x = center + maxScale/2;
		}
		if(type == AxisType.Y && edgePosVector.z - edgePosVector.y > maxScale) {
			float center = (edgePosVector.z + edgePosVector.y)/2;
			edgePosVector.y = center - maxScale/2;
			edgePosVector.z = center + maxScale/2;
		}
		return edgePosVector;
	}

}
