/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.awt.Graphics;

/**
 * Not used.
 * 
 * @author Jia Jia Chen
 */
public class Missile extends Controllable {

    protected static final int RADIUS_MISSILE = 5;

    public Missile(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_MISSILE, faceAngle, 1.1, teamID, map);
    }

    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        super.draw(g, scale, offsetX, offsetY);
    }
}
