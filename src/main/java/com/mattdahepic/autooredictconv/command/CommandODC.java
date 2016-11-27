package com.mattdahepic.autooredictconv.command;

import com.mattdahepic.autooredictconv.command.logic.*;
import com.mattdahepic.mdecore.command.AbstractCommand;

public class CommandODC extends AbstractCommand {
    public static CommandODC instance = new CommandODC();

    public CommandODC () {
        registerCommandLogic(HelpLogic.instance);
        registerCommandLogic(DetectLogic.instance);
        registerCommandLogic(DumpLogic.instance);
        registerCommandLogic(FindLogic.instance);
        registerCommandLogic(ListLogic.instance);
        registerCommandLogic(AddLogic.instance);
        registerCommandLogic(ReloadLogic.instance);
        registerCommandLogic(RemoveLogic.instance);
    }
    @Override
    public String getCommandName () {
        return "odc";
    }
}
