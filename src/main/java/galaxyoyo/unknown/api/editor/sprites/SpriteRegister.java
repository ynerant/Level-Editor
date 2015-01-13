package galaxyoyo.unknown.api.editor.sprites;

import galaxyoyo.unknown.client.main.Main;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;

import com.google.gson.Gson;

public class SpriteRegister
{
	private static Map<String, List<List<Double>>> nameToCoords;
	private static Map<String, Category> sprites = new HashMap<String, Category>();
	
	public static void unpack() throws IOException, URISyntaxException
	{
		if (Main.isInDevelopmentMode())
		{
			File dir = new File(SpriteRegister.class.getResource("/assets").toURI());
			unpackDir(dir);
		}
		else
		{
			@SuppressWarnings("deprecation")
			String path = URLDecoder.decode(SpriteRegister.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			path = path.substring(1, path.length());
			File jarFile = new File(path);

			if(jarFile.isFile())
			{
				JarFile jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements())
				{
					JarEntry je = entries.nextElement();
					String name = je.getName();
					if (name.startsWith("assets/"))
					{
						File f = new File(name);
						if (name.endsWith("/"))
							f.mkdirs();
						else if (!f.isFile())
							Files.copy(jar.getInputStream(je), Paths.get(f.toURI()));
					}
				}
				jar.close();
			}
		}
	}
	
	private static void unpackDir(File dir) throws IOException
	{
		for (File f : dir.listFiles())
		{
			if (f.isDirectory())
			{
				unpackDir(f);
				continue;
			}
			
			String path = f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(File.separatorChar + "assets") + 1);
			File local = new File(path);
			local.getParentFile().mkdirs();
			if (local.exists())
				local.delete();
			Files.copy(Paths.get(f.toURI()), Paths.get(local.toURI()));
		}
	}

	@SuppressWarnings("unchecked")
	public static void refreshAllSprites()
	{
		if (nameToCoords != null && !nameToCoords.isEmpty() && !sprites.isEmpty())
		{
			return;
		}
		
		File assetsDir = new File("assets");
		List<String> assets = new ArrayList<String>();
		
		for (File dir : assetsDir.listFiles())
		{
			assets.add(dir.getName());
		}
		
		for (String asset : assets)
		{
			try
			{
				File f = new File(assetsDir.getAbsolutePath() + "/" + asset + "/textures/sprites");
				f.mkdirs();
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(f, "sprites.json"))));
				nameToCoords =  new Gson().fromJson(br, Map.class);
				br.close();
				
				for (String key : nameToCoords.keySet())
				{
					try
					{
						
						BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(f, key + ".png")));
						BufferedImage img = ImageIO.read(is);
						Category cat = Category.create(key, new ArrayList<String>(nameToCoords.keySet()).indexOf(key), new ArrayList<Sprite>());
						
						for (List<Double> list : nameToCoords.get(key))
						{
							int x = list.get(0).intValue();
							int y = list.get(1).intValue();
							BufferedImage child = img.getSubimage(x, y, 16, 16);
							new Sprite(child, cat, nameToCoords.get(key).indexOf(list));
						}
						
						sprites.put(key, cat);
					}
					catch (Throwable t)
					{
						LogManager.getLogger("SpriteRegister").fatal("Erreur lors de la lecture du sprite '" + key + "'", t);
						continue;
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static Category getCategory(String name)
	{
		return sprites.get(name);
	}
	
	public static Category getCategory(int index)
	{
		return getCategory(new ArrayList<String>(sprites.keySet()).get(index));
	}
	
	public static List<Category> getAllCategories()
	{
		return new ArrayList<Category>(sprites.values());
	}
	
	public static List<Sprite> getAllSprites()
	{
		List<Sprite> list = new ArrayList<Sprite>();
		
		for (Category c : sprites.values())
		{
			list.addAll(c.getSprites());
		}
		
		return list;
	}
}
