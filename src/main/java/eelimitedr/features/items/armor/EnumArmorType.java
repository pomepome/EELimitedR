package eelimitedr.features.items.armor;

public enum EnumArmorType
{
	HEAD("head"),
	CHEST("chest"),
	LEGS("legs"),
	FEET("feet");
	public final String name;
	EnumArmorType(String name)
	{
	    this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
