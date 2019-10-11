package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 5, 0);
	private float pitch;
	private float yaw = 90;
	private float roll;
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
	}
	
	public void move() {
		calcZoom();
		calcPitch();
		calcAngleAroundPlayer();
		float horizontalDist = distanceFromPlayer*(float)Math.cos(Math.toRadians(pitch));
		float verticalDist = distanceFromPlayer*(float)Math.sin(Math.toRadians(pitch));
		calcCameraPos(horizontalDist, verticalDist);
		yaw = 180-(player.getRoty()+angleAroundPlayer);
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
	
	private void calcCameraPos(float horizontalDist, float verticalDist) {
		float theta = player.getRoty()+angleAroundPlayer;
		float xOff = horizontalDist*(float) Math.sin(Math.toRadians(theta));
		float zOff = horizontalDist*(float) Math.cos(Math.toRadians(theta));
		position.x = player.getPosition().x-xOff;
		position.y = verticalDist+player.getPosition().y;
		position.z = player.getPosition().z-zOff;
		
	}
	
	private void calcZoom() {
		float zoomLevel = (float) DisplayManager.mouseScrollInput.getScrollY() * 0.3f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calcPitch() {
		if(DisplayManager.mouseButtonInput.isRightButtonPressed()) {
			float pitchChange = (float) DisplayManager.cursorPosInput.getDeltaCursorY()*0.1f;
			pitch += pitchChange;
		}
	}
	
	private void calcAngleAroundPlayer() {
		if(DisplayManager.mouseButtonInput.isRightButtonPressed()) {
			float angleChange = (float) DisplayManager.cursorPosInput.getDeltaCursorX()*0.1f;
			angleAroundPlayer -= angleChange;
		}
	}
	
}
