package me.neznamy.tab.shared;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.neznamy.tab.shared.Shared.ServerType;

public class NameTag16 {

	public static boolean enable;
	public static int refresh;
	
	public static void unload() {
		if (enable) for (ITabPlayer p : Shared.getPlayers()) p.unregisterTeam();
	}
	public static void load() {
		if (!enable) return;
		for (ITabPlayer p : Shared.getPlayers()) p.registerTeam();
		Shared.scheduleRepeatingTask(refresh, "refreshing nametags", new Runnable() {
			public void run() {
				for (ITabPlayer p : Shared.getPlayers()) p.updateTeam();
			}
		});
		//fixing a 1.8.x client-sided vanilla bug
		if (Shared.servertype == ServerType.BUKKIT) Shared.scheduleRepeatingTask(200, "refreshing nametag visibility", new Runnable() {
			public void run() {
				for (ITabPlayer p : Shared.getPlayers()) p.setTeamVisible(!((Player) p.getPlayer()).hasPotionEffect(PotionEffectType.INVISIBILITY));
			}
		});
	}
	public static void playerJoin(ITabPlayer p) {
		if (!enable) return;
		p.registerTeam();
		for (ITabPlayer all : Shared.getPlayers()) all.registerTeam(p);
	}
	public static void playerQuit(ITabPlayer p) {
		if (enable) p.unregisterTeam();
	}
}