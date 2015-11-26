package com.mattdahepic.autooredictconv.config;

import com.mattdahepic.mdecore.config.Config;
import net.minecraftforge.common.config.Configuration;

public class OptionsConfig extends Config {
    public static boolean enableBlock = false;
    public static boolean enableKeypress = false;

    @Override
    public void processConfig (Configuration c) {
        enableBlock = c.getBoolean("enableBlock",Configuration.CATEGORY_GENERAL,enableBlock,"If true, enable a block that converts any items pumped into it. Useful for storage systems.");
        enableKeypress = c.getBoolean("enableKeypress", Configuration.CATEGORY_GENERAL, enableKeypress, "If true, conversions will only occur upon pressing the definded key (defaults to c).");
    }
}
