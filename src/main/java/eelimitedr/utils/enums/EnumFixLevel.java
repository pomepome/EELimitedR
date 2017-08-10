package eelimitedr.utils.enums;

public enum EnumFixLevel
{
	LOW(0),
	MID(1),
	HIGH(2);

	private int itemDamage;

	EnumFixLevel(int damage)
	{
		itemDamage = damage;
	}

	public int getItemDamage()
	{
		return itemDamage;
	}
}
