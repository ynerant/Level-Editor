/**
 * @author galaxyoyo
 */
package galaxyoyo.unknown.client.main;

import galaxyoyo.unknown.api.editor.EditorAPI;
import galaxyoyo.unknown.api.editor.RawMap;
import galaxyoyo.unknown.api.editor.sprites.SpriteRegister;
import galaxyoyo.unknown.frame.MainFrame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Class principale qui lance le jeu
 * @author galaxyoyo
 * @see #main(String...)
 */
public class Main
{
	/**
	 * Variable disant si le jeu est en d&eacute;bogage ou non. S'active en ins&eacute;rant l'argument --debug dans le lancement.
	 * @see #isInDebugMode()
	 * @see #main(String...)
	 * @since 0.1-aplha
	 */
	private static boolean DEBUG;
	
	/**
	 * Variable disant si le jeu est lanc&eacute; en d&eacute;veloppement ou non.
	 * @see #isInDevelopmentMode()
	 * @see #main(String...)
	 * @since 0.1-aplha
	 */
	private static boolean DEV;
	
	/**
	 * @param args arguments du jeu. Possibilit&eacute;s :<br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>--edit</strong> lancera un &eacute;diteur<br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>--help</strong> lance l'aide affichant toutes les options possibles
	 * @see #launchEditMode()
	 * @since 0.1-alpha
	 */
	public static void main(String ... args)
	{
		Locale.setDefault(Locale.FRANCE);
		
		DEV = Main.class.getClassLoader().getResource("/META-INF/MANIFEST.MF") == null;
		
		Logger LOGGER = (Logger) LogManager.getRootLogger();
		ConsoleAppender console = ConsoleAppender.newBuilder().setLayout(PatternLayout.newBuilder().withPattern("[%d{dd/MM/yyyy}] [%d{HH:mm:ss}] [%t] [%c] [%p] %m%n").build()).setName("Console").build();
		console.start();
		LOGGER.addAppender(console);
		LOGGER.setLevel(Level.INFO);
		
		checkJava();
		
		OptionParser parser = new OptionParser();
		
		OptionSpec<String> edit = parser.accepts("edit", "Lancer l'\u00e9diteur de monde").withOptionalArg();
		OptionSpec<Boolean> debug = parser.accepts("debug").withOptionalArg().ofType(Boolean.class).defaultsTo(true);
		OptionSpec<String> help = parser.accepts("help", "Affiche ce menu d'aide").withOptionalArg().forHelp();
		
		OptionSet set = parser.parse(args);
		
		if (set.has(help))
		{
			try
			{
				parser.printHelpOn(System.out);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				System.exit(0);
			}
		}
		
		if (set.has(debug))
		{
			DEBUG = set.valueOf(debug);
			
			if (DEBUG)
			{
				LOGGER.setLevel(Level.ALL);
			}
		}
		
		SpriteRegister.refreshAllSprites();
		
		if (set.has(edit))
		{
			launchEditMode();
			return;
		}
		
		launchFrame();
	}

	private static void checkJava()
	{
		if (GraphicsEnvironment.isHeadless())
		{
			HeadlessException ex = new HeadlessException("Impossible de lancer un jeu sans \u00e9cran !");
			LogManager.getLogger("JAVAX-SWING").fatal("Cette application est un jeu, sans écran, elle aura du mal \u00e0 tourner ...");
			LogManager.getLogger("JAVAX-SWING").catching(Level.FATAL, ex);
			System.exit(1);
		}
		
		try
		{
			Map.class.getDeclaredMethod("getOrDefault", Object.class, Object.class);
		}
		catch (NoSuchMethodException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "<html>Cette application requiert <strong>Java 8</strong>.<br />La page de t\u00e9l\u00e9chargement va maintenant s'ouvrir.</html>");
			JOptionPane.showMessageDialog(null, "<html>Si vous êtes certain que Java 8 est installé sur votre machine, assurez-vous qu'il n'y a pas de versions obsolètes de Java,<br />ou si vous êtes plus expérimentés si le path vers Java est bien défini vers la bonne version.</html>");
			try
			{
				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().browse(new URL("http://java.com/download").toURI());
				else
					JOptionPane.showMessageDialog(null, "<html>Votre machine ne supporte pas la classe Desktop, impossible d'ouvrir la page.<br />Rendez-vous y manuellement sur <a href=\"http://java.com/download\">http://java.com/download</a> pour installer Java.</html>");
			}
			catch (IOException | URISyntaxException e)
			{
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	/**
	 * Lance la fen&ecirc;tre principale
	 * @see #main(String...)
	 * @see #launchEditMode()
	 */
	private static void launchFrame()
	{
		MainFrame.getInstance().setVisible(true);
	}

	/**
	 * Permet de lancer l'&eacute;diteur de carte
	 * @see #main(String...)
	 * @see #launchFrame()
	 * @since 0.1-aplha
	 */
	public static void launchEditMode()
	{
		System.out.println("Lancement de l'\u00e9diteur de monde ...");
		int baseWidth;
		int baseHeight;
		int width;
		int height;
		while (true)
		{
			try
			{
				baseWidth = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrez le nombre de cases longueur de votre carte (0 pour annuler) :")) * 16;
				if (baseWidth < 0)
					throw new NumberFormatException();
				if (baseWidth == 0)
					return;
				break;
			}
			catch (NumberFormatException ex)
			{
				continue;
			}
		}
		
		while (true)
		{
			try
			{
				baseHeight = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrez le nombre de cases hauteur de votre carte (0 pour annuler) :")) * 16;
				if (baseHeight < 0)
					throw new NumberFormatException();
				if (baseHeight == 0)
					return;
				break;
			}
			catch (NumberFormatException ex)
			{
				continue;
			}
		}
		
		width = baseWidth + ((int) baseWidth / 16) + 1;
		height = baseHeight + ((int) baseHeight / 16) + 1;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawLine(0, 0, width, 0);
		g.drawLine(0, 0, 0, height);
		for (int x = 17; x <= width; x += 17)
		{
			g.drawLine(x, 0, x, height);
		}
		
		for (int y = 17; y <= height; y += 17)
		{
			g.drawLine(0, y, width, y);
		}
		
		RawMap rm = EditorAPI.toRawMap(baseWidth, baseHeight);
		rm.setFont(image);
		
		EditorAPI.open(rm);
	}
	
	/**
	 * Accesseur disant si le jeu est en d&eacute;bogage ou non. S'active en ins&eacute;rant l'argument --debug dans le lancement.
	 * @see #DEBUG
	 * @since 0.1-aplha
	 */
	public static boolean isInDebugMode()
	{
		return DEBUG;
	}
	

	/**
	 * Accesseur disant si le jeu est lanc&eacute; en d&eacute;veloppement ou non.
	 * @see #DEV
	 * @since 0.1-alpha
	 */
	public static boolean isInDevelopmentMode()
	{
		return DEV;
	}
}
