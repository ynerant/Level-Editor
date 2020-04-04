package fr.ynerant.leveleditor.client.main

import fr.ynerant.leveleditor.api.editor.EditorAPI
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister
import fr.ynerant.leveleditor.frame.MainFrame
import fr.ynerant.leveleditor.game.GameFrame
import javax.swing._
import java.awt._
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.net.URISyntaxException
import java.net.URL
import java.util.Locale

import fr.ynerant.leveleditor.client.main.Main.DEV


/**
 * Class principale qui lance le jeu
 *
 * @author ÿnérant
 * @see #main(String...)
 */
object Main {
	/**
	 * Variable disant si le jeu est lanc&eacute; en d&eacute;veloppement ou non.
	 *
	 * @see #isInDevelopmentMode()
	 * @see #main(String...)
	 * @since 0.1-aplha
	 */
	private var DEV = false

	/**
	 * Accesseur disant si le jeu est lanc&eacute; en d&eacute;veloppement ou non.
	 *
	 * @see #DEV
	 * @since 0.1-alpha
	 */
	def isInDevelopmentMode: Boolean = DEV

	/**
	 * @param args arguments du jeu. Possibilit&eacute;s :<br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>--edit</strong> lancera un &eacute;diteur<br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>--help</strong> lance l'aide affichant toutes les options possibles
	 * @see #launchEditMode()
	 * @since 0.1-alpha
	 */
	def main(args: Array[String]): Unit = {
		System.setProperty("sun.java2d.noddraw", "true")
		Locale.setDefault(Locale.FRANCE)
		try UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
		catch {
			case e: Exception =>
				new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace()
				System.err.print("Caused by ")
				e.printStackTrace()
		}
		try {
			new File(getClass.getResource("/assets").toURI)
			DEV = true
		} catch {
			case t: Throwable =>
				DEV = false
		}

		try SpriteRegister.unpack()
		catch {
			case e: IOException =>
				e.printStackTrace()
		}
		SpriteRegister.refreshAllSprites()

		launchFrame()
	}

	private def checkJava(): Unit = {
		if (GraphicsEnvironment.isHeadless) {
			val ex = new HeadlessException("Impossible de lancer un jeu sans \u00e9cran !")
			System.err.println("Cette application est un jeu, sans écran, elle aura du mal \u00e0 tourner ...")
			ex.printStackTrace()
			System.exit(1)
		}
		try classOf[Map[_, _]].getDeclaredMethod("getOrDefault", classOf[Any], classOf[Any])
		catch {
			case ex: NoSuchMethodException =>
				ex.printStackTrace()
				JOptionPane.showMessageDialog(null, "<html>Cette application requiert <strong>Java 8</strong>.<br />La page de t\u00e9l\u00e9chargement va maintenant s'ouvrir.</html>")
				JOptionPane.showMessageDialog(null, "<html>Si vous êtes certain que Java 8 est installé sur votre machine, assurez-vous qu'il n'y a pas de versions obsolètes de Java,<br />ou si vous êtes plus expérimentés si le path vers Java est bien défini vers la bonne version.</html>")
				try if (Desktop.isDesktopSupported) Desktop.getDesktop.browse(new URL("http://java.com/download").toURI)
				else JOptionPane.showMessageDialog(null, "<html>Votre machine ne supporte pas la classe Desktop, impossible d'ouvrir la page.<br />Rendez-vous y manuellement sur <a href=\"http://java.com/download\">http://java.com/download</a> pour installer Java.</html>")
				catch {
					case e@(_: IOException | _: URISyntaxException) =>
						e.printStackTrace()
				}
				System.exit(1)
		}
	}

	/**
	 * Lance la fen&ecirc;tre principale
	 *
	 * @see #main(String...)
	 * @see #launchEditMode()
	 */
	private def launchFrame(): Unit = {
		MainFrame.getInstance.setVisible(true)
	}

	/**
	 * Permet de lancer l'&eacute;diteur de carte
	 *
	 * @see #main(String...)
	 * @see #launchFrame()
	 * @since 0.1-aplha
	 */
	def launchEditMode: Boolean = {
		System.out.println("Lancement de l'\u00e9diteur de monde ...")
		var baseWidth = 0
		var baseHeight = 0
		var width = 0
		var height = 0
		while (baseWidth <= 0) {
			try {
				val baseWidthStr = JOptionPane.showInputDialog(null, "Veuillez entrez le nombre de cases longueur de votre carte (0 pour annuler) :")
				if (baseWidthStr == null) return false
				baseWidth = baseWidthStr.toInt * 16
				if (baseWidth < 0) throw new NumberFormatException
				if (baseWidth == 0) return false
			} catch {
				case ignored: NumberFormatException =>

			}
		}
		while (baseHeight <= 0) {
			try {
				val baseHeightStr = JOptionPane.showInputDialog("Veuillez entrez le nombre de cases hauteur de votre carte (0 pour annuler) :")
				if (baseHeightStr == null) return false
				baseHeight = baseHeightStr.toInt * 16
				if (baseHeight < 0) throw new NumberFormatException
				if (baseHeight == 0) return false
			} catch {
				case ignored: NumberFormatException =>
			}
		}
		width = baseWidth + (baseWidth / 16) + 1
		height = baseHeight + (baseHeight / 16) + 1
		val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		val g = image.createGraphics
		g.setColor(Color.white)
		g.fillRect(0, 0, width, height)
		g.setColor(Color.black)
		g.drawLine(0, 0, width, 0)
		g.drawLine(0, 0, 0, height)
		var x = 17
		while ( {
			x <= width
		}) {
			g.drawLine(x, 0, x, height)

			x += 17
		}
		var y = 17
		while ( {
			y <= height
		}) {
			g.drawLine(0, y, width, y)

			y += 17
		}
		val rm = EditorAPI.toRawMap(baseWidth, baseHeight)
		rm.setFont(image)
		EditorAPI.open(rm)
		true
	}

	def launchGameMode: Boolean = {
		println("Lancement du jeu ...")
		val jfc = EditorAPI.createJFC
		jfc.showOpenDialog(MainFrame.getInstance)
		if (jfc.getSelectedFile == null) return false
		val map = EditorAPI.getRawMap(jfc.getSelectedFile)
		new GameFrame(map)
		true
	}
}
