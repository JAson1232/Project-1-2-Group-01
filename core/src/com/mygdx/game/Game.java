package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game extends ApplicationAdapter {

	Vector state = new Vector(40,40,0,null,-5,1);
	Ball ball = new Ball(state);
	ShapeRenderer shapeRenderer;
	double stepSize = 0.3;
	double h =stepSize;
	MathFunctions math = new MathFunctions();
	// Starting position
	Reader reader = new Reader();
	float ballX = Float.parseFloat(reader.compute().get(0))+25;
	float ballY = Float.parseFloat((reader.compute().get(1)))+25;
	float holeX = Float.parseFloat((reader.compute().get(2)))+25*4;
	float holeY = Float.parseFloat((reader.compute().get(3)))+25*4;
	boolean holeIn = false;
	boolean inWater = false;
	float trajecX, trajecY;
	double angle = 90;
	double lineLength = 100;
	double strengthLength = 0;
	double holdConstant = 20;
	boolean moving = false;
	int counter = 0;
	Vector[][] vectors;
	boolean fieldCreated = false;
	int fieldLength = 50;
	int fieldWidth = 65;
	int numOfHits = 0;
	double prevX = ballX;
	double prevY = ballY;
	boolean readVelocity = true;

	public Game() throws FileNotFoundException {
	}

	public Vector[][] createField() throws FileNotFoundException {
		HeightFunction f = new HeightFunction();
		Field field = new Field();
		field.setLength(fieldLength);
		field.setWidth(fieldWidth);
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
		try {
			vectors = createField();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		HeightFunction f  = new HeightFunction();
		PartialDerivative px = new PartialDerivative(f);
		// Changes color of background
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		// Illustrates heights of field
		Gdx.graphics.setWindowedMode(650, 500);
		for(int i = 0; i < fieldWidth; i++) {
			for(int j = 0; j < fieldLength; j++) {
				float color = (float) (vectors[j][i].getZ() + 0.5)/2;

				shapeRenderer.setColor(0, color, 0, 1);
				shapeRenderer.rect(10*i, 10*j, 10, 10);

			}
		}
		// Text to display xy-coordinates of ball & number of hits
		SpriteBatch spriteBatch;
		BitmapFont font;
		CharSequence coordinates = ("Ball at x: " + ballX + ", y: " + ballY);
		CharSequence hits = ("Number of hits: " + numOfHits);
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		spriteBatch.begin();
		font.draw(spriteBatch, coordinates, 10, 40);
		font.draw(spriteBatch, hits, 10, 20);
		spriteBatch.end();



		// User input just given, ball in motion
		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && moving) {
			readVelocity = false;

			// Ball continues to move
			if(!((ball.state.getVx() < 0.15 && ball.state.getVx() > -0.15) && ((ball.state.getVy() < 0.15 && ball.state.getVy() > -0.15)))) {
				double prevX = ball.state.getX();

				double prevY = ball.state.getY();
				try {
					ball.state = math.euler(ball.state, h);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				// Bounce-off
				if( ball.state.getX() < 30 ||  ball.state.getX() > Gdx.graphics.getWidth()-30) {

						ball.state.setVx(ball.state.getVx()*-1.0);
						h = stepSize;
				}
				ballX = (float) ball.state.getX();

				// Bounce-off
				if(ball.state.getY() < 30 || ball.state.getY() > Gdx.graphics.getHeight()-30) {

						ball.state.setVy(ball.state.getVy()*-1.0);
						//ball.state.setY(ball.state.getY()*-1 );



					h = stepSize;
				}
				ballY = (float) ball.state.getY();
				h = stepSize;
			}
			System.out.println(ball.state.getVx());
			//System.out.println(ball.state.getX());
			//System.out.println(Math.sqrt(((px.getX(ball.state.getX(), ball.state.getY(),0,0))*(px.getX(ball.state.getX(), ball.state.getY(),0,0))))+((px.getY(ball.state.getX(), ball.state.getY(),0,0))*(px.getY(ball.state.getX(), ball.state.getY(),0,0))));

			if((ball.state.getVx() < 0.15 && ball.state.getVx() > -0.15) && ((ball.state.getVy() < 0.15 && ball.state.getVy() > -0.15))) { // TODO
				// Resets; prepares for next user inputs
				counter = 0;
				strengthLength = 0;
				try {
					if(px.getX(ball.state.getX(), ball.state.getY(), 0, 0) != 0 || px.getY(ball.state.getX(), ball.state.getY(), 0, 0) != 0) {
						// Ball comes to stop due to static friction
						if(Field.frictionStatic > Math.sqrt(((px.getX(ball.state.getX(), ball.state.getY(), 0, 0)) * (px.getX(ball.state.getX(), ball.state.getY(), 0, 0)))) + ((px.getY(ball.state.getX(), ball.state.getY(), 0, 0)) * (px.getY(ball.state.getX(), ball.state.getY(), 0, 0)))) {
							moving = false;
							readVelocity = true;
							//ball.state.setX(0);
							//ball.state.setY(0);
							//ball.state.setVx(10);
							//ball.state.setVy(0.1);
							h = stepSize;
						} else {
							moving = true;
							///////

							if((ball.state.getVx() < 0.1 && ball.state.getVx() > -0.1)){
								ball.state.setVx(-1*Math.abs(ball.state.getVx()));
							}
							// Ball starts falling down slope
							if(((ball.state.getVy() < 0.1 && ball.state.getVy() > -0.1))) {
								ball.state.setVy(-1 * Math.abs(ball.state.getVy()));
							}
						}
					} else {
						moving = false;
						readVelocity = true;
						//ball.state.setX(0);
						//ball.state.setY(0);
						//ball.state.setVx(10);
						//ball.state.setVy(0.1);
						h = stepSize;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			try {
				if(f.f(ball.state.getX(),ball.state.getY(),0,0) < 0){
					holeIn = true;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		else {
			// User giving inputs
			if(!holeIn && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				// Decreases holdConstant (to simulate ball going further from holding space bar for longer)
				holdConstant *= 0.975;
				// Signifies that movement is to be expected
				if(!moving)
					numOfHits++;
				moving = true;
				// Increases length of strength bar if pressing of space bar is sustained
				if(strengthLength < 5)
					strengthLength += 0.125;
			}
			// Falls into hole

			// Trajectory line
			if(!holeIn) {
				// Trajectory
				shapeRenderer.setColor(Color.LIGHT_GRAY);
				if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
					angle += 2;
				if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					angle -= 2;
				// New location (angle) of trajectory line from ball
				if(angle>360){
					angle -=360;
				}
				if(angle<0){
					angle += 360;
				}
				trajecX = (float) (ballX + (lineLength*Math.cos(Math.toRadians(angle))));
				trajecY = (float) (ballY + (lineLength*Math.sin(Math.toRadians(angle))));

				shapeRenderer.rectLine(ballX, ballY, trajecX, trajecY, 5);
				// Strength
				float strengthX = (float) (ballX + strengthLength/5*(trajecX - ballX));
				double vXX = strengthLength;
				double vYY = strengthLength;
				float strengthY = (float) (ballY + strengthLength/5*(trajecY - ballY));

				if(angle <= 270 && angle > 180){
					vXX = strengthLength*-1*Math.cos(Math.toRadians(angle-180));
					vYY = strengthLength*-1*Math.sin(Math.toRadians(angle-180));
				}else if(angle > 270){
					vXX = strengthLength*Math.cos(Math.toRadians(360-angle));
					vYY = strengthLength*-1*Math.sin(Math.toRadians(360-angle));
				}else if(angle<=180 && angle >90){
					vXX = strengthLength*-1*Math.cos(Math.toRadians(180-angle));
					vYY = strengthLength*Math.sin(Math.toRadians(180-angle));
				}else{
					vXX = strengthLength*Math.cos(Math.toRadians(angle));
					vYY = strengthLength*Math.sin(Math.toRadians(angle));
				}
				if(readVelocity) {
					//System.out.println("here");
					 prevX = ball.state.getX();
					 prevY = ball.state.getY();
					ball.state.setVx(vXX * 20);
					ball.state.setVy(vYY * 20);

				}



				shapeRenderer.setColor(Color.RED);
				shapeRenderer.rectLine(ballX, ballY, strengthX, strengthY, 5);
			}
		}

		// Hole
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.circle(holeX, holeY, 30);

		// Ball
		if(Math.abs(holeX - ballX) <= 40 && Math.abs(holeY - ballY) <= 40) {
			holeIn = true;
		}



		if(!holeIn && !inWater ) {
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

	public double calculateHeight(double x, double y) throws FileNotFoundException {
		HeightFunction hf = new HeightFunction();

		return hf.f(x,y,0,0);
	}

	public int getUserInputAngle(){
		return 0;
	}
}
