package input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseScrollInput extends GLFWScrollCallback{
	
	private double scrollX = 0;
	private double scrollY = 0;
	
	private boolean reset = true;
	
	@Override
	public void invoke(long window, double dx, double dy) {
		reset = false;
		scrollX = dx;
		scrollY = dy;
	}
	
	public void reset() {
		if(reset) {
			scrollX = 0;
			scrollY = 0;
		}else {
			reset = true;
		}
	}

	public double getScrollX() {
		return scrollX;
	}

	public double getScrollY() {
		return scrollY;
	}

}
