package com.ru.tgra.lab1;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class BoxGraphic {

	private static FloatBuffer vertexBuffer;
	private static FloatBuffer normalBuffer;
	private static FloatBuffer invertedNormalBuffer;
	private static FloatBuffer uvBuffer;
	private static ShortBuffer indexBuffer;

	public static void create() {
		//VERTEX ARRAY IS FILLED HERE
		float[] vertexArray = {
				-0.5f, -0.5f, -0.5f,	// Left
				-0.5f, 0.5f, -0.5f,
				 0.5f, 0.5f, -0.5f,
				 0.5f, -0.5f, -0.5f,
				
				-0.5f, -0.5f, 0.5f,		// Right
				-0.5f, 0.5f, 0.5f,
		 		 0.5f, 0.5f, 0.5f,
				 0.5f, -0.5f, 0.5f,
				
				-0.5f, -0.5f, -0.5f,	// Bottom
				 0.5f, -0.5f, -0.5f,
				 0.5f, -0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f,
				
				-0.5f, 0.5f, -0.5f,		// Top
				 0.5f, 0.5f, -0.5f,
				 0.5f, 0.5f, 0.5f,
				-0.5f, 0.5f, 0.5f,
				
				-0.5f, -0.5f, -0.5f,	// Forward
				-0.5f, -0.5f, 0.5f,
				-0.5f, 0.5f, 0.5f,
				-0.5f, 0.5f, -0.5f,
				
				 0.5f, -0.5f, -0.5f,	// Backwards
				 0.5f, -0.5f, 0.5f,
				 0.5f, 0.5f, 0.5f,
				 0.5f, 0.5f, -0.5f};

		vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(vertexArray);
		vertexBuffer.rewind();

		//NORMAL ARRAY IS FILLED HERE
		float[] normalArray = {
				 0.0f, 0.0f, -1.0f,
				 0.0f, 0.0f, -1.0f,
				 0.0f, 0.0f, -1.0f,
				 0.0f, 0.0f, -1.0f,
				
				 0.0f, 0.0f, 1.0f,
				 0.0f, 0.0f, 1.0f,
				 0.0f, 0.0f, 1.0f,
				 0.0f, 0.0f, 1.0f,
				
				 0.0f, -1.0f, 0.0f,
				 0.0f, -1.0f, 0.0f,
				 0.0f, -1.0f, 0.0f,
				 0.0f, -1.0f, 0.0f,
				
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				
				 1.0f, 0.0f, 0.0f,
				 1.0f, 0.0f, 0.0f,
				 1.0f, 0.0f, 0.0f,
				 1.0f, 0.0f, 0.0f};

		normalBuffer = BufferUtils.newFloatBuffer(72);
		normalBuffer.put(normalArray);
		normalBuffer.rewind();
		
		//INVERTED NORMAL ARRAY IS FILLED HERE
		float[] invertedNormalArray = {
				 0.0f, 0.0f, 1.0f,
				 0.0f, 0.0f, 1.0f,
				 0.0f, 0.0f, 1.0f,
				 0.0f, 0.0f, 1.0f,
				 
				 0.0f, 0.0f, -1.0f,
				 0.0f, 0.0f, -1.0f,
				 0.0f, 0.0f, -1.0f,
				 0.0f, 0.0f, -1.0f,
				
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 0.0f, 1.0f, 0.0f,
				 
				 0.0f, -1.0f, 0.0f,
				 0.0f, -1.0f, 0.0f,
				 0.0f, -1.0f, 0.0f,
				 0.0f, -1.0f, 0.0f,
				
				 1.0f, 0.0f, 0.0f,
				 1.0f, 0.0f, 0.0f,
				 1.0f, 0.0f, 0.0f,
				 1.0f, 0.0f, 0.0f,
				 
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f};

		invertedNormalBuffer = BufferUtils.newFloatBuffer(72);
		invertedNormalBuffer.put(invertedNormalArray);
		invertedNormalBuffer.rewind();
		
		//UV TEXTURE COORD ARRAY IS FILLED HERE
		float[] uvArray = {
				1/4f, 2/3f,
				1/4f, 1/3f,
				0.0f, 1/3f,
				0.0f, 2/3f,
				
				2/4f, 2/3f,
				2/4f, 1/3f,
				3/4f, 1/3f,
				3/4f, 2/3f,

				1/4f, 2/3f,
				1/4f, 1.0f,
				2/4f, 1.0f,
				2/4f, 2/3f,

				1/4f, 1/3f,
				1/4f, 0.0f,
				2/4f, 0.0f,
				2/4f, 1/3f,

				1/4f, 2/3f,
				2/4f, 2/3f,
				2/4f, 1/3f,
				1/4f, 1/3f,
				
				1.0f, 2/3f,
				3/4f, 2/3f,
				3/4f, 1/3f,
				1.0f, 1/3f };
		
		uvBuffer = BufferUtils.newFloatBuffer(48);
		BufferUtils.copy(uvArray, 0, uvBuffer, 48);
		uvBuffer.rewind();
		
		//INDEX ARRAY IS FILLED HERE
		short[] indexArray = {0, 1, 2, 0, 2, 3,
							4, 5, 6, 4, 6, 7,
							8, 9, 10, 8, 10, 11,
							12, 13, 14, 12, 14, 15,
							16, 17, 18, 16, 18, 19,
							20, 21, 22, 20, 22, 23};

		indexBuffer = BufferUtils.newShortBuffer(36);
		BufferUtils.copy(indexArray, 0, indexBuffer, 36);
		indexBuffer.rewind();
	}

	public static void drawSolidCube(Shader shader, Texture diffuseTexture, boolean invertedNormal) {
		shader.setDiffuseTexture(diffuseTexture);
		
		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		if(invertedNormal)
			Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, invertedNormalBuffer);
		else
			Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
		
		Gdx.gl.glDrawElements(GL20.GL_TRIANGLES, 36, GL20.GL_UNSIGNED_SHORT, indexBuffer);
		
		/*Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 4, 4);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 8, 4);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 12, 4);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 16, 4);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 20, 4); */
	}

	/*public static void drawOutlineCube(Shader shader, Texture diffuseTexture) {

		Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 4);
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 4, 4);
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 8, 4);
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 12, 4);
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 16, 4);
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 20, 4);
	} */
}
