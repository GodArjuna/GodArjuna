package com.godarjuna.narutojutsu.handlers;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.IJutsu;
import com.godarjuna.narutojutsu.core.JutsuRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class JutsuEventHandler {
    
    /**
     * Manipula o uso de itens para ativar jutsus
     */
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR && 
            event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        EntityPlayer player = event.entityPlayer;
        ItemStack heldItem = player.getHeldItem();
        
        if (heldItem == null) {
            return;
        }
        
        // Verificar se o item é um item de jutsu
        if (!isJutsuItem(heldItem)) {
            return;
        }
        
        // Obter o jutsu associado ao item
        String jutsuName = getJutsuFromItem(heldItem);
        
        if (jutsuName == null || jutsuName.isEmpty()) {
            return;
        }
        
        // Verificar se o jogador possui o jutsu
        if (!PlayerJutsuData.hasJutsu(player, jutsuName)) {
            if (!player.worldObj.isRemote) {
                player.addChatMessage(new ChatComponentText("§cVocê não possui este jutsu!"));
            }
            return;
        }
        
        // Verificar cooldown
        if (PlayerJutsuData.isOnCooldown(player, jutsuName)) {
            if (!player.worldObj.isRemote) {
                int remainingTicks = PlayerJutsuData.getRemainingCooldown(player, jutsuName);
                player.addChatMessage(new ChatComponentText("§cJutsu em cooldown! Aguarde " + (remainingTicks / 20) + " segundos"));
            }
            return;
        }
        
        // Executar o jutsu
        IJutsu jutsu = JutsuRegistry.getJutsu(jutsuName);
        if (jutsu != null && !player.worldObj.isRemote) {
            if (jutsu.execute(player.worldObj, player)) {
                PlayerJutsuData.setCooldown(player, jutsuName, jutsu.getCooldown());
                player.addChatMessage(new ChatComponentText("§aUsou: §e" + jutsu.getName()));
                
                // Consumir item se configurado
                if (ConfigHandler.requireItemToUse && !player.capabilities.isCreativeMode) {
                    heldItem.stackSize--;
                    if (heldItem.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }
            } else {
                player.addChatMessage(new ChatComponentText("§cFalha ao usar o jutsu!"));
            }
        }
        
        event.setCanceled(true);
    }
    
    /**
     * Atualiza cooldowns dos jogadores
     */
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            PlayerJutsuData.updateCooldowns(event.player);
        }
    }
    
    /**
     * Verifica se um item é um item de jutsu
     */
    private boolean isJutsuItem(ItemStack item) {
        if (!item.hasTagCompound()) {
            return false;
        }
        
        NBTTagCompound tag = item.getTagCompound();
        return tag.hasKey("NarutoJutsu");
    }
    
    /**
     * Obtém o nome do jutsu de um item
     */
    private String getJutsuFromItem(ItemStack item) {
        if (!item.hasTagCompound()) {
            return null;
        }
        
        NBTTagCompound tag = item.getTagCompound();
        if (tag.hasKey("NarutoJutsu")) {
            return tag.getString("NarutoJutsu");
        }
        
        return null;
    }
    
    /**
     * Cria um item de jutsu
     */
    public static ItemStack createJutsuItem(String jutsuName) {
        // Obter o item configurado
        String[] itemParts = ConfigHandler.jutsuActivationItem.split(":");
        
        Item item;
        int metadata = 0;
        
        if (itemParts.length >= 2) {
            // Tentar obter o item do registro
            item = (Item) Item.itemRegistry.getObject(itemParts[0] + ":" + itemParts[1]);
            
            if (item == null) {
                // Fallback para papel
                item = (Item) Item.itemRegistry.getObject("minecraft:paper");
            }
            
            if (itemParts.length >= 3) {
                try {
                    metadata = Integer.parseInt(itemParts[2]);
                } catch (NumberFormatException e) {
                    metadata = 0;
                }
            }
        } else {
            // Fallback para papel
            item = (Item) Item.itemRegistry.getObject("minecraft:paper");
        }
        
        ItemStack itemStack = new ItemStack(item, 1, metadata);
        
        // Adicionar NBT tag com o nome do jutsu
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("NarutoJutsu", jutsuName);
        itemStack.setTagCompound(tag);
        
        // Definir nome customizado
        IJutsu jutsu = JutsuRegistry.getJutsu(jutsuName);
        if (jutsu != null) {
            itemStack.setStackDisplayName("§eJutsu: §b" + jutsu.getName());
        }
        
        return itemStack;
    }
}
