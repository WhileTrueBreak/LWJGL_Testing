package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		final int FPS_CAP = 120;
		
		double startTime = System.nanoTime();
		double avgMSF = 0;
		double timeElapsed = 0;
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		//Setting up models
		TexturedModel tree = new TexturedModel(OBJLoader.loadOBJModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadOBJModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setTransparent(true);
		TexturedModel fern = new TexturedModel(OBJLoader.loadOBJModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setTransparent(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		
		for(int i = 0;i < 500;i++) {
			entities.add(new Entity(tree, new Vector3f((float) (Math.random()*800-400), 0, (float) (Math.random()*800-400)), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f((float) (Math.random()*800-400), 0, (float) (Math.random()*800-400)), 0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f((float) (Math.random()*800-400), 0, (float) (Math.random()*800-400)), 0, 0, 0, 0.6f));
		}
		
		Light light = new Light(new Vector3f(0, 100, 0), new Vector3f(1, 1, 1));
		
		//create terrain
		Terrain terrain1 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain3 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain4 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		
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
