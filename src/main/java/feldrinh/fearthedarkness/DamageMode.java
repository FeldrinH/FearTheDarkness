package feldrinh.fearthedarkness;

public enum DamageMode
{
	CONSTANT
	{
		@Override
		public float getDamage(float damage, int threshold, int light)
		{
			return damage;
		}
	},
	LINEARLIGHT
	{
		@Override
		public float getDamage(float damage, int threshold, int light)
		{
			return damage * (threshold - light + 1);
		}
	},
	EXPLIGHT
	{
		@Override
		public float getDamage(float damage, int threshold, int light)
		{
			return damage * (1 << (threshold - light));
		}
	};

	public abstract float getDamage(float damage, int threshold, int light);

	public static DamageMode valueOfOrDefault(String value, DamageMode defaultValue)
	{
		try
		{
			return valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			return defaultValue;
		}
	}
}
