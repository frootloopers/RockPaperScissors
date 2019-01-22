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
     * @author John Popovici
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
        vel = new Vel(Math.sin(Math.toRadians(faceAngle)) * speed, Math.cos(Math.toRadians(faceAngle)) * -1 * speed);
        //the faceAngle does not have to be between 0 and 360
        this.faceAngle = faceAngle;
    }

    /**
     * the vel object of this entity
     */
    protected Vel vel;
    /**
     * the angle at which the entity is facing. A value of 0 represents a value
     * horizontally and to the right and the angle is measured in radians
     */
    protected double faceAngle;
    /**
     * whether the entity has moved this frame such that the move method only
     * functions once per frame even if called multiple times
     */
    protected boolean hasMove = true;

    /**
     * Gets the vel object of the entity
     *
     * @return the vel object of the entity
     */
    public Vel getVel() {
        return new Vel(this.vel.getX(), this.vel.getY());
    }

    /**
     * Gets the angle at which the entity is facing
     *
     * @return the angle at which the entity is facing. A value of 0 represents
     * a value vertically upwards and the angle is measured in degrees
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

    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        super.draw(g, scale, offsetX, offsetY);
        int drawX = (int) ((getPos().getX() + offsetX) * scale);
        int drawY = (int) ((getPos().getY() + offsetY) * scale);
        g.drawLine(drawX, drawY,
                (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle)) * scale),
                (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle)) * scale));
    }

    /**
     * Must be called each frame of the game to move the entity
     *
     * @author John Popovici
     */
    public void move() {
        //changes the position according to the vel
        pos.x += vel.x;
        pos.y += vel.y;
        //tells the entity has moved this frame
        hasMove = false;
    }

    protected void collision() {
        if (faceAngle > 180) {
            faceAngle -= 180;
        } else {
            faceAngle += 180;
        }
        vel.x = -vel.x;
        vel.y = -vel.y;
    }

    /**
     * protected int[] rotate(int velX, int velY, double angle) { int[]
     * rotatedVelocities = {(int) (velX * Math.cos(angle) - velY *
     * Math.sin(angle)), (int) (velX * Math.sin(angle) + velY *
     * Math.cos(angle))}; return rotatedVelocities; }
     *
     * //ffs protected void resolveCollision(Movable other) {
     * System.out.println("hi"); // Grab angle between the two colliding thiss
     * int angle = (int) -Math.atan2(other.pos.y - this.pos.y, other.pos.x -
     * this.pos.x);
     *
     * // Store mass in var for better readability in collision equation int m1
     * = this.radius; int m2 = other.radius;
     *
     * // Velocity before equation int[] u1 = rotate((int) this.vel.x, (int)
     * this.vel.y, angle); int[] u2 = rotate((int) other.vel.x, (int)
     * other.vel.x, angle);
     *
     * // Velocity after 1d collision equation int[] v1 = {u1[0] * (m1 - m2) /
     * (m1 + m2) + u2[0] * 2 * m2 / (m1 + m2), u1[1]}; int[] v2 = {u2[0] * (m1 -
     * m2) / (m1 + m2) + u1[0] * 2 * m2 / (m1 + m2), u2[1]};
     *
     * // Final vel after rotating axis back to original location int[] vFinal1
     * = rotate(v1[0], v1[1], -angle); int[] vFinal2 = rotate(v2[0], v2[1],
     * -angle);
     *
     * // Swap this velocities for realistic bounce effect this.vel.x =
     * vFinal1[0]; this.vel.y = vFinal1[1];
     *
     * other.vel.x = vFinal2[0]; other.vel.y = vFinal2[1]; }
     */
    // a VERY bugy collision handeler for more realist collision (carl (i tried)) 
    protected void collision(Movable other) {
        if (vel.y > 0 || vel.x > 0) {
            double difx = pos.x - other.getPos().x;
            double dify = pos.y - other.getPos().y;
            double targetAngle;
            if (difx >= 0.0 && dify <= 0.0) { //top right, x positive, y negative
                targetAngle = (Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
            } else if (difx >= 0.0 && dify >= 0.0) { //bottom right, x positive, y positive
                targetAngle = (180 - Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
            } else if (difx <= 0.0 && dify >= 0.0) { //bottom left, x negative, y positive
                targetAngle = (180 + Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
            } else { //if (x <= 0.0 && y <= 0.0) { //top left, x negative, y negative
                targetAngle = (360 - Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
            }
            targetAngle = (targetAngle + 90) % 180;
            collision(targetAngle);
        }
    }
    // a VERY bugy collision handeler for more realist collision (carl (i tried)) 

    protected void collision(double targetAngle) {
        double oldAngle = Math.toDegrees(Math.atan(Math.abs(vel.x) / Math.abs(vel.y)));
        System.out.println(targetAngle + " " + faceAngle + " " + oldAngle);
        double oldVel = vel.y * vel.y + vel.x * vel.x;
        double difAngle = Math.abs(targetAngle - oldAngle);
        if (targetAngle > oldAngle && 180 - targetAngle < oldAngle) {
            faceAngle = targetAngle + difAngle;
        } else if (180 - targetAngle > oldAngle && 180 + targetAngle < oldAngle) {
            faceAngle = targetAngle - difAngle;
        } else if (180 + targetAngle > oldAngle && 360 - targetAngle > oldAngle) {
            faceAngle = targetAngle - difAngle + 360;
        } else if (360 - targetAngle > oldAngle) {
            faceAngle = 180 - targetAngle - (180 + oldAngle - targetAngle) % 360;
        }
        System.out.println(faceAngle);
        vel.y = Math.sqrt(oldVel) * Math.sin(Math.toRadians(faceAngle));
        vel.x = Math.sqrt(oldVel) * Math.cos(Math.toRadians(faceAngle));
        faceAngle = (450 - faceAngle) % 360;
    }

    public void collisionX() {
        faceAngle = (faceAngle + 360) % 360;
        faceAngle = (360 - faceAngle) % 360;
        vel.x = -vel.x;
    }

    public void collisionY() {
        faceAngle = (faceAngle + 360) % 360;
        faceAngle = (180 - faceAngle + 360) % 360;
        vel.y = -vel.y;
    }

    public void collision(Entity other) {
        /**
         * if (this.checkCollision(other)) { if(other instanceof Movable){
         * collision((Movable)other);} else
         */
        collision();
    }
}
