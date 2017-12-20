package commands;

import com.pengrad.telegrambot.model.Update;

/**
 *
 */
public interface CommandProcessor {
  boolean processCommand(Update update);
}
