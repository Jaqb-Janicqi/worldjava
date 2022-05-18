package worldjava.world.organisms.animals.species;

import java.util.ArrayList;

import worldjava.world.World;
import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.animals.Animal;


public class Human extends Animal {
    public Human(int x, int y)
    {
        directionX = 0;
        directionY = 0;
        name = "Human";
        posX = x;
        posY = y;
        prevX = posX;
        prevY = posY;
        id = 9999;
        baseStrength = strength = 5;
        initiative = 4;
        skin = 'H';
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {        
        if(inactive) inactive = false;
        else
        {
            if(strength <= baseStrength) events.add("Potion avaible");
            else 
            {
                strength--;
                if(strength == baseStrength) events.add("Potion avaible");
                else events.add("You have " + strength + " strength");
            }
        }

        if (posX+directionX < World.WORLDSIZEX && posX+directionX >= 0 && posY+directionY < World.WORLDSIZEY && posY+directionY >= 0)
        {
            prevX = posX;
            prevY = posY;
            posX += directionX;
            posY += directionY;
        }
        else 
        {
            directionX = (-1)*directionX;
            directionY = (-1)*directionY;
            prevX = posX;
            prevY = posY;
            posX += directionX;
            posY += directionY;
        }

        return null;
    }
}
