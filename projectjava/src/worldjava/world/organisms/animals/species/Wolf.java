package worldjava.world.organisms.animals.species;

import worldjava.world.organisms.animals.Animal;

public class Wolf extends Animal {
    public Wolf(int x, int y)
    {
        super();
        this.name = "Wolf";
        this.posX = x;
        this.posY = y;
        this.prevX = this.posX;
        this.prevY = this.posY;
        this.id = 1;
        this.baseStrength = this.strength = 9;
        this.initiative = 5;
        this.skin = 'W';
    }
}