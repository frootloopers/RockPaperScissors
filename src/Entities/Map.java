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
import java.io.IOException;
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
    private final static int harvestables = 20;
    //list of teams
    private Team[] Teams;
    //practice good modularism
    private int xMax;
    private int yMax;
    //time in ms
    private int time;
    private Random rand = new Random();

    //time for an asteriod to respawn
    private static final int asteroidTick = 600;
    //how far from a corner a planet is offset
    private static final int xPlanet = 40;
    private static final int yPlanet = 40;
    //how far from eachother the other entities are offset
    private static final int offset = 40;
    //max time is equivalent to maxTime*2ms
    public static final int maxTime = 30000;

    /**
     *
     * @param teams # of teams excluding the neutral
     * @param xMax X size of map
     * @param yMax Y size of map
     */
    public Map(int teams, int xMax, int yMax) {
        this.Teams = new Team[teams + 1];
        Controllables = new Controllable[teams * 3];
        Planets = new Planet[teams];
        Harvestables = new Harvestable[harvestables];
        this.xMax = xMax;
        this.yMax = yMax;
        reset();
    }
    
    public void reset() {
        time = 0;

        //initialize bullets here to erase bullets from map after a reset mid-game
        Bullets = new ArrayList<>();

        //spawn harvestables in the map randomly (near the center of the map)
        for (int x = 0; x < harvestables; x++) {
            Harvestables[x] = new Harvestable(100 + rand.nextInt(xMax - 200), 100 + rand.nextInt(yMax - 200), this);
        }
        
        switch (Teams.length - 1) {
            //create entities for teams 3 and 4
            case 4:
                Planets[2] = new Planet(xPlanet, yMax - yPlanet, 3, this);
                Controllables[6] = new Ship((xPlanet + offset), yMax - (yPlanet + offset), 45, 3, this);
                Controllables[7] = new Drone((xPlanet + offset), yMax - yPlanet, 45, 3, this);
                Controllables[8] = new Drone(xPlanet, yMax - (yPlanet + offset), 45, 3, this);
                
                Planets[3] = new Planet(xMax - xPlanet, yMax - yPlanet, 4, this);
                Controllables[9] = new Ship(xMax - (xPlanet + offset), yMax - (yPlanet + offset), 315, 4, this);
                Controllables[10] = new Drone(xMax - (xPlanet + offset), yMax - yPlanet, 315, 4, this);
                Controllables[11] = new Drone(xMax - xPlanet, yMax - (yPlanet + offset), 315, 4, this);
            //create entities for teams 1 and 2, purposely no break here
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
        //Create neutral team for non-aligned entities
        Teams[0] = new Team(0, "Neutral");
        for (int x = 0; x < Teams.length - 1; x++) {
            //create the teams for the teamed entities
            Teams[x + 1] = new Team(Planets[x], (Ship) Controllables[x * 3], (Drone) Controllables[x * 3 + 1], (Drone) Controllables[x * 3 + 2], "Player " + Integer.toString(x + 1), this);
        }
    }

    /**
     * Returns the maximum point of the map (The corner furthest from the
     * origin).
     *
     * @return
     */
    public Point getMax() {
        return new Point(xMax, yMax);
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
     * Returns a list of all teams
     *
     * @return
     */
    protected Team[] getTeams() {
        return Teams;
    }
    
    public void saveTeams() throws IOException {
        Saviour.saveScore(Teams);
    }

    /**
     * Gets the scores of each team
     *
     * @return
     */
    public int[] getScores() {
        int[] scores = new int[Teams.length - 1];
        for (int x = 1; x < Teams.length; x++) {
            scores[x - 1] = Teams[x].getScore();
        }
        return scores;
    }

    /**
     * Gets the names of each team
     *
     * @return
     */
    public String[] getNames() {
        String[] scores = new String[Teams.length - 1];
        for (int x = 1; x < Teams.length; x++) {
            scores[x - 1] = Teams[x].getName();
        }
        return scores;
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
            int x;
            int ang;
            //spawn meteor from right or left side?
            if (rand.nextFloat() >= 0.5) {
                x = 3;
                //nextInt is exclusive for some reason
                ang = rand.nextInt(11) + 85;
            } else {
                x = xMax - 3;
                ang = rand.nextInt(11) + 85 + 180;
            }
            Bullets.add(new Bullet(x, rand.nextInt(yMax - (yPlanet + offset * 3) * 2 + 1) + (yPlanet + offset * 3), 0.5, ang, 0, this));
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
            //if (distance - radius of bullet) <= range of pulse
            if (Math.sqrt(Math.pow(pos.x - (e.getPos().x), 2) + (Math.pow(pos.y - (e.getPos().y), 2))) - e.radius <= range) {
                temp.add(e);
            }
        }
        return temp;
    }

    /**
     * checks and handles all collision in the map (Carl)
     */
    public void collide() {
        for (int i = 0; i < Controllables.length; i++) {
            //  Entities - Entities
            for (int j = i + 1; j < Controllables.length; j++) {
                if (Controllables[i].checkCollision(Controllables[j])) {
                    Controllables[i].collision((Entity)Controllables[j]);
                    Controllables[j].collision((Entity)Controllables[i]);
                    if (Controllables[j] instanceof Drone && Controllables[i] instanceof Ship) {
                        ((Drone) Controllables[j]).collideShip((Ship) Controllables[i]);
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
                        Harvestables[j] = new Harvestable(100 + rand.nextInt(this.getMax().x - 200), 100 + rand.nextInt(this.getMax().y - 200), this);
                    }
                }
            }
            //  Entities - Bullets
            for (int j = 0; j < Bullets.size(); j++) {
//                if (Controllables[i].checkCollision(Bullets.get(j)) && Controllables[i].teamID != Bullets.get(i).teamID) {
                if (Controllables[i].checkCollision(Bullets.get(j))) {
                    if (Controllables[i].collideBullet(Bullets.get(j))) //System.out.println("2");
                    {
                        Bullets.remove(j);
                    }
                } else if ((Bullets.get(j).getPos().getX() - Bullets.get(j).getRadius()
                        <= 0 || Bullets.get(j).getPos().getY() - Bullets.get(j).getRadius() <= 0)
                        || (Bullets.get(j).getPos().getX() + Bullets.get(j).getRadius()
                        >= xMax || Bullets.get(j).getPos().getY() + Bullets.get(j).getRadius() >= yMax)) {
                    //System.out.println("3");
                    Bullets.remove(j);
                }
            }
            
            if (Controllables[i].getPos().getX() - Controllables[i].getRadius()
                    <= 0 || (Controllables[i].getPos().getX() + Controllables[i].getRadius() >= xMax)) {
                Controllables[i].collisionX();
            }
            if (Controllables[i].getPos().getY() - Controllables[i].getRadius()
                    <= 0 || Controllables[i].getPos().getY() + Controllables[i].getRadius() >= yMax) {
                Controllables[i].collisionY();
            }
        }
    }
    
}
