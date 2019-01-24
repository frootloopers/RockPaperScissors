/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleAI;

import Blocks.Pos;
import Development.AIShell;
import static Development.Command.chase;
import static Development.Command.circle;
import static Development.Command.distance;
import static Development.Command.getTo;
import static Development.Command.turnTo;
import static Development.Command.willCollide;
import RazeSource.Bullet;
import RazeSource.Controllable;
import RazeSource.Drone;
import RazeSource.Harvestable;
import RazeSource.Map;
import RazeSource.Planet;
import RazeSource.Ship;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author John Popovici, Jia Jia Chen
 */
public class FSHunterKillerAI extends AIShell {

    public FSHunterKillerAI() {
        name = "Hunter-Killer";
        author = "Foresight Software Developers";
        desc = "A very annoying version of ForesightAI modified with a Ship that pulses at nearby enemies with resources.";
    }

    //name entities
    private Drone r;
    private Drone l;
    private Ship s;
    private Controllable[] cs;
    private Planet p;
    private Map m;
    private Pos parkS;
    private int timeFire = 0;
    private int endTime;
    //dictates if drones are harvesting
    private boolean[] harvestingRandom = {true, false, false};
    private Pos[] toPos = new Pos[3];
    private int[] timeChase = {0, 0, 0};
    //changeable variables
    private final int COLLIDE_TOLERANCE = 10;
    private final int SHIP_MAX = 200;
    private final int DRONE_MAX = 35;
    private final int FIRE_DELAY = 100;
    private final int DRONE_GIVEUP = 250;

    private final int INIT_DELAY = 100;

    private Random rand = new Random();

    @Override
    public void act() {
        //commands each entity
        for (int i = 0; i < 3; i++) {
            //reset all thrusters
            cs[i].setThrustF(0);
            cs[i].setThrustRotL(0);
            cs[i].setThrustRotR(0);
            if (m.getTime() >= endTime) {
                endGame();
            } else if (i == 0) {
                playGameShip();
            } else {
                if (!(i == 2 && m.getTime() < INIT_DELAY)) {
                    playGameDrone(cs[i], i);
                }
            }
        }
    }

    private void playGameShip() {
        //shield if about to be hit
        ArrayList<Bullet> Bullets = m.getBulletsData();
        for (int i = 0; i < Bullets.size(); i++) {
            if (distance(s.getPos(), Bullets.get(i).getPos()) <= (s.getRadius() + Bullets.get(i).getRadius() + Bullets.get(i).getVel().getSpeed())) {
                s.shield();
            }
        }
        if (s.getStorage() >= SHIP_MAX) {
            getTo(s, p.getPos(), 0.5);
        } else if (s.getStorage() > s.PULSECOST) {
            Controllable target = getTarget();
            if (getTo(s, target.getPos(), s.getRadius() + s.PULSERANGE + target.getRadius())) {
                if (distance(s.getPos(), target.getPos()) <= (s.getRadius() + s.PULSERANGE) && target.getPos().x != s.getPos().x && target.getPos().y != s.getPos().y) {
                    s.pulse();
                }
            }
        } else {
            s.setThrustF(0);
            s.setThrustRotL(0);
            s.setThrustRotR(0);
        }
    }

    private Controllable getTarget() {
        double shortest = 10000;
        Controllable temp = s;
        Controllable[] Controllables = m.getControllablesData();
        for (int i = 0; i < Controllables.length; i++) {
            if (Controllables[i].getTeamID() != s.getTeamID() && Controllables[i].getStorage() > 0) {
                if (distance(s.getPos(), Controllables[i].getPos()) < shortest) {
                    shortest = distance(s.getPos(), Controllables[i].getPos());
                    temp = Controllables[i];
                }
            }
        }
        return temp;
    }

    private void fireShip() {
        //for every entity if still, turn to and fire
        Controllable[] Controllables = m.getControllablesData();
        for (int i = 0; i < Controllables.length; i++) {
            if (Controllables[i].getVel().getSpeed() == 0 && turnTo(s, Controllables[i].getPos(), 0.5)
                    && (m.getTime() - timeFire) >= FIRE_DELAY) {
                s.fireBullet();
                timeFire = m.getTime();
            }
        }
    }

    private void playGameDrone(Controllable c, int k) {
        Harvestable[] Harvestables = m.getHarvestData();
        if (!harvestingRandom[k] && cs[k].getStorage() < DRONE_MAX) {
            double shortest = 10000;
            for (int i = 0; i < Harvestables.length; i++) {
                if (distance(cs[k].getPos(), Harvestables[i].getPos()) < shortest) {
                    shortest = distance(cs[k].getPos(), Harvestables[i].getPos());
                    toPos[k] = Harvestables[i].getPos();
                }
            }
            timeChase[k] = m.getTime();
        }
        if (!harvestingRandom[k] && m.getTime() - timeChase[k] > DRONE_GIVEUP) {
            Random rand = new Random();
            int randomNum = rand.nextInt((Harvestables.length));
            toPos[k] = Harvestables[randomNum].getPos();
            harvestingRandom[k] = true;
        }
        if (getTo(cs[k], toPos[k], 3)) {
            harvestingRandom[k] = false;
        }
        if (cs[k].getStorage() >= DRONE_MAX) {
            chase(cs[k], s, 50);
        }
    }

    private void avoidBeHit(Controllable c) {
        boolean thrustF = c.getThrustF() > 50;
        boolean thrustRot = c.getThrustRotL() > 50 || c.getThrustRotR() > 50;
        //if moving and rotating do nothing
        //if moving and not rotaing, stop moving
        //if not moving, move and rotate in a random direction
        if (thrustF && thrustRot) {
        } else if (thrustF && !thrustRot) {
            c.setThrustF(0);
        } else if (!thrustF) {
            Random rand = new Random();
            circle(c, 100, rand.nextBoolean());
        }

    }

    private boolean willBeHit(Controllable c, int k) {
        try {
            ArrayList<Bullet> Bullets = m.getBulletsData();
            for (int i = 0; i < Bullets.size(); i++) {
                //checks if will collide in the future
                if (willCollide(c, Bullets.get(i), COLLIDE_TOLERANCE)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void endGame() {
        getTo(s, p.getPos(), 0.5);
        chase(r, s, 50);
        chase(l, s, 50);
    }

    @Override
    public void setUnits(Planet planet, Ship ship, Drone drone1, Drone drone2) {
        this.p = planet;
        this.s = ship;
        this.r = drone1;
        this.l = drone2;
        m = planet.getMap();
        cs = new Controllable[]{ship, drone1, drone2};
        parkS = ship.getPos();
        endTime = m.getMaxTime() - 600;
    }

}
