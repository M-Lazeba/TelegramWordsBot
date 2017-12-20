package commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;

import bot.Launcher;

/**
 *
 */
public class Greeter implements CommandProcessor {
  @Override
  public boolean processCommand(Update update) {
    User from = update.message().from();
    String text = update.message().text();
    if (text != null) {
      String textProcessed = text.toLowerCase();
      if (textProcessed.contains("hello")) {
        greetUser(from);
        return true;
      } else if (textProcessed.contains("show")) {
        choice(from);
      }
    }
    return false;
  }

  private static void greetUser(User user) {
    Launcher.bot.execute(new SendChatAction(user.id(), ChatAction.typing));
    Launcher.bot.execute(new SendMessage(user.id(), "Hello, " + user.firstName() + "!"));
  }

  private static void choice(User user) {
    Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
        new String[]{"first row button1", "first row button2"},
        new String[]{"second row button1", "second row button2"})
        .oneTimeKeyboard(true)   // optional
        .resizeKeyboard(true)    // optional
        .selective(true);        // optional

    Launcher.bot.execute(new SendMessage(user.id(), "Wanna more? Keyboard example")
        .replyMarkup(replyKeyboardMarkup));
  }
}
