package me.desertdweller.sky3d.renderengine.guis;

import org.joml.Vector2f;

public class GUITexture {

	protected int texture;
	private Vector2f position;
	private Vector2f scale;
	
	public GUITexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	public void setTexture(int texture) {
		this.texture = texture;
	}
	
	
}
