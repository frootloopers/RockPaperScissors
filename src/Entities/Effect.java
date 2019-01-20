/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Blocks.Pos;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Jia Jia Chen
 */
public class Effect extends Movable {

    int type;
    int tick;

    public Effect(double x, double y, int radius, double speed, double faceAngle, int teamID, Map map, int type, int tick) {
        super(x, y, radius, speed, faceAngle, teamID, map);
        this.type = type;
        this.tick = tick;
    }

    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        int drawX = (int) ((pos.getX() - radius / 2 + offsetX) * scale);
        int drawY = (int) ((pos.getY() - radius / 2 + offsetY) * scale);
        int drawSize = (int) (radius / 2 * 2 * scale);
        g.setColor(Color.MAGENTA);
        g.fillOval(drawX, drawY, drawSize, drawSize);
        g.setColor(Color.WHITE);
        g.drawOval(drawX, drawY, drawSize, drawSize);
    }
}
