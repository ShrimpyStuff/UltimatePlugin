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

        String skin = "ew0KICAidGltZXN0YW1wIiA6IDE2MjgxMjg0NzUxNzcsDQogICJwcm9maWxlSWQiIDogIjUwYTg5ZjgyMGUzYTRhNjlhY2MxNDY4YzNjMTY0MjFhIiwNCiAgInByb2ZpbGVOYW1lIiA6ICJTdXNoaUNhdDIyIiwNCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsDQogICJ0ZXh0dXJlcyIgOiB7DQogICAgIlNLSU4iIDogew0KICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84MjAxMzZiNWU1MGVkMzFmMWRkOTAyY2RkYzFiYjM3ZjdlMWY1OWUyNjllMDUxM2Q0MjJlNzJjYWEwOTdmY2U1IiwNCiAgICB9LA0KICAgICJDQVBFIiA6IHsNCiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhmOGQxM2ExYWRmOTYzNmExNmMzMWQ0N2YzZWNjOWJiOGQ4NTMzMTA4YWE1YWQyYTAxYjEzYjFhMGM1NWVhYyINCiAgICB9DQogIH0NCn0=";
        String signature = "DyIluQsJinkcrk9buajddNRtV3vbvwwP3QYjogrOs3JwAwm+L8lihTNOETUm0OA42+qXQ5R4Ktrbq47rgHT7ymn567dKTeDj8FgdMKjO5Tq8XO0KAKMx83QIrNM15j1DRYfxaz/5osiyz3IgzANmegAdjkiWpg5qZPf91MLqVhW9cdl33Q1lrd9vhY4r4w8ajVcF10vzo0SMBkRbMcf0LqLLFxMwfSY7Fsy4Fu3GifvpGf5pDsLRcrShK/m1qulwIefKBVyo0gdQ4oZ4uzjaiVBzYvOnAmy6hWPFglb6Hel+Oc445J+ZNGBB2rxWI+2h69iXT3Rz+B17hyCn5nXcA4MrSviaP2t0V51JEl/kPzk4/yew1NuNxrsMirB1wMfY7+4fbV4N2qR3/Ms4aE0+TBu6LLOGf/ijNELavf6R4G+etJxdmE8nESOHNzV6l7NDe2zFtMXpRpURqJ3HxLUwvdwHJIsuAxFEK9PtY+Cv4AQRqernE/OADP/eaMjiGN72Ky7kUcvTY1qDMiOEGf+cBwoNbuHcnSXgJ7u3vl+EnCXVYRZD9WMxzb19bmft8fHkVAFgwo5AEvaTXHYX2jylBBGHgq7zbp8HAO5/3GBz8XQ2Knc3PNyS90BO+o31W9VOoH7A6EoQ3AWV7ppStn8kyuVIIGglmOjPZI2WHLWSRKY=";
        gameProfile.getProperties().put("textures", new Property("textures", skin, signature));

        DataWatcher watcher = npc.getDataWatcher();
        watcher.set(new DataWatcherObject<>(15, DataWatcherRegistry.a), (byte)127);
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(npc.getId(), watcher, true);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        addNPCPacket(npc, player);
    }
}
