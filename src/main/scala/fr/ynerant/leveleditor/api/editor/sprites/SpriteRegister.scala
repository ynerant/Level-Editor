package fr.ynerant.leveleditor.api.editor.sprites

import java.io.{BufferedInputStream, BufferedReader, File, FileInputStream, IOException, InputStreamReader}
import java.net.{URISyntaxException, URLDecoder}
import java.nio.file.{Files, Paths}
import java.util
import java.util.Objects
import java.util.jar.JarFile

import com.google.gson.Gson
import javax.imageio.ImageIO
import fr.ynerant.leveleditor.client.main.Main

object SpriteRegister {
	private var nameToCoords = null: util.Map[String, util.List[util.List[Double]]]
	private val sprites = new util.HashMap[String, Category]

	@throws[IOException]
	def unpack(): Unit = {
		if (Main.isInDevelopmentMode) try {
			val dir = new File(getClass.getResource("/assets").toURI)
			unpackDir(dir)
		} catch {
			case e: URISyntaxException =>
				e.printStackTrace()
		}
		else {
			@SuppressWarnings(Array("deprecation")) val path = URLDecoder.decode(getClass.getProtectionDomain.getCodeSource.getLocation.getPath, "UTF-8")
			val jarFile = new File(path)
			if (jarFile.isFile) {
				val jar = new JarFile(jarFile)
				val entries = jar.entries
				while ( {
					entries.hasMoreElements
				}) {
					val je = entries.nextElement
					val name = je.getName
					if (name.startsWith("assets/")) {
						val f = new File(name)
						if (name.endsWith("/")) if (!f.mkdirs && !f.isDirectory) throw new IOException("Unable to make dir: " + f)
						else if (!f.isFile) Files.copy(jar.getInputStream(je), Paths.get(f.toURI))
					}
				}
				jar.close()
			}
		}
	}

	@throws[IOException]
	private def unpackDir(dir: File): Unit = {
		for (f <- Objects.requireNonNull(dir.listFiles)) {
			if (f.isDirectory) {
				unpackDir(f)
			}
			else {
				val path = f.getAbsolutePath.substring(f.getAbsolutePath.indexOf(File.separatorChar + "assets") + 1)
				val local = new File(path)
				assert(local.getParentFile.isDirectory || local.getParentFile.mkdirs)
				assert(!local.exists || local.delete)
				Files.copy(Paths.get(f.toURI), Paths.get(local.toURI))
			}
		}
	}

	@SuppressWarnings(Array("unchecked")) def refreshAllSprites(): Unit = {
		if (nameToCoords != null && !nameToCoords.isEmpty && !sprites.isEmpty) return
		val assetsDir = new File("assets")
		val assets = new util.ArrayList[String]
		for (dir <- Objects.requireNonNull(assetsDir.listFiles)) {
			assets.add(dir.getName)
		}

		assets.forEach(asset => {
			try {
				val f = new File(assetsDir.getAbsolutePath + "/" + asset + "/textures/sprites")
				assert(f.isDirectory || f.mkdirs)
				val br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(f, "sprites.json"))))
				nameToCoords = new Gson().fromJson(br, classOf[util.Map[_, _]])
				br.close()
				nameToCoords.keySet.forEach(key => {
					try {
						val is = new BufferedInputStream(new FileInputStream(new File(f, key + ".png")))
						val img = ImageIO.read(is)
						val cat = Category.create(key, new util.ArrayList[Sprite])
						nameToCoords.get(key).forEach(list => {
							val x = list.get(0).intValue
							val y = list.get(1).intValue
							val child = img.getSubimage(x, y, 16, 16)
							new Sprite(child, cat, nameToCoords.get(key).indexOf(list))
						})
						sprites.put(key, cat)
					} catch {
						case t: Throwable =>
							System.err.println("Erreur lors de la lecture du sprite '" + key + "'")
							t.printStackTrace()
					}
				})
			} catch {
				case e: IOException =>
					e.printStackTrace()
			}
		})
	}

	def getCategory(name: String): Category = sprites.get(name)

	def getAllCategories = new util.ArrayList[Category](sprites.values)
}
