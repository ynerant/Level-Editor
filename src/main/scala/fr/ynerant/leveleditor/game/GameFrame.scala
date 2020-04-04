package fr.ynerant.leveleditor.game

import java.awt._
import java.awt.event.{MouseEvent, MouseListener}
import java.util.Random

import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister
import fr.ynerant.leveleditor.api.editor.{Collision, RawMap}
import fr.ynerant.leveleditor.editor.CollidPanel
import fr.ynerant.leveleditor.game.mobs.Mob
import fr.ynerant.leveleditor.game.towers.{AutoTower, BasicTower, NullTower, Tower}
import javax.swing._

import scala.collection.mutable.ListBuffer


class GameFrame(val map: RawMap) extends JFrame("Jeu") {
	final private val RANDOM = new Random
	private var round = 0
	private var hp = 5
	private var reward = 20
	private var mobs = ListBuffer[Mob]()
	private var towers = ListBuffer[Tower]()
	private val pathFinder = PathFinder(this)
	final private var basicTower = null: JRadioButton
	final private var nullTower = null: JRadioButton
	final private var autoTower = null: JRadioButton
	final private var waveLabel = null: JLabel
	final private var nbMobsLabel = null: JLabel
	final private var hpLabel = null: JLabel
	final private var rewardLabel = null: JLabel
	final private var winLabel = null: JLabel

	this.setSize(600, 600)
	this.setPreferredSize(getSize)
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
	this.setExtendedState(Frame.MAXIMIZED_BOTH)
	this.setLocationRelativeTo(null)
	val root = new JPanel
	root.setLayout(new BorderLayout)
	this.setContentPane(root)
	val pane = new JPanel
	pane.setLayout(new GridLayout(8, 1))
	root.add(pane, BorderLayout.SOUTH)
	val grid = new Grid()
	grid.setSize(map.getWidth, map.getHeight)
	grid.setPreferredSize(grid.getSize)
	grid.setMinimumSize(grid.getSize)
	grid.setMaximumSize(grid.getSize)
	root.add(grid, BorderLayout.CENTER)
	val towerSelect = new ButtonGroup
	basicTower = new JRadioButton("Tour basique (" + new BasicTower(0, 0).getPrice + " pièces)")
	basicTower.setSelected(true)
	towerSelect.add(basicTower)
	pane.add(basicTower)
	nullTower = new JRadioButton("Tour nulle (" + new NullTower(0, 0).getPrice + " pièces)")
	towerSelect.add(nullTower)
	pane.add(nullTower)
	autoTower = new JRadioButton("Tour automatique (" + new AutoTower(0, 0).getPrice + " pièces)")
	towerSelect.add(autoTower)
	pane.add(autoTower)
	waveLabel = new JLabel
	pane.add(waveLabel)
	nbMobsLabel = new JLabel
	pane.add(nbMobsLabel)
	hpLabel = new JLabel
	pane.add(hpLabel)
	rewardLabel = new JLabel
	pane.add(rewardLabel)
	winLabel = new JLabel
	pane.add(winLabel)
	setVisible(true)
	new Thread(() => {
		pathFinder.calculatePath()

		while (hp > 0 && (round < 4 || mobs.nonEmpty)) {
			try
				tick()
			catch {
				case e: Throwable =>
					e.printStackTrace()
			}

			try Thread.sleep(50L)
			catch {
				case e: InterruptedException =>
					e.printStackTrace()
			}
		}
	}).start()
	repaint()

	def getMap: RawMap = map

	def getPathFinder: PathFinder = pathFinder

	def tick(): Unit = {
		if (mobs.isEmpty && round < 4) {
			round += 1
			val nb_mobs = round * (RANDOM.nextInt(8) + 1)
			for (_ <- 1 to nb_mobs) {
				val mob = Mob.getRandomMob
				do mob.move(RANDOM.nextInt(getMap.getWidth / 16), RANDOM.nextInt(getMap.getHeight / 16)) while ( {
					!getMap.getCase(mob.getX, mob.getY).getCollision.equals(Collision.ANY)
				})
				getMap.getCase(mob.getX, mob.getY).setCollision(Collision.PARTIAL)
				mobs += mob
			}
		}
		towers.foreach(tower => {
			tower.filterDetectedMobs(mobs).foreach(mob => {
				mob.hit(tower.getDamagePerShot)
			})
		})
		mobs.foreach(mob => {
			getMap.getCase(mob.getX, mob.getY).setCollision(Collision.ANY)
			mob.tick(this)
			if (mob.getX < 0 || mob.isDead) {
				mobs -= mob
				if (mob.getX < 0) {
					hp -= 1
					if (hp == 0) {
						winLabel.setForeground(Color.red)
						winLabel.setText("Vous avez perdu !")
						return
					}
				}
				else reward += mob.getReward
			}
			else getMap.getCase(mob.getX, mob.getY).setCollision(Collision.PARTIAL)
		})
		waveLabel.setText("Vague " + round)
		nbMobsLabel.setText(mobs.size + " mob" + (if (mobs.size > 1) "s"
		else "") + " restant" + (if (mobs.size > 1) "s"
		else ""))
		hpLabel.setText("Points de vie : " + hp)
		rewardLabel.setText("Butin : " + reward)
		if (round == 4 && mobs.isEmpty) {
			winLabel.setForeground(Color.green.darker)
			winLabel.setText("Vous avez gagné !")
		}
	}

	class Grid() extends JComponent with MouseListener {
		addMouseListener(this)

		override def mouseReleased(event: MouseEvent): Unit = {
			val x = event.getX / 32
			val y = event.getY / 32
			val tower = if (basicTower.isSelected) new BasicTower(x, y)
			else if (nullTower.isSelected) new NullTower(x, y)
			else if (autoTower.isSelected) new AutoTower(x, y)
			else null
			if (tower == null || tower.getPrice > reward) return
			val c = getMap.getCase(x, y)
			if (c == null || !c.getCollision.equals(Collision.ANY)) return
			c.setCollision(Collision.FULL)

			pathFinder.invalidate()

			val accessible = getMap.getCases.filter(c => !Collision.FULL.equals(c.getCollision))
			if (accessible.exists(c => c.getPosX > 0 && pathFinder.nextPos(c.getPosX, c.getPosY) == null) || !accessible.exists(c => c.getPosX == 0 && !c.getCollision.equals(Collision.FULL))) {
				println(accessible.exists(c => c.getPosX > 0 && pathFinder.nextPos(c.getPosX, c.getPosY) == null))
				println(!accessible.exists(c => c.getPosX == 0 && pathFinder.nextPos(c.getPosX, c.getPosY) != null))
				// We ensure that the end of the game is accessible from everywhere, the tower should not block the game
				c.setCollision(Collision.ANY)
				pathFinder.invalidate()
			}
			else {
				reward -= tower.getPrice
				towers += tower
			}
		}

		override def mouseClicked(event: MouseEvent): Unit = {
		}

		override def mousePressed(event: MouseEvent): Unit = {
		}

		override protected def paintComponent(_g: Graphics): Unit = {
			val g = _g.asInstanceOf[Graphics2D]
			if (getMap.getFont != null) g.drawImage(getMap.getFont, null, null)
			SpriteRegister.refreshAllSprites()
			val SPRITE_SIZE = 32
			getMap.getCases.foreach(c => {
				val s1 = SpriteRegister.getCategory(c.getCoucheOne.getCategory).getSprites(c.getCoucheOne.getIndex)
				val s2 = SpriteRegister.getCategory(c.getCoucheTwo.getCategory).getSprites(c.getCoucheTwo.getIndex)
				val s3 = SpriteRegister.getCategory(c.getCoucheThree.getCategory).getSprites(c.getCoucheThree.getIndex)
				g.drawImage(s1.getImage, SPRITE_SIZE * c.getPosX, SPRITE_SIZE * c.getPosY, SPRITE_SIZE, SPRITE_SIZE, Color.white, null)
				if (!CollidPanel.isEmpty(s2.getImage)) g.drawImage(s2.getImage, SPRITE_SIZE * c.getPosX, SPRITE_SIZE * c.getPosY, SPRITE_SIZE, SPRITE_SIZE, null, null)
				if (!CollidPanel.isEmpty(s3.getImage)) g.drawImage(s3.getImage, SPRITE_SIZE * c.getPosX, SPRITE_SIZE * c.getPosY, SPRITE_SIZE, SPRITE_SIZE, null, null)
			})
			mobs.foreach(mob => {
				val s = mob.getSprite
				g.drawImage(s.getImage, SPRITE_SIZE * mob.getX, SPRITE_SIZE * mob.getY, SPRITE_SIZE, SPRITE_SIZE, null, null)
			})
			towers.foreach(tower => {
				val s = tower.getSprite
				g.drawImage(s.getImage, SPRITE_SIZE * tower.getX, SPRITE_SIZE * tower.getY, SPRITE_SIZE, SPRITE_SIZE, null, null)
			})
			repaint()
		}

		override def mouseEntered(event: MouseEvent): Unit = {
		}

		override def mouseExited(event: MouseEvent): Unit = {
		}
	}

}
