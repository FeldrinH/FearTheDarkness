package feldrinh.fearthedarkness;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import feldrinh.fearthedarkness.utility.LogHelper;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class FTDConfig
{
	public float damage;
	public float deepDamage;

	public float damageDelta;
	public float deepDamageDelta;
	
	public float[] damageTable;
	public float[] deepDamageTable;
	
	public DamageMode damageMode;
	public DamageMode deepDamageMode;
	
	public int lightLevel;
	public int deepLightLevel;

	public int cooldown;
	public int deepCooldown;
	
	
	public static final DamageSource darkness = new DamageSource("darkness");
	public static final DamageSource deepDarkness = new DamageSource("deepDarkness");
	
	public static boolean supressRedFlash;

	public static final FTDConfig defaultConfig = new FTDConfig();

	private static final Map<Integer, FTDConfig> configs = new HashMap();

	public float getDamage(int light)
	{
		return damageMode.getDamage(damage, damageDelta, lightLevel, light, damageTable);
	}
	public float getDeepDamage(int light)
	{
		return deepDamageMode.getDamage(deepDamage, deepDamageDelta, deepLightLevel, light, deepDamageTable);
	}
	
	public static FTDConfig getConfigOrDefault(int dimension)
	{
		return configs.getOrDefault(dimension, defaultConfig);
	}
	
	private static float[] convertDamageTable(double[] input)
	{
	    float[] output = new float[input.length];
	    for (int i = 0; i < input.length; i++)
	    {
	        output[i] = (float)input[i];
	    }
	    return output;
	}
	
	private static double[] deconvertDamageTable(float[] input)
	{
	    double[] output = new double[input.length];
	    for (int i = 0; i < input.length; i++)
	    {
	        output[i] = input[i];
	    }
	    return output;
	}
	
	public static void configBackwardsCompatibility(Configuration config, String catName)
	{
		config.renameProperty(catName, "DarknessDamage", "Damage");
		config.renameProperty(catName, "DeepDarknessDamage", "Damage");
		config.renameProperty(catName, "DeepLightLevel", "DeepLightThreshold");
		config.renameProperty(catName, "LightLevel", "LightThreshold");
	}
	
	public static void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();

		
		//Load Darkness damage configuration
		configBackwardsCompatibility(config, "default");
		
		config.setCategoryComment("default", "Default configuration" + Configuration.NEW_LINE + "Can be overriden per-dimension, by creating a category with dimension id as name and changing options in there");

		config.setCategoryPropertyOrder("default", new ArrayList(Arrays.asList("LightThreshold", "Cooldown", "DamageMode", "Damage", "DamageDelta", "DamageTable", "DeepLightThreshold", "DeepCooldown", "DeepDamageMode", "DeepDamage", "DeepDamageDelta", "DeepDamageTable")));

		defaultConfig.damage = (float)config.get("default", "Damage", 2.0D, "Amount of darkness damage to deal. Set to 0.0 with DamageMode CONSTANT to disable").getDouble();
		defaultConfig.damageDelta = (float)config.get("default", "DamageDelta", 2.0D, "Used with DamageMode LINEARLIGHT and EXPLIGHT to modify damage based on light level, see DamageMode for details").getDouble();
		defaultConfig.damageTable = convertDamageTable(config.get("default", "DamageTable", new double[]{7,6,5,4,3,2,1}, "Used with DamageMode TABLELIGHT. List of damage values, each corresponding to a light level from threshold darker.\nElement to access is calculated: threshold - light, where 0 is the 1st element.\nIf element is out of range, the last (corresponding to darkest light level) element is returned. If list is empty, Damage is returned.\nDefault value has corresponding light levels (assuming default LightLevel) as values.").getDoubleList());
		defaultConfig.damageMode = DamageMode.valueOfOrDefault(config.get("default", "DamageMode", "CONSTANT", "Method of calculating damage:\nCONSTANT - Unmodified Damage value. (Damage)\nLINEARLIGHT - Damage increases by DamageDelta each light level darker. (Damage + DamageDelta * (Threshold - Light))\nEXPLIGHT - Damage is multiplied by DamageDelta each light level darker. (Damage * DamageDelta^(Threshold - Light))\nTABLELIGHT - Damage value is taken from DamageTable").getString(), DamageMode.CONSTANT);
		defaultConfig.cooldown = config.get("default", "Cooldown", 20, "Duration of cooldown (in ticks) after dealing darkness damage").setMinValue(1).getInt();
		defaultConfig.lightLevel = config.get("default", "LightThreshold", 7, "Light level threshold below or equal to which to deal darkness damage").getInt();

		defaultConfig.deepDamage = (float)config.get("default", "DeepDamage", 5.0, "Amount of deep darkness damage to deal. Set to 0.0 with DeepDamageMode CONSTANT to disable").getDouble();
		defaultConfig.deepDamageDelta = (float)config.get("default", "DeepDamageDelta", 2.0D, "Used with DamageMode LINEARLIGHT and EXPLIGHT to modify damage based on light level, see DamageMode for details").getDouble();
		defaultConfig.deepDamageTable = convertDamageTable(config.get("default", "DeepDamageTable", new double[]{0}, "Used with DamageMode TABLELIGHT. List of damage values, each corresponding to a light level from threshold darker.\nElement to access is calculated: threshold - light, where 0 is the 1st element.\nIf element is out of range, the last (corresponding to darkest light level) element is returned. If list is empty, Damage is returned.\nDefault value has corresponding light levels (assuming default DeepLightLevel) as values.").getDoubleList());
		defaultConfig.deepDamageMode = DamageMode.valueOfOrDefault(config.get("default", "DeepDamageMode", "CONSTANT", "Method of calculating damage:\nCONSTANT - Unmodified Damage value. (Damage)\nLINEARLIGHT - Damage increases by DamageDelta each light level darker. (Damage + DamageDelta * (Threshold - Light))\nEXPLIGHT - Damage is multiplied by DamageDelta each light level darker. (Damage * DamageDelta^(Threshold - Light))\nTABLELIGHT - Damage value is taken from DamageTable").getString(), DamageMode.CONSTANT);
		defaultConfig.deepCooldown = config.get("default", "DeepCooldown", 20, "Duration of cooldown (in ticks) after dealing deep darkness damage").setMinValue(1).getInt();
		defaultConfig.deepLightLevel = config.get("default", "DeepLightThreshold", 0, "Light level threshold below or equal to which to deal deep darkness damage").getInt();
		
		if(defaultConfig.damageMode == DamageMode.TABLELIGHT && defaultConfig.damageTable.length == 0)
		{
			defaultConfig.damageMode = DamageMode.CONSTANT;
		}
		if(defaultConfig.deepDamageMode == DamageMode.TABLELIGHT && defaultConfig.deepDamageTable.length == 0)
		{
			defaultConfig.deepDamageMode = DamageMode.CONSTANT;
		}
		
		config.setCategoryComment("global", "Global configuration" + Configuration.NEW_LINE + "Can not be overriden per-dimension");

		config.setCategoryPropertyOrder("global", new ArrayList(Arrays.asList("BypassArmor", "IsAbsolute", "DeepBypassArmor", "DeepIsAbsolute", "SupressRedFlash")));

		if(config.get("global", "BypassArmor", true, "Darkness damage bypasses armor protection. If false, also damages armor").getBoolean())
		{
			darkness.setDamageBypassesArmor();
		}
		if(config.get("global", "IsAbsolute", true, "Darkness damage bypasses protecting potion effects and armor enchantments").getBoolean())
		{
			darkness.setDamageIsAbsolute();
		}
		
		if(config.get("global", "DeepBypassArmor", true, "Deep darkness damage bypasses armor protection. If false, also damages armor").getBoolean())
		{
			deepDarkness.setDamageBypassesArmor();
		}
		if(config.get("global", "DeepIsAbsolute", true, "Deep darkness damage bypasses protecting potion effects and armor enchantments").getBoolean())
		{
			deepDarkness.setDamageIsAbsolute();
		}
		
		supressRedFlash = config.get("global", "SupressRedFlash", true, "Supress player turning red on darkness and deep darkness damage").getBoolean();
		
		
		if (config.hasChanged())
		{
			config.save();
		}

		
		for (String catName : config.getCategoryNames())
		{
			if (!catName.equals("default") && !catName.equals("global"))
			{
				try
				{
					int dimension = Integer.parseInt(catName);
					FTDConfig conf = new FTDConfig();
					LogHelper.log(Level.INFO, "Found config for dimension " + dimension);
					
					configBackwardsCompatibility(config, catName);
					
					conf.damage = (float)config.get(catName, "Damage", defaultConfig.damage).getDouble();
					conf.damageDelta = (float)config.get(catName, "DamageDelta", defaultConfig.damageDelta).getDouble();
					conf.damageTable = convertDamageTable(config.get(catName, "DamageTable", deconvertDamageTable(defaultConfig.damageTable)).getDoubleList());
					conf.damageMode = DamageMode.valueOfOrDefault(config.get(catName,"DamageMode", defaultConfig.damageMode.toString()).getString(), FTDConfig.defaultConfig.damageMode);
					conf.cooldown =  config.get(catName, "Cooldown", defaultConfig.cooldown).setMinValue(1).getInt();
					conf.lightLevel = config.get(catName, "LightThreshold", defaultConfig.lightLevel).getInt();

					conf.deepDamage = (float)config.get(catName,"DeepDamage", defaultConfig.deepDamage).getDouble();
					conf.deepDamageDelta = (float)config.get(catName, "DeepDamageDelta", defaultConfig.deepDamageDelta).getDouble();
					conf.deepDamageTable = convertDamageTable(config.get(catName, "DeepDamageTable", deconvertDamageTable(defaultConfig.deepDamageTable)).getDoubleList());
					conf.deepDamageMode = DamageMode.valueOfOrDefault(config.get(catName, "DeepDamageMode", defaultConfig.deepDamageMode.toString()).getString(), FTDConfig.defaultConfig.deepDamageMode);
					conf.deepCooldown =  config.get(catName, "DeepCooldown", defaultConfig.deepCooldown).setMinValue(1).getInt();
					conf.deepLightLevel = config.get(catName, "DeepLightThreshold", defaultConfig.deepLightLevel).getInt();
				
					if(conf.damageMode == DamageMode.TABLELIGHT && conf.damageTable.length == 0)
					{
						conf.damageMode = DamageMode.CONSTANT;
					}
					if(conf.deepDamageMode == DamageMode.TABLELIGHT && conf.deepDamageTable.length == 0)
					{
						conf.deepDamageMode = DamageMode.CONSTANT;
					}
					
					configs.put(dimension, conf);
				}
				catch (NumberFormatException e)
				{
					LogHelper.log(Level.WARN, "Unused config category " + catName);
				}
			}
		}
		
		
		//Load miscellaneous features configuration
		config.setCategoryComment("features", "Miscellaneous features configuration");
		
		int shadowcloakId = 42; //Load from config
		boolean multilevelShadowcloak = true; //Load from config
		
		ShadowcloakEnchantment.self = new ShadowcloakEnchantment(shadowcloakId, multilevelShadowcloak ? 2 : 1);
	}
}
