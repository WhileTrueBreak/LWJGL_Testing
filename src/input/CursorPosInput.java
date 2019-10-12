package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPosInput extends GLFWCursorPosCallback{

	private double cursorX = 0;
	private double cursorY = 0;
	private double deltaCursorX = 0;
	private double deltaCursorY = 0;
	
	private boolean reset = false;
	
	@Override
	public void invoke(long window, double x, double y) {
		reset = false;
		deltaCursorX = x-cursorX;
		deltaCursorY = y-cursorY;
		cursorX = x;
		cursorY = y;
	}
	
	public void reset() {
		if(reset) {
			deltaCursorX = 0;
			deltaCursorY = 0;
		}else {
			reset = true;
		}
	}

	public double getCursorX() {
		return cursorX;
	}

	public double getCursorY() {
		return cursorY;
	}

	public double getDeltaCursorX() {
		return deltaCursorX;
	}

	public double getDeltaCursorY() {
		return deltaCursorY;
	}

}
