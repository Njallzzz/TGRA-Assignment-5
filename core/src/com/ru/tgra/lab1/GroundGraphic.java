package com.ru.tgra.lab1;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class GroundGraphic {
	private static FloatBuffer vertexBuffer;
	private static FloatBuffer normalBuffer;
	private static FloatBuffer uvBuffer;
	
	public static void create() {
		//VERTEX ARRAY IS FILLED HERE
		float[] vertexArray = {
				-90.0f, 0.0f, -90.0f,
				 90.0f, 0.0f, -90.0f,
				 90.0f, 0.0f, 90.0f,
				-90.0f, 0.0f, 90.0f
				};

		vertexBuffer = BufferUtils.newFloatBuffer(12);
		vertexBuffer.put(vertexArray);
		vertexBuffer.rewind();

		//NORMAL ARRAY IS FILLED HERE
		float[] normalArray = {
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f};

		normalBuffer = BufferUtils.newFloatBuffer(12);
		normalBuffer.put(normalArray);
		normalBuffer.rewind();
		
		float[] uvArray = {
			0.0f, 0.0f,
			180.0f, 0.0f,
			180.0f, 180.0f,
			0.0f, 180.0f
		};

		uvBuffer = BufferUtils.newFloatBuffer(8);
		BufferUtils.copy(uvArray, 0, uvBuffer, 8);
		uvBuffer.rewind();
		
	}
	
	public static void draw(Shader shader, Texture diffuseTexture) {
		shader.setDiffuseTexture(diffuseTexture);
		
		shader.SetMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
		shader.SetMaterialSpecular(0.1f, 0.1f, 0.1f, 1.0f);
		shader.SetMaterialShinyness(100);
		shader.SetMaterialAmbient(0.0f, 0.0f, 0.0f, 1.0f);
		shader.setMaterialEmission(0.0f, 0.0f, 0.0f, 0.0f);
		
		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
		
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
	}
}









