package engineTester;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
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
		Renderer renderer = new Renderer(shader);

		RawModel dragonModel = OBJLoader.loadOBJModel("dragon", loader);
		RawModel stallModel = OBJLoader.loadOBJModel("stall", loader);
		ModelTexture stallTexture = new ModelTexture(loader.loadTexture("stallTexture"));
		ModelTexture pngTexture = new ModelTexture(loader.loadTexture("solid_blue"));
		TexturedModel texturedModel = new TexturedModel(dragonModel, pngTexture);

		Entity entity = new Entity(texturedModel, new Vector3f(0, -5, -15), 0, 0, 0, 1);

		Camera camera = new Camera();
		
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
			//START//
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			GLFW.glfwPollEvents();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
			//END//
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
