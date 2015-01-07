package galaxyoyo.unknown.api.editor.sprites;

import java.awt.image.BufferedImage;

public class Sprite
{
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
