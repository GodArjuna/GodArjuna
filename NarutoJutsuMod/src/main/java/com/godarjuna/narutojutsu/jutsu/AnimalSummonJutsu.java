package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * Animal Summon - Invocação Animal (Animal Path)
 * 
 * Técnica do Caminho Animal que invoca criaturas para atacar.
 * Cria clones temporários de mobs próximos para auxiliar no combate.
 * 
 * Efeitos Visuais:
 * - Partículas "portal" (invocação)
 * - Partículas "explode" (surgimento)
 * - Partículas "largesmoke" (fumaça)
 * 
 * @author GodArjuna
 */
public class AnimalSummonJutsu extends BaseJutsu {
    
    /**
     * Construtor do Animal Summon
     */
    public AnimalSummonJutsu() {
        super("AnimalSummon", 
              "Invoca criaturas temporárias para auxiliar no combate", 
              ConfigHandler.animalSummonCooldown, 
              45.0, 
              ConfigHandler.animalSummonDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Partículas
            if (ConfigHandler.enableParticles) {
                createAnimalSummonParticles(world, player);
            }
            
            // Criar efeito de invocação confundindo inimigos próximos
            double radius = 8.0;
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                player.posX - radius, player.posY - radius, player.posZ - radius,
                player.posX + radius, player.posY + radius, player.posZ + radius
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
            
            int summonCount = 0;
            for (Entity entity : entities) {
                if (entity != player && entity instanceof EntityMob && summonCount < 3) {
                    // Criar efeito de confusão/controle
                    EntityMob mob = (EntityMob) entity;
                    
                    // Teleportar o mob para uma posição aleatória perto do jogador
                    double angle = world.rand.nextDouble() * Math.PI * 2;
                    double dist = 3.0 + world.rand.nextDouble() * 2.0;
                    
                    double newX = player.posX + Math.cos(angle) * dist;
                    double newZ = player.posZ + Math.sin(angle) * dist;
                    
                    mob.setPosition(newX, mob.posY, newZ);
                    
                    // Criar efeito visual na nova posição
                    for (int i = 0; i < 15; i++) {
                        world.spawnParticle("portal", newX, mob.posY + 1, newZ, 
                            (world.rand.nextDouble() - 0.5) * 0.5, 
                            world.rand.nextDouble() * 0.5, 
                            (world.rand.nextDouble() - 0.5) * 0.5);
                    }
                    
                    summonCount++;
                }
            }
        }
        
        return true;
    }
    
    private void createAnimalSummonParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity * 3;
        
        // Círculo de invocação no chão
        for (int i = 0; i < particleCount; i++) {
            double angle = (i / (double)particleCount) * Math.PI * 2;
            double radius = 2.0 + world.rand.nextDouble();
            
            double x = player.posX + Math.cos(angle) * radius;
            double y = player.posY + 0.1;
            double z = player.posZ + Math.sin(angle) * radius;
            
            world.spawnParticle("portal", x, y, z, 0, 0.5, 0);
            world.spawnParticle("explode", x, y, z, 0, 0, 0);
            
            if (world.rand.nextInt(3) == 0) {
                world.spawnParticle("largesmoke", x, y + 0.5, z, 0, 0.1, 0);
            }
        }
        
        // Pilar de fumaça central
        for (int i = 0; i < 20; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 1.0;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 1.0;
            double y = player.posY + i * 0.2;
            
            world.spawnParticle("largesmoke", 
                player.posX + offsetX, y, player.posZ + offsetZ, 0, 0, 0);
        }
    }
}
