package worldjava.world.organisms.plants.species;

import worldjava.world.organisms.plants.Plant;

public class Belladona extends Plant {
    public Belladona(int x, int y)
    {
        super();
        this.posX = x;
        this.posY = y;
        this.prevX = this.posX;
        this.prevY = this.posY;
        this.id = 4;
        this.baseStrength = this.strength = 99;
        this.skin = 'B';
        this.name = "Belladona";
    }
}