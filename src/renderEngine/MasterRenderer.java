package renderEngine;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;

public class MasterRenderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	public MasterRenderer() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
	}
	
	public void render(Light light, Camera camera) {
		prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity) {
		TexturedModel texturedModel = entity.getTexturedModel();
		List<Entity> batch = entities.get(texturedModel);
		if(batch != null) {
			batch.add(entity);
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(texturedModel, newBatch);
		}
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	private void createProjectionMatrix() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(DisplayManager.window, w, h);
		int width = w.get(0);
		int height = h.get(0);
		float aspectRatio = width/height;
		float yScale = (float) ((1f/Math.tan(Math.toRadians(FOV/2f)))*aspectRatio);
		float xScale = yScale/aspectRatio;
		float frustumLength = FAR_PLANE-NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -((FAR_PLANE+NEAR_PLANE)/frustumLength);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2*FAR_PLANE*NEAR_PLANE)/frustumLength);
		projectionMatrix.m33 = 0;
	}
	
}
