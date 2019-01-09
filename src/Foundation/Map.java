/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foundation;

import Entities.Bullet;
import Entities.Entity;
import Entities.Harvestable;
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

    public Map(int harvestables) {
        this.teams = teams;
        Entities = new Entity[teams * 4];
        Harvestables = new Harvestable[harvestables];
        Bullets = new ArrayList<>();
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
}
