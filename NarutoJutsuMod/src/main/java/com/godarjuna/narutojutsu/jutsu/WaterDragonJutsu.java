package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

/**
 * Water Dragon - Dragão de Água
 * 
 * Técnica de elemento água que cria um dragão feito de água
 * que ataca os inimigos causando dano e aplicando lentidão.
 * 
 * Efeitos Visuais:
 * - Partículas "splash" (água)
 * - Partículas "portal" (energia azul)
 * - Partículas "spell" (magia de água)
 * 
 * @author GodArjuna
 */
public class WaterDragonJutsu extends BaseJutsu {
    
    /**
     * Construtor do Water Dragon
     * Inicializa com valores configuráveis
     */
    public WaterDragonJutsu() {
        super("WaterDragon", 
              "Dragão feito de água que ataca inimigos com força devastadora", 
              ConfigHandler.waterDragonCooldown, 
              40.0, 
              ConfigHandler.waterDragonDamage);
    }
    
    /**
     * Executa o Water Dragon
     * 
     * 1. Cria dragão de água na frente do jogador
     * 2. O dragão avança causando dano
     * 3. Aplica efeito de lentidão aos atingidos
     * 4. Knockback nos inimigos
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
            // Criar partículas do dragão de água
            if (ConfigHandler.enableParticles) {
                createWaterDragonParticles(world, player);
            }
            
            // Calcular trajetória do dragão
            Vec3 lookVec = player.getLookVec();
            double reach = 12.0;
            
            // Causar dano ao longo do caminho do dragão
            for (double d = 1.0; d <= reach; d += 1.0) {
                double targetX = player.posX + lookVec.xCoord * d;
                double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
                double targetZ = player.posZ + lookVec.zCoord * d;
                
                // Criar área de efeito
                AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                    targetX - 2, targetY - 2, targetZ - 2,
                    targetX + 2, targetY + 2, targetZ + 2
                );
                
                // Obter todas as entidades na área
                List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
                
                // Aplicar dano e efeitos
                for (Entity entity : entities) {
                    if (entity != player) {
                        // Calcular dano final
                        double finalDamage = getDamage();
                        
                        // Aplicar multiplicador do DBC se ativo
                        if (ConfigHandler.enableDBCIntegration) {
                            finalDamage *= ConfigHandler.dbcDamageMultiplier;
                        }
                        
                        // Causar dano
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                        
                        // Aplicar lentidão (Slowness II por 5 segundos)
                        if (entity instanceof EntityPlayer) {
                            EntityPlayer targetPlayer = (EntityPlayer) entity;
                            targetPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1));
                        }
                        
                        // Knockback forte
                        double knockbackX = lookVec.xCoord * 1.2;
                        double knockbackY = 0.4;
                        double knockbackZ = lookVec.zCoord * 1.2;
                        entity.addVelocity(knockbackX, knockbackY, knockbackZ);
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Cria o efeito visual do Water Dragon com partículas de água
     * 
     * @param world Mundo onde as partículas serão criadas
     * @param player Jogador como ponto de referência
     */
    private void createWaterDragonParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        int particleCount = ConfigHandler.particleDensity * 2;
        
        // Criar corpo serpenteante do dragão
        for (double d = 0.5; d <= 12.0; d += 0.3) {
            double x = player.posX + lookVec.xCoord * d;
            double y = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
            double z = player.posZ + lookVec.zCoord * d;
            
            // Movimento serpenteante
            double serpentOffset = Math.sin(d * 0.5) * 0.8;
            double perpX = -lookVec.zCoord * serpentOffset;
            double perpZ = lookVec.xCoord * serpentOffset;
            
            x += perpX;
            z += perpZ;
            
            // Criar cluster de partículas
            for (int i = 0; i < 3; i++) {
                double offsetX = (world.rand.nextDouble() - 0.5) * 1.5;
                double offsetY = (world.rand.nextDouble() - 0.5) * 1.5;
                double offsetZ = (world.rand.nextDouble() - 0.5) * 1.5;
                
                // Partículas de água (splash)
                world.spawnParticle("splash", 
                    x + offsetX, y + offsetY, z + offsetZ, 
                    0, 0.1, 0);
                
                // Partículas azuis (portal)
                world.spawnParticle("portal", 
                    x + offsetX * 0.5, y + offsetY * 0.5, z + offsetZ * 0.5, 
                    0, 0, 0);
            }
            
            // Partículas de magia de água ocasionais
            if (world.rand.nextInt(2) == 0) {
                world.spawnParticle("spell", x, y, z, 0, 0.5, 1);
            }
        }
        
        // Cabeça do dragão (partículas mais densas no final)
        double headX = player.posX + lookVec.xCoord * 12.0;
        double headY = player.posY + player.getEyeHeight() + lookVec.yCoord * 12.0;
        double headZ = player.posZ + lookVec.zCoord * 12.0;
        
        for (int i = 0; i < 30; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 2.0;
            double offsetY = (world.rand.nextDouble() - 0.5) * 2.0;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 2.0;
            
            world.spawnParticle("splash", headX + offsetX, headY + offsetY, headZ + offsetZ, 0, 0, 0);
            world.spawnParticle("portal", headX + offsetX * 0.5, headY + offsetY * 0.5, headZ + offsetZ * 0.5, 0, 0, 0);
        }
    }
}
