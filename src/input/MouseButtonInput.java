package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonInput extends GLFWMouseButtonCallback{

	private boolean leftButtonPressed;
	private boolean middleButtonPressed;
	private boolean rightButtonPressed;
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && action == GLFW.GLFW_PRESS) {
			rightButtonPressed = true;
		}if(button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && action == GLFW.GLFW_PRESS) {
			middleButtonPressed = true;
		}if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS) {
			leftButtonPressed = true;
		}if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && action == GLFW.GLFW_RELEASE) {
			rightButtonPressed = false;
		}if(button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && action == GLFW.GLFW_RELEASE) {
			middleButtonPressed = false;
		}if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_RELEASE) {
			leftButtonPressed = false;
		}
	}

	public boolean isLeftButtonPressed() {
		return leftButtonPressed;
	}

	public boolean isMiddleButtonPressed() {
		return middleButtonPressed;
	}

	public boolean isRightButtonPressed() {
		return rightButtonPressed;
	}
	
}
