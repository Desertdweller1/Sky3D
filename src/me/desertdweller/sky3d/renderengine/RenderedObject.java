package me.desertdweller.sky3d.renderengine;

import org.joml.Matrix4f;

import me.desertdweller.sky3d.renderengine.models.TextureModel;

public class RenderedObject {

	protected Matrix4f transformation;
	private TextureModel model;
	
	private int textureIndex = 0;
	
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column/(float) model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float) row/(float) model.getTexture().getNumberOfRows();
	}
	
	public Matrix4f getTransformation() {
		return transformation;
	}

	public void setTransformation(Matrix4f transformation) {
		this.transformation = transformation;
	}
	
	public TextureModel getModel() {
		return model;
	}

	public void setModel(TextureModel model) {
		this.model = model;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}
}
