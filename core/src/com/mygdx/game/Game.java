package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.io.FileNotFoundException;

public class Game extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	double stepSize = 0.1;
	double h =stepSize;
	MathFunctions math = new MathFunctions();
	// Starting position
	Reader reader = new Reader();
	float ballX = Float.parseFloat(reader.compute().get(0))+100;
	float ballY = Float.parseFloat((reader.compute().get(1)))+50;
	float holeX = Float.parseFloat((reader.compute().get(2))); //+100*4;
	float holeY = Float.parseFloat((reader.compute().get(3))); //+100*4;
	Vector state = new Vector(ballX,ballY,0,null,-5,1);
	Ball ball = new Ball(state);
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
	int fieldWidth = 50;
	int numOfHits = 0;
	double prevX = ballX;
	double prevY = ballY;
	boolean readVelocity = true;
	// Menu
	private Stage stage;
	private Image image;
	private Label title;
	private Label groupName;
	private BitmapFont bmf;
	private Texture human;
	private Texture bot;
	private boolean isStarted = false;
	private float titleX = 125;
	private float titleY = 330;
	private float gNX = 400;
	private float gNY = 280;

	public Game() throws FileNotFoundException {}

	/**
	 * Creates 2D array of Vectors indicating heights of field
	 * @return created field
	 * @throws FileNotFoundException
	 */
	public Vector[][] createField() throws FileNotFoundException {
		HeightFunction f = new HeightFunction();
		Field field = new Field();
		field.setLength(fieldLength);
		field.setWidth(fieldWidth);
		vectors = field.createField();;
		for(int i = 0; i < vectors.length; i++) {
			for(int j = 0; j < vectors[0].length; j++) {
				vectors[i][j] = new Vector(j, i, f.f((double) j - vectors.length/2, (double) i - vectors.length/2, 0, 0), null, 0, 0);
			}
		}
		return vectors;
	}

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		try {
			vectors = createField();
			// Creating menu
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			stage = new Stage(new FitViewport(515,400));
			Gdx.input.setInputProcessor(stage);
			bmf = new BitmapFont(Gdx.files.internal("font/bitmapfont.fnt"));
			Label.LabelStyle style = new Label.LabelStyle();
			style.font = bmf;
			style.fontColor = new Color(255,255,255,1);
			groupName = new Label("Group 01",style);
			groupName.setPosition(gNX,gNY);
			groupName.setFontScale(0.7f);
			title = new Label("Crazy Putting",style);
			title.setPosition(titleX,titleY);
			title.setFontScale(1.5f);
			human = new Texture(Gdx.files.internal("human.png"));
			bot = new Texture(Gdx.files.internal("bot.png"));
			image = new Image(human);
			stage.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(Gdx.input.getX()<Gdx.graphics.getWidth()/2){
						isStarted = true;
					}

				}
			});
			stage.addActor(title);
			stage.addActor(groupName);
			stage.addActor(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		if(!isStarted){
			if (Gdx.input.getX()>Gdx.graphics.getWidth()/2) {
				image = new Image(bot);
				stage.addActor(image);

			}
			if (Gdx.input.getX()<Gdx.graphics.getWidth()/2) {
				image = new Image(human);
				stage.addActor(image);
			}
			stage.act();
			stage.draw();
		} else {
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
					if(vectors[j][i].getZ() >= 0) {
						float color = (float) (vectors[j][i].getZ() + 0.5)/2;
						shapeRenderer.setColor(0, color, 0, 1);
					} else {
						shapeRenderer.setColor(0, 0, 1, 1);
					}
					shapeRenderer.rect(10*i, 10*j, 10, 10);
				}
			}
			// Text to display xy-coordinates of ball & number of hits
			SpriteBatch spriteBatch;
			BitmapFont font;
			CharSequence coordinates = ("Ball at x: " + ballX/10 + ", y: " + ballY/10);
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
				if(!((ball.state.getVx() < 0.5 && ball.state.getVx() > -0.5) && ((ball.state.getVy() < 0.5 && ball.state.getVy() > -0.5)))) {
					try {
						ball.state = math.RK4(ball.state, h);
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
				if((ball.state.getVx() < 0.5 && ball.state.getVx() > -0.5) && ((ball.state.getVy() < 0.5 && ball.state.getVy() > -0.5))) { // TODO
					// Resets; prepares for next user inputs
					counter = 0;
					strengthLength = 0;
					try {
						if(px.getX(ball.state.getX(), ball.state.getY(), 0, 0) != 0 || px.getY(ball.state.getX(), ball.state.getY(), 0, 0) != 0) {
							// Ball comes to stop due to static friction
							if(Field.frictionStatic > Math.sqrt(((px.getX(ball.state.getX(), ball.state.getY(), 0, 0)) * (px.getX(ball.state.getX(), ball.state.getY(), 0, 0)))) + ((px.getY(ball.state.getX(), ball.state.getY(), 0, 0)) * (px.getY(ball.state.getX(), ball.state.getY(), 0, 0)))) {
								moving = false;
								readVelocity = true;
								h = stepSize;
							} else {
								moving = true;
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
							h = stepSize;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				try {
					if(f.f(ballX/10,ballY/10,0,0) < 0) {
						holeIn = true;
						moving = false;
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
				// Trajectory line
				if(!holeIn && !inWater) {
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

					// Set readVelocity to false to disable manual velocity inputs
					// readVelocity = false;
					if(readVelocity) {
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
				moving = false;
			}
			// Ball still visible
			if(!holeIn && !inWater) {
				shapeRenderer.setColor(1, 1, 1, 1);
				shapeRenderer.circle(ballX, ballY, 20);
			}
			shapeRenderer.end();
		}
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
