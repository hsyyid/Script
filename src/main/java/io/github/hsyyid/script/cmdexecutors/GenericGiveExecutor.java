package io.github.hsyyid.script.cmdexecutors;

import io.github.hsyyid.script.Script;
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

import java.util.ArrayList;

public class GenericGiveExecutor implements CommandExecutor
{
	public ArrayList<String> itemsToGive = new ArrayList <String>();
	public String command;
	Game game = Script.game;
	
	public GenericGiveExecutor(ArrayList<String> itemsToGive, String command)
	{
		this.itemsToGive = itemsToGive;
		this.command = command;
	}
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			for (String i : itemsToGive)
			{
				game.getCommandDispatcher().process(game.getServer().getConsole(), "give" + " " + player.getName() + " " + i);
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