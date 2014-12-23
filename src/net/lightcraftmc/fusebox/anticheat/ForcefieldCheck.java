package net.lightcraftmc.fusebox.anticheat;

import net.lightcraftmc.fusebox.util.UtilEntity;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ForcefieldCheck {
	//Ctrl + i this plz
	public static void checkForForcefield(Player p){
		if(p.getPassenger() != null) return;
		Witch cow = cowExists(p.getName());
		if(cow == null){
			if(!cow.hasPotionEffect(PotionEffectType.INVISIBILITY)){
				cow = p.getWorld().spawn(p.getLocation().add(0, 2.5, 0), Witch.class);
			}
			cow.setCustomName("AC | " + p.getName());

		}
		cow.teleport(p.getLocation().add(0, 2.5, 0));
		UtilEntity.setNoAI(cow, true);
		cow.getEquipment().setItemInHand(null);
		cow.setHealth(20.0D);
	}

	public static void giveWitchInvis(){
		for(World w : Bukkit.getWorlds()) for(Entity e : w.getEntities()){
			if(e instanceof Witch){
				Witch wi = (Witch)e;
				if(e.getCustomName() != null && e.getCustomName().contains("AC | ")){
					if(!wi.hasPotionEffect(PotionEffectType.INVISIBILITY)){
						wi.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1, true));
					}
					//Only problem with this is- it may fall to the ground which kinda defeats the whole point of it
				}
			}
		}
	}

	public static Witch cowExists(String cowTag){
		for(World w : Bukkit.getWorlds()) for(Entity e : w.getEntities()){
			if(e instanceof Witch){
				Witch cow = (Witch)e;
				if(cow.getCustomName() != null){
					if(cow.getCustomName().contains("AC")){
						if(!hasAnApplicableOnlinePlayer(cow.getCustomName().split("AC | ")[1])){
							cow.remove();
						}
						cow.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1, true));
					}
					if(cow.getCustomName().startsWith("AC | ") && cow.getCustomName().contains(cowTag)){
						return cow;
					}
				}
			}
		}
		return null;
	}

	public static boolean hasAnApplicableOnlinePlayer(String name){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}

}
