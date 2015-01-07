/**
 * @author galaxyoyo
 */
package galaxyoyo.unknown.frame;

import galaxyoyo.unknown.frame.listeners.CreateMapListener;

import java.awt.Dimension;

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
	 * ID de s&eacute;
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
	private JMenu editer = new JMenu("\u00c9diter");
	private JMenu editMaps = new JMenu("Cartes");
	private JMenuItem createMap = new JMenuItem("Cr\u00e9er");
	private JMenuItem editMap = new JMenuItem("\u00c9diter");

	/**
	 * Constructeur
	 * @see galaxyoyo.unknown.client.main.Main#launchFrame()
	 */
	private MainFrame()
	{
		super ();
		LOGGER.info("Initialisation de la fen\u00eatre");
		this.setTitle("WHAT IS THE NAME PLEASE");
		this.setPreferredSize(new Dimension(1000, 800));
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		menuBar.add(fichier);
		
		createMap.addActionListener(new CreateMapListener());
		editMaps.add(createMap);
		editMaps.add(editMap);
		editer.add(editMaps);
		
		menuBar.add(editer);
		
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
