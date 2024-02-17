package com.michaelyvars.guacamole.configuration;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.configuration.options.Option;
import com.michaelyvars.guacamole.configuration.options.OptionBasic;
import de.leonhard.storage.Json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private final Guacamole plugin;
    private final Json configurationFile;

    public static final OptionBasic<String> GAME_NAME = new OptionBasic<>("game.name", new String[]{"desc"}, "Saison #");
    public static final OptionBasic<Integer> TIMER_EPISODE = new OptionBasic<>("timers.episode", new String[]{"desc"}, 1200); // 20 minutes
    public static final OptionBasic<Integer> TIMER_INVINCIBILITY = new OptionBasic<>("timers.invincibility", new String[]{"desc"}, 300); // 5 minutes
    public static final OptionBasic<Integer> TIMER_PVP = new OptionBasic<>("timers.pvp", new String[]{"desc"}, 1800); // 30 minutes
    public static final OptionBasic<Integer> TIMER_MOLES = new OptionBasic<>("timers.moles", new String[]{"desc"}, 3000); // 50 minutes
    public static final OptionBasic<Integer> TIMER_BORDER = new OptionBasic<>("timers.border", new String[]{"desc"}, 5400); // 90 minutes
    public static final OptionBasic<Integer> BORDER_START = new OptionBasic<>("border.start", new String[]{"desc"}, 2000);
    public static final OptionBasic<Integer> BORDER_END = new OptionBasic<>("border.end", new String[]{"desc"}, 100);
    public static final OptionBasic<Double> BORDER_SPEED = new OptionBasic<>("border.speed", new String[]{"desc"}, 1.0D);
    public static final OptionBasic<Integer> ENCHANTMENT_PROTECTION = new OptionBasic<>("enchantments.protection", new String[]{"desc"}, 3);
    public static final OptionBasic<Integer> ENCHANTMENT_SHARPNESS = new OptionBasic<>("enchantments.sharpness", new String[]{"desc"}, 3);
    public static final OptionBasic<Integer> ENCHANTMENT_KNOCKBACK = new OptionBasic<>("enchantments.knockback", new String[]{"desc"}, 1);
    public static final OptionBasic<Integer> ENCHANTMENT_POWER = new OptionBasic<>("enchantments.power", new String[]{"desc"}, 3);
    public static final OptionBasic<Integer> ENCHANTMENT_PUNCH = new OptionBasic<>("enchantments.punch", new String[]{"desc"}, 1);
    public static final OptionBasic<Integer> RATE_APPLE = new OptionBasic<>("rates.apple", new String[]{"desc"}, 5);
    public static final OptionBasic<Integer> RATE_FLINT = new OptionBasic<>("rates.flint", new String[]{"desc"}, 5);
    public static final OptionBasic<Integer> RATE_ENDER_PEARL = new OptionBasic<>("rates.ender-pearl", new String[]{"desc"}, 5);

    public Configuration(Guacamole plugin, String path) {
        this.plugin = plugin;
        this.configurationFile = new Json("configuration", path);

        for(Option<?> option : getOptions())
            option.getValue(configurationFile);
    }

    public <T> T get(Option<T> option) {
        return option.getValue(configurationFile);
    }

    public <T> void set(Option<T> option, T value) {
        option.setValue(configurationFile, value);
    }

    private List<Option<?>> getOptions() {
        List<Option<?>> options = new ArrayList<>();

        for(Field field : getClass().getFields()) {
            try {
                Object o = field.get(null);

                if(o instanceof Option)
                    options.add((Option<?>) o);

            } catch(IllegalAccessException e) {
                plugin.getCustomLogger().logError(e.getMessage());
            }
        }

        return options;
    }
}
