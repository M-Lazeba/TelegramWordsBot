package bot;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import commands.AddWordCommand;
import commands.CommandProcessor;
import commands.Greeter;
import commands.ListWordsCommand;
import data.WordsRepository;

public class Launcher {

  public static TelegramBot bot;
  public static WordsRepository wordsRepository = new WordsRepository();
  private static List<CommandProcessor> processors = Arrays.asList(
      new Greeter(),
      new AddWordCommand(),
      new ListWordsCommand()
  );

  public static void main(String[] args) throws IOException {
    String token = args[0];

    bot = new TelegramBot(token);

    bot.setUpdatesListener(updates -> {
      printUpdates(updates);
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });
  }

  private static void printUpdates(List<Update> updates) {
    updates.forEach(update -> {
      for (CommandProcessor p : processors) {
        if (p.processCommand(update)) {
          return;
        }
      }
    });
  }

}
