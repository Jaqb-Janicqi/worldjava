package worldjava.world.organisms.animals.species;

import worldjava.world.organisms.animals.Animal;

public class Sheep extends Animal {
    public Sheep(int x, int y)
    {
        this.name = "Sheep";
        this.posX = x;
        this.posY = y;
        this.prevX = this.posX;
        this.prevY = this.posY;
        this.id = 2;
        this.baseStrength = this.strength = 4;
        this.initiative = 4;
        this.skin = 'S';
    }
}