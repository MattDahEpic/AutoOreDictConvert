package com.mattdahepic.autooredictconv.command;

/*
public class CommandConfig implements ICommand {
    private List aliases, tabCompletionOptions;
    public CommandConfig () {
        this.aliases = new ArrayList();
        this.aliases.add("odc");
        this.tabCompletionOptions = new ArrayList();
        this.tabCompletionOptions.add("detect");
        this.tabCompletionOptions.add("dump");
        this.tabCompletionOptions.add("find");
        this.tabCompletionOptions.add("help");
    }
    @Override
    public int compareTo (Object arg0) {
        return 0;
    }
    @Override
    public String getCommandName () {
        return "odc";
    }
    @Override
    public String getCommandUsage (ICommandSender iCommandSender) {
        return EnumChatFormatting.RED + "/odc <command>";
    }
    @Override
    public List getCommandAliases () {
        return this.aliases;
    }
    @Override
    public void processCommand (ICommandSender iCommandSender, String[] inputString) {
        ChatComponentText returnText = new ChatComponentText("");
        if (inputString.length == 0) { // no input command
            iCommandSender.addChatMessage(new ChatComponentText("Use \"/odc help\" for useage help."));
            return;
        } else { //message contains data
            ItemStack item = null;
            if (iCommandSender instanceof EntityPlayer) { //if the command runner is a player, get the item they are holding
                item = ((EntityPlayer) iCommandSender).getHeldItem();
            }
            if (inputString[0].equalsIgnoreCase("detect")) {
                if (item != null) {
                    Detect.detect(iCommandSender,item);
                    return;
                } else {
                    iCommandSender.addChatMessage(new ChatComponentText("You\'re not holding an item!"));
                    return;
                }
            } else if (inputString[0].equalsIgnoreCase("dump")) {
                DumpOreDict.dump(iCommandSender);
                return;
            } else if (inputString[0].equalsIgnoreCase("find")) {
                if (inputString.length < 2) {
                    iCommandSender.addChatMessage(new ChatComponentText("You didn\'t specify a Ore Dictionary name! Use \"/odc help\" for help."));
                    return;
                }
                Find.find(iCommandSender,inputString[1]);
                return;
            } else if (inputString[0].equalsIgnoreCase("help")) {
                iCommandSender.addChatMessage(new ChatComponentText("To get the ore dictionary entries of the item currently held, use \"/odc detect\"."));
                iCommandSender.addChatMessage(new ChatComponentText("To dump all ore dictionary entries to the chat and log, use \"/odc dump\"."));
                iCommandSender.addChatMessage(new ChatComponentText("To find all items listed as the specified Ore Dictionary name, use \"/odc find <oreDictName>\"."));
                return;
            }
        }
    }
    @Override
    public boolean canCommandSenderUseCommand (ICommandSender iCommandSender) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            if (iCommandSender instanceof EntityPlayer) {
                return iCommandSender.canCommandSenderUseCommand(2,this.getCommandName());
            } else {
                return true;
            }
        }
        return false;
    }
    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] inputString) {
        return this.tabCompletionOptions;
    }
    @Override
    public boolean isUsernameIndex(String[] inputString, int i) {
        return false;
    }
}
*/