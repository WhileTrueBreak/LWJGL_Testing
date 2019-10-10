package renderEngine;

import java.awt.Toolkit;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	//private static final int FPS_CAP = 120;
	private static final String TITLE = "Display";

	public static long window;

	public static void createDisplay() {
		System.out.println("Initializing Display...");
		if(!GLFW.glfwInit())
			System.err.println("GLFW failed to initialize");
		System.out.print("\tCreating Display...");
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
		if(window == MemoryUtil.NULL) System.err.println("Could not create display");
		System.out.print("Done!\n\tSetting Dimensions...");
		GLFW.glfwSetWindowPos(window, 
				Toolkit.getDefaultToolkit().getScreenSize().width/2-WIDTH/2, 
				Toolkit.getDefaultToolkit().getScreenSize().height/2-HEIGHT/2);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		System.out.print("Done!\n");
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		System.out.println("Done!");
	}

	public static void updateDisplay() {
		GLFW.glfwSwapBuffers(DisplayManager.window);
		GLFW.glfwGetTimerFrequency();
	}

	public static void closeDisplay() {
		System.out.print("Closing Display...");
		GLFW.glfwTerminate();
		System.out.println("Done!");
	}

}
