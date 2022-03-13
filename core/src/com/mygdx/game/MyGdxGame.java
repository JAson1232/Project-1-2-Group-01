package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		float xSpeed = 20;
		float ySpeed = 20;
		float trajecX = ballX;
		float trajecY = ballY + 100;


		if(!holeIn && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			ballX += xSpeed;
			ballY += ySpeed;
		}

		// Changes direction of ball
		// if(ballX < 0 || ballX > Gdx.graphics.getWidth()) {
		// 	xSpeed *= -1;
		// }

		// if(ballY < 0 || ballY > Gdx.graphics.getHeight()) {
		// 	ySpeed *= -1;
		// }

		// Falls into hole
		if((ballX >= holeX - 10 && ballX <= holeX + 10) && ballY >= holeY - 10 && ballY <= holeY + 10) {
			holeIn = true;
		}

		// Changes color of background
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		// Removes color of circle in previous positions
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		// Trajectory line

		// Problem: It needs to move around in unit circle




		// trajecX = ballX, trajecY = ballY + 100
		// each key changes angle by 10 degrees

		double angle = 10;
        double radian = Math.toRadians(angle);




		shapeRenderer.setColor(Color.LIGHT_GRAY);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			trajecX -= Math.cos(radian);
			trajecY -= Math.sin(radian);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			trajecX += 5;
		shapeRenderer.rectLine(ballX, ballY, trajecX, trajecY, 5);



		// Hole
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.circle(holeX, holeY, 30);
		// Ball
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


	public void calculatePos(double vo,int x, int y, int angle){

	}
	public double calculateHeight(int x, int y){
		return Math.sin((x-y)/7)+0.5;
	}

	public int getUserInputAngle(){
		return 0;
	}
}
