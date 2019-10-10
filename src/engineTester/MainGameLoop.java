package engineTester;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		final int FPS_CAP = 120;
		
		double startTime = System.nanoTime();
		double avgMSF = 0;
		double timeElapsed = 0;
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();

		//Setting up models
		RawModel dragonModel = OBJLoader.loadOBJModel("dragon", loader);
		RawModel stallModel = OBJLoader.loadOBJModel("stall", loader);
		ModelTexture stallTexture = new ModelTexture(loader.loadTexture("stallTexture"));
		ModelTexture solidBlueTexture = new ModelTexture(loader.loadTexture("solid_blue"));
		TexturedModel texturedDragonModel = new TexturedModel(dragonModel, solidBlueTexture);
		texturedDragonModel.getTexture().setReflectivity(1);
		texturedDragonModel.getTexture().setShineDamper(10);
		TexturedModel texturedStallModel = new TexturedModel(stallModel, stallTexture);
		texturedStallModel.getTexture().setReflectivity(1);
		texturedStallModel.getTexture().setShineDamper(10);
		Entity entity = new Entity(texturedDragonModel , new Vector3f(0, -7, -25), 0, 0, 0, 1);
		
		Light light = new Light(new Vector3f(0, 10, -20), new Vector3f(1, 1, 1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!GLFW.glfwWindowShouldClose(DisplayManager.window)) {
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
			GLFW.glfwPollEvents();
			//START//
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 0.5f, 0);
			camera.move();
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
