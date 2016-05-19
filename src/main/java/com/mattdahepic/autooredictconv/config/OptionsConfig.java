package com.mattdahepic.autooredictconv.config;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import com.mattdahepic.mdecore.config.sync.Config;
import com.mattdahepic.mdecore.config.sync.ConfigSyncable;

public class OptionsConfig extends ConfigSyncable {
    public String getConfigVersion () {return "1";}
    public String getConfigName () {return AutoOreDictConv.MODID;}
    public Class getConfigClass () {return getClass();}

    @Config(comment = {"If true, enable a block that converts any items pumped into it.","Useful for storage systems."}, restartReq = Config.RestartReqs.REQUIRES_MC_RESTART)
    public static boolean enableBlock = false;
    @Config(comment = {"If true, conversions will only occur upon pressing the defined key (defaults to c)."}, restartReq = Config.RestartReqs.REQUIRES_MC_RESTART)
    public static boolean enableKeypress = false;
}
