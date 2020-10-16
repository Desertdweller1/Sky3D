package me.desertdweller.sky3d.renderengine.skybox;

import java.io.InputStream;

import org.joml.Matrix4f;

import me.desertdweller.sky3d.renderengine.renderobjects.Camera;
import me.desertdweller.sky3d.renderengine.shaders.ShaderProgram;
import me.desertdweller.sky3d.renderengine.shaders.StaticShader;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram{
 
	private static final InputStream VERTEX_FILE = StaticShader.class.getResourceAsStream("skyboxVertexShader.shader");
	private static final InputStream FRAGMENT_FILE = StaticShader.class.getResourceAsStream("skyboxFragmentShader.shader");
     
    private int location_projectionMatrix;
    private int location_viewMatrix;
     
    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
	 
	public void loadViewMatrix(Camera camera){
       Matrix4f viewMatrix = Maths.createLookMatrix(camera);
       super.loadMatrix(location_viewMatrix, viewMatrix);
   }
    
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
 
}