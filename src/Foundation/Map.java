/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foundation;

import Blocks.Pos;
import Entities.*;
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
    private final static int harvestables = 5;
    //list of teams
    private Team[] Teams;
    private int xMax;
    private int yMax;
    private Random rand = new Random();

    private static final int xPlanet = 20;
    private static final int yPlanet = 20;

    public Map(int teams, int xMax, int yMax) {
        this.Teams = new Team[teams];
        Controllables = new Controllable[teams * 3];
        Planets = new Planet[teams];
        Harvestables = new Harvestable[harvestables];
        Bullets = new ArrayList<>();
        this.xMax = xMax;
        this.yMax = yMax;
        reset();
    }

    public void reset() {
        switch (Teams.length) {
            case 2:
                Planets[0] = new Planet(xPlanet, yPlanet, 1, this);
                Controllables[1] = new Ship(xPlanet + 10, yPlanet + 10, 135, 1, this);
                Controllables[2] = new Drone(xPlanet + 10, yPlanet, 135, 1, this);
                Controllables[3] = new Drone(xPlanet, yPlanet + 10, 135, 1, this);

                Planets[4] = new Planet(xMax - xPlanet, yPlanet, 3, this);
                Controllables[5] = new Ship(xMax - (xPlanet + 10), (yPlanet + 10), 225, 1, this);
                Controllables[6] = new Drone(xMax - (xPlanet + 10), yPlanet, 225, 1, this);
                Controllables[7] = new Drone(xMax - xPlanet, (yPlanet + 10), 225, 1, this);
                break;

            case 4:
                Planets[0] = new Planet(xPlanet, yPlanet, 1, this);
                Controllables[1] = new Ship(xPlanet + 10, (yPlanet + 10), 135, 1, this);
                Controllables[2] = new Drone(xPlanet + 10, yPlanet, 135, 1, this);
                Controllables[3] = new Drone(xPlanet, (yPlanet + 10), 135, 1, this);

                Planets[4] = new Planet(xMax - xPlanet, yPlanet, 3, this);
                Controllables[5] = new Ship(xMax - (xPlanet + 10), (yPlanet + 10), 225, 1, this);
                Controllables[6] = new Drone(xMax - (xPlanet + 10), yPlanet, 225, 1, this);
                Controllables[7] = new Drone(xMax - xPlanet, (yPlanet + 10), 225, 1, this);

                Planets[8] = new Planet(xPlanet, yMax - yPlanet, 2, this);
                Controllables[9] = new Ship((xPlanet + 10), yMax - (yPlanet + 10), 45, 1, this);
                Controllables[10] = new Drone((xPlanet + 10), yMax - yPlanet, 45, 1, this);
                Controllables[11] = new Drone(xPlanet, yMax - (yPlanet + 10), 45, 1, this);

                Planets[12] = new Planet(xMax - xPlanet, yMax - yPlanet, 4, this);
                Controllables[13] = new Ship(xMax - (xPlanet + 10), yMax - (yPlanet + 10), 315, 1, this);
                Controllables[14] = new Drone(xMax - (xPlanet + 10), yMax - yPlanet, 315, 1, this);
                Controllables[15] = new Drone(xMax - xPlanet, yMax - (yPlanet + 10), 315, 1, this);
                break;

            default:
                throw new java.lang.Error("ERROR, UNSUPPORTED TEAM NUMBER");
        }
    }

    /**
     * Put the rock somewhere else
     *
     * @param h The rock you want to reset.
     */
    public void newRock(Harvestable h) {
        h = new Harvestable(rand.nextInt(xMax), rand.nextInt(yMax), this);
    }

    /**
     * Add a bullet to the map
     *
     * @param bullet What bullet to add
     */
    public void addBullet(Bullet bullet) {
        Bullets.add(bullet);
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
     * Makes all entities act.
     */
    public void moveAll() {
        for (Entity e : Controllables) {
            if (e instanceof Movable) {
                ((Movable) e).move();
            }
        }
        for (Movable e : Bullets) {
            e.move();
        }
    }

    /**
     * Returns a list of all entities within striking range
     *
     * @param pos The position from which to scan (Center point of the entity).
     * @param range The range of the scan.
     * @return
     */
    public ArrayList<Entity> aoe(Pos pos, double range) {
        ArrayList<Entity> temp = new ArrayList<>();
        //find targets within the range
        for (Entity e : Entities) {
            if (Math.sqrt(Math.pow(pos.x - (e.getPos().x), 2) + (Math.pow(pos.y - (e.getPos().y), 2))) <= range) {
                temp.add(e);
            }
        }
        return temp;
    }

    public void collide() {
        //  Entities - Entities
        for (int i = 0; i < Entities.length; i++) {
            for (int j = i; j < Entities.length; j++) {
                if (Entities[i].checkCollision(Entities[j])) {
                    if (Entities[i] instanceof Movable) {
                        ((Movable) Entities[i]).collision();
                    }
                    if (Entities[j] instanceof Movable) {
                        ((Movable) Entities[j]).collision();
                    }
                }
            }
            //  Entities - Harvestables

            for (int j = i; j < Harvestables.length; j++) {
                if (Entities[i].checkCollision(Harvestables[j])) {
                    if (Entities[i] instanceof Drone) //                       ((Drone)Entities[i]).collideHarvestable(Harvestables[j]);
                    {
                        Harvestables[j] = null; //this is temp
                    }
                }
            }
            //  Entities - Bullets
            //  Bullets - Harvestables
        }
    }
}
