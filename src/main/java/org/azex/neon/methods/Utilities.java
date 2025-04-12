package org.azex.neon.methods;

import org.bukkit.command.CommandSender;

public class Utilities {

    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean debtCheck(CommandSender sender, int number, int minus, String type) {
        if (number - minus < 0) {
            Messages.sendMessage(sender, "<red>The player would be in debt if you removed that many " + type  + " from them!", "error");
            return false;
        }
        return true;
    }

}
