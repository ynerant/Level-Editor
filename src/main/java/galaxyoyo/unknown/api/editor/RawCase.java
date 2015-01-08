package galaxyoyo.unknown.api.editor;

public class RawCase
{
	private int x;
	private int y;
	private RawSprite couche1;
	private RawSprite couche2;
	private RawSprite couche3;
	
	public int getPosX()
	{
		return x;
	}
	
	public int getPosY()
	{
		return y;
	}
	
	public RawSprite getCoucheOne()
	{
		return couche1;
	}
	
	public RawSprite getCoucheTwo()
	{
		return couche2;
	}
	
	public RawSprite getCoucheThree()
	{
		return couche3;
	}
	
	public static RawCase create(int posX, int posY, RawSprite couche1,  RawSprite couche2, RawSprite couche3)
	{
		RawCase c = new RawCase();
		c.x = posX;
		c.y = posY;
		c.couche1 = couche1;
		c.couche2 = couche2;
		c.couche3 = couche3;
		return c;
	}
}
