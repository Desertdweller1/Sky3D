package me.desertdweller.sky3d.renderengine.guis.guiobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class GUITransition {

	private double timeLeft;
	private double waitTime;
	
	private Vector3f colourRate;
	private Vector2f positionRate;
	private Vector2f scaleRate;
	private float cornerRadiusRate;
	private float opacityRate;
	private GUITransition followingTransition;
	
	public GUITransition(double timeToFinish, Vector3f colourDifference, Vector2f positionDifference, Vector2f scaleDifference, float opacityDifference, float cornerRadiusDifference) {
		timeLeft = timeToFinish;
		colourRate = new Vector3f((float) (colourDifference.x / timeToFinish), (float) (colourDifference.y / timeToFinish), (float) (colourDifference.z / timeToFinish));
		positionRate = new Vector2f((float) (positionDifference.x / timeToFinish), (float) (positionDifference.y / timeToFinish));
		if(Float.isInfinite(positionRate.x))
			positionRate.x = 0;
		if(Float.isInfinite(positionRate.y))
			positionRate.y = 0;
		
		scaleRate = new Vector2f((float) (scaleDifference.x / timeToFinish), (float) (scaleDifference.y / timeToFinish));
		cornerRadiusRate = (float) (cornerRadiusDifference / timeToFinish);
		opacityRate = (float) (opacityDifference / timeToFinish);
	}
	
	public GUITransition(double timeToFinish, Vector3f colourDifference, Vector2f positionDifference, Vector2f scaleDifference, float opacityDifference, float cornerRadiusDifference, double waitTime) {
		timeLeft = timeToFinish;
		colourRate = new Vector3f((float) (colourDifference.x / timeToFinish), (float) (colourDifference.y / timeToFinish), (float) (colourDifference.z / timeToFinish));
		positionRate = new Vector2f((float) (positionDifference.x / timeToFinish), (float) (positionDifference.y / timeToFinish));
		if(Float.isInfinite(positionRate.x))
			positionRate.x = 0;
		if(Float.isInfinite(positionRate.y))
			positionRate.y = 0;
		
		scaleRate = new Vector2f((float) (scaleDifference.x / timeToFinish), (float) (scaleDifference.y / timeToFinish));
		cornerRadiusRate = (float) (cornerRadiusDifference / timeToFinish);
		opacityRate = (float) (opacityDifference / timeToFinish);
		this.setWaitTime(waitTime);
	}
	
	public void update(double delta) {
		timeLeft -= delta;
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(long timeLeft) {
		this.timeLeft = timeLeft;
	}

	public Vector3f getColourRate() {
		return colourRate;
	}

	public Vector2f getPositionRate() {
		return positionRate;
	}

	public Vector2f getScaleRate() {
		return scaleRate;
	}

	public float getCornerRadiusRate() {
		return cornerRadiusRate;
	}

	public float getOpacityRate() {
		return opacityRate;
	}

	public double getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

	public GUITransition getFollowingTransition() {
		return followingTransition;
	}

	public void setFollowingTransition(GUITransition followingTransition) {
		this.followingTransition = followingTransition;
	}
}
