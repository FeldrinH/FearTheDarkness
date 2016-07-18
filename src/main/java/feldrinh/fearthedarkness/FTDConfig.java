package feldrinh.fearthedarkness;

import java.io.File;
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

	public int lightLevel;
	public int deepLightLevel;

	public int cooldown;
	public int deepCooldown;
	
	
	public static final DamageSource darkness = new DamageSource("darkness");
	public static final DamageSource deepDarkness = new DamageSource("deepDarkness");
	
	public static boolean supressRedFlash;

	public static final FTDConfig defaultConfig = new FTDConfig();

	private static final Map<Integer, FTDConfig> configs = new HashMap();

	public static FTDConfig getConfigOrDefault(int dimension)
	{
		return configs.getOrDefault(dimension, defaultConfig);
	}
	
	public static void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();

		
		config.setCategoryComment("default", "Default configuration" + Configuration.NEW_LINE + "Can be overriden per-dimension, by creating a category with dimension id as name and changing options in there");

		config.setCategoryPropertyOrder("default", Arrays.asList("DarknessDamage", "Cooldown", "LightLevel", "DeepDarknessDamage", "DeepCooldown", "DeepLightLevel"));

		defaultConfig.damage = (float)config.get("default", "DarknessDamage", 2.0D, "Amount of darkness damage to deal. Set to 0.0 to disable").getDouble();
		defaultConfig.cooldown = config.get("default", "Cooldown", 20, "Duration of cooldown (in ticks) after dealing darkness damage").setMinValue(1).getInt();
		defaultConfig.lightLevel = config.get("default", "LightLevel", 7, "Light level below or equal to which to deal darkness damage").getInt();

		defaultConfig.deepDamage = (float)config.get("default", "DeepDarknessDamage", 5.0, "Amount of deep darkness damage to deal. Set to 0.0 to disable").getDouble();
		defaultConfig.deepCooldown = config.get("default", "DeepCooldown", 20, "Duration of cooldown (in ticks) after dealing deep darkness damage").setMinValue(1).getInt();
		defaultConfig.deepLightLevel = config.get("default", "DeepLightLevel", 0, "Light level below or equal to which to deal deep darkness damage").getInt();

		
		config.setCategoryComment("global", "Global configuration" + Configuration.NEW_LINE + "Can not be overriden per-dimension");

		config.setCategoryPropertyOrder("global", Arrays.asList("BypassArmor", "IsAbsolute", "DeepBypassArmor", "DeepIsAbsolute"));

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
					ConfigCategory cat = config.getCategory(catName);
					FTDConfig conf = new FTDConfig();
					LogHelper.log(Level.INFO, "Found config for dimension " + dimension);
					
					conf.damage = (float)cat.get("DarknessDamage").getDouble(FTDConfig.defaultConfig.damage);
					conf.cooldown =  cat.get("Cooldown").setMinValue(1).getInt(FTDConfig.defaultConfig.cooldown);
					conf.lightLevel = cat.get("LightLevel").getInt(FTDConfig.defaultConfig.lightLevel);

					conf.deepDamage = (float)cat.get("DeepDarknessDamage").getDouble(FTDConfig.defaultConfig.deepDamage);
					conf.deepCooldown = cat.get("DeepCooldown").setMinValue(1).getInt(FTDConfig.defaultConfig.deepCooldown);
					conf.deepLightLevel = cat.get("DeepLightLevel").getInt(FTDConfig.defaultConfig.deepLightLevel);
				
					configs.put(dimension, conf);
				}
				catch (NumberFormatException e)
				{
					LogHelper.log(Level.WARN, "Unused config category " + catName);
				}
			}
		}
	}
}
