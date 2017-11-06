package com.ru.tgra.lab1;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Shader {
	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;
	private int normalLoc;
	private int uvLoc;

	private int modelMatrixLoc;
	private int viewMatrixLoc;
	private int projectionMatrixLoc;
	
	private boolean usesDiffuseTexture = false;
	private int usesDiffuseTexLoc;
	private int diffuseTextureLoc;
	
	private int lightCount;
	private int lightCountLoc;
	private int[] lightPosLoc;
	private int[] lightIntLoc;
	private int[] constantAttLoc;
	private int[] linearAttLoc;
	private int[] quadraticAttLoc;
	
	private int cameraPosLoc;
	
	private int matDifLoc;
	private int matSpecLoc;
	private int matShinLoc;
	private int matAmbLoc;
	private int matEmiLoc;
	
	public Shader() {
		lightPosLoc = new int[4];
		lightIntLoc = new int[4];
		constantAttLoc = new int[4];
		linearAttLoc = new int[4];
		quadraticAttLoc = new int[4];
		
		String vertexShaderString;
		String fragmentShaderString;
	
		vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();
	
		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	
		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);
	
		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);
		
		System.out.println("Vertex: " + Gdx.gl.glGetShaderInfoLog(vertexShaderID));
		System.out.println("Fragment: " + Gdx.gl.glGetShaderInfoLog(fragmentShaderID));
	
		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);
	
		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);
	
		normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
		Gdx.gl.glEnableVertexAttribArray(normalLoc);
		
		uvLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_uv");
		Gdx.gl.glEnableVertexAttribArray(uvLoc);
	
		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
		projectionMatrixLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");
		
		usesDiffuseTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesDiffuseTexture");
		diffuseTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_diffuseTexture");
		
		cameraPosLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_cameraPosition"); 
		
		lightCount = 0;
		lightCountLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "lightCount");
		for(int i = 0; i < 4; i++) {
			lightPosLoc[i]				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition[" + i + "]");
			lightIntLoc[i]				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightIntensity[" + i + "]");
			
			constantAttLoc[i]			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_constantAttenuation[" + i + "]");
			linearAttLoc[i]				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_linearAttenuation[" + i + "]");
			quadraticAttLoc[i]			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_quadraticAttenuation[" + i + "]");
		}
		
		matDifLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
		matSpecLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");
		matShinLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialShinyness");
		matAmbLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialAmbient");
		matEmiLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialEmission");
		
		Gdx.gl.glUseProgram(renderingProgramID);
	}
	
	public void setDiffuseTexture(Texture tex) {
		if(tex == null) {
			Gdx.gl.glUniform1f(usesDiffuseTexLoc, 0.0f);
			usesDiffuseTexture = false;
		} else {
			tex.bind(0);
			Gdx.gl.glUniform1i(diffuseTextureLoc, 0);
			Gdx.gl.glUniform1f(usesDiffuseTexLoc, 1.0f);
			usesDiffuseTexture = true;

			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
		}
	}
	
	public boolean usesTextures() {
		return usesDiffuseTexture;
	}
	
	public int getLightCount() {
		return lightCount;
	}
	
	public void SetLightCount(int lights) {
		if(lights > 4) {
			System.out.println("Shader does not support more than 4 lights at a time.");
			return;
		}
		lightCount = lights;
		Gdx.gl.glUniform1i(lightCountLoc, lights);
	}
	
	public void SetCameraPosition(float x, float y, float z) {
		Gdx.gl.glUniform3f(cameraPosLoc, x, y, z);
	}
	
	public void SetLightPosition(int light, float x, float y, float z) {
		Gdx.gl.glUniform3f(lightPosLoc[light], x, y, z);
	}

	public void SetLightIntensity(int light, float r, float g, float b, float a) {
		Gdx.gl.glUniform4f(lightIntLoc[light], r, g, b, a);
	}
	
	public void SetLightAttenuation(int light, float constAtt, float linearAtt, float quadraticAtt) {
		Gdx.gl.glUniform1f(constantAttLoc[light], constAtt);
		Gdx.gl.glUniform1f(linearAttLoc[light], linearAtt);
		Gdx.gl.glUniform1f(quadraticAttLoc[light], quadraticAtt);
	}
	
	public void SetMaterialDiffuse(float r, float g, float b, float a) {
		Gdx.gl.glUniform4f(matDifLoc, r, g, b, a);
	}
	
	public void SetMaterialSpecular(float r, float g, float b, float a) {
		Gdx.gl.glUniform4f(matSpecLoc, r, g, b, a);
	}
	
	public void SetMaterialShinyness(float shinyness) {
		Gdx.gl.glUniform1f(matShinLoc, shinyness);
	}
	
	public void SetMaterialAmbient(float r, float g, float b, float a) {
		Gdx.gl.glUniform4f(matAmbLoc, r, g, b, a);
	}
	public void setMaterialEmission(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matEmiLoc, r, g, b, a);
	}
	
	public int getVertexPointer() {
		return positionLoc;
	}
	
	public int getNormalPointer() {
		return normalLoc;
	}
	public int getUVPointer() {
		return uvLoc;
	}
	
	public void SetModelMatrix(FloatBuffer matrix) {
		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
	}
	
	public void SetViewMatrix(FloatBuffer matrix) {
		Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
	}
	
	public void SetProjectionMatrix(FloatBuffer matrix) {
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
	}
}
