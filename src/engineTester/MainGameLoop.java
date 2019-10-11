package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		
		final int FPS_CAP = 120;
		
		double startTime = System.nanoTime();
		double avgMSF = 0;
		double timeElapsed = 0;
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//Setting up models
		System.out.print("Loading Models...");
		TexturedModel tree1 = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("tree")), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel tree2 = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("lowPolyTree")), new ModelTexture(loader.loadTexture("lowPolyTree")));
		TexturedModel grass = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")), new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setTransparent(true);
		grass.getTexture().setUseFakeLighting(true);
		TexturedModel fern = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("fern")), new ModelTexture(loader.loadTexture("fern")));
		ModelTexture solidBlue = new ModelTexture(loader.loadTexture("white"));
		solidBlue.setReflectivity(1.5f);
		solidBlue.setShineDamper(5);
		TexturedModel dragon = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("bunny")), solidBlue);
		fern.getTexture().setTransparent(true);
		System.out.println("Done!");
		List<Entity> entities = new ArrayList<Entity>();
		
		for(int i = 0;i < 500;i++) {
			entities.add(new Entity(tree1, new Vector3f((float) (Math.random()*1600), 0, (float) (Math.random()*1600)), 0, (float) Math.random()*360, 0, 3));
			entities.add(new Entity(tree2, new Vector3f((float) (Math.random()*1600), 0, (float) (Math.random()*1600)), 0, (float) Math.random()*360, 0, 0.25f));
			entities.add(new Entity(grass, new Vector3f((float) (Math.random()*1600), 0, (float) (Math.random()*1600)), 0, (float) Math.random()*360, 0, 1));
			entities.add(new Entity(fern, new Vector3f((float) (Math.random()*1600), 0, (float) (Math.random()*1600)), 0, (float) Math.random()*360, 0, 0.6f));
		}
		entities.add(new Entity(dragon, new Vector3f(0, 0, 0), 0, (float) Math.random()*360, 0, 0.6f));
		
		Light light = new Light(new Vector3f(0, 100, 0), new Vector3f(1, 1, 1));
		
		//create terrain
		
		//terrain texture
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain1 = new Terrain(0, 0, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(0, 1, loader, texturePack, blendMap);
		Terrain terrain3 = new Terrain(1, 1, loader, texturePack, blendMap);
		Terrain terrain4 = new Terrain(1, 0, loader, texturePack, blendMap);
		
		///////////
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!GLFW.glfwWindowShouldClose(DisplayManager.window)) {
			//sync start
			if(System.nanoTime()-startTime<1000000000/FPS_CAP)
				continue;
			timeElapsed += System.nanoTime()-startTime;
			avgMSF += System.nanoTime()-startTime;
			avgMSF /= 2;
			if(timeElapsed>1000000000) {
				System.out.printf("%f ms/frame\n", avgMSF/1000000);
				avgMSF = System.nanoTime()-startTime;
				timeElapsed = 0;
			}
			startTime = System.nanoTime();
			//sync end
			GLFW.glfwPollEvents();
			//START//
			//entity.increasePosition(0, 0, 0);
			//entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			
			renderer.processingTerrain(terrain1);
			renderer.processingTerrain(terrain2);
			renderer.processingTerrain(terrain3);
			renderer.processingTerrain(terrain4);
			
			for(Entity entity:entities)
				renderer.processEntity(entity);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			//END//
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
