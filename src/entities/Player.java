package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_FORCE = 30;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean inAir = false;

	public Player(TexturedModel texturedModel, Vector3f position, float rotx, float roty, float rotz, float scale) {
		super(texturedModel, position, rotx, roty, rotz, scale);
	}

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(distance*(float)Math.sin(Math.toRadians(super.getRoty())), 0, distance*(float)Math.cos(Math.toRadians(super.getRoty())));
		upwardsSpeed += GRAVITY*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOffTerrain(this.getPosition().x, this.getPosition().z);
		if(super.getPosition().y<terrainHeight) {
			upwardsSpeed = 0;
			inAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump() {
		if(!inAir) {
			this.upwardsSpeed = JUMP_FORCE;
			inAir = true;
		}
	}

	private void checkInputs() {
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_W)==GLFW.GLFW_PRESS) {
			currentSpeed = RUN_SPEED;
		}else if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_S)==GLFW.GLFW_PRESS) {
			currentSpeed = -RUN_SPEED;
		}else {
			currentSpeed = 0;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_A)==GLFW.GLFW_PRESS) {
			currentTurnSpeed = TURN_SPEED;
		}else if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_D)==GLFW.GLFW_PRESS) {
			currentTurnSpeed = -TURN_SPEED;
		}else {
			currentTurnSpeed = 0;
		}
		if(GLFW.glfwGetKey(DisplayManager.window, GLFW.GLFW_KEY_SPACE)==GLFW.GLFW_PRESS) {
			jump();
		}
	}

}
