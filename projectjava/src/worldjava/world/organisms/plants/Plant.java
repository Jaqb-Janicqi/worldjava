package worldjava.world.organisms.plants;

import java.util.ArrayList;

import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;

public abstract class Plant extends Organism {
    protected Plant()
    {
        super();
        this.animal = false;
        this.initiative = 0;
    }
    
    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        if(randInt(0,100) < 10) // chance of sewing
        {
            return spawnNew(this, organisms, events);
        }
        else return null;
    }
}