/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.Team;
import Blocks.Pos;
import Entities.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jia Jia Chen
 */
public class Map {

    //All player controllables
    private Controllable[] Controllables;
    //All planets
    private Planet[] Planets;
    //all raw resources
    private Harvestable[] Harvestables;
    //all damaging projectiles
    private ArrayList<Bullet> Bullets;
    //default # of resource nodes that spawn on the map
    private final static int harvestables = 16;
    //list of teams
    private Team[] Teams;
    private int xMax;
    private int yMax;
    private int time;
    private Random rand = new Random();

    private static final int asteroidTick = 1000;
    private static final int xPlanet = 40;
    private static final int yPlanet = 40;
    private static final int offset = 40;
    //max time is equivalent to maxTime*2ms
    public static final int maxTime = 30000;

    public Map(int teams, int xMax, int yMax) {
        time = 0;
        this.Teams = new Team[teams + 1];
        Controllables = new Controllable[teams * 3];
        Planets = new Planet[teams];
        Harvestables = new Harvestable[harvestables];
        Bullets = new ArrayList<>();
        this.xMax = xMax;
        this.yMax = yMax;
        reset();
    }

    public void reset() {
        time = 0;

        for (int x = 0; x < harvestables; x++) {
            Harvestables[x] = new Harvestable(100 + rand.nextInt(xMax - 200), 100 + rand.nextInt(yMax - 200), this);
        }

        switch (Teams.length - 1) {
            case 4:
                Planets[2] = new Planet(xPlanet, yMax - yPlanet, 3, this);
                Controllables[6] = new Ship((xPlanet + offset), yMax - (yPlanet + offset), 45, 3, this);
                Controllables[7] = new Drone((xPlanet + offset), yMax - yPlanet, 45, 3, this);
                Controllables[8] = new Drone(xPlanet, yMax - (yPlanet + offset), 45, 3, this);

                Planets[3] = new Planet(xMax - xPlanet, yMax - yPlanet, 4, this);
                Controllables[9] = new Ship(xMax - (xPlanet + offset), yMax - (yPlanet + offset), 315, 4, this);
                Controllables[10] = new Drone(xMax - (xPlanet + offset), yMax - yPlanet, 315, 4, this);
                Controllables[11] = new Drone(xMax - xPlanet, yMax - (yPlanet + offset), 315, 4, this);
            case 2:
                Planets[0] = new Planet(xPlanet, yPlanet, 1, this);
                Controllables[0] = new Ship(xPlanet + offset, yPlanet + offset, 135, 1, this);
                Controllables[1] = new Drone(xPlanet + offset, yPlanet, 135, 1, this);
                Controllables[2] = new Drone(xPlanet, yPlanet + offset, 135, 1, this);

                Planets[1] = new Planet(xMax - xPlanet, yPlanet, 2, this);
                Controllables[3] = new Ship(xMax - (xPlanet + offset), (yPlanet + offset), 225, 2, this);
                Controllables[4] = new Drone(xMax - (xPlanet + offset), yPlanet, 225, 2, this);
                Controllables[5] = new Drone(xMax - xPlanet, (yPlanet + offset), 225, 2, this);
                break;
            default:
                throw new java.lang.Error("ERROR, UNSUPPORTED TEAM NUMBER");
        }
        for (int x = 0; x < Teams.length - 1; x++) {
            Teams[0] = new Team(0, "Neutral");
            Teams[x + 1] = new Team(Planets[x], (Ship) Controllables[x * 3], (Drone) Controllables[x * 3 + 1], (Drone) Controllables[x * 3 + 2], "Player " + Integer.toString(x + 1));
        }
    }

    public Point getMax() {
        return new Point(xMax, yMax);
    }

    /**
     * Returns the time
     *
     * @return The number of game ticks that have passed since reset.
     */
    public String[] getScore() {
        return null;
    }

    /**
     * Returns the time
     *
     * @return The number of game ticks that have passed since reset.
     */
    public int getTime() {
        return time;
    }

    /**
     * Returns a list of harvestables
     *
     * @return
     */
    public Harvestable[] getHarvest() {
        return Harvestables;
    }

    /**
     * Returns a list of all AI objects
     *
     * @return
     */
    public Controllable[] getControllables() {
        return Controllables;
    }

    /**
     * Returns a list of all AI objects
     *
     * @return
     */
    public Planet[] getPlanets() {
        return Planets;
    }

    /**
     * Returns a list of all bullets
     *
     * @return
     */
    public ArrayList<Bullet> getBullets() {
        return Bullets;
    }

    /**
     * Returns a list of all bullets
     *
     * @return
     */
    public Team[] getTeams() {
        return Teams;
    }

    /**
     * Main game loop
     */
    public void moveAll() {
        //keep time
        time++;

        //move all things that move
        for (Entity e : Controllables) {
            if (e instanceof Movable) {
                ((Movable) e).move();
            }
        }
        for (Movable e : Bullets) {
            e.move();
        }

        //spawn a new meteor if the time is right (time is divisible by asteroidTick without a remainder)
        if (time % asteroidTick == 0) {
            Bullets.add(new Bullet(2, rand.nextInt(xMax - (xPlanet + offset * 3) * 2) + (xPlanet + offset * 3), 0.5, rand.nextInt(20) + 80, 0, this));
        }

        //use Carl's collision detection
        collide();
    }

    /**
     * Returns a list of all bullets within striking range
     *
     * @param pos The position from which to scan (Center point of the entity).
     * @param range The range of the scan.
     * @return
     */
    public ArrayList<Bullet> aoe(Pos pos, double range) {
        ArrayList<Bullet> temp = new ArrayList<>();
        //find targets within the range
        for (Bullet e : Bullets) {
            if (Math.sqrt(Math.pow(pos.x - (e.getPos().x), 2) + (Math.pow(pos.y - (e.getPos().y), 2))) + e.radius <= range) {
                temp.add(e);
            }
        }
        return temp;
    }

    public void collide() {
        for (int i = 0; i < Controllables.length; i++) {
            //  Entities - Entities
            for (int j = i; j < Controllables.length; j++) {
                if (Controllables[i].checkCollision(Controllables[j])) {
                    Controllables[i].collision(Controllables[j]);
                    Controllables[j].collision(Controllables[i]);
                    if (Controllables[i] instanceof Drone && Controllables[j] instanceof Ship) {
                        ((Drone) Controllables[i]).collideShip((Ship) Controllables[j]);
                    }
                }
            }

            for (int j = 0; j < Planets.length; j++) {
                if (Controllables[i].checkCollision(Planets[j])) {
                    Controllables[i].collision(Planets[j]);
                    if (Controllables[i] instanceof Ship) {
                        ((Ship) Controllables[i]).collidePlanet(Planets[j]);
                    }
                }
            }
            //  Entities - Harvestables
            for (int j = 0; j < Harvestables.length; j++) {
                if (Harvestables[j] == null) {
                    continue;
                }
                if (Controllables[i].checkCollision(Harvestables[j])) {
                    if (Controllables[i] instanceof Drone) {
                        ((Drone) Controllables[i]).collideHarvestable(Harvestables[j]);
                        Harvestables[j] = null; //this is temp
                    }
                }
            }
            //  Entities - Bullets
            for (int j = 0; j < Bullets.size(); j++) {
                if (Controllables[i].checkCollision(Bullets.get(j))) {
                    Controllables[i].collideBullet(Bullets.get(j));
                }
                if ((Bullets.get(j).getPos().getX() - Bullets.get(j).getRadius()
                    <= 0 || Bullets.get(j).getPos().getY() - Bullets.get(j).getRadius() <= 0)
                    || (Bullets.get(j).getPos().getX() + Bullets.get(j).getRadius()
                    >= xMax || Bullets.get(j).getPos().getY() + Bullets.get(j).getRadius() >= yMax)) {
                    Bullets.remove(j);
                }
            }
 
            if ((Controllables[i].getPos().getX() - Controllables[i].getRadius()
                    <= 0 || Controllables[i].getPos().getY() - Controllables[i].getRadius() <= 0)
                    || (Controllables[i].getPos().getX() + Controllables[i].getRadius()
                    >= xMax || Controllables[i].getPos().getY() + Controllables[i].getRadius() >= yMax)) {
                Controllables[i].collision();
            }
        }
    }

}
