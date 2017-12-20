package commands;

import java.util.Arrays;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import bot.Launcher;
import data.WordsRepository;

/**
 *
 */
public class AddWordCommand implements CommandProcessor {
  @Override
  public boolean processCommand(Update update) {
    User from = update.message().from();
    String text = update.message().text();
    if (text != null) {
      String[] textProcessed = text.toLowerCase().split("\\s+");
      System.out.println(Arrays.toString(textProcessed));
      if (textProcessed.length == 0) return false;
      if (textProcessed[0].equals("\\add")) {
        System.out.println("Add command");
        if (textProcessed.length != 3) {
          sendWrongFormat(from);
        } else {
          Launcher.wordsRepository.addWord(new WordsRepository.Word(textProcessed[1], textProcessed[2]));
          sendSuccess(from);
        }
        return true;
      }
    }
    return false;
  }

  private void sendSuccess(User user) {
    System.out.println("Send added");
    Launcher.bot.execute(new SendMessage(user.id(),
        String.format("New word was added... Amazing! I know already %d words!", Launcher.wordsRepository.size())));
  }

  private void sendWrongFormat(User user) {
    System.out.println("Send wrong format");
    Launcher.bot.execute(new SendMessage(user.id(),
        "Sorry, I don't understand you! To add new word, please, use \"\\add original translation\" command"));
  }
}
