package net.lightcraftmc.fusebox.anticheat;

import java.util.HashMap;
import java.util.UUID;

import net.lightcraftmc.fusebox.Core;
import net.lightcraftmc.fusebox.util.PingUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class AntiCheat implements Listener {

	private static AntiCheat instance;

	/*
	 * NOTE:
	 * THIS CLASS IS EXTREMELY EXPERIMENTAL.
	 */

	public static void init() {
		instance = new AntiCheat();
		Bukkit.getServer().getPluginManager().registerEvents(instance, Core.getInstance());
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable(){
			public void run(){
				for(Player p : Bukkit.getOnlinePlayers()){
					ForcefieldCheck.checkForForcefield(p);
				}
			}
		}, 0, 1);
	}

	public static AntiCheat getInstance(){
		return instance;
	}

	static HashMap<UUID, Location> moving = new HashMap<UUID, Location>();
	HashMap<UUID, Integer> test = new HashMap<UUID, Integer>();




	@EventHandler
	public void attackEntity(EntityDamageByEntityEvent e){

		if(e.getEntityType() != EntityType.WITCH) return;
		Witch witch = (Witch) e.getEntity();
		if(witch.getCustomName() == null) return;
		if(!witch.getCustomName().contains("AC | ")) return;
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			HackAction.warnPlayer(p, HackType.FORCEFIELD);

		}
	}

}