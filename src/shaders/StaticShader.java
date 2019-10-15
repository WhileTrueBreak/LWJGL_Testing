package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final int LIGHT_SOURCE_COUNT = 16;

	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPos[];
	private int locationLightColor[];
	private int locationShineDamper;
	private int locationReflectivity;
	private int locationUseFakeLighting;
	private int loactionSkyColor;
	private int locationOffset;
	private int locationNumRows;
	private int locationLightSourceCount;
	private int locationAttenuation[];

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationShineDamper = super.getUniformLocation("shineDamper");
		locationReflectivity = super.getUniformLocation("reflectivity");
		locationUseFakeLighting = super.getUniformLocation("useFakeLighting");
		loactionSkyColor = super.getUniformLocation("skyColor");
		locationOffset = super.getUniformLocation("offset");
		locationNumRows = super.getUniformLocation("numRows");
		locationLightSourceCount = super.getUniformLocation("lightSourceCount");

		locationLightPos = new int[LIGHT_SOURCE_COUNT];
		locationLightColor = new int[LIGHT_SOURCE_COUNT];
		locationAttenuation = new int[LIGHT_SOURCE_COUNT];
		for(int i = 0;i < LIGHT_SOURCE_COUNT;i++) {
			locationLightPos[i] = super.getUniformLocation("lightPos["+i+"]");
			locationLightColor[i] = super.getUniformLocation("lightColor["+i+"]");
			locationAttenuation[i] = super.getUniformLocation("attenuation["+i+"]");
		}
	}

	public void loadOffset(float xOff, float yOff) {
		super.loadVector2(locationOffset, new Vector2f(xOff, yOff));
	}

	public void loadNumRows(int rows) {
		super.loadInt(locationNumRows, rows);
	}

	public void loadShineVariables(float shineDamper, float reflectivity) {
		super.loadFloat(locationShineDamper, shineDamper);
		super.loadFloat(locationReflectivity, reflectivity);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(locationTransformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights) {
		for(int i = 0;i < LIGHT_SOURCE_COUNT;i++) {
			if(i<lights.size()) {
				super.loadVector3(locationLightPos[i], lights.get(i).getPosition());
				super.loadVector3(locationLightColor[i], lights.get(i).getColor());
				super.loadVector3(locationAttenuation[i], lights.get(i).getAttenuation());
			}else {
				super.loadVector3(locationLightPos[i], new Vector3f(0, 0, 0));
				super.loadVector3(locationLightColor[i], new Vector3f(0, 0, 0));
				super.loadVector3(locationAttenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(locationProjectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(locationViewMatrix, Maths.createViewMatrix(camera));
	}	

	public void loadUseFakeLighting(boolean useFakeLighting) {
		super.loadBoolean(locationUseFakeLighting, useFakeLighting);
	}

	public void loadSkyColor(float r, float g, float b) {
		super.loadVector3(loactionSkyColor, new Vector3f(r, g, b));
	}

	public void loadLightSourceCount(int count) {
		super.loadInt(locationLightSourceCount, Math.min(count, LIGHT_SOURCE_COUNT));
	}

}
