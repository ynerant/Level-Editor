package fr.ynerant.leveleditor.api.editor

import java.awt.Color
import java.awt.image.BufferedImage
import java.io._
import java.nio.charset.StandardCharsets
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import fr.ynerant.leveleditor.editor.GMap
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import net.liftweb.json._

object EditorAPI {
	private var LAST_FILE = null: File

	def toRawMap(width: Int, height: Int): RawMap = {
		var cases = Nil: List[RawCase]
		var y = 1
		while ( {
			y < height
		}) {
			var x = 1
			while ( {
				x < width
			}) {
				val c = RawCase.create(x / 16, y / 16, RawSprite.BLANK, RawSprite.BLANK, RawSprite.BLANK, Collision.ANY)
				cases ::= c

				x += 16
			}

			y += 16
		}
		RawMap.create(cases, width, height)
	}

	def createJFC: JFileChooser = {
		val jfc = new JFileChooser
		jfc.setFileFilter(new FileNameExtensionFilter("Fichiers monde (*.gmap, *.dat)", "gmap", "dat"))
		jfc.setFileHidingEnabled(true)
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY)
		val dir = new File("maps")
		assert(dir.isDirectory || dir.mkdirs)
		jfc.setCurrentDirectory(dir)
		jfc
	}

	def saveAs(map: RawMap): Unit = {
		val jfc = createJFC
		var file = null: File
		jfc.showSaveDialog(null)
		file = jfc.getSelectedFile
		if (file == null) return
		if (!file.getName.toLowerCase.endsWith(".gmap") && !file.getName.toLowerCase.endsWith(".dat")) file = new File(file.getParentFile, file.getName + ".gmap")
		LAST_FILE = file
		save(file, map)
	}

	def save(map: RawMap): Unit = {
		if (LAST_FILE != null) save(LAST_FILE, map)
		else saveAs(map)
	}

	def save(file: File, map: RawMap): Unit = {
		implicit val formats: DefaultFormats.type = DefaultFormats
		val json = Serialization.writePretty(map)
		try {
			assert(file.createNewFile)
			val bos = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file)))
			bos.write(json.getBytes(StandardCharsets.UTF_8))
			bos.close()
		} catch {
			case ex: IOException =>
				ex.printStackTrace()
		}
	}

	def open: GMap = {
		val jfc = createJFC
		var file = null: File
		jfc.showOpenDialog(null)
		file = jfc.getSelectedFile
		if (file == null) return null
		LAST_FILE = file
		open(file)
	}

	def getRawMap(f: File): RawMap = {
		var json = null: String
		try {
			val gis = new GZIPInputStream(new BufferedInputStream(new FileInputStream(f)))
			val bytes = new Array[Byte](512 * 1024)
			var count = 0
			val text = new StringBuilder
			while ( {
				count = gis.read(bytes)
				count != -1
			}) text.append(new String(bytes, 0, count, StandardCharsets.UTF_8))
			gis.close()
			json = text.toString
		} catch {
			case e: IOException =>
				e.printStackTrace()
		}
		implicit val formats: DefaultFormats.type = DefaultFormats
		parse(json).extract[RawMap]
	}

	def open(f: File): GMap = open(getRawMap(f))

	def open(map: RawMap): GMap = {
		if (map.getFont == null) {
			val baseWidth = map.getWidth
			val baseHeight = map.getHeight
			val width = baseWidth + (baseWidth / 16) + 1
			val height = baseHeight + (baseHeight / 16) + 1
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
			map.setFont(image)
		}
		new GMap(map)
	}
}
