/**
 * @author galaxyoyo
 */
package galaxyoyo.unknown.client.main;

import galaxyoyo.unknown.editor.Editor;
import galaxyoyo.unknown.frame.MainFrame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
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
		DEV = Main.class.getResource("/META-INF/MANIFEST.MF") == null;
		
		Logger LOGGER = (Logger) LogManager.getRootLogger();
		ConsoleAppender console = ConsoleAppender.newBuilder().setLayout(PatternLayout.newBuilder().withPattern("[%d{dd/MM/yyyy}] [%d{HH:mm:ss}] [%t] [%c] [%p] %m%n").build()).setName("Console").build();
		FileAppender file = FileAppender.createAppender("Console.log", "false", "false", "File", "true", "true", "true", "8192", PatternLayout.newBuilder().withPattern("[%d{dd/MM/yyyy}] [%d{HH:mm:ss}] [%t] [%c] [%p] %m%n").build(), null, "false", "false", null);
		console.start();
		file.start();
		LOGGER.addAppender(console);
		LOGGER.addAppender(file);
		LOGGER.setLevel(Level.INFO);
		
		
		
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
		
		if (set.has(edit))
		{
			launchEditMode();
			return;
		}
		
		launchFrame();
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
	private static void launchEditMode()
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
				baseWidth = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrez le nombre de cases longueur de votre carte :")) * 16;
				if (baseWidth <= 0)
					throw new NumberFormatException();
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
				baseHeight = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrez le nombre de cases hauteur de votre carte :")) * 16;
				if (baseHeight <= 0)
					throw new NumberFormatException();
				break;
			}
			catch (NumberFormatException ex)
			{
				continue;
			}
		}
		
		width = baseWidth + ((int) baseWidth / 16) + 1;
		height = baseHeight + ((int) baseHeight / 16) + 1;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
		
		Byte[] bytes = Editor.toBytes(baseWidth, baseHeight);
		
		for (byte b : bytes)
		{
			System.err.print(b);
		}
		
		try
		{
			ImageIO.write(image, "png", new File("img.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
