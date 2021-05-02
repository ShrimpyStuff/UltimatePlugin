package ca.sajid.ultimateplugin.modules;

import ca.sajid.ultimateplugin.util.BaseModule;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class SansAI extends BaseModule implements Listener {
    static GameProfile Sans_gameProfile;
    static EntityPlayer SANS_NPC;
    static boolean enabled = true;

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
        npcPacket();
    }

    public static void npcPacket() {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld("world"))).getHandle();
        Sans_gameProfile = new GameProfile(UUID.randomUUID(), "Sans");
        SANS_NPC = new EntityPlayer(nmsServer, nmsWorld, Sans_gameProfile, new PlayerInteractManager(nmsWorld));
        SANS_NPC.setLocation(nmsWorld.getSpawn().getX(), nmsWorld.getSpawn().getY(), nmsWorld.getSpawn().getZ(), 0, 0);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!enabled) return;
        sendSetNPCSkinPacket(SANS_NPC, e.getPlayer(), Sans_gameProfile);
        addNPCPacket(SANS_NPC, e.getPlayer());
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        if (e.getMessage().equals("Sans, You win.")) {
            for (Player player : Objects.requireNonNull(Bukkit.getWorld("world")).getPlayers())
            removeNPCPacket(SANS_NPC, player);
            enabled = false;
        }
    }


    public static void addNPCPacket(EntityPlayer npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360))); // Correct head rotation when spawned in player look direction.
    }

    public static void removeNPCPacket(Entity npc, Player player) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }

    public static void sendSetNPCSkinPacket(EntityPlayer npc, Player player, GameProfile profile) { // The username is the name for the player that has the skin.
                removeNPCPacket(npc, player);
                String skin = "ewogICJ0aW1lc3RhbXAiIDogMTYxOTk3MTI1MTcyMSwKICAicHJvZmlsZUlkIiA6ICI1MGE4OWY4MjBlM2E0YTY5YWNjMTQ2OGMzYzE2NDIxYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdXNoaUNhdDIyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzgyMDEzNmI1ZTUwZWQzMWYxZGQ5MDJjZGRjMWJiMzdmN2UxZjU5ZTI2OWUwNTEzZDQyMmU3MmNhYTA5N2ZjZTUiCiAgICB9CiAgfQp9";
                String signature = "qWxovMq30IqVh1n50KrO00w6Q67OxRABVWiTickHz84JENXPSJw+Dx5trQs2n9pfhDZDygv/z7/cjHWrK1OMOO7CPnSbr6pH3NKHLc/6RVGlGBBvWO2hw7Gl+aTwVx7Ieh5koKOPumcZ9Wv/MxfO/SR4EfelSErGBzteTrtQ6E5Gt+1bwR+cNES2UixMOqPPcBDABing/m2dQoU8ytQRh91djdh/EU5+BO9ekgz+mWNJyVo7ac9yO7ZvyQqKNg+8NZ0v7SSsl6c9gq1ERZE/yzUwTG4mGoDvbtXvZhe0X1w5NzATt6yO4eoUMyrWpGFZpUsazAy+2PHIc4roLIDg856cXIUyMb6wgI4SjaSPxpPPCDIGLzzfx1vgnPVlapg6THoVBaq+ZQkFqOJ3BOjJbTgeF0z7B5JoKB01rbVUHQJgXviKqO6j7+NCr05t4vQNSPdSdFMdPRMVUCLmtK2AqkxQc2ggJdapW1R1XJMs5/G949YUWTnDPPLF/kA5sSgFb20YS+WdpLQcEra2SpCRxmJFQ/AiUoh46QENB8ArwJKvEJ/1Cs8H3SRovpMoMO6pyZtUebt9CI4xmGl470FlNbOFsPCjh/VmNg0+1omesNnM4D5Yxf8P4C8qx9BvsCEn1PDgr78KhU96sgCJ+64kmeRBZPONl+ZWa0TZ3DnX7gM=";
                profile.getProperties().put("textures", new Property("textures", skin, signature));
    }
}
