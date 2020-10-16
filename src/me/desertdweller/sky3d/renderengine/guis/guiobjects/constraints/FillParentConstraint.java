package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import org.joml.Vector4f;

public class FillParentConstraint extends Constraint{
	
	private AxisType type;
	
	public FillParentConstraint(AxisType type) {
		this.type = type;
	}
	
	@Override
	public Vector4f applyConstraints(Vector4f edgePosVector) {
		if(type == AxisType.X)
			return new Vector4f(2f, edgePosVector.y, edgePosVector.z, 0f);
		return new Vector4f(edgePosVector.x, 0f, 2f, edgePosVector.w);
		
	}

}
