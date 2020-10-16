package toolbox;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import me.desertdweller.sky3d.renderengine.Window;
import me.desertdweller.sky3d.renderengine.renderobjects.Camera;

public class MousePicker {

	private static Vector3f currentRay;
	
	private static Matrix4f projectionMatrix;
	private static Matrix4f viewMatrix;
	private static Camera camera;
	private static Window window;

	public static Vector3f getCurrentRay() {
		return currentRay;
	}
	
	public static void update(float x, float y) {
		MousePicker.viewMatrix = Maths.createViewMatrix(camera);
		MousePicker.currentRay = calculateMouseRay(x,y);
	}
	
	private static Vector3f calculateMouseRay(float x, float y) {
		Vector2f normalizedCoords = getNormalizedDeviceCoords(x, y);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}
	
	private static Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = new Matrix4f();
		viewMatrix.invert(invertedView);
		Vector4f rayWorld = invertedView.transform(eyeCoords);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalize();
		return mouseRay;
	}
	
	private static Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = new Matrix4f();
		projectionMatrix.invert(invertedProjection);
		Vector4f eyeCoords = invertedProjection.transform(clipCoords);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
		
	}
	
	private static Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
		float x = (2f*mouseX) / window.getWidth() - 1;
		float y = (2f*mouseY) / window.getHeight() - 1f;
		return new Vector2f(x,y);
	}
	
	public static void setCamera(Camera camera) {
		MousePicker.camera = camera;
		MousePicker.viewMatrix = Maths.createViewMatrix(camera);
	}
	
	public static void setWindow(Window window) {
		MousePicker.window = window;
	}
	
	public static void setProjection(Matrix4f projection) {
		MousePicker.projectionMatrix = projection;
	}
}
