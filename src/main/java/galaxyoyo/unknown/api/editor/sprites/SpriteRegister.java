package galaxyoyo.unknown.api.editor.sprites;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	private static Map<String, List<Sprite>> sprites = new HashMap<String, List<Sprite>>();
	
	@SuppressWarnings("unchecked")
	public static void refreshAllSprites()
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(SpriteRegister.class.getResourceAsStream("/assets/unknown/textures/sprites/sprites.json")));
			nameToCoords =  new Gson().fromJson(br, Map.class);
			System.out.println(nameToCoords);
			br.close();
			
			for (String key : nameToCoords.keySet())
			{
				try
				{
					InputStream is = SpriteRegister.class.getResourceAsStream("/assets/unknown/textures/sprites/" + key + ".png");
					BufferedImage img = ImageIO.read(is);
					List<Sprite> lSprites = new ArrayList<Sprite>();
					
					for (List<Double> list : nameToCoords.get(key))
					{
						int x = list.get(0).intValue();
						int y = list.get(1).intValue();
						int width = list.get(2).intValue() * 16;
						int height = list.get(3).intValue() * 16;
						BufferedImage child = img.getSubimage(x, y, width, height);
						Sprite sprite = new Sprite(child);
						lSprites.add(sprite);
					}
					
					sprites.put(key, lSprites);
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
	
	public static List<Sprite> getCategory(String name)
	{
		return sprites.get(name);
	}
	
	public static List<Sprite> getCategory(int index)
	{
		return getCategory(new ArrayList<String>(sprites.keySet()).get(index));
	}
}
