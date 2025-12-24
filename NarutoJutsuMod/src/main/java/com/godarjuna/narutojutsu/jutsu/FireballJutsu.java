package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class FireballJutsu extends BaseJutsu {
    
    public FireballJutsu() {
        super("Fireball", 
              "Jutsu de Bola de Fogo - Lança uma grande bola de fogo em direção ao inimigo", 
              ConfigHandler.fireballCooldown, 
              15.0, 
              ConfigHandler.fireballDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Criar bola de fogo
            Vec3 lookVec = player.getLookVec();
            
            EntitySmallFireball fireball = new EntitySmallFireball(world);
            fireball.setPosition(
                player.posX + lookVec.xCoord * 1.5,
                player.posY + player.getEyeHeight(),
                player.posZ + lookVec.zCoord * 1.5
            );
            
            fireball.accelerationX = lookVec.xCoord * 0.1;
            fireball.accelerationY = lookVec.yCoord * 0.1;
            fireball.accelerationZ = lookVec.zCoord * 0.1;
            
            world.spawnEntityInWorld(fireball);
            
            // Criar partículas de fogo
            if (ConfigHandler.enableParticles) {
                createFireballParticles(world, player);
            }
            
            // Dano adicional em área ao redor da bola de fogo
            scheduleFireballDamage(world, player, lookVec);
        }
        
        return true;
    }
    
    private void createFireballParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        
        int particleCount = ConfigHandler.particleDensity;
        for (int i = 0; i < particleCount; i++) {
            double distance = 1.0 + world.rand.nextDouble() * 2.0;
            double x = player.posX + lookVec.xCoord * distance;
            double y = player.posY + player.getEyeHeight() + lookVec.yCoord * distance;
            double z = player.posZ + lookVec.zCoord * distance;
            
            double offsetX = (world.rand.nextDouble() - 0.5) * 0.5;
            double offsetY = (world.rand.nextDouble() - 0.5) * 0.5;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 0.5;
            
            world.spawnParticle("flame", x + offsetX, y + offsetY, z + offsetZ, 
                lookVec.xCoord * 0.5, lookVec.yCoord * 0.5, lookVec.zCoord * 0.5);
            
            world.spawnParticle("smoke", x + offsetX, y + offsetY, z + offsetZ, 
                offsetX * 0.1, offsetY * 0.1, offsetZ * 0.1);
            
            if (i % 3 == 0) {
                world.spawnParticle("lava", x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
            }
        }
    }
    
    private void scheduleFireballDamage(World world, EntityPlayer player, Vec3 lookVec) {
        // Criar dano ao longo do caminho
        for (double d = 2.0; d <= 10.0; d += 1.0) {
            double targetX = player.posX + lookVec.xCoord * d;
            double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
            double targetZ = player.posZ + lookVec.zCoord * d;
            
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                targetX - 2, targetY - 2, targetZ - 2,
                targetX + 2, targetY + 2, targetZ + 2
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            for (Entity entity : entities) {
                if (entity != player) {
                    double finalDamage = getDamage();
                    if (ConfigHandler.enableDBCIntegration) {
                        finalDamage *= ConfigHandler.dbcDamageMultiplier;
                    }
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    entity.setFire(5); // Colocar fogo por 5 segundos
                }
            }
        }
    }
}
