package fr.ynerant.leveleditor.editor

import java.awt.image.BufferedImage

import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister
import fr.ynerant.leveleditor.api.editor.{Case, RawMap}


object GMap {
	private var cases = Nil: List[Case]
}

class GMap(val raw: RawMap) {
	final private var frame = null: EditorFrame
	final private var width = 0
	final private var height = 0
	private var casesMap = Map(): Map[Int, Map[Int, Case]]
	final private var font = null: BufferedImage

	GMap.cases = Nil
	this.width = raw.getWidth
	this.height = raw.getHeight
	this.font = raw.getFont

	raw.getCases.foreach(rc =>
		GMap.cases = Case.create(rc.getPosX, rc.getPosY, SpriteRegister.getCategory(rc.getCoucheOne.getCategory).getSprites(rc.getCoucheOne.getIndex), SpriteRegister.getCategory(rc.getCoucheTwo.getCategory).getSprites(rc.getCoucheTwo.getIndex), SpriteRegister.getCategory(rc.getCoucheThree.getCategory).getSprites(rc.getCoucheThree.getIndex), rc.getCollision) :: GMap.cases
	)

	reorganizeMap()
	frame = new EditorFrame(this)
	getFrame.setVisible(true)

	def getFrame: EditorFrame = frame

	def getWidth: Int = width

	def getHeight: Int = height

	def getCase(x: Int, y: Int): Case = casesMap.getOrElse(x, Map())(y)

	def getAllCases: List[Case] = {
		val list = Nil: List[Case]
		casesMap.values.foreach(l => list.appendedAll(l.values))
		list
	}

	def getFont: BufferedImage = font

	private def reorganizeMap(): Unit = {
		for (i <- 0 until width) {
			casesMap += (i -> Map())
		}
		GMap.cases.foreach(c => setCase(c.getPosX, c.getPosY, c))
	}

	def setCase(x: Int, y: Int, c: Case): Unit = {
		var map = casesMap(x)
		map = map + (y -> c)
		casesMap = casesMap.updated(x, map)
	}
}
