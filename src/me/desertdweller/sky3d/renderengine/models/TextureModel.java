package me.desertdweller.sky3d.renderengine.models;

import me.desertdweller.sky3d.renderengine.textures.Texture;

public class TextureModel{

	private RawModel rawModel;
	private Texture texture;
	
	public TextureModel(RawModel model, Texture texture) {
		this.rawModel = model;
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}
}
