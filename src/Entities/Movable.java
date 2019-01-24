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
        //adds a linr to show where the Movable is facing
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

    private void collision() {

        /**
         * double oldVel = vel.y * vel.y + vel.x * vel.x; if(oldVel > 0.001){
         * double oldAngle = Math.toDegrees(Math.atan(Math.abs(vel.x) /
         * Math.abs(vel.y))); faceAngle = (180 - oldAngle + 360) % 360;
         *
         * vel.y = Math.sqrt(oldVel) * Math.sin(Math.toRadians(faceAngle));
         * vel.x = Math.sqrt(oldVel) * Math.cos(Math.toRadians(faceAngle));}
         */
        //faceAngle = (450 - faceAngle) % 360;
    }

    // a VERY bugy collision handeler for more realist collision (carl (i tried)) 
    //finds the acute complementary angle of the two movables
    private void collision(Movable other) {
        if ((vel.y > 0.01 || vel.x > 0.01) && (other.vel.y > 0.01 || other.vel.x > 0.01)) {
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
            double oldAngle = Math.toDegrees(Math.atan(Math.abs(vel.x) / Math.abs(vel.y)));
            double otherAngle = Math.toDegrees(Math.atan(Math.abs(other.vel.x) / Math.abs(other.vel.y)));
            System.out.println(targetAngle + " " + faceAngle + " " + oldAngle);
            double oldVel = Math.sqrt(vel.y * vel.y + vel.x * vel.x);
            double otherVel = Math.sqrt(other.vel.y * other.vel.y + other.vel.x * other.vel.x);
            vel.x = otherVel * Math.cos(Math.toRadians(otherAngle - targetAngle)) * Math.cos(Math.toRadians(targetAngle))
                    + oldVel * Math.sin(Math.toRadians(otherAngle - targetAngle)) * Math.sin(Math.toRadians(targetAngle));
            vel.y = otherVel * Math.cos(Math.toRadians(otherAngle - targetAngle)) * Math.sin(Math.toRadians(targetAngle))
                    + oldVel * Math.sin(Math.toRadians(otherAngle - targetAngle)) * Math.cos(Math.toRadians(targetAngle));
            other.vel.x = oldVel * Math.cos(Math.toRadians(oldAngle - (targetAngle + 180) % 360)) * Math.cos(Math.toRadians((targetAngle + 180) % 360))
                    + otherVel * Math.sin(Math.toRadians(oldAngle - (targetAngle + 180) % 360)) * Math.sin(Math.toRadians((targetAngle + 180) % 360));
            other.vel.y = oldVel * Math.cos(Math.toRadians(oldAngle - (targetAngle + 180) % 360)) * Math.sin(Math.toRadians((targetAngle + 180) % 360))
                    + otherVel * Math.sin(Math.toRadians(oldAngle - (targetAngle + 180) % 360)) * Math.cos(Math.toRadians((targetAngle + 180) % 360));
            other.faceAngle = Math.toDegrees(Math.atan(Math.abs(other.vel.x) / Math.abs(other.vel.y)));
            faceAngle = Math.toDegrees(Math.atan(Math.abs(vel.x) / Math.abs(vel.y)));
            System.out.println(faceAngle);
            //collision(targetAngle);
        }
    }

    // a VERY bugy collision handeler for more realist collision (carl (i tried)) 
    //tries to simlate a ball to wall collision with acute complementary angle of the two movables
    private void collision(double targetAngle) {
        double oldAngle = Math.toDegrees(Math.atan(Math.abs(vel.x) / Math.abs(vel.y)));
        System.out.println(targetAngle + " " + faceAngle + " " + oldAngle);
        double oldVel = vel.y * vel.y + vel.x * vel.x;
        double difAngle = Math.abs(targetAngle - oldAngle);
        /**
         * if (targetAngle > oldAngle && 180 - targetAngle < oldAngle) {
         * faceAngle = targetAngle + difAngle;
         * } else if (180 - targetAngle > oldAngle && 180 + targetAngle < oldAngle) {
         * faceAngle = targetAngle - difAngle;
         * } else if (180 + targetAngle > oldAngle && 360 - targetAngle >
         * oldAngle) { faceAngle = targetAngle - difAngle + 360; } else if (360
         * - targetAngle > oldAngle) { faceAngle = 180 - targetAngle - (180 +
         * oldAngle - targetAngle) % 360; }
         */
        if (targetAngle < oldAngle && targetAngle + 180 > oldAngle) {
            System.out.println(faceAngle);
        }
        vel.y = Math.sqrt(oldVel) * Math.sin(Math.toRadians(faceAngle));
        vel.x = Math.sqrt(oldVel) * Math.cos(Math.toRadians(faceAngle));
        faceAngle = (450 - faceAngle) % 360;
    }

    /* wall collisions on the x axis
    *  (Carl) 
     */
    protected void collisionX() {
        faceAngle = (faceAngle + 360) % 360;
        faceAngle = (-faceAngle + 360) % 360;
        vel.x = -vel.x;
    }

    /* wall collisions on the y axis
    *  (Carl) 
     */
    protected void collisionY() {
        faceAngle = (faceAngle + 360) % 360;
        faceAngle = (180 - faceAngle + 360) % 360;
        vel.y = -vel.y;
    }

    protected void collision(Entity other) {

//          uncomment this for the bugged collision version 
//        if (this.checkCollision(other)) {
//            if (other instanceof Movable) {
//                collision((Movable) other);
//            } else {
        if (!(this instanceof Ship && other instanceof Drone)) {
            if (faceAngle > 180) {
                faceAngle -= 180;
            } else {
                faceAngle += 180;
            }
        }
        vel.x = -vel.x;
        vel.y = -vel.y;
//            }
//        }
    }
}
