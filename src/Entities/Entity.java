/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Blocks.Pos;
import Foundation.Map;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author John Popovici
 */
public abstract class Entity {

    protected static final int RADIUS_PLANET = 20;
    protected static final int RADIUS_HARVESTABLE = 5;
    protected static final int RADIUS_PROJECTILE = 2;
    protected static final int RADIUS_SHIP = 15;
    protected static final int RADIUS_DRONE = 5;

    protected static final double VEL_DECAY = 0.005;
    protected static final double DRONE_ROT_STRENGTH = 0.05;
    protected static final double SHIP_ROT_STRENGTH = 0.025;
    protected static final double DRONE_STERN_STRENGTH = 0.05;
    protected static final double SHIP_STERN_STRENGTH = 0.025;

    /**
     * Creates an entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param radius the radius of the hit-box of the entity
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Entity(double x, double y, int radius, int teamID, Map map) {
        pos = new Pos(x, y);
        this.teamID = teamID;
        this.radius = radius;
        this.map = map;
    }

    /*
     * The map object the entity is in
     */
    protected final Map map;
    /**
     * the position object of this entity
     */
    protected Pos pos;
    /**
     * the ID of the team this entity belongs to
     */
    protected final int teamID;

    /**
     * the radius of the hit-box of the entity
     */
    protected final int radius;

    /**
     * Gets the position object of the entity
     *
     * @return the position object of the entity
     */
    public Pos getPos() {
        return new Pos(this.pos.getX(), this.pos.getY());
    }

    /**
     * Gets the ID of the team the entity belongs to
     *
     * @return the ID of the team the entity belongs to
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * Gets the radius of the hit-box of the entity
     *
     * @return the radius of the hit-box of the entity
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Gets the map the entity is in
     *
     * @return the map the entity is in
     */
    public Map getMap() {
        return map;
    }

    /**
     * Draw the icon representing the entity
     *
     * @param g the graphics used to draw the entity
     * @param scale the scale of the game board, at which the entity is drawn
     * @param offsetX the offset of the x-value of the game board, at which the
     * entity is drawn
     * @param offsetY the offset of the y-value of the game board, at which the
     * entity is drawn
     */
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        //BufferedImage pos.X+offsetX, pos.Y+offsetY
        /**
         * switch(teamID){ case(1): g.setColor(Color.RED); break; case(2):
         * g.setColor(Color.BLUE); break; case(3): g.setColor(Color.GREEN);
         * break; case(4): g.setColor(Color.YELLOW); break; default:
         * g.setColor(Color.LIGHT_GRAY); break; }
         */
        g.fillOval((int) (getPos().getX()) - radius, (int) (getPos().getY()) - radius, radius * 2, radius * 2);
        g.setColor(Color.black);
        g.drawOval((int) (getPos().getX()) - radius, (int) (getPos().getY()) - radius, radius * 2, radius * 2);
        //color = map.getTeams()[teamID].getColor
    }

}
