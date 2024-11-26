package studio.lothus.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jline.utils.Log;

public class PlayerListener implements Listener {

    @EventHandler
    public void setarquandoentrarnoservidorbbAIIISHAWZINHU(PlayerJoinEvent event) {
        var player = event.getPlayer();

        if (player.getName() == null) return;
        Log.debug("Process command of " + player.getName());

        //NÃO TA PRONTO AINDA!!!!!!
    }
}
