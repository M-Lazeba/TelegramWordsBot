package commands;

import java.util.List;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import bot.Launcher;
import data.WordsRepository;

/**
 *
 */
public class ListWordsCommand implements CommandProcessor {
  @Override
  public boolean processCommand(Update update) {
    User from = update.message().from();
    String text = update.message().text();
    if (text != null) {
      String[] textProcessed = text.toLowerCase().split("\\s+");
      if (textProcessed.length == 0) return false;
      if (textProcessed[0].startsWith("\\list")) {
        System.out.println("List command");
        sendWords(from);
        return true;
      }
    }
    return false;
  }

  private void sendWords(User user) {
    List<WordsRepository.Word> words = Launcher.wordsRepository.getDictionary();
    StringBuilder list = new StringBuilder("Your dictionary contains ").append(words.size()).append(" words:\n");
    words.forEach(w -> list.append(w.original).append(" ").append(w.translation).append('\n'));
    Launcher.bot.execute(new SendMessage(user.id(), list.toString()));
  }
}
