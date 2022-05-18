package worldjava.world.organisms.animals.species;

import java.util.ArrayList;

import worldjava.world.World;
import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.animals.Animal;

public class Turtle extends Animal {
    public Turtle(int x, int y)
    {
        this.name = "Turtle";
        this.posX = x;
        this.posY = y;
        this.prevX = this.posX;
        this.prevY = this.posY;
        this.id = 4;
        this.baseStrength = this.strength = 2;
        this.initiative = 1;
        this.skin = 'T';
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        if(!this.inactive && randInt(0,4) == 0)
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

    public Transporter collision(ArrayList<Organism> organisms, Organism enemy, ArrayList<String> events)
    {
        if (enemy.id == this.id && enemy.animal == this.animal)
        {
            return spawnNew(this, organisms, events);
        }
        else
        {
            if (enemy.strength < 5)
            {
                enemy.posX = enemy.prevX;
                enemy.posY = enemy.prevY;
                events.add(this.name + " has reflected attack from " + enemy.name + '.');
                return null;
            }
            else
            {
                this.alive = false;
                events.add(this.name + " has diet to " + enemy.name + '.');
                return null;
            }
        }
    }
};