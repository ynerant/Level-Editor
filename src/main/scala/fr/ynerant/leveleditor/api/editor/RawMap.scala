package fr.ynerant.leveleditor.api.editor

import java.awt.image.BufferedImage

import fr.ynerant.leveleditor.editor.GMap
import net.liftweb.json._


object RawMap {
	def create(cases: List[RawCase], width: Int, height: Int): RawMap = {
		new RawMap(cases, width, height)
	}

	def create(map: GMap): RawMap = {
		val raw = new RawMap(Nil, map.getWidth, map.getHeight)
		map.getAllCases.foreach(c => raw.cases ::= RawCase.create(c))
		raw
	}
}

case class RawMap(var cases: List[RawCase], var width: Int, var height: Int) {
	private var cases_map = null: Map[Integer, RawCase]
	private var font = null: BufferedImage

	def getNeighbours(c: RawCase): Iterable[RawCase] = {
		var list = Nil: List[RawCase]
		list ::= getCase(c.getPosX, c.getPosY - 1)
		list ::= getCase(c.getPosX + 1, c.getPosY)
		list ::= getCase(c.getPosX, c.getPosY + 1)
		list ::= getCase(c.getPosX - 1, c.getPosY)
		list ::= getCase(c.getPosX - 1, c.getPosY + 1)
		list ::= getCase(c.getPosX - 1, c.getPosY - 1)
		list ::= getCase(c.getPosX + 1, c.getPosY + 1)
		list ::= getCase(c.getPosX + 1, c.getPosY - 1)
		list.filter((_c: RawCase) => _c != null)
	}

	def getCase(x: Int, y: Int): RawCase = {
		if (cases_map == null) {
			cases_map = Map()
			getCases.foreach(c => cases_map = cases_map.updated(c.getPosY * width + c.getPosX, c))
		}
		cases_map.getOrElse(y * getWidth + x, null)
	}

	def getCases: List[RawCase] = cases

	def getWidth: Int = width

	def getHeight: Int = height

	def getFont: BufferedImage = font

	def setFont(font: BufferedImage): Unit = {
		this.font = font
	}

	override def toString: String = {
		implicit val formats: DefaultFormats.type = DefaultFormats
		Serialization.writePretty(this)
	}
}
