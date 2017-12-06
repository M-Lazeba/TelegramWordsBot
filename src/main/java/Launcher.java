import java.io.IOException;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;

public class Launcher {

  private static TelegramBot bot;

  public static void main(String[] args) throws IOException {
    String token = args[0];

    bot = new TelegramBot(token);

    bot.setUpdatesListener(updates -> {
      printUpdates(updates);
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });
  }

  private static void printUpdates(List<Update> updates) {
    for (Update update : updates) {
      User from = update.message().from();
      System.out.println("Message from " + from);
      String text = update.message().text();
      System.out.println(text);
      if (text == null) {
        continue;
      }
      String textProcessed = text.toLowerCase();
      if (textProcessed.contains("hello")) {
        greetUser(from);
      } else if (textProcessed.contains("show")) {
        choice(from);
      }

    }
  }

  private static void greetUser(User user) {
    bot.execute(new SendChatAction(user.id(), ChatAction.typing));
    bot.execute(new SendMessage(user.id(), "Hello, " + user.firstName() + "!"));
  }

  private static void choice(User user) {
    Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
        new String[]{"first row button1", "first row button2"},
        new String[]{"second row button1", "second row button2"})
        .oneTimeKeyboard(true)   // optional
        .resizeKeyboard(true)    // optional
        .selective(true);        // optional

    bot.execute(new SendMessage(user.id(), "Wanna more? Keyboard example")
        .replyMarkup(replyKeyboardMarkup));
  }

}
