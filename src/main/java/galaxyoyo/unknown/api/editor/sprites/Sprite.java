package galaxyoyo.unknown.api.editor.sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite
{
	public static final Sprite BLANK = new Sprite(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), Category.create("blank", 0, new ArrayList<Sprite>()));
	private Category cat;
	
	private final BufferedImage img;
	
	public Sprite(BufferedImage img, Category cat)
	{
		this.img = img;
		this.cat = cat;
		
		if (!this.cat.getSprites().contains(this))
			this.cat.getSprites().add(this);
	}
	
	public BufferedImage getImage()
	{
		return this.img;
	}
	
	public Category getCategory()
	{
		return cat;
	}
}
