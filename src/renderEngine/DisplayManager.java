package renderEngine;

import java.awt.Toolkit;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import input.CursorPosInput;
import input.MouseButtonInput;
import input.MouseScrollInput;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	//private static final int FPS_CAP = 120;
	private static final String TITLE = "Display";
	
	public static long window;
	
	@SuppressWarnings("unused")
	private static GLFWCursorPosCallback cursorPos;
	@SuppressWarnings("unused")
	private static GLFWScrollCallback mouseScroll;
	@SuppressWarnings("unused")
	private static GLFWMouseButtonCallback mouseButton;
	public static CursorPosInput cursorPosInput = new CursorPosInput();
	public static MouseScrollInput mouseScrollInput = new MouseScrollInput();
	public static MouseButtonInput mouseButtonInput = new MouseButtonInput();
	
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay() {
		System.out.println("Initializing Display...");
		if(!GLFW.glfwInit())
			System.err.println("GLFW failed to initialize");
		System.out.print("\tCreating Display...");
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
		if(window == MemoryUtil.NULL) System.err.println("Could not create display");
		System.out.print("Done!\n\tSetting Up Callbacks...");
		GLFW.glfwSetCursorPosCallback(window, cursorPos = cursorPosInput);
		GLFW.glfwSetScrollCallback(window, mouseScroll = mouseScrollInput);
		GLFW.glfwSetMouseButtonCallback(window, mouseButton = mouseButtonInput);
		System.out.print("Done!\n\tSetting Dimensions...");
		GLFW.glfwSetWindowPos(window, 
				Toolkit.getDefaultToolkit().getScreenSize().width/2-WIDTH/2, 
				Toolkit.getDefaultToolkit().getScreenSize().height/2-HEIGHT/2);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		System.out.print("Done!\n");
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(0);
		GL.createCapabilities();
		System.out.println("Done!");
	}

	public static void updateDisplay() {
		mouseScrollInput.reset();
		GLFW.glfwSwapBuffers(DisplayManager.window);
		GLFW.glfwGetTimerFrequency();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime-lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}

	public static void closeDisplay() {
		System.out.print("Closing Display...");
		GLFW.glfwTerminate();
		System.out.println("Done!");
	}
	
	private static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	private static void cursorPositionCallback(long window, double xPos, double yPos) {
		
	}

}
