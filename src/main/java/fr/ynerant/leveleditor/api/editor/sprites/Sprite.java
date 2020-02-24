package fr.ynerant.leveleditor.api.editor.sprites;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite
{
	public static final Sprite BLANK = new Sprite(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), Category.create("blank", 0, new ArrayList<Sprite>()), 0);
	
	static
	{
		Graphics2D g = BLANK.getImage().createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.setColor(new Color(0, true));
		g.fillRect(0, 0, 16, 16);
	}
	
	private final Category cat;
	private final BufferedImage img;
	private final int index;
	
	public Sprite(BufferedImage img, Category cat, int index)
	{
		this.img = img;
		this.cat = cat;
		this.index = index;
		
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
	
	public int getIndex()
	{
		return index;
	}
	
	@Override
	public int hashCode()
	{
		return cat.hashCode() ^ getIndex();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Sprite))
			return false;
		
		Sprite other = (Sprite) o;
		
		return hashCode() == other.hashCode();
	}
	
	@Override
	public String toString()
	{
		return "{Sprite img=" + img + " cat=" + cat.getName() + "}";
	}
}
