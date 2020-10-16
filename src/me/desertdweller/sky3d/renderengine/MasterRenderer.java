package me.desertdweller.sky3d.renderengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import me.desertdweller.sky3d.renderengine.models.TextureModel;
import me.desertdweller.sky3d.renderengine.renderobjects.Camera;
import me.desertdweller.sky3d.renderengine.renderobjects.Light;
import me.desertdweller.sky3d.renderengine.shaders.StaticShader;
import me.desertdweller.sky3d.renderengine.skybox.SkyboxRenderer;
import toolbox.MousePicker;

public class MasterRenderer {
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000000;
	
	private static float fov = 90;
	
	private Matrix4f projectionMatrix;
	private StaticShader shader = new StaticShader();
	private Renderer renderer;
	private SkyboxRenderer skyboxRenderer;
	
	private Map<TextureModel,List<RenderedObject>> objects = new HashMap<TextureModel,List<RenderedObject>>();

	public MasterRenderer(Window window, Loader loader) {
		enableCulling();
		createProjectionMatrix(window);
		renderer = new Renderer(shader, window, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		MousePicker.setProjection(projectionMatrix);
		MousePicker.setWindow(window);
	}
	
	public void render(List<Light> lights, Camera camera) {
		prepare();
		shader.start();
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(objects);
		shader.stop();
		skyboxRenderer.render(camera);
		objects.clear();
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void processObject(RenderedObject object) {
		TextureModel objectModel = object.getModel();
		List<RenderedObject> batch = objects.get(objectModel);
		if(batch != null) {
			batch.add(object);
		}else {
			List<RenderedObject> newBatch = new ArrayList<RenderedObject>();
			newBatch.add(object);
			objects.put(objectModel, newBatch);
		}
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
	}
	
	private void createProjectionMatrix(Window window){
        float aspectRatio = (float) window.getWidth() / (float) window.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
        projectionMatrix.scale(1);
    }
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
