package galaxyoyo.unknown.api.editor;

import galaxyoyo.unknown.api.editor.sprites.Sprite;

public class Case
{
	private int x;
	private int y;
	private Sprite couche1;
	private Sprite couche2;
	private Sprite couche3;
	private Collision collision;
	
	public int getPosX()
	{
		return x;
	}
	
	public int getPosY()
	{
		return y;
	}
	
	public Sprite getCoucheOne()
	{
		return couche1;
	}
	
	public Sprite getCoucheTwo()
	{
		return couche2;
	}
	
	public Sprite getCoucheThree()
	{
		return couche3;
	}
	
	public Collision getCollision()
	{
		return collision;
	}
	
	public static Case create(int posX, int posY, Sprite couche1,  Sprite couche2, Sprite couche3, Collision collision)
	{
		Case c = new Case();
		c.x = posX;
		c.y = posY;
		c.couche1 = couche1;
		c.couche2 = couche2;
		c.couche3 = couche3;
		c.collision = collision;
		return c;
	}
}
