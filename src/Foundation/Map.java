/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foundation;

import Blocks.Pos;
import Entities.*;
import java.util.ArrayList;

/**
 *
 * @author Jia Jia Chen
 */
public class Map {

    private Entity[] Entities;
    private Harvestable[] Harvestables;
    private ArrayList<Bullet> Bullets;
    private final static int harvestables = 5;
    public Team[] Teams;

    public Map(int teams) {
        this.Teams = new Team[teams];
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
     * Add a bullet to the map
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
     * Returns a list of all entities within a striking range
     *
     * @return
     */
    public ArrayList<Entity> aoe(Pos pos, double range) {
        ArrayList<Entity> temp = new ArrayList<>();
        //find targets within the range
        for (Entity e : Entities) {
            if (Math.sqrt(Math.pow(pos.x - (e.getPos().x),2) + (Math.pow(pos.y - (e.getPos().y),2)))<= range) {
                temp.add(e);
            }
        }
        return temp;
    }
    
    public void collide(){
        //  Entities - Entities
        for(int i =0; i<Entities.length; i++){
            for(int j = i; j<Entities.length; j++)
                if(Entities[i].checkCollision(Entities[j])){
                    if(Entities[i] instanceof Movable)
                        ((Movable)Entities[i]).collision();
                    if(Entities[j] instanceof Movable)
                        ((Movable)Entities[j]).collision();
                }
      //  Entities - Harvestables
      
            for(int j = i; j<Harvestables.length; j++)
                if(Entities[i].checkCollision(Harvestables[j])){
                    if(Entities[i] instanceof Drone)
 //                       ((Drone)Entities[i]).collideHarvestable(Harvestables[j]);
                        Harvestables[j] = null; //this is temp
                        }
      //  Entities - Bullets
      //  Bullets - Harvestables
    }
    }
}
