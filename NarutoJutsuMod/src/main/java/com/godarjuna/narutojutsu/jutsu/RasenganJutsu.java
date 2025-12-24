package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class RasenganJutsu extends BaseJutsu {
    
    public RasenganJutsu() {
        super("Rasengan", 
              "Uma esfera rotativa de chakra que causa grande dano ao inimigo", 
              ConfigHandler.rasenganCooldown, 
              20.0, 
              ConfigHandler.rasenganDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Criar partículas em espiral
            if (ConfigHandler.enableParticles) {
                createRasenganParticles(world, player);
            }
            
            // Causar dano em área
            Vec3 lookVec = player.getLookVec();
            double reach = 3.0;
            
            double targetX = player.posX + lookVec.xCoord * reach;
            double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
            double targetZ = player.posZ + lookVec.zCoord * reach;
            
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                targetX - 2, targetY - 2, targetZ - 2,
                targetX + 2, targetY + 2, targetZ + 2
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            for (Entity entity : entities) {
                if (entity != player && entity instanceof EntityPlayer == false) {
                    double finalDamage = getDamage();
                    if (ConfigHandler.enableDBCIntegration) {
                        finalDamage *= ConfigHandler.dbcDamageMultiplier;
                    }
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    
                    // Empurrar o inimigo
                    double knockbackX = lookVec.xCoord * 1.5;
                    double knockbackY = 0.5;
                    double knockbackZ = lookVec.zCoord * 1.5;
                    entity.addVelocity(knockbackX, knockbackY, knockbackZ);
                }
            }
        }
        
        return true;
    }
    
    private void createRasenganParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        double reach = 1.5;
        
        double centerX = player.posX + lookVec.xCoord * reach;
        double centerY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
        double centerZ = player.posZ + lookVec.zCoord * reach;
        
        // Criar espiral de partículas
        int particleCount = ConfigHandler.particleDensity;
        for (int i = 0; i < particleCount; i++) {
            double angle = (i / (double)particleCount) * Math.PI * 4;
            double radius = 0.5 + (i / (double)particleCount) * 0.5;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetY = (i / (double)particleCount) * 2 - 1;
            double offsetZ = Math.sin(angle) * radius;
            
            world.spawnParticle("magicCrit", 
                centerX + offsetX, 
                centerY + offsetY, 
                centerZ + offsetZ, 
                -offsetX * 0.1, 0, -offsetZ * 0.1);
            
            world.spawnParticle("crit", 
                centerX + offsetX * 0.5, 
                centerY + offsetY * 0.5, 
                centerZ + offsetZ * 0.5, 
                0, 0, 0);
        }
    }
}
