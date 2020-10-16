package me.desertdweller.sky3d.renderengine.renderobjects;

import org.joml.Vector3f;

public class Camera {
    
   private Vector3f position = new Vector3f(0,5,0);
   private float pitch = 10;
   private float yaw = 0;
   private float roll = 0;
   private float speed = 0.5f;
    
    public Camera(){}
     
    public void move(Vector3f vec, float pitchDelta, float yawDelta, float rollDelta){
		position.add(vec.mul(speed));
		pitch += pitchDelta;
		yaw += yawDelta;
		roll += rollDelta;
		if(yaw > 360)
			yaw -= 360;
		if(yaw < 0)
			yaw += 360;
    }
 
    public Vector3f getPosition() {
        return position;
    }

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
     
     
 
}