package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.command.CommandODC;
import com.mattdahepic.mdecore.command.AbstractCommand;
import com.mattdahepic.mdecore.command.AbstractHelpLogic;

public class HelpLogic extends AbstractHelpLogic {
    public static HelpLogic instance = new HelpLogic();
    @Override
    public AbstractCommand getBaseCommand () {
        return CommandODC.instance;
    }
}
