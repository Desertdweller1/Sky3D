package me.desertdweller.sky3d.renderengine;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import me.desertdweller.sky3d.renderengine.models.RawModel;
import me.desertdweller.sky3d.renderengine.models.TextureModel;
import me.desertdweller.sky3d.renderengine.shaders.StaticShader;
import me.desertdweller.sky3d.renderengine.textures.Texture;

public class Renderer {
	private StaticShader shader;
	
	public Renderer(StaticShader shader, Window window, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
		
	}
	
	public void render(Map<TextureModel, List<RenderedObject>> renderedObjects) {
		for(TextureModel model : renderedObjects.keySet()) {
			prepareTextureModel(model);
			for(RenderedObject object : renderedObjects.get(model)) {
				prepareInstance(object);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTextureModel();
		}
	}
	
	private void prepareTextureModel(TextureModel model) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Texture texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if(texture.isTransparent())
			MasterRenderer.disableCulling();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		shader.loadUseFaceLighting(texture.isUsingFakeLighting());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTextureModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(RenderedObject object) {
		shader.loadTransformationMatrix(object.getTransformation());
		shader.loadOffset(object.getTextureXOffset(), object.getTextureYOffset());
	}
	
	@Deprecated
	public void render(TextureModel textureModel, Matrix4f transformation, StaticShader shader) {
		RawModel model = textureModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		shader.loadTransformationMatrix(transformation);
		shader.loadShineVariables(textureModel.getTexture().getShineDamper(), textureModel.getTexture().getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureModel.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
}
