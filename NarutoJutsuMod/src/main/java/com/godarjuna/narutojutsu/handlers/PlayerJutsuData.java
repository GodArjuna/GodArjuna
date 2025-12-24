package com.godarjuna.narutojutsu.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerJutsuData {
    
    private static final String JUTSU_DATA_KEY = "NarutoJutsuData";
    private static final String JUTSU_LIST_KEY = "JutsuList";
    private static final String COOLDOWNS_KEY = "Cooldowns";
    
    // Cache de cooldowns em memória
    private static Map<String, Map<String, Integer>> cooldownCache = new HashMap<String, Map<String, Integer>>();
    
    /**
     * Dá um jutsu ao jogador
     */
    public static void giveJutsu(EntityPlayer player, String jutsuName) {
        NBTTagCompound playerData = player.getEntityData();
        
        if (!playerData.hasKey(JUTSU_DATA_KEY)) {
            playerData.setTag(JUTSU_DATA_KEY, new NBTTagCompound());
        }
        
        NBTTagCompound jutsuData = playerData.getCompoundTag(JUTSU_DATA_KEY);
        
        // Obter lista de jutsus
        NBTTagList jutsuList;
        if (jutsuData.hasKey(JUTSU_LIST_KEY)) {
            jutsuList = jutsuData.getTagList(JUTSU_LIST_KEY, 8); // 8 = String
        } else {
            jutsuList = new NBTTagList();
        }
        
        // Verificar se já possui
        boolean hasJutsu = false;
        for (int i = 0; i < jutsuList.tagCount(); i++) {
            String jutsu = jutsuList.getStringTagAt(i);
            if (jutsu.equalsIgnoreCase(jutsuName)) {
                hasJutsu = true;
                break;
            }
        }
        
        // Adicionar se não possuir
        if (!hasJutsu) {
            jutsuList.appendTag(new NBTTagString(jutsuName.toLowerCase()));
            jutsuData.setTag(JUTSU_LIST_KEY, jutsuList);
        }
    }
    
    /**
     * Verifica se o jogador possui um jutsu
     */
    public static boolean hasJutsu(EntityPlayer player, String jutsuName) {
        NBTTagCompound playerData = player.getEntityData();
        
        if (!playerData.hasKey(JUTSU_DATA_KEY)) {
            return false;
        }
        
        NBTTagCompound jutsuData = playerData.getCompoundTag(JUTSU_DATA_KEY);
        
        if (!jutsuData.hasKey(JUTSU_LIST_KEY)) {
            return false;
        }
        
        NBTTagList jutsuList = jutsuData.getTagList(JUTSU_LIST_KEY, 8);
        
        for (int i = 0; i < jutsuList.tagCount(); i++) {
            String jutsu = jutsuList.getStringTagAt(i);
            if (jutsu.equalsIgnoreCase(jutsuName)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Retorna a lista de jutsus do jogador
     */
    public static List<String> getJutsuList(EntityPlayer player) {
        List<String> jutsus = new ArrayList<String>();
        
        NBTTagCompound playerData = player.getEntityData();
        
        if (!playerData.hasKey(JUTSU_DATA_KEY)) {
            return jutsus;
        }
        
        NBTTagCompound jutsuData = playerData.getCompoundTag(JUTSU_DATA_KEY);
        
        if (!jutsuData.hasKey(JUTSU_LIST_KEY)) {
            return jutsus;
        }
        
        NBTTagList jutsuList = jutsuData.getTagList(JUTSU_LIST_KEY, 8);
        
        for (int i = 0; i < jutsuList.tagCount(); i++) {
            jutsus.add(jutsuList.getStringTagAt(i));
        }
        
        return jutsus;
    }
    
    /**
     * Define o cooldown de um jutsu
     */
    public static void setCooldown(EntityPlayer player, String jutsuName, int cooldownTicks) {
        String playerKey = player.getCommandSenderName();
        
        if (!cooldownCache.containsKey(playerKey)) {
            cooldownCache.put(playerKey, new HashMap<String, Integer>());
        }
        
        cooldownCache.get(playerKey).put(jutsuName.toLowerCase(), cooldownTicks);
    }
    
    /**
     * Verifica se um jutsu está em cooldown
     */
    public static boolean isOnCooldown(EntityPlayer player, String jutsuName) {
        String playerKey = player.getCommandSenderName();
        
        if (!cooldownCache.containsKey(playerKey)) {
            return false;
        }
        
        Map<String, Integer> playerCooldowns = cooldownCache.get(playerKey);
        
        if (!playerCooldowns.containsKey(jutsuName.toLowerCase())) {
            return false;
        }
        
        int remainingTicks = playerCooldowns.get(jutsuName.toLowerCase());
        return remainingTicks > 0;
    }
    
    /**
     * Retorna o cooldown restante em ticks
     */
    public static int getRemainingCooldown(EntityPlayer player, String jutsuName) {
        String playerKey = player.getCommandSenderName();
        
        if (!cooldownCache.containsKey(playerKey)) {
            return 0;
        }
        
        Map<String, Integer> playerCooldowns = cooldownCache.get(playerKey);
        
        if (!playerCooldowns.containsKey(jutsuName.toLowerCase())) {
            return 0;
        }
        
        return playerCooldowns.get(jutsuName.toLowerCase());
    }
    
    /**
     * Atualiza os cooldowns (deve ser chamado a cada tick)
     */
    public static void updateCooldowns(EntityPlayer player) {
        String playerKey = player.getCommandSenderName();
        
        if (!cooldownCache.containsKey(playerKey)) {
            return;
        }
        
        Map<String, Integer> playerCooldowns = cooldownCache.get(playerKey);
        List<String> toRemove = new ArrayList<String>();
        
        for (Map.Entry<String, Integer> entry : playerCooldowns.entrySet()) {
            int newCooldown = entry.getValue() - 1;
            if (newCooldown <= 0) {
                toRemove.add(entry.getKey());
            } else {
                playerCooldowns.put(entry.getKey(), newCooldown);
            }
        }
        
        for (String key : toRemove) {
            playerCooldowns.remove(key);
        }
    }
}
