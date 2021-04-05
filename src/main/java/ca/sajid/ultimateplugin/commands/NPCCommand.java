package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.util.BaseCommand;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPCCommand extends BaseCommand {

    public NPCCommand() {
        super("npc", true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player senderPlayer = (Player) sender;
        Location location = senderPlayer.getLocation();

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Sans");
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        addNPCPacket(npc, senderPlayer);
        return true;
    }

    public void addNPCPacket(EntityPlayer npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360))); // Correct head rotation when spawned in player look direction.
    }
}
