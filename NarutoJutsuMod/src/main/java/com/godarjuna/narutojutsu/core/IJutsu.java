package com.godarjuna.narutojutsu.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Interface base para todos os Jutsus
 * 
 * Define o contrato que todas as técnicas ninja devem implementar,
 * incluindo execução, custos e verificações.
 * 
 * @author GodArjuna
 */
public interface IJutsu {
    
    /**
     * Retorna o nome do Jutsu
     * @return Nome da técnica
     */
    String getName();
    
    /**
     * Retorna a descrição do Jutsu
     * @return Descrição detalhada da técnica
     */
    String getDescription();
    
    /**
     * Executa o Jutsu
     * @param world Mundo onde o jutsu será executado
     * @param player Jogador que está usando o jutsu
     * @return true se o jutsu foi executado com sucesso, false caso contrário
     */
    boolean execute(World world, EntityPlayer player);
    
    /**
     * Retorna o cooldown do Jutsu em ticks
     * @return Tempo de recarga em ticks (20 ticks = 1 segundo)
     */
    int getCooldown();
    
    /**
     * Retorna o custo de chakra (ou energia) do Jutsu
     * @return Custo de chakra para executar a técnica
     */
    double getChakraCost();
    
    /**
     * Retorna o dano base do Jutsu
     * @return Valor de dano base
     */
    double getDamage();
    
    /**
     * Verifica se o jogador pode usar o jutsu
     * @param player Jogador a ser verificado
     * @return true se o jogador pode usar, false caso contrário
     */
    boolean canUse(EntityPlayer player);
}
