package Base;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main {
    public static void main(String[] args) {
        new MainMenu(); // Start with the menu
    }
}




/*--------------------------------------------------------------Windows--------------------------------------------------------------*/
class MainMenu {
    private JFrame MenuFrame;

    public MainMenu()
    {
        // Create window
        MenuFrame = new JFrame("BoardedUp");
        MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuFrame.setSize(1280, 960);
        MenuFrame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("logo.png");
        MenuFrame.setIconImage(icon.getImage());

        // Title
        JLabel title = new JLabel("BoardedUp", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        MenuFrame.add(title, BorderLayout.NORTH);


        // Button Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton playButton = new JButton("Play");
        JButton settingButton = new JButton("Settings");
        JButton modButton = new JButton("Mods");
        JButton exitButton = new JButton("Exit");

        playButton.setFont(new Font("Arial", Font.BOLD, 78));
        settingButton.setFont(new Font("Arial", Font.BOLD, 78));
        modButton.setFont(new Font("Arial", Font.BOLD, 78));
        exitButton.setFont(new Font("Arial", Font.BOLD, 78));


        // Button Actions
        // Lambda is fun lol

        //play
        playButton.addActionListener(e ->
        {
            MenuFrame.dispose(); // Close menu
            new GameWindow(); // Start game
        });

        //settings
        settingButton.addActionListener(e ->
        {
            MenuFrame.dispose(); // Close menu
            new SettingsWindow(); // open settings
        });

        //mods
        modButton.addActionListener(e ->
        {
            MenuFrame.dispose(); // Close menu
            new modWindow(); // Open mods menu
        });

        //exit
        exitButton.addActionListener(e -> 
        {
            System.exit(0);//exits
        });

        // Add buttons
        panel.add(playButton);
        panel.add(settingButton);
        panel.add(modButton);
        panel.add(exitButton);

        MenuFrame.add(panel, BorderLayout.CENTER);

        // Show window
        MenuFrame.setLocationRelativeTo(null);
        MenuFrame.setVisible(true);
    }
}


class modWindow
{
    private JFrame modsFrame;

    public modWindow()
    {
        modsFrame = new JFrame("BoardedUp");
        modsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        modsFrame.setSize(1280, 960);
        modsFrame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("logo.png");
        modsFrame.setIconImage(icon.getImage());


        JPanel mainP = new JPanel();
        mainP.setLayout(new GridLayout(1, 2));

        //buttons
        JPanel buttonP = new JPanel();
        buttonP.setLayout(new GridLayout(2, 1));

        JButton backB = new JButton("Back");//add to main
        JButton mapB = new JButton("Map mods");//add to button
        JButton notYet = new JButton("Not yet...");//add to button

        backB.setFont(new Font("Arial", Font.BOLD, 78));
        mapB.setFont(new Font("Arial", Font.BOLD, 78));
        notYet.setFont(new Font("Arial", Font.BOLD, 78));

        backB.addActionListener(e ->
        {
            modsFrame.dispose();
            new MainMenu();
        });

        mapB.addActionListener(e ->
        {
            modsFrame.dispose();
            new mapModding();
        });

        notYet.addActionListener(e ->
        {
            new popUp();
        });

        buttonP.add(mapB);
        buttonP.add(notYet);

        mainP.add(backB);
        mainP.add(buttonP);

        modsFrame.add(mainP);
        modsFrame.setLocationRelativeTo(null);
        modsFrame.setVisible(true);
    }
}


class mapModding
{
    private JFrame mapFrame;

    public mapModding()
    {
        mapFrame = new JFrame("BoardedUp");
        mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapFrame.setSize(1280, 960);
        mapFrame.setLayout(new GridLayout(2, 2));

        ImageIcon icon = new ImageIcon("logo.png");
        mapFrame.setIconImage(icon.getImage());

        JPanel optionP = new JPanel();
        JPanel mapEX = new JPanel();
        mapEX.setLayout(new GridLayout(1, 2));
        optionP.setLayout(new GridLayout(1, 4));

        ImageIcon image1 = new ImageIcon("Howto.png");
        ImageIcon image2 = new ImageIcon("Rulesformap.png");
        
        Image img1 = image1.getImage().getScaledInstance(300, 480, Image.SCALE_SMOOTH);
        Image img2 = image2.getImage().getScaledInstance(300, 480, Image.SCALE_SMOOTH);
        
        ImageIcon resizedImage1 = new ImageIcon(img1);
        ImageIcon resizedImage2 = new ImageIcon(img2);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 78));
        backButton.addActionListener(e ->
        {
            mapFrame.dispose();
            new modWindow();
        });

        JRadioButton og = new JRadioButton("OG map");
        JRadioButton ModdedMap1 = new JRadioButton("Modded map 1");
        JRadioButton ModdedMap2 = new JRadioButton("Modded map 2");
        JRadioButton ModdedMap3 = new JRadioButton("Modded map 3");

        og.setSelected(true);
        
        ButtonGroup group = new ButtonGroup();
        group.add(og);
        group.add(ModdedMap1);
        group.add(ModdedMap2);
        group.add(ModdedMap3);



        og.addActionListener(e ->
        {
            GlobalStates.mapNum = 0;
        });

        ModdedMap1.addActionListener(e ->
        {
            GlobalStates.mapNum = 1;
        });

        ModdedMap2.addActionListener(e ->
        {
            GlobalStates.mapNum = 2;
        });

        ModdedMap3.addActionListener(e ->
        {
            GlobalStates.mapNum = 3;
        });


        optionP.add(og);
        optionP.add(ModdedMap1);
        optionP.add(ModdedMap2);
        optionP.add(ModdedMap3);

        JLabel imageLabel1 = new JLabel(resizedImage1);
        JLabel imageLabel2 = new JLabel(resizedImage2);
        
        mapEX.add(imageLabel1);
        mapEX.add(imageLabel2);
        mapEX.add(backButton);

        mapFrame.add(mapEX);
        mapFrame.add(optionP);
        mapFrame.setLocationRelativeTo(null);
        mapFrame.setVisible(true);
    }
}

class popUp
{
    private JFrame popFrame;

    public popUp()
    {
        popFrame = new JFrame("BoardedUp");
        popFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popFrame.setSize(900, 400);
        popFrame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("logo.png");
        popFrame.setIconImage(icon.getImage());

        JLabel wait = new JLabel("    Patience is a virtue");
        wait.setFont(new Font("Arial", Font.BOLD, 78));

        popFrame.add(wait);
        popFrame.setLocationRelativeTo(null);
        popFrame.setVisible(true);
    }
}

class GameWindow
{
    public JFrame window;
    
    public GameWindow() {
        window = new JFrame("BoardedUp");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("BoardedUp");

        // Load Icon
        ImageIcon icon = new ImageIcon("logo.png");
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.mainThread();

        if(SettingsWindow.music)
        {
            PlaySound ps = new PlaySound();
            ps.getReadyToPlaySound(); // Start background music at game start
        }
    }
}


class SettingsWindow
{
    private JFrame SettingsFrame;

    public static boolean music = true;
    public static float musicVolumeF = (float) -15;

    public SettingsWindow()
    {
        SettingsFrame = new JFrame("BoardedUp");
        SettingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SettingsFrame.setSize(1280, 960);
        SettingsFrame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("logo.png");
        SettingsFrame.setIconImage(icon.getImage());




        // Fill Panel
        JPanel fillPanel = new JPanel();



        // Root Panel
        JPanel Rpanel = new JPanel();
        Rpanel.setLayout(new GridLayout(1, 2));



        // Options Panel
        JPanel OPpanel = new JPanel();
        JPanel SliderPanel = new JPanel();

        OPpanel.setLayout(new GridLayout(4, 1));
        SliderPanel.setLayout(new GridLayout(4, 1));
        
        JCheckBox backgroundMusic = new JCheckBox("Background Music", true);
        JCheckBox hitBox = new JCheckBox("hitBox", false);
        JLabel musicVolumeLabel = new JLabel("Music Volume");
        JSlider musicVolume = new JSlider(0, 100, 20);

        OPpanel.add(backgroundMusic);
        SliderPanel.add(musicVolumeLabel);
        SliderPanel.add(musicVolume);

        OPpanel.add(fillPanel);
        OPpanel.add(SliderPanel);
        OPpanel.add(hitBox);

        //copy from below
        hitBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    GlobalStates.setHitBoxb(false);
                }
                else if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    GlobalStates.setHitBoxb(true);
                }
            }
        });


        //made by AI
        // Options actions
        backgroundMusic.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    music = false;
                }
                else if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    music = true;
                }
            }
        });
        //^ ^ ^ AI ^ ^ ^

        //needed help from AI here as well
        //volume action
        musicVolume.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider) e.getSource();
                float volume = source.getValue(); // Get slider value

                float minDB = -25f;  // Leftmost (-25 dB)
                float maxDB = 6.0f;    // Rightmost (6 dB)
        
                float dB = minDB + ((volume / 100.0f) * (maxDB - minDB));

                musicVolumeF = dB; // Apply volume change
            }
        });

        // Button Panel
        JPanel BUpanel = new JPanel();
        BUpanel.setLayout(new GridLayout(1, 1));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 78));


        // Button Actions
        //back
        backButton.addActionListener(e ->
        {
            SettingsFrame.dispose(); // Close menu
            new MainMenu();//go back
        });

        // Add buttons
        BUpanel.add(backButton);

        Rpanel.add(BUpanel);
        Rpanel.add(fillPanel);
        Rpanel.add(OPpanel);


        SettingsFrame.add(Rpanel, BorderLayout.CENTER);

        // Show window
        SettingsFrame.setLocationRelativeTo(null);
        SettingsFrame.setVisible(true);
    }
}

class END
{
    private JFrame endFrame;

    public END()
    {
        endFrame = new JFrame("BoardedUp");
        endFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        endFrame.setSize(800, 800);
        endFrame.setLayout(new GridLayout(2, 1));

        ImageIcon icon = new ImageIcon("logo.png");
        endFrame.setIconImage(icon.getImage());

        JPanel Buttons = new JPanel();
        JPanel txt = new JPanel();

        JLabel scoreTXT = new JLabel("Final Score: " + GlobalStates.score);
        JLabel roundTXT = new JLabel("Rounds survived: " + GlobalStates.round);

        JButton menu = new JButton("Main Menu");
        JButton quit = new JButton("Rage Quit");

        menu.addActionListener(e ->
        {

        });
        quit.addActionListener(e ->
        {

        });

        txt.add(scoreTXT);
        txt.add(roundTXT);

        endFrame.add(txt);

        endFrame.setLocationRelativeTo(null);
        endFrame.setVisible(true);
    }
}
