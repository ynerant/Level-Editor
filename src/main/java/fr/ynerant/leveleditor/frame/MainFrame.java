package fr.ynerant.leveleditor.frame;

import fr.ynerant.leveleditor.client.main.Main;
import fr.ynerant.leveleditor.frame.listeners.ChangeLAFListener;
import fr.ynerant.leveleditor.frame.listeners.CreateMapListener;
import fr.ynerant.leveleditor.frame.listeners.OpenMapListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Fen&ecirc;tre principale du jeu
 *
 * @author ÿnérant
 */
public class MainFrame extends JFrame {
    /**
     * ID de s&eacute;rie
     */
    private static final long serialVersionUID = -3168760121879418534L;

    /**
     * Instance unique principale
     *
     * @see #MainFrame()
     * @see #getInstance()
     */
    private static MainFrame INSTANCE;

    /**
     * Constructeur
     *
     * @see Main#launchFrame()
     */
    @SuppressWarnings("JavadocReference")
    private MainFrame() {
        super();
        System.out.println("Initialisation de la fen\u00eatre");
        this.setTitle("Level Editor");
        this.setPreferredSize(new Dimension(1000, 800));
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu fichier = new JMenu("Fichier");
        fichier.setMnemonic(KeyEvent.VK_F + KeyEvent.ALT_DOWN_MASK);
        JMenu display = new JMenu("Affichage");
        display.setMnemonic(KeyEvent.VK_A + KeyEvent.ALT_DOWN_MASK);

        JMenuItem createMap = new JMenuItem("Cr\u00e9er");
        createMap.addActionListener(new CreateMapListener());
        JMenu editMaps = new JMenu("Cartes");
        editMaps.add(createMap);
        JMenuItem openMap = new JMenuItem("Ouvrir");
        openMap.addActionListener(new OpenMapListener());
        editMaps.add(openMap);

        fichier.add(editMaps);

        JMenuItem systemLAF = new JMenuItem("Apparence syst\u00e8me");
        systemLAF.addActionListener(new ChangeLAFListener(systemLAF, this));
        JMenu changeLAF = new JMenu("Modfier l'apparence");
        changeLAF.add(systemLAF);
        JMenuItem javaLAF = new JMenuItem("Apparence Java");
        javaLAF.addActionListener(new ChangeLAFListener(javaLAF, this));
        changeLAF.add(javaLAF);
        JMenuItem darkLAF = new JMenuItem("Apparence sombre");
        darkLAF.addActionListener(new ChangeLAFListener(darkLAF, this));
        changeLAF.add(darkLAF);

        display.add(changeLAF);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fichier);
        menuBar.add(display);

        this.setJMenuBar(menuBar);

        JButton start = new JButton("Commencer la partie !");
        start.addActionListener(actionEvent -> {
            if (Main.launchGameMode())
                getInstance().dispose();
        });
        this.setContentPane(start);
    }

    /**
     * Cet accesseur renvoie l'accesseur unique de la classe
     *
     * @return l'instance unique de la classe
     * @see #INSTANCE
     * @see #MainFrame()
     */
    public static MainFrame getInstance() {
        if (INSTANCE == null)
            return INSTANCE = new MainFrame();

        return INSTANCE;
    }
}
