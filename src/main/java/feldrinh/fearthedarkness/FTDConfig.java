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

		
		config.setCategoryComment("default", "Default configuration" + Configuration.NEW_LINE + "Can be overriden per-dimension, by creating a category with dimension id as name and changing options in there.");

		config.setCategoryPropertyOrder("default", Arrays.asList("DarknessDamage", "Cooldown", "LightLevel", "DeepDarknessDamage", "DeepCooldown", "DeepLightLevel"));

		defaultConfig.damage = config.getFloat("DarknessDamage", "default", 2.0F, 0.0F, 100.0F, "Amount of darkness damage to deal. Set to 0.0 to disable");
		defaultConfig.cooldown = config.getInt("Cooldown", "default", 20, 0, 10000, "Duration of cooldown (in ticks) after dealing darkness damage");
		defaultConfig.lightLevel = config.getInt("LightLevel", "default", 7, 0, 15, "Light level below or equal to which to deal darkness damage");

		defaultConfig.deepDamage = config.getFloat("DeepDarknessDamage", "default", 5.0F, 0.0F, 100.0F, "Amount of deep darkness damage to deal. Set to 0.0 to disable");
		defaultConfig.deepCooldown = config.getInt("DeepCooldown", "default", 20, 0, 10000, "Duration of cooldown (in ticks) after dealing deep darkness damage");
		defaultConfig.deepLightLevel = config.getInt("DeepLightLevel", "default", 0, 0, 15, "Light level below or equal to which to deal deep darkness damage");

		
		config.setCategoryComment("global", "Global configuration" + Configuration.NEW_LINE + "Can not be overriden per-dimension.");

		config.setCategoryPropertyOrder("global", Arrays.asList("BypassArmor", "IsAbsolute", "DeepBypassArmor", "DeepIsAbsolute"));

		if(config.getBoolean("BypassArmor", "global", true, "Darkness damage bypasses armor protection. If false, also damages armor"))
		{
			darkness.setDamageBypassesArmor();
		}
		if(config.getBoolean("IsAbsolute", "global", true, "Darkness damage bypasses protecting potion effects and armor enchantments"))
		{
			darkness.setDamageIsAbsolute();
		}
		
		if(config.getBoolean("DeepBypassArmor", "global", true, "Deep darkness damage bypasses armor protection. If false, also damages armor"))
		{
			deepDarkness.setDamageBypassesArmor();
		}
		if(config.getBoolean("DeepIsAbsolute", "global", true, "Deep darkness damage bypasses protecting potion effects and armor enchantments"))
		{
			deepDarkness.setDamageIsAbsolute();
		}
		
		
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
					
					conf.damage = config.getFloat("DarknessDamage", catName, FTDConfig.defaultConfig.damage, 0.0F, 100.0F, "");
					conf.cooldown = config.getInt("Cooldown", catName, FTDConfig.defaultConfig.cooldown, 0, 10000, "");
					conf.lightLevel = config.getInt("LightLevel", catName, FTDConfig.defaultConfig.lightLevel, 0, 15, "");

					conf.deepDamage = config.getFloat("DeepDarknessDamage", catName, FTDConfig.defaultConfig.deepDamage, 0.0F, 100.0F, "");
					conf.deepCooldown = config.getInt("DeepCooldown", catName, FTDConfig.defaultConfig.deepCooldown, 0, 10000, "");
					conf.deepLightLevel = config.getInt("DeepLightLevel", catName, FTDConfig.defaultConfig.deepLightLevel, 0, 15, "");
				
					configs.put(dimension, conf);
				}
				catch (NumberFormatException e)
				{
					LogHelper.log(Level.WARN, "Unused config category " + catName + " in FearTheDarkness.cfg");
				}
			}
		}
	}
}
