package io.github.hsyyid.script.cmdexecutors;

import io.github.hsyyid.script.Script;
import io.github.hsyyid.script.utils.Utils;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;

public class GenericMultipleTaskExecutor implements CommandExecutor
{	
	ArrayList<String> tasks = new ArrayList<String>();
	String commandName;
	Game game = Script.game;
	
	public GenericMultipleTaskExecutor(ArrayList<String> tasks, String commandName)
	{
		this.tasks = tasks;
		this.commandName = commandName;
	}
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			
			for(String task : tasks)
			{
				if(task.equals("message"))
				{
					player.sendMessage(Texts.of(Utils.getMessage(commandName, tasks.indexOf(task) + 1)));
				}
				else if(task.equals("teleport coord"))
				{
					Location<World> location = new Location<World>(player.getWorld(), Utils.getX(commandName, tasks.indexOf(task) + 1), Utils.getY(task, tasks.indexOf(task) + 1), Utils.getZ(task, tasks.indexOf(task) + 1));
					player.setLocation(location);
				}
				else if(task.equals("give items"))
				{
					for (String i: Utils.getItemsToGive(commandName, tasks.indexOf(task) + 1))
					{
						game.getCommandDispatcher().process(game.getServer().getConsole(), "give" + " " + player.getName() + " " + i);
					}
				}
			}
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use this command!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use this command!"));
		}

		return CommandResult.success();
	}
}