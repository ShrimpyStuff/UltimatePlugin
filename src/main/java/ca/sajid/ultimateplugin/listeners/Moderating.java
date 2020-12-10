package ca.sajid.ultimateplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Moderating implements Listener {
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        String[] badWords = {"fuck", "shit"};
        for (String word : badWords) {
            int length = word.length();
            String fixedWord = new String(new char[length]).replace("\0", "#");
            String fixed = message.replaceAll(word, fixedWord);
            e.setMessage(fixed);
        }
    }
}
