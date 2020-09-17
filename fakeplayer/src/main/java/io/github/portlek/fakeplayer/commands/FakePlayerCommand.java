package io.github.portlek.fakeplayer.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import io.github.portlek.configs.util.MapEntry;
import io.github.portlek.fakeplayer.FakePlayer;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

@CommandAlias("fakeplayer|fp")
public final class FakePlayerCommand extends BaseCommand {

    @Default
    @CommandPermission("fakeplayer.command.main")
    public static void defaultCommand(final CommandSender sender) {
        sender.sendMessage(FakePlayer.getAPI().languageFile.help_messages.build());
    }

    @Subcommand("help")
    @CommandPermission("fakeplayer.command.help")
    public static void helpCommand(final CommandSender sender) {
        sender.sendMessage(FakePlayer.getAPI().languageFile.help_messages.build());
    }

    @Subcommand("reload")
    @CommandPermission("fakeplayer.command.reload")
    public static void reloadCommand(final CommandSender sender) {
        final long millis = System.currentTimeMillis();
        FakePlayer.getAPI().reloadPlugin(false);
        sender.sendMessage(
            FakePlayer.getAPI().languageFile.generals.reload_complete.build(
                "%ms%", () -> String.valueOf(System.currentTimeMillis() - millis)));
    }

    @Subcommand("version")
    @CommandPermission("fakeplayer.command.version")
    public static void versionCommand(final CommandSender sender) {
        FakePlayer.getAPI().checkForUpdate(sender);
    }

    @Subcommand("menu")
    @CommandPermission("fakeplayer.command.menu")
    public static void listCommand(final Player player) {
        FakePlayer.getAPI().menuFile.fakePlayers.inventory().open(player);
    }

    @Syntax("<realname>")
    @Subcommand("add")
    @CommandPermission("fakeplayer.command.add")
    public static void addCommand(final CommandSender sender,String realname) {
        if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(realname)) {
            sender.sendMessage(realname + " player already build");
            return ;
        }
        sender.sendMessage(realname + " add fake player success");
        FakePlayer.getAPI().fakesFile.addFakes(realname.trim(), new Location(Bukkit.getWorld("world"),0,0,0));
    }

    @Syntax("<realname>")
    @Subcommand("remove")
    @CommandPermission("fakeplayer.command.remove")
    public static void removeCommand(final CommandSender sender,String realname) {
        sender.sendMessage(realname + " remove fake player success");
        FakePlayer.getAPI().fakesFile.remove(realname.trim());
    }

    @Syntax("<realname>")
    @Subcommand("toggle")
    @CommandPermission("fakeplayer.command.toggle")
    public static void toggleCommand(final CommandSender sender,String realname) {
        sender.sendMessage(realname + " toggle remove fake player success");
        if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(realname)) {
            sender.sendMessage(realname + " remove toggle fake player success");
            FakePlayer.getAPI().fakesFile.remove(realname.trim());
            return ;
        }
        sender.sendMessage(realname + " toggle add fake player success");
        FakePlayer.getAPI().fakesFile.addFakes(realname.trim(), new Location(Bukkit.getWorld("world"),0,0,0));
    }
}
