package worldjava.world;
import java.util.ArrayList;
import java.util.Random;

import worldjava.world.organisms.Organism;
import worldjava.world.organisms.animals.species.Antelope;
import worldjava.world.organisms.animals.species.Fox;
import worldjava.world.organisms.animals.species.Human;
import worldjava.world.organisms.animals.species.Sheep;
import worldjava.world.organisms.animals.species.Turtle;
import worldjava.world.organisms.animals.species.Wolf;
import worldjava.world.organisms.plants.species.Belladona;
import worldjava.world.organisms.plants.species.Grass;
import worldjava.world.organisms.plants.species.Guarana;
import worldjava.world.organisms.plants.species.SosnowskyWeed;
import worldjava.world.organisms.plants.species.SowThistle;
import worldjava.Gui;
import worldjava.world.Transporter.Transporter;


public class World {
    public static int WORLDSIZEX;
    public static int WORLDSIZEY;
    public static boolean humanPresent = false;
    public Organism human;
    public ArrayList<String> events = new ArrayList<>();
    public ArrayList<Organism> organisms = new ArrayList<>(); 

    public static int randInt(int min, int max) 
    {
        Random random = new Random();
        return random.ints(min, max)
        .findFirst()
        .getAsInt();
    }

    public void populate()
    {
        addOrganism(9999, true);
        // human = organisms.get(organisms.size()-1);   
        for(int i = 0; i < WORLDSIZEX/10; i++)
        {
            addOrganism(1, true);     //add animals
            addOrganism(2, true);
            addOrganism(3, true);
            addOrganism(4, true);
            addOrganism(5, true);
            addOrganism(1, true);
            addOrganism(2, true);
            addOrganism(3, true);
            addOrganism(4, true);
            addOrganism(5, true);

            addOrganism(1, false);     //add plants
            addOrganism(2, false);
            addOrganism(3, false);
            addOrganism(4, false);
            addOrganism(5, false);
            addOrganism(1, false);
            addOrganism(2, false);
            addOrganism(3, false);
            addOrganism(4, false);
            addOrganism(5, false);
        }
    }

    public World(int x, int y)
    {
        WORLDSIZEX = x;
        WORLDSIZEY = y;
    }

    public Boolean makeTurn()
    {
        Boolean keepRunning = true;
        Transporter data = null;
        int i = 0;
        events.clear();

        while (i < organisms.size())
        {
            if(organisms.get(i).id == 9999) Gui.updateButtons();

            data = organisms.get(i).action(organisms, events);

            if(data != null) 
            {
                addOrganism(data.id, data.posX, data.posY, data.animal);
                data = null;
            }

            for (int j = 0; j < organisms.size(); j++)
            {
                if (organisms.get(i).alive && organisms.get(j).alive)
                {
                    if (i != j && organisms.get(i).posX == organisms.get(j).posX && organisms.get(i).posY == organisms.get(j).posY)
                    {
                        data = organisms.get(j).collision(organisms.get(i), organisms, events);

                        if (data != null)
                        {
                            addOrganism(data.id, data.posX, data.posY, data.animal);
                        }
                        data = null;
                    }
                }
            }
            for (int k = organisms.size()-1; k >= 0; k--)
            {
                if(organisms.get(k).alive == false) 
                {
                    if(organisms.get(k).id == 9999) humanPresent = false;
                    organisms.remove(k);
                }
            }
            i++;
        }

        if (organisms.size() <= 1) keepRunning = false;
        return keepRunning;
    }

    public void addOrganism(int orgarnismId, int x, int y, Boolean animal)
    {
        Organism newOrganism = null;
        if (animal)
        {
            switch (orgarnismId)
            {
            case 9999:
                newOrganism = new Human(x, y);
                human = newOrganism;
                humanPresent = true;
                break;

            case 1:
                newOrganism = new Wolf(x, y);
                break;

            case 2:
                newOrganism = new Sheep(x, y);
                break;

            case 3:
                newOrganism = new Fox(x, y);
                break;

            case 4:
                newOrganism = new Turtle(x, y);
                break;

            case 5:
                newOrganism = new Antelope(x, y);
                break;
            
            default:
                break;
            }
        }
        else
        {
            switch (orgarnismId)
            {
            case 1:
                newOrganism = new Grass(x, y);
                break;

            case 2:
                newOrganism = new SowThistle(x, y);
                break;

            case 3:
                newOrganism = new Guarana(x, y);
                break;

            case 4:
                newOrganism = new Belladona(x, y);
                break;

            case 5:
                newOrganism = new SosnowskyWeed(x, y);
                break;
            
            default:
                break;
            }
        }

        if(newOrganism != null)
        {
            if(organisms.size() >= 1)       //mainains a sorted vector of organisms (youngest organisms inserted at last pos of given initiative)
            {
                for (int i = organisms.size(); i >= 1; i--)
                {
                    if (organisms.get(i-1).initiative >= newOrganism.initiative)
                    {
                        organisms.add(i, newOrganism);
                        break;
                    }
                    if (i == 1) organisms.add(0 ,newOrganism);
                }
            }
            else organisms.add(newOrganism);
        }
    }

    public void addOrganism(int orgarnismId, Boolean animal)
    {
        if(orgarnismId == 9999 && !humanPresent)
        {
            humanPresent = true;
            int x = randInt(0, WORLDSIZEX);
            int y = randInt(0, WORLDSIZEY);
            addOrganism(orgarnismId, x, y, animal);

            for (int j = -1; j <= 1; j++)
            {
                for (int k = -1; k <= 1; k++)
                {
                    if(x+k >= 0 && x+k < WORLDSIZEX && y+j >= 0 && y+j < WORLDSIZEY && !(j == 0 && k == 0))
                        addOrganism(1, x+k, y+j, false);
                }
            }
        }
        else if(organisms.size() < WORLDSIZEX*WORLDSIZEY && orgarnismId != 9999)
        {
            int x = randInt(0, WORLDSIZEX);
            int y = randInt(0, WORLDSIZEY);
            int i = 0;
            while(i < organisms.size())
            {
                if (x == organisms.get(i).posX && y == organisms.get(i).posY)
                {
                    i = 0;
                    x = randInt(0, WORLDSIZEX);
                    y = randInt(0, WORLDSIZEY);
                } 
                else i++;
            }
            addOrganism(orgarnismId, x, y, animal);
        }
    }
}
