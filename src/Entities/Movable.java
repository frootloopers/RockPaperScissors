/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Blocks.Vel;
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
     * @param teamID the ID of the team this entity belongs to
     */
    public Movable(double x, double y, int radius, int teamID) {
        super(x, y, radius, teamID);
        vel = new Vel(0.0, 0.0);
        faceAngle = 90;
    }

    /**
     * the velocity object of this entity
     */
    private Vel vel;
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
        return faceAngle;
    }

    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        g.drawOval((int) (getPos().getX()) - radius, (int) (getPos().getY()) - radius, radius * 2, radius * 2);
        g.drawLine((int) (getPos().getX()), (int) (getPos().getY()),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle))));
    }
}
