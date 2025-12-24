package com.godarjuna.narutojutsu.integration;

import com.godarjuna.narutojutsu.NarutoJutsuMod;
import com.godarjuna.narutojutsu.config.ConfigHandler;
import cpw.mods.fml.common.Loader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Classe de integração com Dragon Block C
 * Esta classe fornece métodos para integrar o sistema de dano com o DBC
 */
public class DBCIntegration {
    
    private static boolean dbcLoaded = false;
    private static boolean checkedForDBC = false;
    
    /**
     * Verifica se o Dragon Block C está carregado
     */
    public static boolean isDBCLoaded() {
        if (!checkedForDBC) {
            dbcLoaded = Loader.isModLoaded("dragonblockc") || Loader.isModLoaded("jinryuudragonblockc");
            checkedForDBC = true;
            
            if (dbcLoaded) {
                NarutoJutsuMod.logger.info("Dragon Block C detectado! Integração ativada.");
            } else {
                NarutoJutsuMod.logger.info("Dragon Block C não encontrado. Usando sistema de dano padrão.");
            }
        }
        
        return dbcLoaded;
    }
    
    /**
     * Aplica dano usando o sistema do DBC se disponível
     * @param attacker Atacante
     * @param target Alvo
     * @param baseDamage Dano base
     * @return Dano final aplicado
     */
    public static float applyDBCDamage(EntityPlayer attacker, Entity target, float baseDamage) {
        if (!ConfigHandler.enableDBCIntegration || !isDBCLoaded()) {
            return baseDamage;
        }
        
        // Aplicar multiplicador de dano do DBC
        float finalDamage = baseDamage * (float)ConfigHandler.dbcDamageMultiplier;
        
        try {
            // Tentar usar a API do DBC se disponível
            // NOTA: Isso requer que o DBC esteja presente no classpath
            // Como não temos acesso direto, usamos reflexão ou apenas aplicamos o multiplicador
            
            // Aqui você pode adicionar código específico do DBC quando o mod estiver disponível
            // Por exemplo, reduzir o Ki do jogador, etc.
            
            NarutoJutsuMod.logger.debug("Aplicando dano DBC: " + finalDamage + " (base: " + baseDamage + ")");
            
        } catch (Exception e) {
            NarutoJutsuMod.logger.warn("Erro ao aplicar integração DBC: " + e.getMessage());
            return baseDamage;
        }
        
        return finalDamage;
    }
    
    /**
     * Verifica se o jogador tem chakra/ki suficiente
     * @param player Jogador
     * @param cost Custo de chakra
     * @return true se o jogador pode usar
     */
    public static boolean hasEnoughChakra(EntityPlayer player, double cost) {
        if (!ConfigHandler.enableDBCIntegration || !isDBCLoaded()) {
            // Sem DBC, sempre permitir
            return true;
        }
        
        try {
            // Aqui você pode verificar o Ki do jogador usando a API do DBC
            // Por enquanto, sempre retornar true
            return true;
            
        } catch (Exception e) {
            NarutoJutsuMod.logger.warn("Erro ao verificar chakra DBC: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * Consome chakra/ki do jogador
     * @param player Jogador
     * @param cost Custo de chakra
     */
    public static void consumeChakra(EntityPlayer player, double cost) {
        if (!ConfigHandler.enableDBCIntegration || !isDBCLoaded()) {
            return;
        }
        
        try {
            // Aqui você pode reduzir o Ki do jogador usando a API do DBC
            NarutoJutsuMod.logger.debug("Consumindo " + cost + " de chakra/ki");
            
        } catch (Exception e) {
            NarutoJutsuMod.logger.warn("Erro ao consumir chakra DBC: " + e.getMessage());
        }
    }
    
    /**
     * Obtém o multiplicador de poder do jogador baseado em seu status no DBC
     * @param player Jogador
     * @return Multiplicador de poder
     */
    public static double getPlayerPowerMultiplier(EntityPlayer player) {
        if (!ConfigHandler.enableDBCIntegration || !isDBCLoaded()) {
            return 1.0;
        }
        
        try {
            // Aqui você pode obter o nível de poder do jogador do DBC
            // e retornar um multiplicador baseado nisso
            
            // Por enquanto, retornar 1.0
            return 1.0;
            
        } catch (Exception e) {
            NarutoJutsuMod.logger.warn("Erro ao obter multiplicador DBC: " + e.getMessage());
            return 1.0;
        }
    }
}
