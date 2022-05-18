package worldjava.world.organisms.animals.species;

import java.util.ArrayList;

import worldjava.world.World;
import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.animals.Animal;

public class Fox extends Animal {
    public Fox(int x, int y)
    {
        this.name = "Fox";
        this.posX = x;
        this.posY = y;
        this.prevX = this.posX;
        this.prevY = this.posY;
        this.id = 3;
        this.baseStrength = this.strength = 3;
        this.initiative = 7;
        this.skin = 'F';
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        if(!this.inactive)
        {
            ArrayList<Coordinates> freeSpaces = new ArrayList<>();
            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    int cellx = j+this.posX, celly = i+this.posY;
                    if ((i == 0 || j == 0) && !(j == 0 && i == 0) && cellx < World.WORLDSIZEX && cellx >=0 && celly < World.WORLDSIZEY && celly >= 0)
                    {
                        for (int k = 0; k < organisms.size(); k++)
                        {
                            if(!(organisms.get(k).posX == cellx && organisms.get(k).posY == celly) || !organisms.get(k).alive)
                            {
                                if(organisms.get(k).strength <= strength || !organisms.get(k).alive)
                                {
                                    Coordinates newCoordinates = new Coordinates(cellx, celly);
                                    freeSpaces.add(newCoordinates);
                                }
                            }
                        }
                    }
                }
            }

            if (freeSpaces.size() > 0)
            {
                int randomIndex;
                if(freeSpaces.size() == 1) randomIndex = 0;
                else randomIndex = randInt(0, freeSpaces.size()-1);

                prevX = posX;
                prevY = posY;
                posX = freeSpaces.get(randomIndex).x;
                posY = freeSpaces.get(randomIndex).y;
            }
        }
        else
        {
            prevX = posX;
            prevY = posY;
            inactive = false;
        }
    return null;
    }
};