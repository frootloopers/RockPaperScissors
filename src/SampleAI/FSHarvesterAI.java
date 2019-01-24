/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleAI;

import Blocks.Pos;
import Development.AIShell;
import static Development.Command.*;
import Entities.*;
import java.util.Random;

/**
 * A demo AI Algorithm created by Foresight Software
 *
 * @author John Popovici
 */
public class FSHarvesterAI extends AIShell {

    public FSHarvesterAI() {
        name = "FSHarvesterAI";
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
    private final int SHIP_MAX = 40;
    private final int DRONE_MAX = 5;
    private final int FIRE_DELAY = 100;
    private final int DRONE_GIVEUP = 500;

    @Override
    public void act() {
        //commands each entity
        for (int i = 0; i < 3; i++) {
            //reset all thrusters
            cs[i].setThrustF(0);
            cs[i].setThrustRotL(0);
            cs[i].setThrustRotR(0);
//            if (willBeHit(cs[i], i)) {
//                avoidBeHit(cs[i]);
//            } else
            if (m.getTime() >= endTime) {
                endGame();
            } else if (i == 0) {
                playGameShip();
            } else {
                playGameDrone(cs[i], i);
            }
        }
    }

    private void playGameShip() {
        //pulse if about to be hit
        for (int i = 0; i < m.getBulletsData().size(); i++) {
            if (distance(s.getPos(), m.getBulletsData().get(i).getPos()) <= 30 && s.getStorage() > 0) {
                s.pulse();
            }
        }
        if (s.getStorage() >= SHIP_MAX) {
            getTo(s, p.getPos(), 0.5);
        } else {
            if (getTo(s, parkS, 10)) {
                //fireShip();
            }
        }
    }

    private void fireShip() {
        //for every entity if still, turn to and fire
        for (int i = 0; i < m.getControllablesData().length; i++) {
            if (m.getControllablesData()[i].getVel().getSpeed() == 0 && turnTo(s, m.getControllablesData()[i].getPos(), 0.5)
                    && (m.getTime() - timeFire) >= FIRE_DELAY) {
                s.fireBullet();
                timeFire = m.getTime();
            }
        }
    }

    private void playGameDrone(Controllable c, int k) {
        if (!harvestingRandom[k] && cs[k].getStorage() < DRONE_MAX) {
            double shortest = 10000;
            for (int i = 0; i < m.getHarvestData().length; i++) {
                if (distance(cs[k].getPos(), m.getHarvestData()[i].getPos()) < shortest) {
                    shortest = distance(cs[k].getPos(), m.getHarvestData()[i].getPos());
                    toPos[k] = m.getHarvestData()[i].getPos();
                }
            }
            timeChase[k] = m.getTime();
        }
        if (!harvestingRandom[k] && m.getTime() - timeChase[k] > DRONE_GIVEUP) {
            Random rand = new Random();
            int randomNum = rand.nextInt((m.getHarvestData().length));
            toPos[k] = m.getHarvestData()[randomNum].getPos();
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
        if (m.getBulletsData().size() > 0) {
            for (int i = 0; i < m.getBulletsData().size(); i++) {
                //checks if will collide in the future
                if (willCollide(c, m.getBulletsData().get(i), COLLIDE_TOLERANCE)) {
                    return true;
                }
            }

            return false;
        }
        return false;
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

    @Override
    public String getDesc() {
        return "A Simple Straightforward Sample AI Algorithm";
    }

    @Override
    public String getName() {
        return "FSHarvesterAI";
    }

    @Override
    public String getAuthor() {
        return "Foresight Software Developers";
    }

}
