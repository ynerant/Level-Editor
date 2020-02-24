/**
 * @author galaxyoyo
 */
package fr.ynerant.leveleditor.frame;

import fr.ynerant.leveleditor.client.main.Main;
import fr.ynerant.leveleditor.frame.listeners.ChangeLAFListener;
import fr.ynerant.leveleditor.frame.listeners.CreateMapListener;
import fr.ynerant.leveleditor.frame.listeners.OpenMapListener;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Fen&ecirc;tre principale du jeu
 * @author galaxyoyo
 */
public class MainFrame extends JFrame
{
	/**
	 * ID de s&eacute;rie
	 * @see {@link JFrame}
	 */
	private static final long serialVersionUID = -3168760121879418534L;
	
	/**
	 * Instance unique principale
	 * @see #MainFrame()
	 * @see #getInstance()
	 */
	private static MainFrame INSTANCE;
	
	/**
	 * Logger de la classe
	 * @see LogManager#getLogger(String)
	 */
	private static Logger LOGGER = (Logger) LogManager.getLogger("MainFrame");

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenu display = new JMenu("Affichage");
	private JMenu editMaps = new JMenu("Cartes");
	private JMenu changeLAF = new JMenu("Modfier l'apparence");
	private JMenuItem createMap = new JMenuItem("Cr\u00e9er");
	private JMenuItem openMap = new JMenuItem("Ouvrir");
	private JMenuItem systemLAF = new JMenuItem("Apparence syst\u00e8me");
	private JMenuItem javaLAF = new JMenuItem("Apparence Java");
	private JMenuItem darkLAF = new JMenuItem("Apparence sombre");

	/**
	 * Constructeur
	 * @see Main#launchFrame()
	 */
	private MainFrame()
	{
		super ();
		LOGGER.info("Initialisation de la fen\u00eatre");
		this.setTitle("Alice Game Engine");
		this.setPreferredSize(new Dimension(1000, 800));
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fichier.setMnemonic(KeyEvent.VK_F + KeyEvent.ALT_DOWN_MASK);
		display.setMnemonic(KeyEvent.VK_A + KeyEvent.ALT_DOWN_MASK);
		
		createMap.addActionListener(new CreateMapListener());
		editMaps.add(createMap);
		openMap.addActionListener(new OpenMapListener());
		editMaps.add(openMap);
		
		fichier.add(editMaps);
		
		systemLAF.addActionListener(new ChangeLAFListener(systemLAF, this));
		changeLAF.add(systemLAF);
		javaLAF.addActionListener(new ChangeLAFListener(javaLAF, this));
		changeLAF.add(javaLAF);
		darkLAF.addActionListener(new ChangeLAFListener(darkLAF, this));
		changeLAF.add(darkLAF);
		
		display.add(changeLAF);

		menuBar.add(fichier);
		menuBar.add(display);
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Cet accesseur renvoie l'accesseur unique de la classe
	 * @see #INSTANCE
	 * @see #MainFrame()
	 * @return l'instance unique de la classe
	 */
	public static MainFrame getInstance()
	{
		if (INSTANCE == null)
			return INSTANCE = new MainFrame();
		
		return INSTANCE;
	}
}
