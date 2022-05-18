package worldjava.world.organisms;

import java.util.ArrayList;
import java.util.Random;

import worldjava.world.World;
import worldjava.world.Transporter.Transporter;


public abstract class Organism {
    public int id;
    public boolean alive = true;
    public int initiative;
    public int posX;
    public int posY;
    public int prevX;
    public int prevY;
    public int strength;
    public int baseStrength;
    protected char skin;
    public boolean inactive = false;
    public boolean animal;
    public String name;
    public int directionX;
    public int directionY;

    public Organism(){}

    public int randInt(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
        .findFirst()
        .getAsInt();
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        return null;
    }

    public Transporter spawnNew(Organism organism, ArrayList<Organism> organisms, ArrayList<String> events)
    {
        Transporter data = null;
        int newX, newY, smallerX, smallerY, biggerX, biggerY;
        organism.inactive = true;
        inactive = true;
        organism.posX = organism.prevX;
        organism.posY = organism.prevY;

        if(organism.prevX < posX)
        {
            smallerX = organism.prevX;
            biggerX = posX;
        }
        else
        {
            smallerX = posX;
            biggerX = organism.prevX;
        }

        if(organism.prevY < posY)
        {
            smallerY = organism.prevY;
            biggerY = posY;
        }
        else
        {
            smallerY = posY;
            biggerY = organism.prevY;
        }
        
        int freeSpaces = 0;
        int randomIndex;
        int[][] freeCoordinates = new int[10][2];    // [index][0-y 1-x]
        ArrayList<Integer> validIndexes = new ArrayList<>();         

        for (int y = smallerY-1; y <= biggerY+1; y++)       //get coordinates around two organisms
        {
            if(y != smallerY && y != biggerY && y >= 0 && y < World.WORLDSIZEY) 
            {
                for (int x = smallerX-1; x <= biggerX+1; x++)
                {
                    if (x != smallerX && x != biggerX && x >= 0 && x < World.WORLDSIZEX)
                    {
                        freeCoordinates[freeSpaces][0] = y;
                        freeCoordinates[freeSpaces][1] = x;
                        validIndexes.add(freeSpaces);
                        freeSpaces++;
                    }
                }
            }
        }

        for (int i = 0; i < organisms.size(); i++)      //remove 
        {
            for (int j = 0; j < freeSpaces; j++)
            {
                if (organisms.get(i).posY == freeCoordinates[j][0] && organisms.get(i).posX == freeCoordinates[j][1])
                {
                    validIndexes.remove(j);
                    freeSpaces--;
                }
            }
        }

        if (freeSpaces > 1)
        {
            randomIndex = randInt(0, validIndexes.size());
            newY = freeCoordinates[randomIndex][0];
            newX = freeCoordinates[randomIndex][1];
            data = new Transporter(id, newX, newY, this.animal);
            events.add("New " + name + " has been spawned!");
            return data;
        }
        else if(freeSpaces == 1)
        {
            newY = freeCoordinates[0][0];
            newX = freeCoordinates[0][1];
            data = new Transporter(id, newX, newY, this.animal);
            events.add("New " + name + " has been spawned!");
            return data;
        }
        else return null;
    }

    public Transporter collision(Organism organism, ArrayList<Organism> organisms, ArrayList<String> events)
    {
        if (organism.id == this.id && organism.animal == this.animal)
        {
            return spawnNew(organism, organisms, events);
        }
        else
        {
            if (organism.strength > strength)
            {
                alive = false;
                events.add(this.name + " has died to " + organism.name + ".");
                return null;
            }
            else
            {
                organism.alive = false;
                events.add(organism.name + " has died to " + this.name + ".");
                return null;
            }
        }
    }

    public char draw()     //return organism's char
    {
        return skin;
    }
}