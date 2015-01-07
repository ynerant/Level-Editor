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
 */
public class Main
{
	private static boolean DEBUG;
	private static boolean DEV;
	
	/**
	 * @param args arguments du jeu. Possibilit\u00e9s :<br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>--edit</strong> lancera un \u00e9diteur<br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>--help</strong> lance l'aide affichant toutes les options possibles
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
	
	private static void launchEditMode()
	{
		System.out.println("Lancement de l'\u00e9diteurde monde ...");
	}

	public static boolean isDebugMode()
	{
		return DEBUG;
	}
	
	public static boolean isInDevelopmentMode()
	{
		return DEV;
	}
}
