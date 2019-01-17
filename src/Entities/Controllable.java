/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Foundation.Map;
import java.awt.Graphics;

/**
 *
 * @author John Popovici
 */
public abstract class Controllable extends Movable {

    /**
     * Creates a controllable entity
     *
     * @author John Popovici
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param radius the radius of the hit-box of the entity
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Controllable(double x, double y, int radius, double faceAngle, int teamID, Map map) {
        super(x, y, radius, 0.0, faceAngle, teamID, map);
        storage = 0;
        thrustF = 0;
        thrustRotR = 0;
        thrustRotL = 0;
    }

    @Override //@author Carl Wu hehe
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        super.draw(g, scale, offsetX, offsetY);
        int drawX = (int) ((getPos().getX() + offsetX) * scale);
        int drawX1 = (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle)) * scale);
        int drawX2 = (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle + 120)) * scale);
        int drawX3 = (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle - 120)) * scale);
        int drawY = (int) ((getPos().getY() + offsetY) * scale);
        int drawY1 = (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle)) * scale);
        int drawY2 = (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle + 120)) * scale);
        int drawY3 = (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle - 120)) * scale);
        g.drawLine(drawX1, drawY1, drawX2, drawY2);
        g.drawLine(drawX1, drawY1, drawX3, drawY3);
        g.drawLine(drawX2, drawY2, drawX3, drawY3);

    }
/**
 * set the storage to 0 when hit with a bullet 
 * 
 * @param other the bullet that's being collided with
 * 
 * @author Carl Wu
 */
    public void collideBullet(Bullet other) {
        if (this.checkCollision(other) && !(teamID == other.teamID)) {
            storage = 0;
        }
    }

    /**
     * the amount of resources stored by the entity
     */
    protected int storage;
    /**
     * the percent of the forward thruster being used. The value must be between
     * 0 and 100, inclusive
     */
    protected int thrustF;
    /**
     * the percent of the right rotation thruster being used. The value must be
     * between 0 and 100, inclusive. Thruster rotates counterclockwise
     */
    protected int thrustRotR;
    /**
     * the percent of the left rotation thruster being used. The value must be
     * between 0 and 100, inclusive. Thruster rotates clockwise
     */
    protected int thrustRotL;
    /**
     * whether the entity has acted this frame such that the move method only
     * functions once per frame even if called multiple times
     */
    protected boolean hasAct = true;

    /**
     * Gets the amount of resources stored by the entity
     *
     * @return the amount of resources stored by the entity
     */
    public int getStorage() {
        return storage;
    }

    /**
     * Gets the percent of the forward thruster being used.
     *
     * @return the percent of the forward thruster being used. The value must be
     * between 0 and 100, inclusive
     */
    public int getThrustF() {
        return thrustF;
    }

    /**
     * Gets the percent of the right rotation thruster being used.
     *
     * @return the percent of the right rotation thruster being used. The value
     * must be between 0 and 100, inclusive. Thruster rotates counterclockwise
     */
    public int getThrustRotR() {
        return thrustRotR;
    }

    /**
     * Gets the percent of the left rotation thruster being used.
     *
     * @return the percent of the left rotation thruster being used. The value
     * must be between 0 and 100, inclusive. Thruster rotates clockwise
     */
    public int getThrustRotL() {
        return thrustRotL;
    }

    /**
     * Set the forward thruster to accelerate the entity
     *
     * @param thrustF the percent of the forward thruster being used. The value
     * must be between 0 and 100, inclusive
     * @throws IllegalArgumentException for a value out of bounds
     */
    public void setThrustF(int thrustF) throws IllegalArgumentException {
        if (thrustF < 0 || thrustF > 100) {
            throw new IllegalArgumentException();
        }
        this.thrustF = thrustF;
    }

    /**
     * Set the right rotational thruster to rotate the entity
     *
     * @param thrustRotR the percent of the right rotation thruster being used.
     * The value must be between 0 and 100, inclusive. Thruster rotates
     * counterclockwise
     * @throws IllegalArgumentException for a value out of bounds
     */
    public void setThrustRotR(int thrustRotR) throws IllegalArgumentException {
        if (thrustRotR < 0 || thrustRotR > 100) {
            throw new IllegalArgumentException();
        }
        this.thrustRotR = thrustRotR;
    }

    /**
     * Set the left rotational thruster to rotate the entity
     *
     * @param thrustRotL the percent of the left rotation thruster being used.
     * The value must be between 0 and 100, inclusive. Thruster rotates
     * clockwise
     * @throws IllegalArgumentException for a value out of bounds
     */
    public void setThrustRotL(int thrustRotL) throws IllegalArgumentException {
        if (thrustRotL < 0 || thrustRotL > 100) {
            throw new IllegalArgumentException();
        }
        this.thrustRotL = thrustRotL;
    }

    @Override
    public void move() {
        //maintain maximum velocity of 1 pixel per frame
        if (vel.getSpeed() > 1.0) {
            double newx = vel.x / vel.getSpeed();
            double newy = vel.y / vel.getSpeed();
            vel.x = newx;
            vel.y = newy;
        }

        //decay velocity
        if (vel.x < 0.001 && vel.x > -0.001) {
            vel.x = 0;
        } else {
            double newx = (vel.x / vel.getSpeed()) * (vel.getSpeed() - VEL_DECAY);
            double newy = (vel.y / vel.getSpeed()) * (vel.getSpeed() - VEL_DECAY);
            vel.x = newx;
            vel.y = newy;
        }

        super.move();
    }

}
