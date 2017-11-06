package com.ru.tgra.lab1;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.nio.FloatBuffer;

import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.lab1.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.lab1.shapes.g3djmodel.MeshModel;

public class LabFirst3DGame extends ApplicationAdapter implements InputProcessor {
	private Shader shader;

	private Camera cam;
	//private Camera orthoCam;
	
	private float fov = 90.0f;
	private float angle;
	
	Vector3D up;
	Point3D curpos, curlook, curball;
	long start_time;
	
	Point3D[] curvePoints;
	BezierCurve[] curves;
	
	private Texture skyBoxTex;
	private Texture chairTex;
	private Texture cannonTex;
	private Texture groundTex;
	
	MeshModel chair;
	MeshModel cannon;

	@Override
	public void create () {
		start_time = -1;
		angle = 0.0f;
		
		Gdx.input.setInputProcessor(this);

		shader = new Shader();
		
		skyBoxTex = new Texture(Gdx.files.internal("textures/skybox.png"));
		chairTex = new Texture(Gdx.files.internal("textures/chair_albedo.png"));
		groundTex = new Texture(Gdx.files.internal("textures/ground.jpg"));
		cannonTex = new Texture(Gdx.files.internal("textures/cannon.jpg"));
		groundTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		
		chair = G3DJModelLoader.loadG3DJFromFile("chair.g3dj");
		cannon = G3DJModelLoader.loadG3DJFromFile("cannon.g3dj");

		//COLOR IS SET HERE
		shader.SetLightCount(3);
		// Player light
		shader.SetLightPosition(0, -2.0f, 0.0f, -2.0f);
		shader.SetLightIntensity(0, 1.0f, 1.0f, 1.0f, 1.0f);
		shader.SetLightAttenuation(0, 0.5f, 0.1f, 0.2f);
		
		// Cannonball light
		shader.SetLightPosition(1, 0.0f, 0.0f, 0.0f);
		shader.SetLightIntensity(1, 0.0f, 0.0f, 0.0f, 1.0f);
		shader.SetLightAttenuation(1, 1.0f, 0.5f, 0.4f);
		
		// Misc light
		shader.SetLightPosition(2, -0.5f, 1.0f, -2.5f);
		shader.SetLightIntensity(2, 1.0f, 1.0f, 1.0f, 1.0f);
		shader.SetLightAttenuation(2, 0.5f, 0.1f, 0.2f);
		
		// Skybox light
		shader.SetLightPosition(3, 0.0f, 0.0f, 0.0f);
		shader.SetLightIntensity(3, 1.0f, 1.0f, 1.0f, 1.0f);
		shader.SetLightAttenuation(3, 1.0f, 0.01f, 0.0f);
		
		shader.SetMaterialDiffuse(0.0f, 0.0f, 0.0f, 1.0f);
		shader.SetMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
		shader.SetMaterialShinyness(100);
		shader.SetMaterialAmbient(0.0f, 0.0f, 0.0f, 1.0f);
		shader.setMaterialEmission(0.0f, 0.0f, 0.0f, 0.0f);

		BoxGraphic.create();
		GroundGraphic.create();
		SphereGraphic.create();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.SetModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		
		curball = new Point3D(-1.0f, -0.03f, -2.15f);
		
		up = new Vector3D(0,1,0);
		cam = new Camera();
		cam.look(new Point3D(-1.0f, 0.0f, 0.0f), new Point3D(-1.0f , 0, -1.0f), up);
		
		/*orthoCam = new Camera();
		orthoCam.orthographicProjection(-2, 2, -2, 2, 1.0f, 100); //*/
		
		Point3D playerLoc = cam.getEye();
		curpos = new Point3D(-1.0f, 0.0f, 0.0f);
		shader.SetCameraPosition(playerLoc.x, playerLoc.y, playerLoc.z);
		
		curvePoints = new Point3D[8];
		curvePoints[0] = new Point3D(-1.8f, 0.0f, -1.8f);
		curvePoints[1] = new Point3D(-1.0f, 0.0f, -3.0f);
		curvePoints[2] = new Point3D(-0.0f, 0.0f, -0.85f);
		curvePoints[3] = new Point3D(-0.5f, 0.0f, -1.0f);
		
		curvePoints[4] = new Point3D(-1.0f, 0.0f, -1.0f);
		curvePoints[5] = new Point3D(-1.5f, 0.0f, -1.0f);
		curvePoints[6] = new Point3D(-1.0f, 0.0f, -1.8f);
		curvePoints[7] = new Point3D(-1.0f, 0.0f, 3.0f);
		
		curves = new BezierCurve[7];
		curves[0] = new BezierCurve(curpos, new Vector3D(0.0f, 0.0f, -2.0f),
				new Vector3D(0.0f, 0.0f, -1.0f), curvePoints[0] );
		curves[1] = new BezierCurve(curvePoints[0], new Vector3D(0.0f, 0.0f, -1.0f),
				new Vector3D(0.5f, 0.0f, 0.0f), curvePoints[1] );
		curves[2] = new BezierCurve(curvePoints[1], new Vector3D(0.5f, 0.0f, 0.0f),
				new Vector3D(0.0f, 0.0f, 0.2f), curvePoints[2] );
		curves[3] = new BezierCurve(curvePoints[2], new Vector3D(0.0f, 0.0f, 0.01f),
				new Vector3D(-0.2f, 0.0f, -0.4f), curvePoints[3] );
		
		curves[4] = new BezierCurve(curvePoints[4], new Vector3D(-0.2f, 0.0f, 0.0f),
				new Vector3D(-0.2f, 0.0f, 0.0f), curvePoints[5] );
		curves[5] = new BezierCurve(curvePoints[5], new Vector3D(0.2f, 0.0f, -0.1f),
				new Vector3D(0.2f, 0.0f, -0.2f), curvePoints[6] );
		curves[6] = new BezierCurve(curvePoints[6], new Vector3D(0.0f, 0.0f, 0.2f),
				new Vector3D(0.0f, 0.0f, 1.0f), curvePoints[7] );
	}

	private void input()
	{
	}
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		angle += 90.0f * deltaTime;

		float delta = -3.0f * deltaTime;
		/*if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(delta, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(-delta, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.slide(0, 0, delta);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.slide(0, 0, -delta);
		} */
		if(Gdx.input.isKeyPressed(Input.Keys.G)) {
			start_time = System.currentTimeMillis();
		}
		
		/*if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.yaw(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.pitch(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.pitch(90.0f * deltaTime);
		}*/
		
		Point3D eye = cam.getEye();
		shader.SetLightPosition(0, eye.x, eye.y, eye.z);
		shader.SetCameraPosition(eye.x, eye.y, eye.z);
		
		
		if(start_time != -1) {
			float offset = (System.currentTimeMillis() - start_time);
			offset /= 1000.0f;
			
			offset += 28.0f;
			
			if(offset < 10.0f) {
				curpos = curves[0].calculate(offset / 10.0f);
			} else if(offset < 20.0f) {
				curpos = curves[1].calculate((offset - 10.0f) / 10.0f);
			} else if(offset < 30.0f) {
				curpos = curves[2].calculate((offset - 20.0f) / 10.0f);
			} else if(offset < 32.0f) {
				curpos = curves[3].calculate((offset - 30.0f) / 2.0f);
			} else {
				curpos = curvePoints[3];
			}
			
			if(offset < 2.0f) {
				curlook = curves[4].calculate(offset / 2.0f);
			} else if(offset <= 5.0f) {
				curlook = curvePoints[5];
			} else if(offset > 5.0f && offset < 10.0f) {
				curlook = curves[5].calculate((offset - 5.0f) / 5.0f);
			} else if(offset <= 32.5f) {
				curlook = curvePoints[6];
			} else if(offset > 32.5f && offset < 37.5f) {
				curlook = curves[6].calculate((offset - 32.5f) / 5.0f);
			} else {
				curlook = curvePoints[7];
			}
			
			if(offset > 33.5f) {
				curball.z = -2.15f + (((offset - 33.5f) % 2) * 16.0f);
			}
			
			cam.look(curpos, curlook, up);
			
			if(offset < 3.0f) {
				cam.pitch(offset / 3.0f * -35.0f);
			} else if(offset < 5.0f) {
				cam.pitch(-35.0f);
			} else if(offset < 7.0f) {
				cam.pitch((7.0f - offset) / 2.0f * -35.0f);
			}
		}
	}
	
	private void display() {
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.perspectiveProjection(fov, 1.0f, 0.1f, 100.0f);
		shader.SetViewMatrix(cam.getViewMatrix());
		shader.SetProjectionMatrix(cam.getProjectionMatrix());

		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.addTranslation(0, 0, 0);
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addScale(1.0f, 1.0f, 1.0f);
		
		{	// Chair
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
			ModelMatrix.main.addTranslation(-3.0f, -0.5f, -2.0f);
			
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addRotationY(45.0f);
			shader.SetModelMatrix(ModelMatrix.main.getMatrix());
			chair.draw(shader, chairTex);
			ModelMatrix.main.popMatrix();
			
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(2.0f, 0.0f, 0.0f);
			ModelMatrix.main.addRotationY(-45.0f);
			shader.SetModelMatrix(ModelMatrix.main.getMatrix());
			chair.draw(shader, chairTex);
			ModelMatrix.main.popMatrix();
			
			ModelMatrix.main.popMatrix();
		}
		
		{	// Cannon
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
			ModelMatrix.main.addTranslation(-2.0f, -1.0f, -4.0f);

			cannon.draw(shader, cannonTex);
			ModelMatrix.main.popMatrix();
		}
		
		{	// Ground
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(0.0f, -0.5f, 0.0f);
			shader.SetModelMatrix(ModelMatrix.main.getMatrix());
			GroundGraphic.draw(shader, groundTex);
			ModelMatrix.main.popMatrix();
		}
		
		{	// Cannon Balls
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(curball.x, curball.y, curball.z);
			ModelMatrix.main.addScale(0.05f, 0.05f, 0.05f);
			shader.SetModelMatrix(ModelMatrix.main.getMatrix());
			shader.SetMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
			shader.SetMaterialShinyness(0.0f);
			shader.SetMaterialAmbient(0.0f, 0.0f, 0.0f, 1.0f);
			shader.SetLightPosition(1, curball.x, curball.y, curball.z);
			int lights = shader.getLightCount();
			shader.SetLightCount(0);
			if(curball.z < -1.2f) {
				shader.SetMaterialDiffuse(0.0f, 0.0f, 0.0f, 1.0f);
				shader.SetLightIntensity(1, 0.0f, 0.0f, 0.0f, 1.0f);
			} else {
				shader.SetLightIntensity(1, 1.0f, 0.0f, 0.0f, 1.0f);
				shader.setMaterialEmission(1.0f, 0.2f, 0.2f, 1.0f);
			}
			SphereGraphic.draw(shader, null);
			shader.SetLightCount(lights);
			ModelMatrix.main.popMatrix();
		}
		
		{	// Skybox
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addScale(90.0f, 90.0f * 4/3, 90.0f);
			shader.SetMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
			shader.SetMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
			shader.SetMaterialShinyness(0);
			shader.SetMaterialAmbient(0.0f, 0.0f, 0.0f, 1.0f);
			shader.setMaterialEmission(0.0f, 0.0f, 0.0f, 0.0f);
			shader.SetLightCount(4);
			shader.SetModelMatrix(ModelMatrix.main.getMatrix());
			BoxGraphic.drawSolidCube(shader, skyBoxTex, true);
			shader.SetLightCount(3);
			ModelMatrix.main.popMatrix();
		}
		
	}

	@Override
	public void render () {
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}