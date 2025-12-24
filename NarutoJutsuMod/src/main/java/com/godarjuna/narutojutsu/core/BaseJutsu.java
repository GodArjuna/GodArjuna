package com.godarjuna.narutojutsu.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Classe Base para Implementação de Jutsus
 * 
 * Fornece implementação padrão para a interface IJutsu,
 * facilitando a criação de novos jutsus.
 * 
 * @author GodArjuna
 */
public abstract class BaseJutsu implements IJutsu {
    
    // Propriedades básicas do jutsu
    protected String name;           // Nome da técnica
    protected String description;    // Descrição da técnica
    protected int cooldown;         // Tempo de recarga em ticks
    protected double chakraCost;    // Custo de chakra
    protected double damage;        // Dano base
    
    /**
     * Construtor base para jutsus
     * 
     * @param name Nome do jutsu
     * @param description Descrição detalhada
     * @param cooldown Cooldown em ticks (20 ticks = 1 segundo)
     * @param chakraCost Custo de chakra para usar
     * @param damage Dano base causado
     */
    public BaseJutsu(String name, String description, int cooldown, double chakraCost, double damage) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.chakraCost = chakraCost;
        this.damage = damage;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public int getCooldown() {
        return cooldown;
    }
    
    @Override
    public double getChakraCost() {
        return chakraCost;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
    
    @Override
    public boolean canUse(EntityPlayer player) {
        // Verificações padrão
        // TODO: Implementar verificação de chakra quando integrado com DBC
        // TODO: Implementar verificação de cooldown
        // Por padrão, permitir uso
        return true;
    }
    
    /**
     * Método abstrato que deve ser implementado por cada jutsu específico
     * Define o comportamento único de cada técnica
     */
    @Override
    public abstract boolean execute(World world, EntityPlayer player);
    
    /**
     * Método auxiliar para spawnar partículas ao redor do jogador
     * 
     * @param world Mundo onde as partículas serão criadas
     * @param player Jogador como ponto de referência
     * @param particleType Tipo de partícula (ex: "flame", "smoke", "crit")
     * @param count Quantidade de partículas a spawnar
     */
    protected void spawnParticles(World world, EntityPlayer player, String particleType, int count) {
        // Apenas no servidor (evita duplicação)
        if (!world.isRemote) {
            // Posição central (jogador)
            double x = player.posX;
            double y = player.posY + 1.0;
            double z = player.posZ;
            
            // Spawnar partículas em posições aleatórias ao redor
            for (int i = 0; i < count; i++) {
                double offsetX = (world.rand.nextDouble() - 0.5) * 2.0;
                double offsetY = (world.rand.nextDouble() - 0.5) * 2.0;
                double offsetZ = (world.rand.nextDouble() - 0.5) * 2.0;
                
                world.spawnParticle(particleType, x + offsetX, y + offsetY, z + offsetZ, 0.0, 0.0, 0.0);
            }
        }
    }
}
