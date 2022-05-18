package worldjava.world.organisms.plants.species;

import java.util.ArrayList;

import worldjava.world.Transporter.Transporter;
import worldjava.world.organisms.Organism;
import worldjava.world.organisms.plants.Plant;

public class Guarana extends Plant {
    public Guarana(int x, int y)
    {
        super();
        this.posX = x;
        this.posY = y;
        this.prevX = x;
        this.prevY = y;
        this.id = 3;
        this.baseStrength = this.strength = 0;
        this.skin = 'G';
        this.name = "Guarana";
    }

    public Transporter collision(ArrayList<Organism> organisms, Organism enemy, ArrayList<String> events)
    {
        enemy.strength += 3;
        enemy.baseStrength += 3;
        this.alive = false;
        events.add(name + " has been eaten by " + enemy.name + ".");
        return null;
    }
}