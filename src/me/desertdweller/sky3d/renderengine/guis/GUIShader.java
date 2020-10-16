package me.desertdweller.sky3d.renderengine.guis;

import java.io.InputStream;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.desertdweller.sky3d.renderengine.shaders.ShaderProgram;
import me.desertdweller.sky3d.renderengine.shaders.StaticShader;

public class GUIShader extends ShaderProgram{

	private static final InputStream VERTEX_FILE = StaticShader.class.getResourceAsStream("guiVertexShader.shader");
	private static final InputStream FRAGMENT_FILE = StaticShader.class.getResourceAsStream("guiFragmentShader.shader");
	 
   private int location_transformationMatrix;
   private int location_opacity;
   private int location_cornerRadius;
   private int location_colour;

   public GUIShader() {
       super(VERTEX_FILE, FRAGMENT_FILE);
   }
   
   public void loadModifiers(float opacity, float cornerRadius, Vector3f colour) {
	   super.loadFloat(location_opacity, opacity);
	   if(cornerRadius > 0.5f)
		   cornerRadius = 0.5f;
	   super.loadFloat(location_cornerRadius, cornerRadius);
	   super.loadVector(location_colour, colour);
   }
   
   public void loadTransformation(Matrix4f matrix){
       super.loadMatrix(location_transformationMatrix, matrix);
   }

   @Override
   protected void getAllUniformLocations() {
       location_transformationMatrix = super.getUniformLocation("transformationMatrix");
       location_opacity = super.getUniformLocation("opacity");
       location_cornerRadius = super.getUniformLocation("cornerRadius");
       location_colour = super.getUniformLocation("modColour");
   }

   @Override
   protected void bindAttributes() {
       super.bindAttribute(0, "position");
   }
    
    
    

}