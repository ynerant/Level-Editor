package galaxyoyo.unknown.api.editor.sprites;

import java.awt.image.BufferedImage;

public class Sprite
{
	public static final Sprite BLANK = new Sprite(new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB));
	
	private final BufferedImage img;
	
	public Sprite(BufferedImage img)
	{
		this.img = img;
	}
	
	public BufferedImage getImage()
	{
		return this.img;
	}
}
