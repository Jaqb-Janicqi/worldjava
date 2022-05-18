package worldjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.DimensionUIResource;

import worldjava.world.World;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Gui extends JFrame implements ActionListener , KeyListener
{
    static JButton[][] grid;
    static JPanel worldGrid;
    protected Container pane;
    protected JLabel healthPanel;
    static Gui frame;
    private JButton nextTurnButton;
    private JButton restoreGame;
    private JButton saveButton;

    public Gui() {
        super("World Simulation");
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {
                
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP:
            Main.world.human.directionY = -1;
            Main.world.human.directionX = 0;
            break;

        case KeyEvent.VK_DOWN:
            Main.world.human.directionY = 1;
            Main.world.human.directionX = 0;
            break;
            
        case KeyEvent.VK_LEFT:
            Main.world.human.directionX = -1;
            Main.world.human.directionY = 0;
            break;
            
        case KeyEvent.VK_RIGHT:
            Main.world.human.directionX = 1;
            Main.world.human.directionY = 0;
            break;
        
        case KeyEvent.VK_SPACE:
            nextTurn();
            break;

        case KeyEvent.VK_SHIFT:
            if(Main.world.human.strength <= Main.world.human.baseStrength && World.humanPresent)
            {
                Main.world.human.strength = 10;
                healthPanel.setText("   Health: "+ Main.world.human.strength + "  ");
            }
            break;
        
        default:
            break;
        }        
    }

    @Override
    public void keyReleased(KeyEvent e) {};
    
    public static void createWindow()
    {
        frame = new Gui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.addComponentsToPane(frame.getContentPane());
        
        frame.setPreferredSize(new DimensionUIResource(1280, 720));
        frame.pack();

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == nextTurnButton)
        {
            nextTurn();
        }
        else if(source == restoreGame)
        {
            Main.restoreState();
            updateButtons();
        }
        else if(source == saveButton)
        {
            Main.saveState();
        }
        frame.requestFocusInWindow();
    }

    public void addComponentsToPane(final Container pane)
    {
        worldGrid = new JPanel();
        GridLayout gridLayout = new GridLayout(Main.worldSize, Main.worldSize);
        worldGrid.setLayout(gridLayout);
        createButtons(worldGrid);
        worldGrid.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel controls = new JPanel();
        controls.setLayout(new GridBagLayout());
        // controls.setLayout(new GridLayout(1,3));
        nextTurnButton = new JButton("Next Turn");
        saveButton = new JButton("Save Game");
        restoreGame = new JButton("Load Save");
        nextTurnButton.addActionListener(this);
        saveButton.addActionListener(this);
        restoreGame.addActionListener(this);
        controls.add(restoreGame);
        controls.add(saveButton);
        controls.add(nextTurnButton);

        healthPanel = new JLabel();
        healthPanel.setText("   Health: "+ Main.world.human.strength + "  ");
        controls.add(healthPanel);

        pane.add(worldGrid, BorderLayout.EAST);
        // pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(controls, BorderLayout.WEST);
        // pane.add(infoPanel, BorderLayout.WEST);

    }

    protected void nextTurn()
    {
        Main.world.makeTurn();
        updateButtons();
        if(!World.humanPresent) healthPanel.setText("   Health: Dead   ");
        else healthPanel.setText("   Health: "+ Main.world.human.strength + "   ");
    }

    public static void updateButtons()
    {
        for(int y = 0; y < Main.worldSize; y++)
        {
            for(int x = 0; x < Main.worldSize; x++)
            {
                grid[y][x].setText("");
            }
        }
        for(int i = 0; i < Main.world.organisms.size(); i++)
        {
            grid[Main.world.organisms.get(i).posY][Main.world.organisms.get(i).posX].setText(Character.toString(Main.world.organisms.get(i).draw()));
        }
        //worldGrid.repaint();
    }

    private void createButtons(final JPanel worldGrid)
    {
        grid = new JButton[Main.worldSize][Main.worldSize];
        for(int y = 0; y < Main.worldSize; y++)
        {
            for(int x = 0; x < Main.worldSize; x++)
            {
                grid[y][x] = new JButton();
            }
        }

        for(int i = 0; i < Main.world.organisms.size(); i++)
        {
            grid[Main.world.organisms.get(i).posY][Main.world.organisms.get(i).posX].setText(Character.toString(Main.world.organisms.get(i).draw()));
        }

        for(int y = 0; y < Main.worldSize; y++)
        {
            for(int x = 0; x < Main.worldSize; x++)
            {
                worldGrid.add(grid[y][x]);
            }
        }
    }
}

class ActionFrame extends JFrame 
{
    public ActionFrame() {
        super("");
        JPanel buttonPanel = new ButtonPanel();
        add(buttonPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

class worldInitPane
{  
    JFrame pane;
    int worldSize;
    worldInitPane()
    {  
        pane = new JFrame();   
        String paneOutput = JOptionPane.showInputDialog(pane ,"World Size");
        this.worldSize = Integer.parseInt(paneOutput);
        if(worldSize < 10) worldSize = 10;
        if(worldSize > 20) worldSize = 20;
        Main.worldSize = this.worldSize;
    }  

}

class ButtonPanel extends JPanel implements ActionListener{
    public static final int HEIGHT = 100;
    public static final int WIDTH = 300;
    private JButton startGame;
    private JButton restoreGame;

    public ButtonPanel() {
        startGame = new JButton("New Game");
        restoreGame = new JButton("Restore Game");

        startGame.addActionListener(this);
        restoreGame.addActionListener(this);

        setLayout(new FlowLayout());
        setPreferredSize(new DimensionUIResource(WIDTH, HEIGHT));
        add(startGame);
        add(restoreGame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == startGame)
        {
            new worldInitPane();
            Main.worldInit();
            Main.world.populate();
            guiInit.initframe.setVisible(false);
            Gui.createWindow();
        }
        else if(source == restoreGame)
        {
            Main.restoreState();
            guiInit.initframe.setVisible(false);
            Gui.createWindow();
        }
    }
}

class guiInit
{
    public guiInit() {
        initialize();
    }
    public static ActionFrame initframe;

    public void initialize() {

        initframe = new ActionFrame();
    }
}