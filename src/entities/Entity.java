package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {
	
	private TexturedModel texturedModel;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private int textureIndex = 0;
	
	public Entity(TexturedModel texturedModel, Vector3f position, float rotx, float roty, float rotz, float scale) {
		this.texturedModel = texturedModel;
		this.position = position;
		this.rotX = rotx;
		this.rotY = roty;
		this.rotZ = rotz;
		this.scale = scale;
	}	
	
	public Entity(TexturedModel texturedModel,int textureIndex, Vector3f position, float rotx, float roty, float rotz, float scale) {
		this.texturedModel = texturedModel;
		this.textureIndex = textureIndex;
		this.position = position;
		this.rotX = rotx;
		this.rotY = roty;
		this.rotZ = rotz;
		this.scale = scale;
	}
	
	public float getTextureOffsetX() {
		int column = textureIndex%texturedModel.getTexture().getNumberOfRows();
		return (float) column/texturedModel.getTexture().getNumberOfRows();
	}
	
	public float getTextureOffsetY() {
		int row = textureIndex/texturedModel.getTexture().getNumberOfRows();
		return (float) row/texturedModel.getTexture().getNumberOfRows();
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		rotX += dx;
		rotY += dy;
		rotZ += dz;
	}
	
	public TexturedModel getTexturedModel() {
		return texturedModel;
	}
	
	public void setTexturedModel(TexturedModel texturedModel) {
		this.texturedModel = texturedModel;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getRotx() {
		return rotX;
	}
	
	public void setRotx(float rotx) {
		this.rotX = rotx;
	}
	
	public float getRoty() {
		return rotY;
	}
	
	public void setRoty(float roty) {
		this.rotY = roty;
	}
	
	public float getRotz() {
		return rotZ;
	}
	
	public void setRotz(float rotz) {
		this.rotZ = rotz;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
