package me.giverplay.melon;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class Melon extends JavaPlugin {
  @Override
  public void onEnable() {
    CraftServer server = ((CraftServer) getServer());
    CommandDispatcher dispatcher = null;

    try {
      Field consoleField = server.getClass().getDeclaredField("console");
      consoleField.setAccessible(true);
      Object console = consoleField.get(server);

      Field dispatcherField = console.getClass().getSuperclass().getDeclaredField("vanillaCommandDispatcher");
      dispatcher = (CommandDispatcher) dispatcherField.get(console);
    } catch (Exception e) {
      e.printStackTrace();
    }

    dispatcher.a().register(literal("melon")
      .then(
        argument("entity", string())
          .executes(ctx -> {
            CommandNode currentCommand = ((CommandListenerWrapper) ctx.getSource()).currentCommand);
            return 0;
          })
      )
      .executes(ctx -> {
        ((CommandListenerWrapper) ctx.getSource()).sendMessage("Executado sem argumentos!");
        return 0;
      }));
  }
}
