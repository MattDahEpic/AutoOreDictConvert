package com.mattdahepic.autooredictconv.config;

import com.mattdahepic.mdecore.config.annot.Comment;
import com.mattdahepic.mdecore.config.annot.Config;
import com.mattdahepic.mdecore.config.annot.RestartReq;
import com.mattdahepic.mdecore.config.sync.ConfigProcessor;
import com.mattdahepic.mdecore.config.sync.ConfigSyncable;

public class OptionsConfig extends ConfigSyncable {
    @Config
    @Comment({"If true, enable a block that converts any items pumped into it.","Useful for storage systems."})
    @RestartReq(RestartReqs.REQUIRES_MC_RESTART)
    public static boolean enableBlock = false;
    @Config
    @Comment({"If true, conversions will only occur upon pressing the defined key (defaults to c)."})
    @RestartReq(RestartReqs.REQUIRES_WORLD_RESTART)
    public static boolean enableKeypress = false;

    private static ConfigSyncable INSTANCE;
    public static ConfigSyncable instance(String configName) {
        if (INSTANCE == null) {
            INSTANCE = new OptionsConfig(configName);
        }
        return INSTANCE;
    }

    public static ConfigProcessor processor;

    protected OptionsConfig(String configName) {
        super(configName);
    }
    @Override
    public void init() {
        processor = new ConfigProcessor(getClass(), this.config, this.configFileName);
        processor.process(true);
    }
    @Override
    protected void reloadIngameConfigs() {}
    @Override
    protected void reloadNonIngameConfigs() {}
    @Override
    public String getConfigName() {
        return this.configFileName;
    }
}
