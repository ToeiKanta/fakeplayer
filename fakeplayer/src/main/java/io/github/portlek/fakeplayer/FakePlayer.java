package io.github.portlek.fakeplayer;

import co.aikar.commands.BukkitCommandManager;
import io.github.portlek.fakeplayer.commands.FakePlayerCommand;
import java.util.Optional;

import io.github.portlek.fakeplayer.util.Placeholder;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class FakePlayer extends JavaPlugin {

    private static FakePlayerAPI api;

    private static FakePlayer instance;

    @NotNull
    public static FakePlayer getInstance() {
        if (FakePlayer.instance == null) {
            throw new IllegalStateException("You cannot be used FakePlayer plugin before its start!");
        }

        return FakePlayer.instance;
    }

    @NotNull
    public static FakePlayerAPI getAPI() {
        if (FakePlayer.api == null) {
            throw new IllegalStateException("You cannot be used FakePlayer plugin before its start!");
        }

        return FakePlayer.api;
    }

    @Override
    public void onLoad() {
        FakePlayer.instance = this;
    }

    @Override
    public void onDisable() {
        Optional.ofNullable(FakePlayer.api).ifPresent(FakePlayerAPI::disablePlugin);
        FakePlayer.getAPI().fakesFile.fakeplayers.values().forEach(fake ->
                FakePlayer.getAPI().fakesFile.remove(fake.getName())
        );
    }

    @Override
    public void onEnable() {
        final BukkitCommandManager manager = new BukkitCommandManager(this);
        FakePlayer.api = new FakePlayerAPI(this);
        FakePlayer.api.reloadPlugin(true);
        manager.registerCommand(new FakePlayerCommand());
    }

}
