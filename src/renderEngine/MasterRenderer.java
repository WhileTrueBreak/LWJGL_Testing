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
import shaders.TerrainShader;
import terrains.Terrain;

public class MasterRenderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private static final float RED = 0.4f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.7f;
	
	
	private Matrix4f projectionMatrix;
	
	private StaticShader entityShader = new StaticShader();
	private EntityRenderer entityRenderer;
	
	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer() {
		enableCulling();
		createProjectionMatrix();
		entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(Light light, Camera camera) {
		prepare();
		entityShader.start();
		entityShader.loadSkyColor(RED, GREEN, BLUE);
		entityShader.loadLight(light);
		entityShader.loadViewMatrix(camera);
		entityRenderer.render(entities);
		entityShader.stop();
		terrainShader.start();
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLight(light);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		entities.clear();
		terrains.clear();
	}
	
	public void processingTerrain(Terrain terrain) {
		terrains.add(terrain);
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
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	public void cleanUp() {
		entityShader.cleanUp();
		terrainShader.cleanUp();
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
