package com.mygdx.game.Phase3;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.*;
import com.mygdx.game.PSO.GolfPSO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game2 extends ApplicationAdapter {

	ShapeRenderer shapeRenderer;
	boolean prevBot = true;
	double stepSize = 0.1;
	double h = stepSize;
	MathFunctions math = new MathFunctions();
	long startTime;
	// Starting position
	ArrayList<double[]> velocities = new ArrayList<>();
	Reader reader = new Reader();
	//HillClimbing hc = new HillClimbing();
	float ballX = Float.parseFloat(reader.compute().get(0)) + 100;
	float ballY = Float.parseFloat((reader.compute().get(1))) + 100;
	float holeX = Float.parseFloat((reader.compute().get(2))) + 100 * 4;
	float holeY = Float.parseFloat((reader.compute().get(3))) + 100 * 4;
	ArrayList<Double> times = new ArrayList<>();
	velocityReader reader2 = new velocityReader();
	float vx = velocityReader.compute().get(0);
	float vy = velocityReader.compute().get(1);
	Vector state = new Vector(ballX, ballY, 0, null, 0, 0);
	double vXX;
	double vYY;
	Vector hole = new Vector(holeX, holeY, 0, null, 0, 0);
	boolean holeIn = false;
	boolean inWater = false;
	// For determining which bot finds a solution
	boolean first = false;
	// first2 to get start time
	boolean first2 = true;
	boolean one = true;
	;
	float trajecX, trajecY;
	double angle = 90;
	double lineLength = 100;
	double strengthLength = 0;
	double holdConstant = 20;
	boolean moving = false;
	int counter = 0;
	Vector[][] vectors;

	boolean fieldCreated = false;
	int fieldLength = 100;
	int fieldWidth = 130;
	int numOfHits = 0;
	double prevX = ballX;
	double prevY = ballY;
	boolean readVelocity;
	boolean testBot = true;
	Ball ball = new Ball(state);
	HeightFunction f = new HeightFunction();
	PartialDerivative px = new PartialDerivative(f);
	boolean Bot = false;
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

	RuleBased rule = new RuleBased();
	ModHillClimbing modHC = new ModHillClimbing();
	GraphGolf graph = new GraphGolf();
	HillClimbing hc = new HillClimbing();
	GolfPSO pso = new GolfPSO();
	ruleBasedBot rule2 = new ruleBasedBot();

	public Game2() throws FileNotFoundException {
	}

	/**
	 * Creates 2D array of Vectors indicating heights of field
	 *
	 * @return created field
	 * @throws FileNotFoundException
	 */
	public Vector[][] createField() throws FileNotFoundException {
//		System.out.println(" BAAL CORD " + " X " + ballX + " Y " + ballY);
//		System.out.println(" HOLE CORD " + " X " + holeX + " Y " + holeY);
		HeightFunction f = new HeightFunction();
		Field field = new Field();
		field.setLength(fieldLength);
		field.setWidth(fieldWidth);
		vectors = field.createField();

		// Adding height values to each Vector tile in field
		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[0].length; j++) {
				// TODO: Does this make it crash?
				int x = (int) (j - fieldLength / 2.0);
				int y = (int) (i - fieldWidth / 2.0);
				vectors[i][j] = new Vector(x, y,
						f.f(x, y, 0, 0), null, 0, 0);
				if (x == holeX && y == holeY) {
					vectors[i][j].setZ(9);
				}
				if (x == ballX && y == ballY) {
					vectors[i][j].setZ(7.5);
				}
			}
		}
		return vectors;
	}

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		try {
			vectors = createField();
			// Creating menu
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			stage = new Stage(new FitViewport(515, 400));
			Gdx.input.setInputProcessor(stage);
			bmf = new BitmapFont(Gdx.files.internal("font/bitmapfont.fnt"));
			Label.LabelStyle style = new Label.LabelStyle();
			style.font = bmf;
			style.fontColor = new Color(255, 255, 255, 1);
			groupName = new Label("Group 01", style);
			groupName.setPosition(gNX, gNY);
			groupName.setFontScale(0.7f);
			title = new Label("Crazy Putting", style);
			title.setPosition(titleX, titleY);
			title.setFontScale(1.5f);
			human = new Texture(Gdx.files.internal("bot.png"));
			bot = new Texture(Gdx.files.internal("human.png"));
			image = new Image(human);
			stage.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
						Bot = true;
					} else if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
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

		if (!isStarted && !Bot) {
			//System.out.println("one");
			if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
				image = new Image(bot);
				stage.addActor(image);
			}
			if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
				image = new Image(human);
				stage.addActor(image);
			}
			stage.act();
			stage.draw();
		} else if (isStarted && !Bot) {
			Gdx.gl.glClearColor(0, 0.5f, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

			for (int i = 0; i < fieldWidth; i++) {
				for (int j = 0; j < fieldLength; j++) {
					if (vectors[j][i].getZ() >= 0 && vectors[j][i].getZ() < 5) {
						// Grass
						float color = (float) (vectors[j][i].getZ() + 0.5) / 2;
						shapeRenderer.setColor(0, color, 0, 1);
						// Water
					} else if (vectors[j][i].getZ() < 0) {
						shapeRenderer.setColor(0, 0, 1, 1);
						// Obstacle
					} else if (vectors[j][i].getZ() > 5 && vectors[j][i].getZ() < 7) {
						shapeRenderer.setColor(Color.BROWN);
					}
					// Hole
					shapeRenderer.rect(5 * i, 5 * j, 5, 5);
				}
			}

			// Text to display xy-coordinates of ball & number of hits
			SpriteBatch spriteBatch;
			BitmapFont font;
			CharSequence legend = ("Legend:");
			CharSequence altitude1 = ("Lighter = higher altitude");
			CharSequence altitude2 = ("Darker = lower altitude");

			CharSequence coordinates = ("Ball at x: " + ball.state.getX() / 10 + ", y: " + ball.state.getY() / 10);
			CharSequence hits = ("Number of hits: " + numOfHits);
			CharSequence filler = ("    ");

			spriteBatch = new SpriteBatch();
			font = new BitmapFont();
			spriteBatch.begin();
			font.draw(spriteBatch, coordinates, 10, 40);
			font.draw(spriteBatch, hits, 10, 20);
			font.draw(spriteBatch, filler, 10, 470);
			font.draw(spriteBatch, legend, 10, 450);
			font.draw(spriteBatch, altitude1, 10, 430);
			font.draw(spriteBatch, altitude2, 10, 410);
			spriteBatch.end();

			if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) && ball.moving) {
				ball = moveBall(ball);
				one = true;
			} else {
				// User giving inputs
				if (!ball.holeIn && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
					// Decreases holdConstant (to simulate ball going further from holding space bar
					// for longer)
					holdConstant *= 0.975;
					// Signifies that movement is to be expected
					if (!ball.moving)
						numOfHits++;
					ball.moving = true;
					// Increases length of strength bar if pressing of space bar is sustained
					if (strengthLength < 5)
						strengthLength += 0.125;
				}
				// Trajectory line
				if (!ball.holeIn) {
					// Trajectory
					shapeRenderer.setColor(Color.LIGHT_GRAY);
					if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
						angle += 2;
					if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
						angle -= 2;
					// New location (angle) of trajectory line from ball
					if (angle > 360) {
						angle -= 360;
					}
					if (angle < 0) {
						angle += 360;
					}
					trajecX = (float) (ball.state.getX() + (lineLength * Math.cos(Math.toRadians(angle))));
					trajecY = (float) (ball.state.getY() + (lineLength * Math.sin(Math.toRadians(angle))));
					if (isStarted == false) {
						shapeRenderer.rectLine((float) ball.state.getX(), (float) ball.state.getY(), trajecX, trajecY,
								5);
					}
					// Strength
					float strengthX = (float) (ball.state.getX() + strengthLength / 5 * (trajecX - ball.state.getX()));
					double vXX = strengthLength;
					double vYY = strengthLength;
					float strengthY = (float) (ball.state.getY() + strengthLength / 5 * (trajecY - ball.state.getY()));
					if (angle <= 270 && angle > 180) {
						vXX = strengthLength * -1 * Math.cos(Math.toRadians(angle - 180));
						vYY = strengthLength * -1 * Math.sin(Math.toRadians(angle - 180));
					} else if (angle > 270) {
						vXX = strengthLength * Math.cos(Math.toRadians(360 - angle));
						vYY = strengthLength * -1 * Math.sin(Math.toRadians(360 - angle));
					} else if (angle <= 180 && angle > 90) {
						vXX = strengthLength * -1 * Math.cos(Math.toRadians(180 - angle));
						vYY = strengthLength * Math.sin(Math.toRadians(180 - angle));
					} else {
						vXX = strengthLength * Math.cos(Math.toRadians(angle));
						vYY = strengthLength * Math.sin(Math.toRadians(angle));
					}
					// TODO: Repeat
					// Set readVelocity to false to disable manual velocity inputs as well as
					// uncomment following else{} statement
					// readVelocity = false;
					if (ball.readVelocity) {
						prevX = ball.state.getX();
						prevY = ball.state.getY();

						ball.state.setVx(vXX * 15);
						ball.state.setVy(vYY * 15);
						vXX = 0;
						vYY = 0;
						// For changing to text file input
					}
					if (ball.inWater) {
						ball.state.setVx(0);
						ball.state.setVy(0);
					}
					if (!ball.inWater) {
						shapeRenderer.setColor(Color.RED);
						if (isStarted == false) {
							shapeRenderer.rectLine((float) ball.state.getX(), (float) ball.state.getY(), strengthX,
									strengthY, 5);
						}
						strengthX = 0;
						strengthY = 0;
					}
				}
			}
			// Hole
			shapeRenderer.setColor(0, 0, 0, 1);
			shapeRenderer.circle(holeX, holeY, 12);

			// Ball is close enough to fall into the hole
			if (Math.abs(holeX - ball.state.getX()) <= 20 && Math.abs(holeY - ball.state.getY()) <= 20) {
				ball.holeIn = true;
				ball.moving = false;
			}
			// Ball still visible
			if (!ball.holeIn) {
				shapeRenderer.setColor(1, 1, 1, 1);
				shapeRenderer.circle((float) ball.state.getX(), (float) ball.state.getY(), 8);
				ball.inWater = false;
			}
			shapeRenderer.end();
		}


//-------------------------------------------------------------------------------------
		if (prevBot) {

//			ball.state = rule2.directlyShooting(ball.state, hole);			//RULE BASED GARUS


//			try {											//PSO
//				ball.state = pso.runPSO(ball.state, hole);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//
			try {
				ball.state = modHC.modHC(ball.state, hole);			// NEW HC
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

//			ball.state =  rule.constructVector(ball.state, hole);  	// rule based (magnitude)

//			try {
//				ball.state = hc.HillClimbing(ball.state, hole);    	// old hc  doesnt work without bouncing
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}


		//MAZE BOT UI DOESN'T WORK

//			graph.assignNeighbors(graph.matrix);
//			VertexGolf target = graph.Dijkstra(graph.matrix, graph.startVertex);
//			ArrayList<int[]> pathCord = graph.storePath(target);
//			ArrayList<Vector> pathVector = graph.convertInVector(pathCord);
//			try {
//				ArrayList<Vector> pathToFollow = graph.runPathWithHc(pathVector);
//				for (int i = 0; i < pathToFollow.size(); i++) {
//					Vector stateP = pathVector.get(i);
//					Vector ballState = new Vector(stateP.getX()+100, stateP.getY()+100, stateP.getZ(),null, stateP.getVx()*5, state.getVy()*5);
//					ball.state = ballState;
//					ball = moveBall(ball);
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}

// -------------------------------------------------------------------------------------

			while (Math.sqrt(Math.pow(ball.state.getVx(), 2) + Math.pow(ball.state.getVy(), 2)) < stepSize * 5) {
				System.out.println("AAAAAAAAAAA");
				ball = moveBall(ball);
				System.out.println("BBBBBBBBBBB");

				ball.holeIn = true;
				ball.moving = false;
			}
			prevBot = false;
			shapeRenderer.flush();
		}
	}

	public Ball moveBall(Ball ball1) {
		Ball ball = ball1;
		ball.inWater = false;
		// Ball continues to move
		if (!((ball.state.getVx() < stepSize * 5 && ball.state.getVx() > stepSize * -5)
				&& ((ball.state.getVy() < stepSize * 5 && ball.state.getVy() > stepSize * -5)))) {
			try {
				ball.state = math.RK4(ball.state, h);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// Dis/enabling bounce-off from borders of field
			if (ball.state.getX() < 20 || ball.state.getX() > Gdx.graphics.getWidth() - 20) {
				ball.state.setVx(0);
				ball.state.setVy(0);
				//ball.state.setVx(ball.state.getVx() * -1.0);
				h = stepSize;
			}

			if (ball.state.getY() < 20 || ball.state.getY() > Gdx.graphics.getHeight() - 20) {
				ball.state.setVx(0);
				ball.state.setVy(0);
				//ball.state.setVy(ball.state.getVy() * -1.0);
				h = stepSize;
			}

			// Finding ball's location on vectors 2D array (field)
			// Determining if ball is touching the obstacle (collision detection; if so, bounce off)
			if (vectors[(int) (ball.state.getY() + 20) / 5][(int) (ball.state.getX()) / 5].getZ() > 5 || vectors[Math.abs((int) (ball.state.getY() - 20) / 5)][(int) (ball.state.getX()) / 5].getZ() > 5) {
				ball.state.setVy(ball.state.getVy() * -1.0);
			}

			if (vectors[(int) (ball.state.getY()) / 5][Math.abs((int) (ball.state.getX() - 22) / 5)].getZ() > 5 || vectors[(int) (ball.state.getY()) / 5][(int) (ball.state.getX() + 22) / 5].getZ() > 5) {
				ball.state.setVx(ball.state.getVx() * -1.0);
			}
			h = stepSize;
		}
		if ((ball.state.getVx() < stepSize * 5 && ball.state.getVx() > -stepSize * 5)
				&& ((ball.state.getVy() < stepSize * 5 && ball.state.getVy() > -stepSize * 5))) {
			// Resets; prepares for next user inputs
			counter = 0;
			strengthLength = 0;
			try {
				if (px.getX(ball.state.getX(), ball.state.getY(), 0, 0) != 0
						|| px.getY(ball.state.getX(), ball.state.getY(), 0, 0) != 0) {
					// Ball comes to stop due to static friction
					// TODO: Slope messed up?
					if (Field.frictionStatic > Math
							.sqrt(((px.getX(ball.state.getX(), ball.state.getY(), 0, 0))
									* (px.getX(ball.state.getX(), ball.state.getY(), 0, 0))))
							+ ((px.getY(ball.state.getX(), ball.state.getY(), 0, 0))
							* (px.getY(ball.state.getX(), ball.state.getY(), 0, 0)))) {
						ball.moving = false;

						ball.readVelocity = true;
						h = stepSize;
					} else {
						// Ball starts falling down slope
						ball.moving = true;
						if ((ball.state.getVx() < 0.1 && ball.state.getVx() > -0.1)) {
							ball.state.setVx(-1 * Math.abs(ball.state.getVx()));
						}
						if (((ball.state.getVy() < 0.1 && ball.state.getVy() > -0.1))) {
							ball.state.setVy(-1 * Math.abs(ball.state.getVy()));
						}
					}
				} else {
					ball.moving = false;
					ball.readVelocity = true;
					h = stepSize;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			// Falling into water
			if (f.f(((ball.state.getX()) / 10 - fieldLength / 2), ((ball.state.getY()) / 10 - fieldLength / 2), 0,
					0) < 0) {
				ball.state.setX(prevX);
				ball.state.setY(prevY);
				ball.state.setVx(0);
				ball.state.setVy(0);
				counter = 0;
				strengthLength = 0;
				holdConstant = 20;
				ball.inWater = true;
				ball.moving = false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ball;
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}