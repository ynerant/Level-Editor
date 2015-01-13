package galaxyoyo.unknown.api.editor.sprites;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;

import com.google.gson.Gson;

public class SpriteRegister
{
	private static Map<String, List<List<Double>>> nameToCoords;
	private static Map<String, Category> sprites = new HashMap<String, Category>();
	
	public static void unpack()
	{
		try
		{
			File dir = new File(SpriteRegister.class.getResource("/assets").toURI());
			unpackDir(dir);
		}
		catch (URISyntaxException | IOException e)
		{
			e.printStackTrace();
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
