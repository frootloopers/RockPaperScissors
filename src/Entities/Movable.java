/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Blocks.Vel;
import Foundation.Map;
import java.awt.Graphics;

/**
 *
 * @author John Popovici
 */
public abstract class Movable extends Entity {

    /**
     * Creates a movable entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param radius the radius of the hit-box of the entity
     * @param speed the speed the entity starts with
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Movable(double x, double y, int radius, double speed, double faceAngle, int teamID, Map map) {
        super(x, y, radius, teamID, map);
        //the maximum speed is 1 pixel per frame
        if (speed > 1.0) {
            speed = 1.0;
        }
        vel = new Vel(Math.sin(Math.toRadians(faceAngle)) * speed, Math.cos(Math.toRadians(faceAngle)) * -1 * speed);
        //the faceAngle does not have to be between 0 and 360
        this.faceAngle = faceAngle;
    }

    /**
     * the velocity object of this entity
     */
    protected Vel vel;
    /**
     * the angle at which the entity is facing. A value of 0 represents a value
     * horizontally and to the right and the angle is measured in radians
     */
    protected double faceAngle;

    /**
     * Gets the velocity object of the entity
     *
     * @return the velocity object of the entity
     */
    public Vel getVel() {
        return new Vel(this.vel.getX(), this.vel.getY());
    }

    /**
     * Gets the angle at which the entity is facing
     *
     * @return the angle at which the entity is facing. A value of 0 represents
     * a value horizontally and to the right and the angle is measured in
     * radians
     */
    public double getFaceAngle() {
        double angle = faceAngle;
        //reset faceAngle
        while (angle >= 360.0) {
            angle -= 360.0;
        }
        while (angle < 0.0) {
            angle += 360.0;
        }
        return angle;
    }

    /**
     * Draw the icon representing the movable has line which represent the
     * faceAngle of the movable
     *
     * @param g the graphics used to draw the entity
     * @param scale the scale of the game board, at which the entity is drawn
     * @param offsetX the offset of the x-value of the game board, at which the
     * entity is drawn
     * @param offsetY the offset of the y-value of the game board, at which the
     * entity is drawn
     */
    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        super.draw(g, scale, offsetX, offsetY);
        g.drawLine((int) (getPos().getX()), (int) (getPos().getY()),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle))));
    }

    /**
     * Must be called each frame of the game to move the entity
     */
    protected void move() {
        //changes the position according to the velocity
        pos.x += vel.x;
        pos.y += vel.y;
    }

    public void collision() {
        vel.x = -vel.x;
        vel.y = -vel.y;
    }

}
