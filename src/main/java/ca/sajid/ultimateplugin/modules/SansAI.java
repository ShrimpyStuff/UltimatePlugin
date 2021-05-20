package ca.sajid.ultimateplugin.modules;

import ca.sajid.ultimateplugin.util.BaseModule;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class SansAI extends BaseModule implements Listener {
    static boolean enabled = false;
    static MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
    static WorldServer nmsWorld = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld("world"))).getHandle();
    static GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Sans");
    static EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!enabled) return;
        sendSetNPCSkinPacket(npc, e.getPlayer());
    }

    public static void EnableSans(Player player) {
        enabled = true;
        Location location = player.getWorld().getSpawnLocation();
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        for (Player player1 : player.getWorld().getPlayers()) {
            sendSetNPCSkinPacket(npc, player1);
        }
    }

    public static void addNPCPacket(EntityPlayer npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc)); // Spawns the NPC for the player client.
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360))); // Correct head rotation when spawned in player look direction.
    }

    public static void removeNPCPacket(Entity npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }

    public static void sendSetNPCSkinPacket(EntityPlayer npc, Player player) { // The username is the name for the player that has the skin.
        removeNPCPacket(npc, player);

        String skin = "";
        String signature = "";
        gameProfile.getProperties().put("textures", new Property("textures", skin, signature));

        DataWatcher watcher = npc.getDataWatcher();
        watcher.set(new DataWatcherObject<>(15, DataWatcherRegistry.a), (byte)127);
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(npc.getId(), watcher, true);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        addNPCPacket(npc, player);
    }
}