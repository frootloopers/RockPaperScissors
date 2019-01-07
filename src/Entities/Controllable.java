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
public abstract class Controllable extends Movable {

    /**
     * Creates a controllable entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param radius the radius of the hit-box of the entity
     * @param teamID the ID of the team this entity belongs to
     */
    public Controllable(double x, double y, int radius, int teamID) {
        super(x, y, radius, teamID);
    }
}
