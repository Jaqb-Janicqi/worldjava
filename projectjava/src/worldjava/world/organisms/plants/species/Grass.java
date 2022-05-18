package worldjava.world.organisms.plants.species;

import worldjava.world.organisms.plants.Plant;

public class Grass extends Plant {
    public Grass(int x, int y)
    {
        super();
        this.posX = x;
        this.posY = y;
        this.prevX = this.posX;
        this.prevY = this.posY;
        this.id = 1;
        this.baseStrength = this.strength = 0;
        this.skin = '.';
    }
}