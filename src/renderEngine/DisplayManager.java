package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	private static final String TITLE = "Our First Display";

	public static void createDisplay() {
		System.out.println("Initializing Display...");
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			System.out.print("\tSetting Display Mode...");
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			System.out.print("Done!\n\tSetting Title...");
			Display.setTitle(TITLE);
			System.out.print("Done!\n\tCreating Display...");
			Display.create(new PixelFormat(), attribs);
			System.out.println("Done!");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		System.out.println("Done!");
	}

	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
	}

	public static void closeDisplay() {
		System.out.print("Closing Display...");
		Display.destroy();
		System.out.println("Done!");
	}

}
