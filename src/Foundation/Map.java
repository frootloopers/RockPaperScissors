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
    private final static int harvestables = 16;
    //list of teams
    private Team[] Teams;
    private int xMax;
    private int yMax;
    private Random rand = new Random();

    private static final int xPlanet = 20;
    private static final int yPlanet = 20;
    private static final int offset = 40;

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
        for (int x = 0; x < harvestables; x++) {
            newRock(Harvestables[x]);
        }

        switch (Teams.length) {
            case 2:
                Planets[0] = new Planet(xPlanet, yPlanet, 1, this);
                Controllables[0] = new Ship(xPlanet + offset, yPlanet + offset, 135, 1, this);
                Controllables[1] = new Drone(xPlanet + offset, yPlanet, 135, 1, this);
                Controllables[2] = new Drone(xPlanet, yPlanet + offset, 135, 1, this);

                Planets[1] = new Planet(xMax - xPlanet, yPlanet, 3, this);
                Controllables[3] = new Ship(xMax - (xPlanet + offset), (yPlanet + offset), 225, 1, this);
                Controllables[4] = new Drone(xMax - (xPlanet + offset), yPlanet, 225, 1, this);
                Controllables[5] = new Drone(xMax - xPlanet, (yPlanet + offset), 225, 1, this);
                break;

            case 4:
                Planets[0] = new Planet(xPlanet, yPlanet, 1, this);
                Controllables[0] = new Ship(xPlanet + offset, (yPlanet + offset), 135, 1, this);
                Controllables[1] = new Drone(xPlanet + offset, yPlanet, 135, 1, this);
                Controllables[2] = new Drone(xPlanet, (yPlanet + offset), 135, 1, this);

                Planets[1] = new Planet(xMax - xPlanet, yPlanet, 3, this);
                Controllables[3] = new Ship(xMax - (xPlanet + offset), (yPlanet + offset), 225, 1, this);
                Controllables[4] = new Drone(xMax - (xPlanet + offset), yPlanet, 225, 1, this);
                Controllables[5] = new Drone(xMax - xPlanet, (yPlanet + offset), 225, 1, this);

                Planets[2] = new Planet(xPlanet, yMax - yPlanet, 2, this);
                Controllables[6] = new Ship((xPlanet + offset), yMax - (yPlanet + offset), 45, 1, this);
                Controllables[7] = new Drone((xPlanet + offset), yMax - yPlanet, 45, 1, this);
                Controllables[8] = new Drone(xPlanet, yMax - (yPlanet + offset), 45, 1, this);

                Planets[3] = new Planet(xMax - xPlanet, yMax - yPlanet, 4, this);
                Controllables[9] = new Ship(xMax - (xPlanet + offset), yMax - (yPlanet + offset), 315, 1, this);
                Controllables[10] = new Drone(xMax - (xPlanet + offset), yMax - yPlanet, 315, 1, this);
                Controllables[11] = new Drone(xMax - xPlanet, yMax - (yPlanet + offset), 315, 1, this);
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
        for (Entity e : Controllables) {
            if (Math.sqrt(Math.pow(pos.x - (e.getPos().x), 2) + (Math.pow(pos.y - (e.getPos().y), 2))) <= range) {
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
                    if (Controllables[i] instanceof Drone && Controllables[j] instanceof Ship)
                            ;
                }
            }

            for (int j = i; j < Planets.length; j++) {
                if (Controllables[i].checkCollision(Planets[j])) {
                    Controllables[i].collision(Planets[j]);
                    if (Controllables[i] instanceof Ship)
                            ;
                }
            }
            //  Entities - Harvestables
            for (int j = i; j < Harvestables.length; j++) {
                if (Controllables[i].checkCollision(Harvestables[j])) {
                    if (Controllables[i] instanceof Drone) {
                        ((Drone) Controllables[i]).collideHarvestable(Harvestables[j]);
                        Harvestables[j] = null; //this is temp
                    }
                }
            }
            //  Entities - Bullets
            for (int j = i; j < Bullets.size(); j++) {
                if (Controllables[i].checkCollision(Bullets.get(j))) {
                    Controllables[i].collideBullet(Bullets.get(j));
                }
            }
        }
        //  Bullets - Harvestables        
    }

}
