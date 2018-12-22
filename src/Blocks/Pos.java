/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blocks;

/**
 * The position object is public such that it can be directly modified by any
 * entity that possesses it, but it is never passed onto the user
 *
 * @author John Popovici
 */
public class Pos {

    /**
     * Creates a position
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     */
    public Pos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * the position value of the entity on the x-value
     */
    public final double x;
    /**
     * the position value of the entity on the y-axis
     */
    public final double y;

    /**
     * Gets the position value of the entity on the y-axis
     *
     * @return the position value of the entity on the y-axis
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the position value of the entity on the y-axis
     *
     * @return the position value of the entity on the y-axis
     */
    public double getX() {
        return x;
    }

}
