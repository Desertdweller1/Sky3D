package toolbox;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import me.desertdweller.sky3d.renderengine.renderobjects.Camera;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
       	matrix.identity();
		matrix.translate(translation);
		matrix.rotateXYZ(rx, ry, rz);
		matrix.scale(scale);
		return matrix;
	}
    
   public static Matrix4f createViewMatrix(Camera camera) {
       Matrix4f viewMatrix = new Matrix4f();
       viewMatrix.identity();
       viewMatrix.rotateXYZ((float) Math.toRadians(camera.getPitch()), (float) Math.toRadians(camera.getYaw()), (float) Math.toRadians(camera.getRoll()));
       Vector3f cameraPos = camera.getPosition();
       Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
       viewMatrix.translate(negativeCameraPos);
       return viewMatrix;
   }
   
   public static Matrix4f createLookMatrix(Camera camera) {
       Matrix4f lookMatrix = new Matrix4f();
       lookMatrix.identity();
       lookMatrix.rotateXYZ((float) Math.toRadians(camera.getPitch()), (float) Math.toRadians(camera.getYaw()), (float) Math.toRadians(camera.getRoll()));
       return lookMatrix;
   }
   
   public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(new Vector3f(translation.x, translation.y, 0));
		matrix.scale(new Vector3f(scale.x, scale.y, 1f));
		return matrix;
	}
   
   public static Vector4f toSideVector(Vector4f toProcess) {
		Vector4f newVec = new Vector4f();
		
		newVec.w = toProcess.w - toProcess.y + 1;
		newVec.x = newVec.w + toProcess.y*2;
		
		newVec.y = toProcess.x - toProcess.z + 1;
		newVec.z = newVec.y + toProcess.z*2;
		
		return newVec;
	}
	
	public static Vector4f toNormalVec4(Vector4f toProcess) {
		Vector4f newVec = new Vector4f();
		
		float xLength = (toProcess.x - toProcess.w)/2;
		float yLength = (toProcess.z - toProcess.y)/2;
		
		newVec.w = toProcess.w + xLength - 1;
		newVec.x = toProcess.y + yLength - 1;
		newVec.y = xLength;
		newVec.z = yLength;
		
		return newVec;
		
	}
}
