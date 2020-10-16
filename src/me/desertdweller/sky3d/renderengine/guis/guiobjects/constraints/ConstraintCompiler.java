package me.desertdweller.sky3d.renderengine.guis.guiobjects.constraints;

import java.util.ArrayList;

import org.joml.Vector4f;

import toolbox.Maths;

public class ConstraintCompiler {

	//EdgeConstaint
	//CenterConstraint
	
	public static Vector4f compile(ArrayList<Constraint> constraints, Vector4f input) {
		input = Maths.toSideVector(input);
		for(Constraint constraint : constraints) {
			input = constraint.applyConstraints(input);
		}
		input = Maths.toNormalVec4(input);
		
		return input;
	}
	
}
