/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Foundation.Map;
import java.awt.Graphics;

/**
 *
 * @author Jia Jia Chen
 */
public class Missile extends Controllable{
    
    protected static final int RADIUS_MISSILE = 5;
    
    public Missile(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_MISSILE, faceAngle, teamID, map);
    }
    
    @Override
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        int drawX = (int) ((getPos().getX() + offsetX) * scale);
        int drawX1 = (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle)) * scale);
        int drawX2 = (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle + 120)) * scale);
        int drawX3 = (int) (drawX + radius * Math.sin(Math.toRadians(faceAngle - 120)) * scale);
        int drawY = (int) ((getPos().getY() + offsetY) * scale);
        int drawY1 = (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle)) * scale);
        int drawY2 = (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle + 120)) * scale);
        int drawY3 = (int) (drawY - radius * Math.cos(Math.toRadians(faceAngle - 120)) * scale);
    }    
}
