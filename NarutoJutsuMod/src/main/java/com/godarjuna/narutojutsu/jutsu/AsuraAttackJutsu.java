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
 * Asura Attack - Ataque Asura (Asura Path)
 * 
 * Técnica do Caminho Asura que lança mísseis e lasers mecânicos.
 * Ataque de longo alcance com múltiplos projéteis.
 * 
 * Efeitos Visuais:
 * - Partículas "flame" (propulsão)
 * - Partículas "smoke" (fumaça dos mísseis)
 * - Partículas "fireworksSpark" (energia)
 * 
 * @author GodArjuna
 */
public class AsuraAttackJutsu extends BaseJutsu {
    
    /**
     * Construtor do Asura Attack
     */
    public AsuraAttackJutsu() {
        super("AsuraAttack", 
              "Ataque mecânico com múltiplos mísseis e lasers devastadores", 
              ConfigHandler.asuraAttackCooldown, 
              50.0, 
              ConfigHandler.asuraAttackDamage);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Partículas
            if (ConfigHandler.enableParticles) {
                createAsuraAttackParticles(world, player);
            }
            
            Vec3 lookVec = player.getLookVec();
            
            // Lançar múltiplos "mísseis" em cone
            for (int missile = 0; missile < 5; missile++) {
                double spread = (missile - 2) * 0.2; // Espalhar mísseis
                
                Vec3 perpVec = Vec3.createVectorHelper(-lookVec.zCoord, 0, lookVec.xCoord).normalize();
                double missileX = lookVec.xCoord + perpVec.xCoord * spread;
                double missileZ = lookVec.zCoord + perpVec.zCoord * spread;
                
                // Trajetória do míssil
                for (double d = 1.0; d <= 15.0; d += 1.0) {
                    double targetX = player.posX + missileX * d;
                    double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
                    double targetZ = player.posZ + missileZ * d;
                    
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
                            entity.setFire(3);
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    private void createAsuraAttackParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        
        // Criar trilhas de mísseis
        for (int missile = 0; missile < 5; missile++) {
            double spread = (missile - 2) * 0.2;
            Vec3 perpVec = Vec3.createVectorHelper(-lookVec.zCoord, 0, lookVec.xCoord).normalize();
            double missileX = lookVec.xCoord + perpVec.xCoord * spread;
            double missileZ = lookVec.zCoord + perpVec.zCoord * spread;
            
            for (double d = 0.5; d <= 15.0; d += 0.5) {
                double x = player.posX + missileX * d;
                double y = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
                double z = player.posZ + missileZ * d;
                
                // Chamas de propulsão
                world.spawnParticle("flame", x, y, z, 0, 0, 0);
                world.spawnParticle("smoke", x, y, z, 0, 0, 0);
                
                if (world.rand.nextInt(2) == 0) {
                    world.spawnParticle("fireworksSpark", x, y, z, 
                        (world.rand.nextDouble() - 0.5) * 0.2, 
                        (world.rand.nextDouble() - 0.5) * 0.2, 
                        (world.rand.nextDouble() - 0.5) * 0.2);
                }
            }
        }
    }
}
