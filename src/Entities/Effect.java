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
public abstract class Effect {

    int type;
    int xSize;
    int ySize;
    Pos pos;

    public Effect(Pos pos, int xSize, int ySize, int type) {
        this.pos = pos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.type = type;
    }

    public void draw(Graphics g, double scale, int offsetX, int offsetY) {

        int drawX = (int) ((pos.getX() - xSize / 2 + offsetX) * scale);
        int drawY = (int) ((pos.getY() - ySize / 2 + offsetY) * scale);
        int drawSize = (int) (xSize / 2 * 2 * scale);
        g.setColor(Color.MAGENTA);
        g.fillOval(drawX, drawY, drawSize, drawSize);
        g.setColor(Color.WHITE);
        g.drawOval(drawX, drawY, drawSize, drawSize);
    }
}
