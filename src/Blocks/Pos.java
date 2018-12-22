/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blocks;

/**
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
    private final double x;
    /**
     * the position value of the entity on the y-axis
     */
    private final double y;

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
