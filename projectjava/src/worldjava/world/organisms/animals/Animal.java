package worldjava.world.organisms.animals;

import java.util.ArrayList;

import worldjava.world.World;
import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;

public abstract class Animal extends Organism {
    protected Animal()
    {
        super();
        this.animal = true;
    }

    public int randMove()
    {
        int x = 1;
        while (x == 1)
        {
            x = randInt(0, 3);
        }
        return x-1;
    }
    
    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        if(!this.inactive)
        {
            if(randInt(0, 2) == 1)        // make random move in one direction 
            {
                int moveX = randMove();
                if (!(moveX+this.posX >= 0 && moveX+this.posX < World.WORLDSIZEX-1))
                {
                    action(organisms, events);
                }
                else
                {
                    this.prevY = this.posY;
                    this.prevX = this.posX;
                    this.posX += moveX;
                }
            }
            else
            {
                int moveY = randMove();
                if (!(moveY+this.posY >= 0 && moveY+this.posY < World.WORLDSIZEY-1))
                {
                    action(organisms, events);
                }
                else
                {
                    this.prevY = this.posY;
                    this.prevX = this.posX;
                    this.posY += moveY;
                }
            }
        }
        else
        {
            this.prevX = this.posX;
            this.prevY = this.posY;
            this.inactive = false;
        }
        return null;
    }
};