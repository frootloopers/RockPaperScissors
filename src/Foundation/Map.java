/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foundation;

import Entities.Entity;
import Entities.Harvestable;
import java.util.ArrayList;

/**
 *
 * @author Jia Jia Chen
 */
public class Map {

    private Entity[] Entities;
    private ArrayList<Harvestable> Resources;
    private int teams;
    
    public Map(int teams){
        this.teams=teams;
    }
    
    /**
     * Returns a list of entities in a team
     * @param team
     * @return 
     */
    protected Entity[] getTeam(int team){
        return Teams.get(team);
    }
    
    /**
     * Returns a list of all teams
     * @return 
     */
    protected ArrayList<Entity[]> getEntities(){
        return Teams;
    }
    
    protected Entity getClose(){
        
    }
}
