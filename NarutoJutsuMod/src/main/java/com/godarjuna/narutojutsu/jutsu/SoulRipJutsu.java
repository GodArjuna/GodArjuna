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

/**
 * Soul Rip - Extração de Alma (Human Path)
 * 
 * Técnica do Caminho Humano que extrai a alma dos inimigos.
 * Causa dano massivo e enfraquece o alvo severamente.
 * 
 * Efeitos Visuais:
 * - Partículas "portal" (dimensão da alma)
 * - Partículas "witchMagic" (magia negra)
 * - Partículas "smoke" (alma sendo extraída)
 * 
 * @author GodArjuna
 */
public class SoulRipJutsu extends BaseJutsu {
    
    /**
     * Construtor do Soul Rip
     */
    public SoulRipJutsu() {
        super("SoulRip", 
              "Extrai a alma do inimigo causando dano massivo e enfraquecimento", 
              ConfigHandler.soulRipCooldown, 
              70.0, 
              ConfigHandler.soulRipDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Partículas
            if (ConfigHandler.enableParticles) {
                createSoulRipParticles(world, player);
            }
            
            // Alvo na frente do jogador (curto alcance)
            Vec3 lookVec = player.getLookVec();
            double reach = 3.0;
            
            double targetX = player.posX + lookVec.xCoord * reach;
            double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
            double targetZ = player.posZ + lookVec.zCoord * reach;
            
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                targetX - 1.5, targetY - 1.5, targetZ - 1.5,
                targetX + 1.5, targetY + 1.5, targetZ + 1.5
            );
            
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            for (Entity entity : entities) {
                if (entity != player) {
                    // Dano muito alto
                    double finalDamage = getDamage();
                    if (ConfigHandler.enableDBCIntegration) {
                        finalDamage *= ConfigHandler.dbcDamageMultiplier;
                    }
                    
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    
                    // Puxar o inimigo para perto
                    double dx = player.posX - entity.posX;
                    double dy = player.posY - entity.posY;
                    double dz = player.posZ - entity.posZ;
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    
                    if (distance > 0) {
                        entity.addVelocity(
                            (dx / distance) * 1.5,
                            (dy / distance) * 1.5,
                            (dz / distance) * 1.5
                        );
                    }
                    
                    // Apenas um alvo por vez
                    break;
                }
            }
        }
        
        return true;
    }
    
    private void createSoulRipParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        double reach = 3.0;
        
        double targetX = player.posX + lookVec.xCoord * reach;
        double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
        double targetZ = player.posZ + lookVec.zCoord * reach;
        
        int particleCount = ConfigHandler.particleDensity * 2;
        
        // Conexão entre jogador e alvo
        for (double d = 0.0; d <= 1.0; d += 0.05) {
            double x = player.posX + (targetX - player.posX) * d;
            double y = player.posY + player.getEyeHeight() + (targetY - player.posY - player.getEyeHeight()) * d;
            double z = player.posZ + (targetZ - player.posZ) * d;
            
            world.spawnParticle("portal", x, y, z, 0, 0, 0);
            
            if (world.rand.nextInt(2) == 0) {
                world.spawnParticle("witchMagic", x, y, z, 0, 0, 0);
            }
        }
        
        // Espiral no ponto alvo
        for (int i = 0; i < particleCount; i++) {
            double angle = (i / (double)particleCount) * Math.PI * 4;
            double radius = 0.3 + (i / (double)particleCount) * 1.0;
            
            double x = targetX + Math.cos(angle) * radius;
            double y = targetY + (i / (double)particleCount) * 2 - 1;
            double z = targetZ + Math.sin(angle) * radius;
            
            world.spawnParticle("smoke", x, y, z, 0, 0.1, 0);
            world.spawnParticle("portal", x, y, z, 0, 0, 0);
        }
    }
}
