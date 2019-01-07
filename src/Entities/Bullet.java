/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author John Popovici
 */
public class Bullet extends Movable {

    /**
     * Creates a bullet entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param teamID the ID of the team this entity belongs to
     */
    public Bullet(double x, double y, int teamID) {
        //bullet hit-box radius of 1
        super(x, y, 1, teamID);
    }
}
