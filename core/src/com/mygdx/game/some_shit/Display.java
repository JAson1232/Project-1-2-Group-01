package com.mygdx.game.some_shit;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;


/**
 * class responsible for UI, holds the frame
 * connected to Mouse object
 * listens to clicks
 */
public class Display extends JPanel implements KeyListener {

    int HEIGHT = 600;
    int WIDTH = 600;

    Ellipse2D ball;

    double xPositionBall = 10;
    double yPositionBall = 10;
    double radius = 20;

    public Display() {

        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        JFrame window = new JFrame();
        window.setPreferredSize(new Dimension(dimension));
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(this);
        window.pack();
        window.setVisible(true);
        window.addKeyListener(this);

        this.ball = new Ellipse2D.Double(xPositionBall,yPositionBall,radius,radius);

    }


    /**
     * This function is called BY THE SYSTEM if required for a new frame,
     * uses the matrix stored by the Painter class.
     */
    public void paintComponent(Graphics g) {

        Graphics2D localGraphics2D = (Graphics2D) g;
        Rectangle rectangle = new Rectangle(
                0, 0, WIDTH, HEIGHT);

        localGraphics2D.setColor(Color.GREEN);
        localGraphics2D.fill(rectangle);
        localGraphics2D.setColor(Color.WHITE);
        localGraphics2D.fill(this.ball);


    }



    /**
     * Key press control
     * possibilities
     * 				ENTER
     * 				keyUP
     * 				keyDOWN
     * 				m
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // ENTER was pressed
        if (e.getKeyCode() == 10 ) {

        }

    }


    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}



}