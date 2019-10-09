package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class OBJLoader {
	
	public static RawModel loadOBJModel(String filename, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+filename+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not load file");
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		
		float[] verticeArr = null;
		float[] textureArr = null;
		float[] normalArr = null;
		int[] indicesArr = null;
		try {
			while(true) {
				line = br.readLine();
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), 
							Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}else if(line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), 
							Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}else if(line.startsWith("f ")) {
					textureArr = new float[vertices.size()*2];
					normalArr = new float[vertices.size()*3];
					break;
				}
			}
			
			while(line!=null) {
				if(!line.startsWith("f ")) {
					line = br.readLine();
					continue;
				}
				String[] currentString = line.split(" ");
				String[]vertex1 = currentString[1].split("/");
				String[]vertex2 = currentString[2].split("/");
				String[]vertex3 = currentString[3].split("/");
				processVertex(vertex1, indices, textures, normals, textureArr, normalArr);
				processVertex(vertex2, indices, textures, normals, textureArr, normalArr);
				processVertex(vertex3, indices, textures, normals, textureArr, normalArr);
				line = br.readLine();
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		verticeArr = new float[vertices.size()*3];
		indicesArr = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex:vertices) {
			verticeArr[vertexPointer++] = vertex.x;
			verticeArr[vertexPointer++] = vertex.y;
			verticeArr[vertexPointer++] = vertex.z;
		}
		for(int i = 0;i < indices.size();i++) {
			indicesArr[i] = indices.get(i);
		}
		return loader.loadToVAO(verticeArr, textureArr, indicesArr);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, 
			float[] textureArr, float[] normalArr) {
		int currentVertexPointer = Integer.parseInt(vertexData[0])-1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArr[currentVertexPointer*2] = currentTex.x;
		textureArr[currentVertexPointer*2+1] = 1-currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalArr[currentVertexPointer*3] = currentNorm.x;
		normalArr[currentVertexPointer*3+1] = currentNorm.y;
		normalArr[currentVertexPointer*3+2] = currentNorm.z;
	}
	
}
