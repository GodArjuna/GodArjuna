package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * Shinra Tensei - Repulsão Celestial (Deva Path)
 * 
 * Técnica do Caminho Deva que repele tudo ao redor com força gravitacional.
 * Empurra todos os inimigos e entidades numa área grande.
 * 
 * Efeitos Visuais:
 * - Partículas "explode" (força repulsiva)
 * - Partículas "spell" (energia gravitacional)
 * - Partículas "magicCrit" (onda de choque)
 * 
 * @author GodArjuna
 */
public class ShinraTenseiJutsu extends BaseJutsu {
    
    /**
     * Construtor do Shinra Tensei
     */
    public ShinraTenseiJutsu() {
        super("ShinraTensei", 
              "Repulsão celestial que empurra tudo ao redor com força devastadora", 
              ConfigHandler.shinraTenseiCooldown, 
              60.0, 
              ConfigHandler.shinraTenseiDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Partículas
            if (ConfigHandler.enableParticles) {
                createShinraTenseiParticles(world, player);
            }
            
            // Área de efeito grande (raio de 10 blocos)
            double radius = 10.0;
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                player.posX - radius, player.posY - radius, player.posZ - radius,
                player.posX + radius, player.posY + radius, player.posZ + radius
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            for (Entity entity : entities) {
                if (entity != player) {
                    // Calcular direção de repulsão
                    double dx = entity.posX - player.posX;
                    double dy = entity.posY - player.posY;
                    double dz = entity.posZ - player.posZ;
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    
                    if (distance > 0) {
                        // Normalizar e aplicar força
                        double force = 3.0 * (1.0 - distance / radius);
                        dx = (dx / distance) * force;
                        dy = (dy / distance) * force + 0.5; // Adicionar componente vertical
                        dz = (dz / distance) * force;
                        
                        entity.addVelocity(dx, dy, dz);
                        
                        // Causar dano
                        double finalDamage = getDamage();
                        if (ConfigHandler.enableDBCIntegration) {
                            finalDamage *= ConfigHandler.dbcDamageMultiplier;
                        }
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    }
                }
            }
        }
        
        return true;
    }
    
    private void createShinraTenseiParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity * 4;
        
        // Ondas de choque concêntricas
        for (int ring = 1; ring <= 5; ring++) {
            double radius = ring * 2.0;
            int particlesInRing = particleCount / 5;
            
            for (int i = 0; i < particlesInRing; i++) {
                double angle = (i / (double)particlesInRing) * Math.PI * 2;
                double x = player.posX + Math.cos(angle) * radius;
                double y = player.posY + 1.0;
                double z = player.posZ + Math.sin(angle) * radius;
                
                world.spawnParticle("explode", x, y, z, 0, 0, 0);
                world.spawnParticle("spell", x, y, z, 
                    Math.cos(angle) * 0.5, 0, Math.sin(angle) * 0.5);
                
                if (world.rand.nextInt(2) == 0) {
                    world.spawnParticle("magicCrit", x, y + 0.5, z, 0, 0, 0);
                }
            }
        }
    }
}
