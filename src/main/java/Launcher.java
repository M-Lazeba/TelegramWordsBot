import java.io.IOException;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
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
      if (text != null && text.toLowerCase().contains("hello")) {
        greetUser(from);
      }
    }
  }

  private static void greetUser(User user) {
    bot.execute(new SendMessage(user.id(), "Hello, " + user.firstName() + "!"));
  }

}
