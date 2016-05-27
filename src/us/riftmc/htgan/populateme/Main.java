package us.riftmc.htgan.populateme;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;
import net.minecraft.server.v1_9_R2.WorldServer;

public class Main extends JavaPlugin implements Listener{

	public String name = "RiftMC";
	//public UUID id = UUID.fromString("201c8750-9ad8-43f0-84fa-bf393da667b4");
	public String id = "201c8750-9ad8-43f0-84fa-bf393da667b4";
	
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		//GameProfile profile = new GameProfile(id, name);
		
		ProfileLoader loader = new ProfileLoader(id, name, name);
		GameProfile profile = loader.loadProfile();
		
		MinecraftServer server = MinecraftServer.getServer();
		
		WorldServer world = server.getWorldServer(0);
		
		PlayerInteractManager manager = new PlayerInteractManager(world);
		
		EntityPlayer player = new EntityPlayer(server, world, profile, manager);
		player.ping = 390;
		
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, player);
		
		for(Player online: Bukkit.getOnlinePlayers()) {
			((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
			
		}
	}
	
}
