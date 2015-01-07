/**
 * @author galaxyoyo
 */
package galaxyoyo.unknown.frame;

import java.awt.Dimension;

import javax.swing.JFrame;

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
	
	private static Logger LOGGER = (Logger) LogManager.getLogger("MainFrame");

	/**
	 * Constructeur
	 * @see galaxyoyo.unknown.client.main.Main#launchFrame()
	 */
	private MainFrame()
	{
		super ();
		LOGGER.info("D\u00e9marrage du jeu ...");
		this.setTitle("WHAT IS THE NAME PLEASE");
		this.setPreferredSize(new Dimension(1000, 800));
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
