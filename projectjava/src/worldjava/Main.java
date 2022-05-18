package worldjava;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import worldjava.world.World;
import worldjava.world.organisms.Organism;


public class Main 
{
    public static final String GameFrame = null;
    static int worldSize = 0;
    static World world;

    public static void worldInit()
    {
        world = new World(worldSize, worldSize);
    }

    public static void restoreState()
    {
        try {
            //Path file = Files.createTempFile("savefile", ".txt");
            File saveFile = new File("C:\\worldJavaSave\\savefile.txt");
            Scanner scanner = new Scanner(saveFile);

            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int amount = scanner.nextInt();
            world = new World(x, y);
            worldSize = x;

            for(int i = 0; i < amount; i++)
            {
                int id = scanner.nextInt();
                int posX = scanner.nextInt();
                int posY = scanner.nextInt();
                int prevX = scanner.nextInt();
                int prevY = scanner.nextInt();
                int baseStrength = scanner.nextInt();
                int strength = scanner.nextInt();
                Boolean alive = scanner.nextBoolean();
                Boolean inactive = scanner.nextBoolean();
                Boolean animal = scanner.nextBoolean();

                world.addOrganism(id, posX, posY, animal);
                Organism o = world.organisms.get(world.organisms.size()-1);
                o.prevX = prevX;
                o.prevY = prevY;
                o.baseStrength = baseStrength;
                o.strength = strength;
                o.alive = alive;
                o.inactive = inactive;
            }
            scanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("SaveFile not found or corrupted");
            e.printStackTrace();
        }
    }

    public static void saveState()
    {
        // Path path = Paths.get("C:\\worldjava\\savefile.txt"); //creates Path instance  
        try {
            
            // Path saveFile = Files.createFile( ".","saveFile.txt");
            //Path saveFile = Files.createFile(path);
            ArrayList<String> lines = new ArrayList<>();
            lines.add(Integer.toString(worldSize) + " " + Integer.toString(worldSize) + " " + Integer.toString(world.organisms.size()));
            
            for(int i = 0; i < world.organisms.size(); i++)
            {
                Organism o = world.organisms.get(i);
                String line = o.id + " " + o.posX + " " + o.posY + " " + o.prevX + " " + o.prevY + " " + o.baseStrength + " " + o.strength 
                                + " " + o.alive + " " + o.inactive + " " + o.animal;
                lines.add(line);
            }
            Files.createDirectories(Paths.get("C:\\worldJavaSave"));
            Files.write(Paths.get("C:\\worldJavaSave\\savefile.txt"), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
    }

    public static void main(String[] args) throws Exception 
    {
        new guiInit();        
    }
}

