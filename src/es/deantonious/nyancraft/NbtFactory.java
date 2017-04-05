package es.deantonious.nyancraft;

import net.minecraft.server.v1_7_R4.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.logging.Level;

/**
 * NBTManager has methods to read and write NBT tags
 */

@SuppressWarnings("UnusedDeclaration")
public class NbtFactory {

	public static <T extends Entity> NBTTagCompound getEntityNBT(T entity) {
	    NBTTagCompound compound = new NBTTagCompound();
	 
	    if(!(entity instanceof Entity))
	        return null;
	 
	    net.minecraft.server.v1_7_R4.Entity nms = ((CraftEntity) entity).getHandle();
	 
	    Class<? extends Object> clazz = nms.getClass();
	    Method[] methods = clazz.getMethods();
	    for (Method method : methods) {
	        if ((method.getName() == "b")
	                && (method.getParameterTypes().length == 1)
	                && (method.getParameterTypes()[0] == NBTTagCompound.class)) {
	            try {
	                method.setAccessible(true);
	                method.invoke(nms, compound);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	       }
	    }
	 
	    if(compound.getString("id") == null || compound.getString("id").isEmpty()) {
	        String id = entity.getType().getName();
	        compound.setString("id", id);
	    }
	 
	    return compound;
	}
	
	public static void setEntityNBT(Entity e, NBTTagCompound n) {
	    CraftEntity craft = ((CraftEntity) e);
	    net.minecraft.server.v1_7_R4.Entity nms = craft.getHandle();
	    Class<?> entityClass = nms.getClass();
	    Method[] methods = entityClass.getMethods();
	    for (Method method : methods) {
	        if ((method.getName() == "a")
	                && (method.getParameterTypes().length == 1)
	                && (method.getParameterTypes()[0] == NBTTagCompound.class)) {
	            try {
	                method.setAccessible(true);
	                method.invoke(nms, n);
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    craft.setHandle(nms);
	}

}