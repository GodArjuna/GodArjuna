package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Eight Gates - Oito Portões Internos
 * 
 * Técnica que abre os portões internos do corpo, aumentando drasticamente
 * força e velocidade, mas causando dano ao usuário.
 * 
 * Efeitos Visuais:
 * - Partículas "reddust" (aura vermelha)
 * - Partículas "flame" (intensidade)
 * - Partículas "crit" (energia)
 * 
 * @author GodArjuna
 */
public class EightGatesJutsu extends BaseJutsu {
    
    /**
     * Construtor do Eight Gates
     * Inicializa com valores configuráveis
     */
    public EightGatesJutsu() {
        super("EightGates", 
              "Abre os portões internos aumentando força e velocidade drasticamente", 
              ConfigHandler.eightGatesCooldown, 
              100.0, 
              ConfigHandler.eightGatesDamage);
    }
    
    /**
     * Executa o Eight Gates
     * 
     * 1. Cria aura vermelha ao redor do jogador
     * 2. Aplica efeitos de velocidade e força
     * 3. Causa dano ao jogador como custo (efeito colateral)
     * 4. Duração temporária dos efeitos
     * 
     * @param world Mundo onde o jutsu será executado
     * @param player Jogador que está usando o jutsu
     * @return true se executado com sucesso
     */
    @Override
    public boolean execute(World world, EntityPlayer player) {
        // Verificar se o jogador pode usar o jutsu
        if (!canUse(player)) {
            return false;
        }
        
        // Executar apenas no servidor para evitar duplicação
        if (!world.isRemote) {
            // Criar partículas de aura vermelha
            if (ConfigHandler.enableParticles) {
                createEightGatesParticles(world, player);
            }
            
            // Aplicar efeitos positivos (20 segundos)
            int duration = 400; // 20 segundos em ticks
            
            // Speed III (velocidade muito alta)
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, duration, 2));
            
            // Strength III (força muito alta)
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, duration, 2));
            
            // Jump Boost II (salto aumentado)
            player.addPotionEffect(new PotionEffect(Potion.jump.id, duration, 1));
            
            // Resistance I (resistência ao dano)
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, duration, 0));
            
            // Custo: causar dano ao jogador (efeito colateral)
            double selfDamage = getDamage();
            if (selfDamage > 0) {
                player.attackEntityFrom(DamageSource.magic, (float)selfDamage);
            }
        }
        
        return true;
    }
    
    /**
     * Cria o efeito visual do Eight Gates com aura vermelha intensa
     * 
     * @param world Mundo onde as partículas serão criadas
     * @param player Jogador como ponto de referência
     */
    private void createEightGatesParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity * 3;
        
        // Criar aura circular ao redor do jogador
        for (int i = 0; i < particleCount; i++) {
            // Ângulo para distribuição circular
            double angle = (i / (double)particleCount) * Math.PI * 2;
            double radius = 1.5 + world.rand.nextDouble() * 0.5;
            
            // Posição em círculo
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double offsetY = world.rand.nextDouble() * 2.5;
            
            double x = player.posX + offsetX;
            double y = player.posY + offsetY;
            double z = player.posZ + offsetZ;
            
            // Partículas vermelhas (reddust)
            world.spawnParticle("reddust", x, y, z, 1.0, 0, 0);
            
            // Chamas ocasionais
            if (world.rand.nextInt(3) == 0) {
                world.spawnParticle("flame", x, y, z, 0, 0.1, 0);
            }
            
            // Partículas de energia (crit)
            if (world.rand.nextInt(2) == 0) {
                world.spawnParticle("crit", x, y, z, 
                    (world.rand.nextDouble() - 0.5) * 0.3, 
                    0.2, 
                    (world.rand.nextDouble() - 0.5) * 0.3);
            }
        }
        
        // Explosão inicial de energia
        for (int i = 0; i < 20; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 3.0;
            double offsetY = world.rand.nextDouble() * 2.0;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 3.0;
            
            world.spawnParticle("explode", 
                player.posX + offsetX, 
                player.posY + offsetY, 
                player.posZ + offsetZ, 
                0, 0, 0);
        }
    }
}
