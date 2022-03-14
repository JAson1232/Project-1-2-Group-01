package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.concurrent.TimeUnit;

public class MyGdxGame extends ApplicationAdapter {
	Vector state = new Vector(0,0,0,null,1,0);
	Ball ball = new Ball(state);
	ShapeRenderer shapeRenderer;

	double h =0.1;
	MathFunctions math = new MathFunctions();
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
	Vector[][] vectors;
	boolean fieldCreated = false;

	public Vector[][] createField() {
		HeightFunction f = new HeightFunction();
		Field field = new Field();
		field.setLength(50);
		field.setWidth(50);
		vectors = field.createField();;
		for(int i = 0; i < vectors.length; i++) {
			for(int j = 0; j < vectors[0].length; j++) {
				vectors[i][j] = new Vector(j, i, f.f((double) j, (double) i, 0, 0), null, 0, 0);
			}
		}
		return vectors;
	}

	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		// Max value = 1.5, min value = =0.5; range = 2

		vectors = createField();


	}

	@Override
	public void render() {
		// Changes color of background
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		// User input just given, ball in motion
		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && moving) {
			// Shows gradual stop: 100 iterations of ball exponentially approaching target position (currently: tip of projection line)
			if(!((ball.state.getVx() < 0.05 && ball.state.getVx() > -0.05) && ((ball.state.getVy() < 0.05 && ball.state.getVy() > -0.05)))){
				ball.state = math.euler(ball.state,h);
				ballX += ball.state.getX();
				ballY += ball.state.getY();

				System.out.println(ball.getState().getVx());
				h+= 0.1;
			}






			if((ball.state.getVx() < 0.05 && ball.state.getVx() > -0.05) && ((ball.state.getVy() < 0.05 && ball.state.getVy() > -0.05))) { // TODO
				// Resets; prepares for next user inputs
				System.out.println("Ball stopped");
				counter = 0;
				strengthLength = 0;
				moving = false;
				ball.state.setVx(1.1);
				ball.state.setVy(0.1);
				h=0.1;
			}

		}
		else {
			// User giving inputs
			if(!holeIn && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				// Decreases holdConstant (to simulate ball going further from holding space bar for longer)
				holdConstant *= 0.975;
				// Signifies that movement is to be expected
				moving = true;
				// Increases length of strength bar if pressing of space bar is sustained
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
			if(Math.abs(holeX - ballX) <= 30 && Math.abs(holeY - ballY) <= 30) {
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
				// New location (angle) of trajectory line from ball
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
