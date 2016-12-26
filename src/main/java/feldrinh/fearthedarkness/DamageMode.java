package feldrinh.fearthedarkness;

public enum DamageMode
{
	CONSTANT
	{
		@Override
		public float getDamage(float damage, float damageDelta, int threshold, int light, float[] damageTable)
		{
			return damage;
		}
	},
	LINEARLIGHT
	{
		@Override
		public float getDamage(float damage, float damageDelta, int threshold, int light, float[] damageTable)
		{
			return damage + damageDelta * (threshold - light);
		}
	},
	EXPLIGHT
	{
		@Override
		public float getDamage(float damage, float damageDelta, int threshold, int light, float[] damageTable)
		{
			return damage * (float)Math.pow(damageDelta, threshold - light);
		}
	},
	TABLELIGHT
	{
		@Override
		public float getDamage(float damage, float damageDelta, int threshold, int light, float[] damageTable)
		{
			return damageTable[Math.min(threshold - light, damageTable.length - 1)];
		}
	},
	NONE
	{
		@Override
		public float getDamage(float damage, float damageDelta, int threshold, int light, float[] damageTable)
		{
			return 0;
		}
	};

	public abstract float getDamage(float damage, float damageDelta, int threshold, int light, float[] damageTable);
	
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
