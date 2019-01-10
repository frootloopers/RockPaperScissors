/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foundation;

import Blocks.Pos;
import Entities.Bullet;
import Entities.Drone;
import Entities.Entity;
import Entities.Harvestable;
import Entities.Planet;
import Entities.Ship;
import java.util.ArrayList;

/**
 *
 * @author Jia Jia Chen
 */
public class Map {

    private Entity[] Entities;
    private Harvestable[] Harvestables;
    private ArrayList<Bullet> Bullets;
    private int teams;
    private final static int harvestables = 5;

    public Map(int teams) {
        this.teams = teams;
        Entities = new Entity[teams * 4];
        Harvestables = new Harvestable[harvestables];
        Bullets = new ArrayList<>();

        for (int x = 0; x < Entities.length; x += 4) {
//        Entities[x]=new Ship();
//        Entities[x+1]=new Drone();
//        Entities[x+2]=new Drone();
//        Entities[x+3]=new Planet();
        }
    }

    /**
     * Returns a list of harvestables
     *
     * @param bullet
     */
    public void addBullet(Bullet bullet) {
        Bullets.add(bullet);
    }

    /**
     * Returns a list of harvestables
     *
     * @param team
     * @return
     */
    public Harvestable[] getHarvest(int team) {
        return Harvestables;
    }

    /**
     * Returns a list of all AI objects
     *
     * @return
     */
    public Entity[] getEntities() {
        return Entities;
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
    public ArrayList<Entity> aoe(Pos pos, double range) {
        ArrayList<Entity> temp = new ArrayList<>();
        //find the ones within the range

        for (Entity e : Entities) {
            if (Math.abs(pos.x - (e.getPos().x)) + Math.abs(pos.y - (e.getPos().y)) <= range) {
                temp.add(e);
            }
        }
        return temp;
    }
}
