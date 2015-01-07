/**
 * 
 */
package galaxyoyo.unknown.client.main;

import java.io.IOException;

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
 * @author galaxyoyo
 * Class principale qui lance le jeu
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
		FileAppender file = FileAppender.createAppender("Console.log", "false", "false", "File", "true", "true", "true", "8192", console.getLayout(), null, "false", "false", null);
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
				LOGGER.setLevel(Level.DEBUG);
			}
		}
		
		if (set.has(edit))
		{
			launchEditMode();
			return;
		}
	}
	
	/**
	 * Permet de lancer l'&eacute;diteur de carte
	 * @see #main(String...)
	 * @since 0.1-aplha
	 */
	private static void launchEditMode()
	{
		System.out.println("Lancement de l'\u00e9diteurde monde ...");
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
