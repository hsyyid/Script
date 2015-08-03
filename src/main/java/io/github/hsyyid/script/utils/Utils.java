package io.github.hsyyid.script.utils;

import io.github.hsyyid.script.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

public class Utils
{
	public static ArrayList<String> getCommands()
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands.commands").split("\\."));
		String list = valueNode.getString();

		ArrayList<String> commandList = new ArrayList<String>();
		boolean finished = false;

		if (finished != true)
		{
			int endIndex = list.indexOf(",");
			if (endIndex != -1)
			{
				String substring = list.substring(0, endIndex);
				commandList.add(substring);

				//If there is more than 1 command.
				while (finished != true)
				{
					int startIndex = endIndex;
					endIndex = list.indexOf(",", startIndex + 1);
					if (endIndex != -1)
					{
						String substrings = list.substring(startIndex + 1, endIndex);
						commandList.add(substrings);
					}
					else
					{
						finished = true;
					}
				}
			}
			else
			{
				commandList.add(list);
				finished = true;
			}
		}

		return commandList;
	}
	
	public static ArrayList<String> getItemsToGive(String command)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + command + ".items").split("\\."));
		String list = valueNode.getString();

		ArrayList<String> itemList = new ArrayList<String>();
		boolean finished = false;

		if (finished != true)
		{
			int endIndex = list.indexOf(",");
			if (endIndex != -1)
			{
				String substring = list.substring(0, endIndex);
				itemList.add(substring);

				//If there is more than 1 command.
				while (finished != true)
				{
					int startIndex = endIndex;
					endIndex = list.indexOf(",", startIndex + 1);
					if (endIndex != -1)
					{
						String substrings = list.substring(startIndex + 1, endIndex);
						itemList.add(substrings);
					}
					else
					{
						finished = true;
					}
				}
			}
			else
			{
				itemList.add(list);
				finished = true;
			}
		}

		return itemList;
	}
	
	public static ArrayList<String> getItemsToGive(String command, int task)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + command + ".items" + task).split("\\."));
		String list = valueNode.getString();

		ArrayList<String> itemList = new ArrayList<String>();
		boolean finished = false;

		if (finished != true)
		{
			int endIndex = list.indexOf(",");
			if (endIndex != -1)
			{
				String substring = list.substring(0, endIndex);
				itemList.add(substring);

				//If there is more than 1 command.
				while (finished != true)
				{
					int startIndex = endIndex;
					endIndex = list.indexOf(",", startIndex + 1);
					if (endIndex != -1)
					{
						String substrings = list.substring(startIndex + 1, endIndex);
						itemList.add(substrings);
					}
					else
					{
						finished = true;
					}
				}
			}
			else
			{
				itemList.add(list);
				finished = true;
			}
		}

		return itemList;
	}
	
	public static String getCommandDescription(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".description").split("\\."));
		return valueNode.getString();
	}
	
	public static int getAmountOfTasks(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".tasks").split("\\."));
		return valueNode.getInt();
	}
	
	public static double getX(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".location.x").split("\\."));
		return valueNode.getDouble();
	}
	
	public static double getY(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".location.y").split("\\."));
		return valueNode.getDouble();
	}
	
	public static double getZ(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".location.z").split("\\."));
		return valueNode.getDouble();
	}
	
	public static double getX(String commandName, int task)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".location" + task + ".x").split("\\."));
		return valueNode.getDouble();
	}
	
	public static double getY(String commandName, int task)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".location" + task + ".y").split("\\."));
		return valueNode.getDouble();
	}
	
	public static double getZ(String commandName, int task)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".location" + task + ".z").split("\\."));
		return valueNode.getDouble();
	}
	
	public static String getMessage(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".message").split("\\."));
		return valueNode.getString();
	}
	
	public static String getMessage(String commandName, int task)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".message" + task).split("\\."));
		return valueNode.getString();
	}
	
	public static String getCommandPermission(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".permission").split("\\."));
		return valueNode.getString();
	}
	
	public static String getCommandArguments(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".arguments").split("\\."));
		return valueNode.getString();
	}
	
	public static String getCommandTask(String commandName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".task").split("\\."));
		return valueNode.getString();
	}
	
	public static String getTask(String commandName, int i)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("commands." + commandName + ".task" + i).split("\\."));
		return valueNode.getString();
	}
}
