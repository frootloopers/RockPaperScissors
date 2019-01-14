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

    //All player-owned entities
    private Entity[] Entities;
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
    private Random rand=new Random();

    public Map(int teams, int xMax, int yMax) {
        this.Teams = new Team[teams];
        Entities = new Entity[teams * 4];
        Harvestables = new Harvestable[harvestables];
        Bullets = new ArrayList<>();
        this.xMax=xMax;
        this.yMax=yMax;
    }

    public void reset() {
        switch (Teams.length) {
            case 2:
                Entities[0]=new Planet(20,20,1,this);
                Entities[1]=new Ship(30,30,135,1,this);
                Entities[2]=new Drone(30,20,135,1,this);
                Entities[3]=new Drone(20,30,135,1,this);
                
                Entities[4]=new Planet(xMax-20,20,3,this);
                Entities[5]=new Ship(xMax-30,30,225,1,this);
                Entities[6]=new Drone(xMax-30,20,225,1,this);
                Entities[7]=new Drone(xMax-20,30,225,1,this);
                break;
            case 4:
                Entities[0]=new Planet(20,20,1,this);
                Entities[1]=new Ship(30,30,135,1,this);
                Entities[2]=new Drone(30,20,135,1,this);
                Entities[3]=new Drone(20,30,135,1,this);
                
                Entities[4]=new Planet(xMax-20,20,3,this);
                Entities[5]=new Ship(xMax-30,30,225,1,this);
                Entities[6]=new Drone(xMax-30,20,225,1,this);
                Entities[7]=new Drone(xMax-20,30,225,1,this);
                
                Entities[8]=new Planet(20,yMax-20,2,this);
                Entities[9]=new Ship(30,yMax-30,45,1,this);
                Entities[10]=new Drone(30,yMax-20,45,1,this);
                Entities[11]=new Drone(20,yMax-30,45,1,this);
                
                Entities[12]=new Planet(xMax-20,yMax-20,4,this);
                Entities[13]=new Ship(xMax-30,yMax-30,315,1,this);
                Entities[14]=new Drone(xMax-30,yMax-20,315,1,this);
                Entities[15]=new Drone(xMax-20,yMax-30,315,1,this);
                break;
            default:
                throw new java.lang.Error("ERROR");
        }
    }

    /**
     * Put the rock somewhere else
     * 
     * @param h The rock you want to reset.
     */
    public void newRock(Harvestable h) {
        h=new Harvestable(rand.nextInt(xMax),rand.nextInt(yMax),this);
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
    public Team[] getTeams() {
        return Teams;
    }

    /**
     * Makes all entities act.
     */
    public void moveAll() {
        for (Entity e : Entities) {
            if(e instanceof Movable){
            ((Movable)e).move();
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
    
    public void collide(){
        for(int i =0; i<Entities.length; i++)
            for(int j = i; j<Entities.length; j++)
                if(Entities[i].checkCollision(Entities[j]))
                    if(Entities[i] isInstancef Moveable)
                        //Entities[i]
      //  Entities - Harvestables
      //  Entities - Bullets
      //  Bullets - Harvestables
    }
    
}
