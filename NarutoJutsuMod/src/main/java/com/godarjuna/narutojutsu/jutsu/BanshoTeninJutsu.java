package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

/**
 * Bansho Tenin - Atração Celestial (Deva Path)
 * 
 * Técnica do Caminho Deva que atrai entidades em direção ao usuário.
 * Puxa todos os inimigos próximos para perto.
 * 
 * Efeitos Visuais:
 * - Partículas "portal" (atração)
 * - Partículas "spell" (energia)
 * - Partículas "enchantmenttable" (magia)
 * 
 * @author GodArjuna
 */
public class BanshoTeninJutsu extends BaseJutsu {
    
    /**
     * Construtor do Bansho Tenin
     */
    public BanshoTeninJutsu() {
        super("BanshoTenin", 
              "Atração celestial que puxa inimigos em direção ao usuário", 
              ConfigHandler.banshoTeninCooldown, 
              40.0, 
              ConfigHandler.banshoTeninDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Partículas
            if (ConfigHandler.enableParticles) {
                createBanshoTeninParticles(world, player);
            }
            
            // Área de efeito
            double radius = 12.0;
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                player.posX - radius, player.posY - radius, player.posZ - radius,
                player.posX + radius, player.posY + radius, player.posZ + radius
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            for (Entity entity : entities) {
                if (entity != player) {
                    // Calcular direção de atração
                    double dx = player.posX - entity.posX;
                    double dy = player.posY - entity.posY;
                    double dz = player.posZ - entity.posZ;
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    
                    if (distance > 0) {
                        // Normalizar e aplicar força
                        double force = 2.0;
                        dx = (dx / distance) * force;
                        dy = (dy / distance) * force;
                        dz = (dz / distance) * force;
                        
                        entity.addVelocity(dx, dy, dz);
                    }
                }
            }
        }
        
        return true;
    }
    
    private void createBanshoTeninParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity * 3;
        
        // Partículas convergindo para o jogador
        for (int i = 0; i < particleCount; i++) {
            double angle = (i / (double)particleCount) * Math.PI * 2;
            double radius = 8.0 + world.rand.nextDouble() * 4.0;
            
            double x = player.posX + Math.cos(angle) * radius;
            double y = player.posY + 1.0 + (world.rand.nextDouble() - 0.5) * 3.0;
            double z = player.posZ + Math.sin(angle) * radius;
            
            // Velocidade em direção ao jogador
            double velX = -Math.cos(angle) * 0.3;
            double velZ = -Math.sin(angle) * 0.3;
            
            world.spawnParticle("portal", x, y, z, velX, 0, velZ);
            world.spawnParticle("spell", x, y, z, velX * 0.5, 0, velZ * 0.5);
            
            if (world.rand.nextInt(3) == 0) {
                world.spawnParticle("enchantmenttable", x, y, z, velX, 0, velZ);
            }
        }
    }
}
