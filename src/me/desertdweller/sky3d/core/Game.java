package me.desertdweller.sky3d.core;

public interface Game {
	String[] skyboxTextures = {"assets/skybox/right", "assets/skybox/left", "assets/skybox/top", "assets/skybox/bottom", "assets/skybox/back", "assets/skybox/front"};
	
	void gameTick(double delta);
	void renderTick();
	
	String getName();
}
