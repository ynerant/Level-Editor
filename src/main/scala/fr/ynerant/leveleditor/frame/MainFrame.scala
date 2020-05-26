package fr.ynerant.leveleditor.frame

import fr.ynerant.leveleditor.frame.listeners.ChangeLAFListener
import fr.ynerant.leveleditor.frame.listeners.CreateMapListener
import fr.ynerant.leveleditor.frame.listeners.OpenMapListener
import javax.swing._
import java.awt._
import java.awt.event.{ActionEvent, KeyEvent}

import fr.ynerant.leveleditor.client.main.Main


/**
 * Fen&ecirc;tre principale du jeu
 *
 * @author ÿnérant
 */
@SerialVersionUID(-3168760121879418534L)
object MainFrame {
	/**
	 * Instance unique principale
	 *
	 * @see #MainFrame()
	 * @see #getInstance()
	 */
	private var INSTANCE = null: MainFrame

	/**
	 * Cet accesseur renvoie l'accesseur unique de la classe
	 *
	 * @return l'instance unique de la classe
	 * @see #INSTANCE
	 * @see #MainFrame()
	 */
	def getInstance: MainFrame = {
		if (INSTANCE == null) INSTANCE = new MainFrame
		INSTANCE
	}
}

@SerialVersionUID(-3168760121879418534L)
class MainFrame @SuppressWarnings(Array("JavadocReference")) private()

/**
 * Constructeur
 *
 * @see Main#launchFrame()
 */
	extends JFrame {
	println("Initialisation de la fen\u00eatre")
	this.setTitle("Level Editor")
	this.setPreferredSize(new Dimension(1000, 800))
	this.setSize(800, 700)
	this.setLocationRelativeTo(null)
	this.setExtendedState(Frame.MAXIMIZED_BOTH)
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
	val fichier = new JMenu("Fichier")
	fichier.setMnemonic(KeyEvent.VK_F + KeyEvent.VK_ALT)
	val display = new JMenu("Affichage")
	display.setMnemonic(KeyEvent.VK_A + KeyEvent.VK_ALT)
	val createMap = new JMenuItem("Cr\u00e9er")
	createMap.addActionListener(new CreateMapListener)
	val editMaps = new JMenu("Cartes")
	editMaps.add(createMap)
	val openMap = new JMenuItem("Ouvrir")
	openMap.addActionListener(new OpenMapListener)
	editMaps.add(openMap)
	fichier.add(editMaps)
	val systemLAF = new JMenuItem("Apparence syst\u00e8me")
	systemLAF.addActionListener(new ChangeLAFListener(systemLAF, this))
	val changeLAF = new JMenu("Modfier l'apparence")
	changeLAF.add(systemLAF)
	val javaLAF = new JMenuItem("Apparence Java")
	javaLAF.addActionListener(new ChangeLAFListener(javaLAF, this))
	changeLAF.add(javaLAF)
	val darkLAF = new JMenuItem("Apparence sombre")
	darkLAF.addActionListener(new ChangeLAFListener(darkLAF, this))
	changeLAF.add(darkLAF)
	display.add(changeLAF)
	val bar = new JMenuBar
	bar.add(fichier)
	bar.add(display)
	this.setJMenuBar(bar)
	val start = new JButton("Commencer la partie !")
	start.addActionListener((actionEvent: ActionEvent) => {
		if (Main.launchGameMode) MainFrame.getInstance.dispose()
	})
	this.setContentPane(start)
}
