/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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
     * @param teamID the ID of the team this entity belongs to
     */
    public Controllable(double x, double y, int radius, int teamID) {
        super(x, y, radius, teamID);
    }
    
    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        super.draw(g,scale,offsetX,offsetY);        
        g.drawLine( (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle))),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle+120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle+120))) );
         g.drawLine( (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle))),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle-120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle-120))) );
         g.drawLine( (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle-120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle-120))),
                (int) (getPos().getX() + radius * Math.sin(Math.toRadians(faceAngle+120))),
                (int) (getPos().getY() - radius * Math.cos(Math.toRadians(faceAngle+120))) );

    }
}
