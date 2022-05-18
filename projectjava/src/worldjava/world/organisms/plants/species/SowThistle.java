package worldjava.world.organisms.plants.species;

import java.util.ArrayList;

import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.plants.Plant;

public class SowThistle extends Plant {
    public SowThistle(int x, int y)
    {
        super();
        this.posX = x;
        this.posY = y;
        this.prevX = x;
        this.prevY = y;
        this.id = 2;
        this.baseStrength = this.strength = 0;
        this.skin = ',';
        this.name = "Sow Thistle";
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        for (int i = 0; i < 3; i++) 
        {
            if(randInt(0,100) < 10) // chance of sewing
            {
                return spawnNew(this, organisms, events);
            }
        }
        return null;
    }
};