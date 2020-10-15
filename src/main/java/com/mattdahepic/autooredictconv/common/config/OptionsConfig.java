package com.mattdahepic.autooredictconv.common.config;

import com.mattdahepic.mdecore.common.config.MDEConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class OptionsConfig {
    public static class Common {
        public final ForgeConfigSpec.BooleanValue enableKeypress;

        public Common(ForgeConfigSpec.Builder builder) {
            this.enableKeypress = builder
                    .comment("If true, conversions will only occur upon pressing the defined key (defaults to c).")
                    .define("enableKeypress", false);
        }
    }
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
