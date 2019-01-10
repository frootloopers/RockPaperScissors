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

    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        super.draw(g, scale, offsetX, offsetY);
        g.drawLine((int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle))),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle + 120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle + 120))));
        g.drawLine((int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle))),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle - 120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle - 120))));
        g.drawLine((int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle - 120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle - 120))),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle + 120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle + 120))));

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
     * @throws IllegalArgumentException
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
     * @throws IllegalArgumentException
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
     * @throws IllegalArgumentException
     */
    public void setThrustRotL(int thrustRotL) throws IllegalArgumentException {
        if (thrustRotL < 0 || thrustRotL > 100) {
            throw new IllegalArgumentException();
        }
        this.thrustRotL = thrustRotL;
    }

    @Override
    protected void move() {
        //maintain maximum velocity of 1 pixel per frame
        if (vel.getSpeed() > 1.0) {
            vel.x = vel.x / vel.getSpeed();
            vel.y = vel.y / vel.getSpeed();
        }

        //apply decay **ONLY WORKS FOR X AXIS NEED TO APPLY TO TOTAL**
        /*
         if (vel.x < 0.0005 && vel.x > -0.0005) {
         vel.x = 0;
         } else if (vel.x < 0.0) {
         vel.x += VEL_DECAY;
         } else if (vel.x > 0.0) {
         vel.x -= VEL_DECAY;
         }
         */
        
        super.move();
    }

}
