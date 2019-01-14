/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blocks;

/**
 * The velocity object is public such that it can be directly modified by any
 * entity that possesses it, but it is never passed onto the user
 *
 * @author John Popovici
 */
public class Vel {

    /**
     * Creates a velocity
     *
     * @param x the velocity value of the entity on the x-axis
     * @param y the velocity value of the entity on the y-axis
     */
    public Vel(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * the velocity value of the entity on the x-value
     */
    public double x;
    /**
     * the velocity value of the entity on the y-axis
     */
    public double y;

    /**
     * Gets the velocity value of the entity on the y-axis
     *
     * @return the velocity value of the entity on the y-axis
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the velocity value of the entity on the y-axis
     *
     * @return the velocity value of the entity on the y-axis
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the total velocity of the entity
     *
     * @return the total velocity of the entity
     */
    public double getSpeed() {
        //the pythagorean theorem to calculate hypotenuse of velocity vectors
        return (Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2)));
    }

    /**
     * Get the angle at which the entity is traveling
     *
     * @return the angle at which the entity is traveling. The angle is in
     * degrees where 0 is vertically upwards and the values increase in a
     * clockwise direction. The angle of movement is not necessarily equal to
     * the angle the entity is facing. Special case if unmoving the angle is 0.0
     */
    public double getAngle() {
        if (x == 0.0 && y == 0.0) { //special case, not moving
            return 0.0;
        } else if (x >= 0.0 && y <= 0.0) { //top right, x positive, y negative
            return (Math.toDegrees(Math.atan(Math.abs(x) / Math.abs(y))));
        } else if (x >= 0.0 && y >= 0.0) { //bottom right, x positive, y positive
            return (180 - Math.toDegrees(Math.atan(Math.abs(x) / Math.abs(y))));
        } else if (x <= 0.0 && y >= 0.0) { //bottom left, x negative, y positive
            return (270 - Math.toDegrees(Math.atan(Math.abs(x) / Math.abs(y))));
        } else { //if (x <= 0.0 && y <= 0.0) { //top left, x negative, y negative
            return (360 - Math.toDegrees(Math.atan(Math.abs(x) / Math.abs(y))));
        }
    }
}
