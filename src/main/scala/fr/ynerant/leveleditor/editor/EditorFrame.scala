package fr.ynerant.leveleditor.editor

import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister
import fr.ynerant.leveleditor.frame.listeners._
import javax.swing._
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import java.awt._
import java.awt.event._
import java.io.File

import fr.ynerant.leveleditor.api.editor.{EditorAPI, RawMap}


@SerialVersionUID(-2705122356101556462L)
class EditorFrame(val map: Map) extends JFrame("Level Editor") with ChangeListener with ActionListener with WindowListener {
	final private val save = new JMenuItem("Sauvegarder")
	final private val saveAs = new JMenuItem("Sauvegarder sous ...")
	final private val exit = new JMenuItem("Quitter")
	final private val pen = new JRadioButtonMenuItem("Pinceau")
	final private val pot = new JRadioButtonMenuItem("Pot de peinture")
	final private val tabs = new JTabbedPane
	final private var tabColl = null: CollidPanel
	final private var mapPanel = null: MapPanel
	final private val resources = new JTabbedPane
	final private val couche1 = new JPanel
	final private val couche2 = new JPanel
	final private val couche3 = new JPanel
	final private[editor] val group = new ButtonGroup
	private var selectedSprite = null: SpriteComp

	this.setSize(600, 600)
	this.setPreferredSize(getSize)
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
	this.setExtendedState(Frame.MAXIMIZED_BOTH)
	this.setLocationRelativeTo(null)
	this.addWindowListener(this)
	val content = new JPanel
	content.setLayout(new BorderLayout)
	this.setContentPane(content)
	this.setVisible(true)
	this.setVisible(false)
	val fichier = new JMenu("Fichier")
	fichier.setMnemonic(KeyEvent.VK_F + KeyEvent.VK_ALT)
	val nouveau = new JMenuItem("Nouveau")
	nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.VK_CONTROL, true))
	nouveau.addActionListener(new CreateMapListener)
	fichier.add(nouveau)
	val open = new JMenuItem("Ouvrir")
	open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.VK_CONTROL, true))
	open.addActionListener(new OpenMapListener)
	fichier.add(open)
	fichier.addSeparator()
	save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_CONTROL, true))
	save.addActionListener(this)
	fichier.add(save)
	saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_CONTROL + KeyEvent.VK_SHIFT, true))
	saveAs.addActionListener(this)
	fichier.add(saveAs)
	fichier.addSeparator()
	exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.VK_CONTROL, true))
	exit.addActionListener(this)
	fichier.add(exit)
	val bar: JMenuBar = new JMenuBar
	bar.add(fichier)
	pen.setSelected(true)
	pen.addActionListener(this)
	pot.addActionListener(this)
	group.add(pen)
	group.add(pot)
	val selectionMode = new JMenu("Mode de s\u00e9lection")
	selectionMode.add(pen)
	selectionMode.add(pot)
	val tools = new JMenu("Outils")
	tools.setMnemonic(KeyEvent.VK_O + KeyEvent.VK_ALT)
	tools.add(selectionMode)
	bar.add(tools)
	this.setJMenuBar(bar)
	mapPanel = new MapPanel(this)
	mapPanel.addMouseListener(new MapMouseListener(mapPanel, this))
	mapPanel.addMouseMotionListener(new MapMouseListener(mapPanel, this))
	tabColl = new CollidPanel(this)
	tabColl.addMouseListener(new CollidMapMouseListener(tabColl, this))
	tabColl.addMouseMotionListener(new CollidMapMouseListener(tabColl, this))
	val scrollMap = new JScrollPane(mapPanel)
	scrollMap.getHorizontalScrollBar.setUnitIncrement(34)
	scrollMap.getVerticalScrollBar.setUnitIncrement(34)
	val scrollCollidMap = new JScrollPane(tabColl)
	scrollCollidMap.getHorizontalScrollBar.setUnitIncrement(34)
	scrollCollidMap.getVerticalScrollBar.setUnitIncrement(34)
	tabs.addTab("Carte", scrollMap)
	val tabEvents = new JPanel
	tabs.addTab("\u00c9vennments", new JScrollPane(tabEvents))
	tabs.addTab("Collisions", scrollCollidMap)
	tabs.addChangeListener(this)
	content.add(tabs, BorderLayout.CENTER)
	couche1.setLayout(new WrapLayout(FlowLayout.LEFT))
	couche2.setLayout(new WrapLayout(FlowLayout.LEFT))
	couche3.setLayout(new WrapLayout(FlowLayout.LEFT))
	val scroll1 = new JScrollPane(couche1, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
	val scroll2 = new JScrollPane(couche2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
	val scroll3 = new JScrollPane(couche3, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
	scroll1.getHorizontalScrollBar.setMaximum(0)
	scroll2.getHorizontalScrollBar.setMaximum(0)
	scroll3.getHorizontalScrollBar.setMaximum(0)
	resources.addTab("", new ImageIcon(new File("assets/leveleditor/textures/layer 1.png").getAbsolutePath), scroll1)
	resources.addTab("", new ImageIcon(new File("assets/leveleditor/textures/layer 2.png").getAbsolutePath), scroll2)
	resources.addTab("", new ImageIcon(new File("assets/leveleditor/textures/layer 3.png").getAbsolutePath), scroll3)
	resources.addChangeListener(this)
	resources.setBackgroundAt(0, Color.white)
	resources.setBackgroundAt(1, Color.white)
	resources.setBackgroundAt(2, Color.white)
	content.add(resources, BorderLayout.EAST)
	resize()
	drawResources()
	revalidate()
	repaint()

	private def drawResources(): Unit = {
		couche1.removeAll()
		couche2.removeAll()
		couche3.removeAll()
		if (couche1.getComponents.length > 0) return
		if (couche1.getWidth == 0 || couche2.getWidth == 0 || couche3.getWidth == 0) {
			couche1.repaint()
			couche2.repaint()
			couche3.repaint()
		}
		SpriteRegister.getAllCategories.forEach(cat => {
			cat.getSprites.forEach(spr => {
				val sprc1 = new SpriteComp(spr, 0)
				val sprc2 = new SpriteComp(spr, 1)
				val sprc3 = new SpriteComp(spr, 2)
				sprc1.addMouseListener(new SpriteMouseListener(sprc1, this))
				sprc2.addMouseListener(new SpriteMouseListener(sprc2, this))
				sprc3.addMouseListener(new SpriteMouseListener(sprc3, this))
				couche1.add(sprc1)
				couche2.add(sprc2)
				couche3.add(sprc3)
			})
		})
		couche1.revalidate()
		couche2.revalidate()
		couche3.revalidate()
		couche1.repaint()
		couche2.repaint()
		couche3.repaint()
	}

	def resize(): Unit = {
		val cursorPos = resources.getSelectedComponent.asInstanceOf[JScrollPane].getVerticalScrollBar.getValue
		tabs.setPreferredSize(new Dimension(getWidth, getHeight / 5))
		tabs.setLocation(0, 0)
		val img = getMap.getFont
		val width = img.getWidth * 2
		val height = img.getHeight * 2
		mapPanel.setPreferredSize(new Dimension(width, height))
		mapPanel.setLocation(0, getHeight / 5)
		tabColl.setPreferredSize(new Dimension(width, height))
		tabColl.setLocation(0, getHeight / 5)
		resources.setPreferredSize(new Dimension(getWidth / 4 - 15, getHeight / 5 * 4 - 40))
		resources.setLocation(getWidth / 4 * 3, getHeight / 5)
		val scroll1 = resources.getComponent(0).asInstanceOf[JScrollPane]
		val scroll2 = resources.getComponent(1).asInstanceOf[JScrollPane]
		val scroll3 = resources.getComponent(2).asInstanceOf[JScrollPane]
		scroll1.getHorizontalScrollBar.setMaximum(0)
		scroll2.getHorizontalScrollBar.setMaximum(0)
		scroll3.getHorizontalScrollBar.setMaximum(0)
		drawResources()
		resources.getSelectedComponent.asInstanceOf[JScrollPane].getVerticalScrollBar.setValue(cursorPos)
	}

	def getMap: Map = map

	def getSelectedSprite: SpriteComp = selectedSprite

	def setSelectedSprite(sprite: SpriteComp): Unit = {
		this.selectedSprite = sprite
	}

	override def stateChanged(event: ChangeEvent): Unit = {
		if (event.getSource eq resources) {
			if (getSelectedLayerIndex == 0) {
				resources.setBackgroundAt(0, Color.white)
				resources.setBackgroundAt(1, Color.white)
				resources.setBackgroundAt(2, Color.white)
			}
			else if (getSelectedLayerIndex == 1) {
				resources.setBackgroundAt(0, Color.black)
				resources.setBackgroundAt(1, Color.white)
				resources.setBackgroundAt(2, Color.white)
			}
			else if (getSelectedLayerIndex == 2) {
				resources.setBackgroundAt(0, Color.black)
				resources.setBackgroundAt(1, Color.black)
				resources.setBackgroundAt(2, Color.white)
			}
			repaint()
		}
		else if (event.getSource eq tabs) {
			resources.setEnabled(tabs.getSelectedIndex == 0)
			couche1.setEnabled(resources.isEnabled)
			couche2.setEnabled(resources.isEnabled)
			couche3.setEnabled(resources.isEnabled)
			repaint()
		}
	}

	def getSelectedLayerIndex: Int = resources.getSelectedIndex

	override def actionPerformed(event: ActionEvent): Unit = {
		if (event.getSource eq save) EditorAPI.save(RawMap.create(map))
		else if (event.getSource eq saveAs) EditorAPI.saveAs(RawMap.create(map))
		else if (event.getSource eq exit) {
			val result = JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder votre carte avant de quitter ? Toute modification sera perdue", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION)
			if (result == 0) save.doClick()
			if (result != 2) dispose()
		}
	}

	def getSelectedPaintingMode: Int = if (pen.isSelected) 0
	else if (pot.isSelected) 1
	else -1

	override def windowActivated(event: WindowEvent): Unit = {
	}

	override def windowClosed(event: WindowEvent): Unit = {
	}

	override def windowClosing(event: WindowEvent): Unit = {
		val result = JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder avant de quitter ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION)
		if (result == 0) EditorAPI.save(RawMap.create(map))
		if (result != 2) dispose()
	}

	override def windowDeactivated(event: WindowEvent): Unit = {
	}

	override def windowDeiconified(event: WindowEvent): Unit = {
	}

	override def windowIconified(event: WindowEvent): Unit = {
	}

	override def windowOpened(event: WindowEvent): Unit = {
	}
}
