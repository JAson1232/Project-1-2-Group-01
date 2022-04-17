package com.mygdx.game;

public interface Obstacle {
    boolean collision(Vector currentPos);
    void effect(Vector currentVelocity, Vector currentPos);
    String getName();
    double getRadius();
}
