package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class ShadowCloneJutsu extends BaseJutsu {
    
    public ShadowCloneJutsu() {
        super("ShadowClone", 
              "Técnica de Clone das Sombras - Cria clones que atacam os inimigos próximos", 
              ConfigHandler.shadowCloneCooldown, 
              30.0, 
              ConfigHandler.shadowCloneDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Criar partículas de fumaça
            if (ConfigHandler.enableParticles) {
                createShadowCloneParticles(world, player);
            }
            
            // Criar "clones" simulados que causam dano aos inimigos próximos
            int cloneCount = 3;
            double radius = 5.0;
            
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                player.posX - radius, player.posY - radius, player.posZ - radius,
                player.posX + radius, player.posY + radius, player.posZ + radius
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
            
            int attackedCount = 0;
            for (Entity entity : entities) {
                if (entity != player && entity instanceof EntityMob && attackedCount < cloneCount) {
                    double finalDamage = getDamage();
                    if (ConfigHandler.enableDBCIntegration) {
                        finalDamage *= ConfigHandler.dbcDamageMultiplier;
                    }
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    
                    // Criar efeito de clone atacando
                    createCloneAttackEffect(world, entity);
                    
                    attackedCount++;
                }
            }
            
            if (attackedCount == 0) {
                // Se não houver inimigos, criar clones visuais ao redor do jogador
                createVisualClones(world, player, cloneCount);
            }
        }
        
        return true;
    }
    
    private void createShadowCloneParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity * 2;
        
        for (int i = 0; i < particleCount; i++) {
            double angle = (i / (double)particleCount) * Math.PI * 2;
            double radius = 1.0 + world.rand.nextDouble();
            
            double offsetX = Math.cos(angle) * radius;
            double offsetY = world.rand.nextDouble() * 2.0;
            double offsetZ = Math.sin(angle) * radius;
            
            world.spawnParticle("explode", 
                player.posX + offsetX, 
                player.posY + offsetY, 
                player.posZ + offsetZ, 
                0, 0.1, 0);
            
            world.spawnParticle("smoke", 
                player.posX + offsetX, 
                player.posY + offsetY, 
                player.posZ + offsetZ, 
                offsetX * 0.1, 0.2, offsetZ * 0.1);
        }
    }
    
    private void createCloneAttackEffect(World world, Entity target) {
        for (int i = 0; i < 10; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 1.0;
            double offsetY = world.rand.nextDouble() * 1.5;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 1.0;
            
            world.spawnParticle("smoke", 
                target.posX + offsetX, 
                target.posY + offsetY, 
                target.posZ + offsetZ, 
                0, 0.1, 0);
            
            world.spawnParticle("crit", 
                target.posX + offsetX, 
                target.posY + offsetY, 
                target.posZ + offsetZ, 
                0, 0, 0);
        }
    }
    
    private void createVisualClones(World world, EntityPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            double angle = (i / (double)count) * Math.PI * 2;
            double radius = 2.0;
            
            double x = player.posX + Math.cos(angle) * radius;
            double y = player.posY;
            double z = player.posZ + Math.sin(angle) * radius;
            
            // Criar explosão de fumaça na posição do clone
            for (int j = 0; j < 15; j++) {
                double offsetX = (world.rand.nextDouble() - 0.5) * 0.5;
                double offsetY = world.rand.nextDouble() * 1.5;
                double offsetZ = (world.rand.nextDouble() - 0.5) * 0.5;
                
                world.spawnParticle("explode", x + offsetX, y + offsetY, z + offsetZ, 0, 0.1, 0);
            }
        }
    }
}
