package es.deantonious.nyancraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_7_R4.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class NyanCraft extends JavaPlugin implements Listener {
	
	Random rand = new Random();
	List<Player> nyanIzados = new ArrayList<Player>();
	List<Player> nyanIzadosR = new ArrayList<Player>();
	HashMap<Player, Integer> customNyanID = new HashMap<Player, Integer>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if((cmd.getName().equalsIgnoreCase("nyan")) && (player.hasPermission("nyan.normal"))) {
				if(!nyanIzados.contains(player)) {
					if(nyanIzadosR.contains(player)) {
						nyanIzadosR.remove(player);
						nyanIzados.add(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Normal] enabled...");
					} 
					if(customNyanID.containsKey(player)){
						customNyanID.remove(player);
						nyanIzados.add(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Normal] enabled...");
					} else {
						nyanIzados.add(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Normal] enabled...");
					}
					
				} else {
					nyanIzados.remove(player);
					player.sendMessage(ChatColor.GOLD + "Nyan [Normal] disabled...");
				}
		
			}
			
			if(cmd.getName().equalsIgnoreCase("nyanr") && (player.hasPermission("nyan.random"))) {
				
				if(!nyanIzadosR.contains(player)) {
					if(nyanIzados.contains(player)) {
						nyanIzados.remove(player);
						nyanIzadosR.add(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Random] enabled...");
					} 
					if(customNyanID.containsKey(player)){
						customNyanID.remove(player);
						nyanIzadosR.add(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Random] enabled...");
					} else {
						nyanIzadosR.add(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Random] enabled...");
					}
				} else {
					nyanIzadosR.remove(player);
					player.sendMessage(ChatColor.GOLD + "Nyan [Random] disabled...");
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("nyanc") && (player.hasPermission("nyan.custom"))) {
				if(args.length == 1) {
					
					if( Material.getMaterial(Integer.parseInt(args[0])) == null ) {
						player.sendMessage(ChatColor.GOLD + "Please enter a valid Block ID...");
						
					} else {
						if(!customNyanID.containsKey(player)) {
							if(nyanIzados.contains(player)) {
								nyanIzados.remove(player);
								customNyanID.put(player, Integer.parseInt(args[0]));
								player.sendMessage(ChatColor.GOLD + "Nyan [Custom ID: " + Integer.parseInt(args[0]) + "] enabled...");
							} 
							if(nyanIzadosR.contains(player)){
								nyanIzadosR.remove(player);
								customNyanID.put(player, Integer.parseInt(args[0]));
								player.sendMessage(ChatColor.GOLD + "Nyan [Custom ID: " + Integer.parseInt(args[0]) + "] enabled...");
							} else {
								customNyanID.put(player, Integer.parseInt(args[0]));
								player.sendMessage(ChatColor.GOLD + "Nyan [Custom ID: " + Integer.parseInt(args[0]) + "] enabled...");
							}
						} else {
							customNyanID.remove(player);
							customNyanID.put(player, Integer.parseInt(args[0]));
							player.sendMessage(ChatColor.GOLD + "Nyan [Custom] changed to: " + Integer.parseInt(args[0]));
						}
					}
					
				} else {
					if(customNyanID.containsKey(player)) {
						customNyanID.remove(player);
						player.sendMessage(ChatColor.GOLD + "Nyan [Custom] disabled...");
					} else {
						player.sendMessage(ChatColor.RED + "Please, enter a valid Block ID (/nyanc <blockId>)");
					}
				}
			}
		}
		
		return false;
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if(nyanIzadosR.contains(player)) {
			
			World world = player.getWorld();
			Location loc = player.getLocation();
			Block behind = loc.getBlock();
			int direction = (int)loc.getYaw();
			 
			if(direction < 0) {
			    direction += 360;
			    direction = (direction + 45) / 90;
			}else {
			    direction = (direction + 45) / 90;
			}
			 
			switch (direction) {
			    case 1:
			        behind = world.getBlockAt(behind.getX() + 1, behind.getY(), behind.getZ());
			        break;
			    case 2:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() + 1);
			        break;
			    case 3:
			        behind = world.getBlockAt(behind.getX() - 1, behind.getY(), behind.getZ());
			        break;
			    case 4:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
			        break;
			    case 0:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
			        break;
			    default:
			        break;
			}
			
			Location behind2 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+1, behind.getLocation().getZ());
			Location behind3 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+2, behind.getLocation().getZ());
			Location behind4 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+3, behind.getLocation().getZ());
			Location behind5 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+4, behind.getLocation().getZ());
			Location behind6 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+5, behind.getLocation().getZ());
			
			final FallingBlock fb1 = player.getWorld().spawnFallingBlock(behind.getLocation(), Material.WOOL, (byte) rand.nextInt(16));
			final FallingBlock fb2 = player.getWorld().spawnFallingBlock(behind2, Material.WOOL, (byte) rand.nextInt(16));
			final FallingBlock fb3 = player.getWorld().spawnFallingBlock(behind3, Material.WOOL, (byte) rand.nextInt(16));
			final FallingBlock fb4 = player.getWorld().spawnFallingBlock(behind4, Material.WOOL, (byte) rand.nextInt(16));
			final FallingBlock fb5 = player.getWorld().spawnFallingBlock(behind5, Material.WOOL, (byte) rand.nextInt(16));
			final FallingBlock fb6 = player.getWorld().spawnFallingBlock(behind6, Material.WOOL, (byte) rand.nextInt(16));

			fb1.setDropItem(false);
			fb2.setDropItem(false);
			fb3.setDropItem(false);
			fb4.setDropItem(false);
			fb5.setDropItem(false);
			fb6.setDropItem(false);
			
			fb1.setVelocity(new Vector(0,0.35,0));
			fb2.setVelocity(new Vector(0,0.35,0));
			fb3.setVelocity(new Vector(0,0.35,0)); 
			fb4.setVelocity(new Vector(0,0.35,0));
			fb5.setVelocity(new Vector(0,0.35,0));
			fb6.setVelocity(new Vector(0,0.35,0));
			

			
			new BukkitRunnable() {
				 
		            @Override
		            public void run() {
		            	fb1.remove();
		            	fb2.remove();		
		            	fb3.remove();
		            	fb4.remove();
		            	fb5.remove();
		            	fb6.remove();
		            }	 
		        }.runTaskLater(this, 10);
			
		}
		
		if(nyanIzados.contains(player)) {
			
			World world = player.getWorld();
			Location loc = player.getLocation();
			Block behind = loc.getBlock();
			int direction = (int)loc.getYaw();
			 
			if(direction < 0) {
			    direction += 360;
			    direction = (direction + 45) / 90;
			}else {
			    direction = (direction + 45) / 90;
			}
			 
			switch (direction) {
			    case 1:
			        behind = world.getBlockAt(behind.getX() + 1, behind.getY(), behind.getZ());
			        break;
			    case 2:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() + 1);
			        break;
			    case 3:
			        behind = world.getBlockAt(behind.getX() - 1, behind.getY(), behind.getZ());
			        break;
			    case 4:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
			        break;
			    case 0:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
			        break;
			    default:
			        break;
			}
			
			Location behind2 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+1, behind.getLocation().getZ());
			Location behind3 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+2, behind.getLocation().getZ());
			Location behind4 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+3, behind.getLocation().getZ());
			Location behind5 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+4, behind.getLocation().getZ());
			Location behind6 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+5, behind.getLocation().getZ());
			
			final FallingBlock fb1 = player.getWorld().spawnFallingBlock(behind.getLocation(), Material.WOOL, (byte) 11);
			final FallingBlock fb2 = player.getWorld().spawnFallingBlock(behind2, Material.WOOL, (byte) 3);
			final FallingBlock fb3 = player.getWorld().spawnFallingBlock(behind3, Material.WOOL, (byte) 5);
			final FallingBlock fb4 = player.getWorld().spawnFallingBlock(behind4, Material.WOOL, (byte) 4);
			final FallingBlock fb5 = player.getWorld().spawnFallingBlock(behind5, Material.WOOL, (byte) 1);
			final FallingBlock fb6 = player.getWorld().spawnFallingBlock(behind6, Material.WOOL, (byte) 14);
			
			fb1.setDropItem(false);
			fb2.setDropItem(false);
			fb3.setDropItem(false);
			fb4.setDropItem(false);
			fb5.setDropItem(false);
			fb6.setDropItem(false);
			
			fb1.setVelocity(new Vector(0,0.35,0));
			fb2.setVelocity(new Vector(0,0.35,0));
			fb3.setVelocity(new Vector(0,0.35,0)); 
			fb4.setVelocity(new Vector(0,0.35,0));
			fb5.setVelocity(new Vector(0,0.35,0));
			fb6.setVelocity(new Vector(0,0.35,0));
			new BukkitRunnable() {
				 
		            @Override
		            public void run() {
		            	fb1.remove();
		            	fb2.remove();		
		            	fb3.remove();
		            	fb4.remove();
		            	fb5.remove();
		            	fb6.remove();
		            }	 
		        }.runTaskLater(this, 10);
		}
		
		if(customNyanID.containsKey(player)) {
			
			World world = player.getWorld();
			Location loc = player.getLocation();
			Block behind = loc.getBlock();
			int direction = (int)loc.getYaw();
			 
			if(direction < 0) {
			    direction += 360;
			    direction = (direction + 45) / 90;
			}else {
			    direction = (direction + 45) / 90;
			}
			 
			switch (direction) {
			    case 1:
			        behind = world.getBlockAt(behind.getX() + 1, behind.getY(), behind.getZ());
			        break;
			    case 2:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() + 1);
			        break;
			    case 3:
			        behind = world.getBlockAt(behind.getX() - 1, behind.getY(), behind.getZ());
			        break;
			    case 4:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
			        break;
			    case 0:
			        behind = world.getBlockAt(behind.getX(), behind.getY(), behind.getZ() - 1);
			        break;
			    default:
			        break;
			}
			
			Location behind2 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+1, behind.getLocation().getZ());
			Location behind3 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+2, behind.getLocation().getZ());
			Location behind4 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+3, behind.getLocation().getZ());
			Location behind5 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+4, behind.getLocation().getZ());
			Location behind6 = new Location(behind.getWorld(), behind.getX(), behind.getLocation().getY()+5, behind.getLocation().getZ());
			
			final FallingBlock fb1 = player.getWorld().spawnFallingBlock(behind.getLocation(), customNyanID.get(player), (byte) 0);
			final FallingBlock fb2 = player.getWorld().spawnFallingBlock(behind2, customNyanID.get(player), (byte) 0);
			final FallingBlock fb3 = player.getWorld().spawnFallingBlock(behind3, customNyanID.get(player), (byte) 0);
			final FallingBlock fb4 = player.getWorld().spawnFallingBlock(behind4, customNyanID.get(player), (byte) 0);
			final FallingBlock fb5 = player.getWorld().spawnFallingBlock(behind5, customNyanID.get(player), (byte) 0);
			final FallingBlock fb6 = player.getWorld().spawnFallingBlock(behind6, customNyanID.get(player), (byte) 0);
			
			
			fb1.setDropItem(false);
			fb2.setDropItem(false);
			fb3.setDropItem(false);
			fb4.setDropItem(false);
			fb5.setDropItem(false);
			fb6.setDropItem(false);
			
			fb1.setVelocity(new Vector(0,0.35,0));
			fb2.setVelocity(new Vector(0,0.35,0));
			fb3.setVelocity(new Vector(0,0.35,0)); 
			fb4.setVelocity(new Vector(0,0.35,0));
			fb5.setVelocity(new Vector(0,0.35,0));
			fb6.setVelocity(new Vector(0,0.35,0));
	
			
			new BukkitRunnable() {
				 
		            @Override
		            public void run() {
		            	fb1.remove();
		            	fb2.remove();		
		            	fb3.remove();
		            	fb4.remove();
		            	fb5.remove();
		            	fb6.remove();
		            }	 
		        }.runTaskLater(this, 10);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(nyanIzados.contains(event.getPlayer())) {
			nyanIzados.remove(event.getPlayer());
		}
		if(nyanIzadosR.contains(event.getPlayer())) {
			nyanIzadosR.remove(event.getPlayer());
		}
		if(customNyanID.containsKey(event.getPlayer())) {
			customNyanID.remove(event.getPlayer());
		}
	}
}