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

public class ChidoriJutsu extends BaseJutsu {
    
    public ChidoriJutsu() {
        super("Chidori", 
              "Técnica de raio concentrada na mão que causa dano elétrico devastador", 
              ConfigHandler.chidoriCooldown, 
              25.0, 
              ConfigHandler.chidoriDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Criar partículas de raio
            if (ConfigHandler.enableParticles) {
                createChidoriParticles(world, player);
            }
            
            // Causar dano em linha reta
            Vec3 lookVec = player.getLookVec();
            double reach = 4.0;
            
            // Dano ao longo da linha
            for (double d = 0.5; d <= reach; d += 0.5) {
                double targetX = player.posX + lookVec.xCoord * d;
                double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
                double targetZ = player.posZ + lookVec.zCoord * d;
                
                AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                    targetX - 1, targetY - 1, targetZ - 1,
                    targetX + 1, targetY + 1, targetZ + 1
                );
                
                List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
                
                for (Entity entity : entities) {
                    if (entity != player) {
                        double finalDamage = getDamage();
                        if (ConfigHandler.enableDBCIntegration) {
                            finalDamage *= ConfigHandler.dbcDamageMultiplier;
                        }
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                        
                        // Empurrar fortemente
                        double knockbackX = lookVec.xCoord * 2.0;
                        double knockbackY = 0.3;
                        double knockbackZ = lookVec.zCoord * 2.0;
                        entity.addVelocity(knockbackX, knockbackY, knockbackZ);
                        
                        break; // Apenas o primeiro inimigo
                    }
                }
            }
        }
        
        return true;
    }
    
    private void createChidoriParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        double reach = 2.0;
        
        double centerX = player.posX + lookVec.xCoord * reach;
        double centerY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
        double centerZ = player.posZ + lookVec.zCoord * reach;
        
        // Criar raios de partículas
        int particleCount = ConfigHandler.particleDensity;
        for (int i = 0; i < particleCount; i++) {
            double angle = world.rand.nextDouble() * Math.PI * 2;
            double radius = world.rand.nextDouble() * 0.8;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetY = (world.rand.nextDouble() - 0.5) * 1.5;
            double offsetZ = Math.sin(angle) * radius;
            
            // Partículas de raio (azul/branco)
            world.spawnParticle("spell", 
                centerX + offsetX, 
                centerY + offsetY, 
                centerZ + offsetZ, 
                (world.rand.nextDouble() - 0.5) * 0.5, 
                (world.rand.nextDouble() - 0.5) * 0.5, 
                (world.rand.nextDouble() - 0.5) * 0.5);
            
            world.spawnParticle("instantSpell", 
                centerX + offsetX * 0.5, 
                centerY + offsetY * 0.5, 
                centerZ + offsetZ * 0.5, 
                0, 0, 0);
        }
        
        // Partículas ao longo do caminho
        for (double d = 0.5; d <= 4.0; d += 0.3) {
            double x = player.posX + lookVec.xCoord * d;
            double y = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
            double z = player.posZ + lookVec.zCoord * d;
            
            world.spawnParticle("spell", x, y, z, 0, 0, 0);
        }
    }
}
