package worldjava.world.organisms.animals.species;

import java.util.ArrayList;

import worldjava.world.World;
import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.animals.Animal;

public class Antelope extends Animal {
    public Antelope(int x, int y)
    {
        name = "Antelope";
        posX = x;
        posY = y;
        prevX = posX;
        prevY = posY;
        id = 5;
        baseStrength = strength = 4;
        initiative = 4;
        skin = 'A';
    }

    public Transporter action(ArrayList<Organism> organisms, ArrayList<String> events)
    {
        if(!inactive)
        {
            if(randInt(0, 2) == 1)
            {
                int moveX = randMove()*randInt(1, 3);
                while (!(moveX+posX >= 0 && moveX+posX < World.WORLDSIZEX)) moveX = randMove();
                prevX = posX;
                posX += moveX;
                prevY = posY;
            }
            else
            {
                int moveY = randMove();
                while (!(moveY+posY >= 0 && moveY+posY < World.WORLDSIZEY)) moveY = randMove();
                prevY = posY;
                posY += moveY;
                prevX = posX;
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

    public Transporter collision(ArrayList<Organism> organisms, Organism enemy, ArrayList<String> events)
    {
        if (enemy.id == this.id && enemy.animal == this.animal)
        {
            spawnNew(this, organisms, events);
        }
        else
        {
            if(randInt(0, 2) == 1)
            {
                if (enemy.strength > this.strength)
                {
                    alive = false;
                    events.add(this.name + " has died to " + enemy.name + ".");
                    return null;
                }
                else
                {
                    enemy.alive = false;
                    events.add(enemy.name + " has died to " + name + ".");
                    return null;
                }
            }
            else
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
                    events.add(this.name + " has fled from " + enemy.name);
                    return null;
                }
                else
                {
                    if (enemy.strength > this.strength)
                    {
                        alive = false;
                        events.add(this.name + " has diet to " + enemy.name + '.');
                        return null;
                    }
                    else
                    {
                        enemy.alive = false;
                        events.add(enemy.name + " has diet to " + this.name + '.');
                        return null;
                    }
                }
            }
        }
        return null;
    }
};