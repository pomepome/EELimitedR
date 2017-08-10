package eelimitedr.utils.enums;

public enum EnumSounds
{
	BREAK(0,"random.break"),
	CHARGE(1,"eelimitedr:items.charge"),
	ACTION(2,"eelimitedr:items.action"),
	UNCHARGE(3,"eelimitedr:items.uncharge"),
	TRANSMUTE(4,"eelimitedr:items.transmute"),
	HEAL(5,"eelimitedr:items.heal"),
	CHARGETICK(5,"eelimitedr:items.chargetick");

	private int id;
	private String soundPath;
	private EnumSounds(int index,String path)
	{
		id = index;
		soundPath = path;
	}
	public int getID()
	{
		return id;
	}
	public String getPath()
	{
		return soundPath;
	}
	public static EnumSounds getFromID(int id)
	{
		return values()[id];
	}
}
