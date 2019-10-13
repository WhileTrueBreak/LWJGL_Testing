package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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

	static final int FPS_CAP = 120;
	
	public static void main(String[] args) {
		
		
		double startTime = System.nanoTime();
		double avgMSF = 0;
		double timeElapsed = 0;
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//create terrain
		
		//terrain texture
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain1 = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		
		//Setting up models
		System.out.print("Loading Models...");
		TexturedModel tree1 = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("tree")), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel tree2 = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("lowPolyTree")), new ModelTexture(loader.loadTexture("lowPolyTree")));
		TexturedModel grass = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")), new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setTransparent(true);
		grass.getTexture().setUseFakeLighting(true);
		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
		fernTexture.setNumberOfRows(2);
		TexturedModel fern = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("fern")), fernTexture);
		ModelTexture solidBlue = new ModelTexture(loader.loadTexture("solid_blue"));
		solidBlue.setReflectivity(1.5f);
		solidBlue.setShineDamper(5);
		//TexturedModel dragon = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("dragon")), solidBlue);
		TexturedModel person = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("person")), new ModelTexture(loader.loadTexture("playerTexture")));
		fern.getTexture().setTransparent(true);
		System.out.println("Done!");
		List<Entity> entities = new ArrayList<Entity>();
		
		for(int i = 0;i < 500;i++) {
			float x = (float) (Math.random()*1600);
			float z = (float) (Math.random()*1600);
			entities.add(new Entity(tree1, new Vector3f(x, terrain1.getHeightOffTerrain(x, z), z), 0, (float) Math.random()*360, 0, 3));
			x = (float) (Math.random()*1600);
			z = (float) (Math.random()*1600);
			entities.add(new Entity(tree2, new Vector3f(x, terrain1.getHeightOffTerrain(x, z), z), 0, (float) Math.random()*360, 0, 0.25f));
			x = (float) (Math.random()*1600);
			z = (float) (Math.random()*1600);
			entities.add(new Entity(fern, (int)(Math.random()*4), new Vector3f(x, terrain1.getHeightOffTerrain(x, z), z), 0, (float) Math.random()*360, 0, 0.6f));
		}
		for(int i = 0;i < 4500;i++) {
			float x = (float) (Math.random()*1600);
			float z = (float) (Math.random()*1600);
			entities.add(new Entity(fern, (int)(Math.random()*4), new Vector3f(x, terrain1.getHeightOffTerrain(x, z), z), 0, (float) Math.random()*360, 0, 0.6f));
		}
		//entities.add(new Entity(dragon, new Vector3f(0, 0, 0), 0, (float) Math.random()*360, 0, 0.6f));
		
		Player player = new Player(person, new Vector3f(100, 0, 50), 0, 0, 0, 1);
		
		Light light = new Light(new Vector3f(256, 1000, 256), new Vector3f(1, 1, 1));
		///////////
		Camera camera = new Camera(player);
		
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
			//upadte??
			//entity.increasePosition(0, 0, 0);
			//entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			player.move(terrain1);
			//render//
			renderer.processingTerrain(terrain1);
			
			renderer.processEntity(player);
			
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
