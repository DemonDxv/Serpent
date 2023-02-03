package dev.demon.serpent.base.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import dev.demon.serpent.Serpent;
import dev.demon.serpent.base.user.User;
import dev.demon.serpent.utils.PacketUtil;
import org.bukkit.entity.Player;

public class PacketManager extends PacketListenerAbstract {

    //Clientbound
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {

        PacketUtil.toPacketReceive(event);

        Player player = (Player) event.getPlayer();

        if (player == null) {
            return;
        }

        User user = Serpent.getInstance().getUserManager().getUser(player);

        if (user == null) {
            return;
        }

        //Register clientbound packets for checks
        user.getChecks().forEach(check -> {
            if (check.isEnabled()) {
                check.onPacket(event);
            }
        });

        //run client processors
        user.getProcessorManager().getProcessors().forEach(processor -> processor.onPacket(event));
    }


    //Serverbound
    @Override
    public void onPacketSend(PacketSendEvent event) {
        PacketUtil.toPacketSend(event);

        Player player = (Player) event.getPlayer();

        if (player == null) {
            return;
        }

        User user = Serpent.getInstance().getUserManager().getUser(player);

        if (user == null) {
            return;
        }

        //Register serverbound packets for checks
        user.getChecks().forEach(check -> {
            if (check.isEnabled()) {
                check.onPacket(event);
            }
        });

        //run server processors
        user.getProcessorManager().getProcessors().forEach(processor -> processor.onPacket(event));
    }
}
