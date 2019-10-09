package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {
		
	}
	
	public void move() {
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
			position.z-=0.02f;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
			position.x-=0.02f;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
			position.z+=0.02f;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
			position.x+=0.02f;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_Q) == GLFW.GLFW_PRESS) {
			yaw-=1f;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_E) == GLFW.GLFW_PRESS) {
			yaw+=1f;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
	
}
