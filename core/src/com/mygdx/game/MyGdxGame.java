package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;

	// Starting position
	float ballX = 400;
	float ballY = 400;
	float holeX = 50;
	float holeY = 250;
	boolean holeIn = false;
	float trajecX, trajecY;
	double angle = 90;
	double lineLength = 100;
	double strengthLength = 0;
	double holdConstant = 20;
	boolean moving = false;
	int counter = 0;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		// Changes color of background
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		// Removes color of circle in previous positions
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && moving) {
			ballX += (trajecX - ballX)/holdConstant;
			ballY += (trajecY - ballY)/holdConstant;
			holdConstant = 20;
			counter++;
			if(counter == 5000)
				moving = false;
		}
		else {
			if(!holeIn && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				holdConstant *= 0.975;
				moving = true;
				if(strengthLength < 1)
					strengthLength += 0.025;
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

			// Trajectory line
			if(!holeIn) {
				// Trajectory
				shapeRenderer.setColor(Color.LIGHT_GRAY);
				if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
					angle += 2;
				if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					angle -= 2;
				trajecX = (float) (ballX + (lineLength*Math.cos(Math.toRadians(angle))));
				trajecY = (float) (ballY + (lineLength*Math.sin(Math.toRadians(angle))));
				shapeRenderer.rectLine(ballX, ballY, trajecX, trajecY, 5);

				// Strength
				float strengthX = (float) (ballX + strengthLength*(trajecX - ballX));
				float strengthY = (float) (ballY + strengthLength*(trajecY - ballY));
				shapeRenderer.setColor(Color.RED);
				shapeRenderer.rectLine(ballX, ballY, strengthX, strengthY, 5);
			}
		}

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
