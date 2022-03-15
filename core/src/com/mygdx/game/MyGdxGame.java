package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class MyGdxGame extends ApplicationAdapter {
	Vector state = new Vector(0,0,0,null,10,0);
	Ball ball = new Ball(state);
	ShapeRenderer shapeRenderer;
	double stepSize = 0.005;
	double h =stepSize;
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
	int fieldLength = 50;
	int fieldWidth = 65;
	int numOfHits = 0;

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

		double prevX = ballX;
		double prevY = ballY;

		// User input just given, ball in motion
		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && moving) {

			// Ball continues to move
			if(!((ball.state.getVx() < 0.1 && ball.state.getVx() > -0.1) && ((ball.state.getVy() < 0.1 && ball.state.getVy() > -0.1)))) {
				try {
					ball.state = math.euler(ball.state, h);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				// Bounce-off
				if(ballX < 0 || ballX > Gdx.graphics.getWidth()) {
					ball.state.setVx(ball.state.getVx()*-1.0);
					ball.state.setX(ball.state.getX()*-1.0);
					h = stepSize;
				}
				ballX += ball.state.getX();
				System.out.println(ball.state.getVx());
				// Bounce-off
				if(ballY < 0 || ballY > Gdx.graphics.getHeight()) {
					ball.state.setVy(ball.state.getVy()*-1);
					ball.state.setY(ball.state.getY()*-1.0);

					h = stepSize;
				}
				ballY += ball.state.getY();
				h += stepSize;
			}

			//System.out.println(Math.sqrt(((px.getX(ball.state.getX(), ball.state.getY(),0,0))*(px.getX(ball.state.getX(), ball.state.getY(),0,0))))+((px.getY(ball.state.getX(), ball.state.getY(),0,0))*(px.getY(ball.state.getX(), ball.state.getY(),0,0))));

			if((ball.state.getVx() < 0.1 && ball.state.getVx() > -0.1) && ((ball.state.getVy() < 0.1 && ball.state.getVy() > -0.1))) { // TODO
				// Resets; prepares for next user inputs
				counter = 0;
				strengthLength = 0;
				try {
					if(px.getX(ball.state.getX(), ball.state.getY(), 0, 0) != 0 || px.getY(ball.state.getX(), ball.state.getY(), 0, 0) != 0) {
						// Ball comes to stop due to static friction
						if(Field.frictionStatic > Math.sqrt(((px.getX(ball.state.getX(), ball.state.getY(), 0, 0)) * (px.getX(ball.state.getX(), ball.state.getY(), 0, 0)))) + ((px.getY(ball.state.getX(), ball.state.getY(), 0, 0)) * (px.getY(ball.state.getX(), ball.state.getY(), 0, 0)))) {
							moving = false;
							ball.state.setX(0);
							ball.state.setY(0);
							ball.state.setVx(10);
							ball.state.setVy(0.1);
							h = stepSize;
						} else {
							moving = true;
							// Ball starts falling down slope
							ball.state.setVx(-1*Math.abs(ball.state.getVx()));
							ball.state.setVy(-1*Math.abs(ball.state.getVy()));
						}
					} else {
						moving = false;
						ball.state.setX(0);
						ball.state.setY(0);
						ball.state.setVx(10);
						ball.state.setVy(0.1);
						h = stepSize;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
				if(strengthLength < 1)
					strengthLength += 0.025;
			}
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
