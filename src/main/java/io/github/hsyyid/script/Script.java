package io.github.hsyyid.script;

import com.google.inject.Inject;
import io.github.hsyyid.script.cmdexecutors.GenericGiveExecutor;
import io.github.hsyyid.script.cmdexecutors.GenericMessageExecutor;
import io.github.hsyyid.script.cmdexecutors.GenericMultipleTaskExecutor;
import io.github.hsyyid.script.cmdexecutors.GenericTeleportCoordExecutor;
import io.github.hsyyid.script.utils.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandSpec;
import org.spongepowered.api.world.TeleportHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Plugin(id = "Script", name = "Script", version = "0.1")
public class Script
{
	public static Game game = null;
	public static ConfigurationNode config = null;
	public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
	public static TeleportHelper helper;

	@Inject
	private Logger logger;

	public Logger getLogger()
	{
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot = true)
	private File dConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> confManager;

	@Listener
	public void onGameInit(GameInitializationEvent event)
	{
		getLogger().info("Script loading...");
		game = event.getGame();
		helper = game.getTeleportHelper();
		// Config File
		try
		{
			if (!dConfig.exists())
			{
				dConfig.createNewFile();
				config = confManager.load();
				//Home
				config.getNode("commands", "home", "permission").setValue("home.use");
				config.getNode("commands", "home", "task").setValue("message");
				config.getNode("commands", "home", "tasks").setValue(1);
				config.getNode("commands", "home", "message").setValue("Hello! This is a command generated from the config of Script!");
				config.getNode("commands", "home", "description").setValue("Sends you a message.");
				//Test
				config.getNode("commands", "test", "permission").setValue("test.use");
				config.getNode("commands", "test", "task").setValue("give items");
				config.getNode("commands", "test", "tasks").setValue(1);
				config.getNode("commands", "test", "items").setValue("minecraft:diamond_sword,");
				config.getNode("commands", "test", "description").setValue("Gives you some items.");
				//Teleport
				config.getNode("commands", "teleport", "permission").setValue("teleport.use");
				config.getNode("commands", "teleport", "tasks").setValue(1);
				config.getNode("commands", "teleport", "task").setValue("teleport coord");
				config.getNode("commands", "teleport", "location", "X").setValue(0);
				config.getNode("commands", "teleport", "location", "Y").setValue(0);
				config.getNode("commands", "teleport", "location", "Z").setValue(0);
				config.getNode("commands", "teleport", "description").setValue("Teleports you to 0,0,0");
				//Multiple
				config.getNode("commands", "multiple", "permission").setValue("multiple.use");
				config.getNode("commands", "multiple", "tasks").setValue(2);
				config.getNode("commands", "multiple", "task1").setValue("teleport coord");
				config.getNode("commands", "multiple", "location1", "X").setValue(0);
				config.getNode("commands", "multiple", "location1", "Y").setValue(0);
				config.getNode("commands", "multiple", "location1", "Z").setValue(0);
				config.getNode("commands", "multiple", "task2").setValue("message");
				config.getNode("commands", "multiple", "message2").setValue("Hello! You just got teleported!");
				config.getNode("commands", "multiple", "description").setValue("multiple tasks are going to be executed by this command");
				//Commands
				config.getNode("commands", "commands").setValue("home,test,teleport,multiple,");
				confManager.save(config);
			}
			configurationManager = confManager;
			config = confManager.load();

		}
		catch (IOException exception)
		{
			getLogger().error("The default configuration could not be loaded or created!");
		}

		for (String command : Utils.getCommands())
		{
			if(Utils.getAmountOfTasks(command) == 1)
			{
				if (Utils.getCommandTask(command).equals("message"))
				{
					CommandSpec commandSpec = CommandSpec.builder()
						.description(Texts.of(Utils.getCommandDescription(command)))
						.permission(Utils.getCommandPermission(command))
						// .arguments(Utils.getCommandArguments(command))
						.executor(new GenericMessageExecutor(Utils.getMessage(command)))
						.build();
					game.getCommandDispatcher().register(this, commandSpec, command);
				}
				else if (Utils.getCommandTask(command).equals("give items"))
				{
					CommandSpec commandSpec = CommandSpec.builder()
						.description(Texts.of(Utils.getCommandDescription(command)))
						.permission(Utils.getCommandPermission(command))
						// .arguments(Utils.getCommandArguments(command))
						.executor(new GenericGiveExecutor(Utils.getItemsToGive(command), command))
						.build();
					game.getCommandDispatcher().register(this, commandSpec, command);
				}
				else if (Utils.getCommandTask(command).equals("teleport coord"))
				{
					CommandSpec commandSpec = CommandSpec.builder()
						.description(Texts.of(Utils.getCommandDescription(command)))
						.permission(Utils.getCommandPermission(command))
						// .arguments(Utils.getCommandArguments(command))
						.executor(new GenericTeleportCoordExecutor(Utils.getX(command), Utils.getY(command), Utils.getZ(command)))
						.build();
					game.getCommandDispatcher().register(this, commandSpec, command);
				}
			}
			else
			{
				int tasks = Utils.getAmountOfTasks(command);
				ArrayList<String> taskList = new ArrayList<String>();
				
				for(int i = 1; i <= tasks; i++)
				{
					taskList.add(Utils.getTask(command, i));
				}
				
				CommandSpec commandSpec = CommandSpec.builder()
					.description(Texts.of(Utils.getCommandDescription(command)))
					.permission(Utils.getCommandPermission(command))
					// .arguments(Utils.getCommandArguments(command))
					.executor(new GenericMultipleTaskExecutor(taskList, command))
					.build();
				game.getCommandDispatcher().register(this, commandSpec, command);
			}
		}

		getLogger().info("-----------------------------");
		getLogger().info("Script was made by HassanS6000!");
		getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
		getLogger().info("Have fun, and enjoy! :D");
		getLogger().info("-----------------------------");
		getLogger().info("Script loaded!");
	}

	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
