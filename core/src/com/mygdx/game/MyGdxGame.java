package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	// Citation: https://happycoding.io/tutorials/libgdx/hello-world
	ShapeRenderer shapeRenderer;

	// Starting position
	float ballX = 0;
	float ballY = 0;
	float holeX = 50;
	float holeY = 50;
	// boolean holeIn = false;

	float xSpeed = 1;
	float ySpeed = 1;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		ballX += xSpeed;
		ballY += ySpeed;

		// Changes direction of circle
		if(ballX < 0 || ballX > Gdx.graphics.getWidth()) {
			xSpeed *= -1;
		}

		if(ballY < 0 || ballY > Gdx.graphics.getHeight()) {
			ySpeed *= -1;
		}

		// Falls into hole
		if((ballX < 50 || ballX > 50) && ballY < 50 || ballY > 50) {

		}

		// Changes color of background
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		// Removes color of circle in previous positions
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// if((ballX < 30 || ballX > 60) && ballY < 30 || ballY > 60) {

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.circle(ballX, ballY, 20);
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.circle(holeX, holeY, 30);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
