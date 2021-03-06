package me.desertdweller.sky3d.renderengine.shaders;

import java.io.InputStream;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import me.desertdweller.sky3d.renderengine.renderobjects.Camera;
import me.desertdweller.sky3d.renderengine.renderobjects.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final InputStream VERTEX_FILE = StaticShader.class.getResourceAsStream("vertexShader.shader");
	private static final InputStream FRAGMENT_FILE = StaticShader.class.getResourceAsStream("fragmentShader.shader");
	
	private static final int MAX_LIGHTS = 20;
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_cameraPos;
	private int location_useFakeLighting;
	private int location_numberOfRows;
	private int location_offset;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
	    super.bindAttribute(0, "position");
	    super.bindAttribute(1, "textureCoordinates");
	    super.bindAttribute(2, "normal");
	}
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_cameraPos = super.getUniformLocation("cameraPosition");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");
        
        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];
        
        for(int i = 0; i < MAX_LIGHTS; i++) {
        	location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
        	location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
        	location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
      
        }
    }
    
    public void loadNumberOfRows(int numberOfRows) {
    	super.loadFloat(location_numberOfRows, numberOfRows);   
    }
    
    public void loadOffset(float x, float y) {
    	super.load2DVector(location_offset, new Vector2f(x,y));   
    }
    
    public void loadUseFaceLighting(boolean useFake) {
    	super.loadBoolean(location_useFakeLighting, useFake);
    }
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	 
	public void loadTransformationMatrix(Matrix4f matrix){
	    super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights) {
		for(int i = 0; i < MAX_LIGHTS; i++) {
			if(i<lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColour[i], lights.get(i).getColour());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}else {
				super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
				super.loadVector(location_lightColour[i], new Vector3f(0,0,0));
				super.loadVector(location_attenuation[i], new Vector3f(1,0,0));
			}
		}
	}
	 
	public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
	    Vector3f pos = new Vector3f(0,0,0);
	    pos.add(camera.getPosition());
	    super.loadVector(location_cameraPos, pos);
    }
	
	public void loadProjectionMatrix(Matrix4f projection){
	    super.loadMatrix(location_projectionMatrix, projection);
	}
}
