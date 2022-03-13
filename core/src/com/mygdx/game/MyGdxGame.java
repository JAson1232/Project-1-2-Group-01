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
	float ballX = 400;
	float ballY = 400;
	float holeX = 50;
	float holeY = 250;
	boolean holeIn = false;

	float xSpeed = 5;
	float ySpeed = 5;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		if(!holeIn) {
			ballX += xSpeed;
			ballY += ySpeed;
		}

		// Changes direction of circle
		if(ballX < 0 || ballX > Gdx.graphics.getWidth()) { // 640
			xSpeed *= -1;
		}

		if(ballY < 0 || ballY > Gdx.graphics.getHeight()) { // 480
			ySpeed *= -1;
		}

		// Falls into hole
		if((ballX >= holeX - 10 && ballX <= holeX + 10) && ballY >= holeY - 10 && ballY <= holeY + 10) {
			holeIn = true;
		}

		// Changes color of background
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		// Removes color of circle in previous positions
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.circle(holeX, holeY, 30);
		if(!holeIn) {
			shapeRenderer.setColor(1, 1, 1, 1);
			shapeRenderer.circle(ballX, ballY, 20);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
