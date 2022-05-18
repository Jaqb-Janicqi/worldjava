package worldjava.world.organisms.plants.species;

import java.util.ArrayList;

import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.plants.Plant;

public class SosnowskyWeed extends Plant {
    public SosnowskyWeed(int x, int y)
    {
        super();
        this.posX = x;
        this.posY = y;
        this.prevX = x;
        this.prevY = y;
        this.id = 5;
        this.baseStrength = this.strength = 10;
        this.skin = '$';
        this.name = "Sosnowsky Weed";
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        for (int i = 0; i < organisms.size(); i++)
        {
            if (organisms.get(i).posX >= this.posX-1 && organisms.get(i).posX <= this.posX+1)
            {
                if (organisms.get(i).posY >= this.posY-1 && organisms.get(i).posY <= this.posY+1)
                {
                    if (organisms.get(i).id != this.id && organisms.get(i).animal)
                    {
                        organisms.get(i).alive = false;
                        events.add(organisms.get(i).name + " has been killed by " + this.name + ".");
                    }
                }
            }
        }

        if(randInt(0,100) < 10) // chance of sewing
        {
            return spawnNew(this, organisms, events);
        }
        else return null;
    }
}