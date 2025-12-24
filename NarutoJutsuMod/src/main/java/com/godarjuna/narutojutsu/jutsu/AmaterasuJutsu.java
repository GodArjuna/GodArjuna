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
 * Amaterasu - Fogo Negro Celestial
 * 
 * Técnica do Mangekyou Sharingan que cria chamas negras inextinguíveis.
 * As chamas causam dano contínuo e alto aos inimigos atingidos.
 * 
 * Efeitos Visuais:
 * - Partículas "smoke" (fumaça negra)
 * - Partículas "flame" (chamas)
 * - Partículas "lava" (intensidade)
 * 
 * @author GodArjuna
 */
public class AmaterasuJutsu extends BaseJutsu {
    
    /**
     * Construtor do Amaterasu
     * Inicializa com valores configuráveis
     */
    public AmaterasuJutsu() {
        super("Amaterasu", 
              "Fogo negro celestial que nunca se apaga e consome tudo", 
              ConfigHandler.amaterasuCooldown, 
              50.0, 
              ConfigHandler.amaterasuDamage);
    }
    
    /**
     * Executa o Amaterasu
     * 
     * 1. Cria chamas negras na direção do olhar
     * 2. Causa alto dano ao inimigo
     * 3. Incendeia o alvo por período prolongado
     * 4. Partículas de fogo negro
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
            // Criar partículas de fogo negro
            if (ConfigHandler.enableParticles) {
                createAmaterasuParticles(world, player);
            }
            
            // Calcular posição alvo na direção que o jogador está olhando
            Vec3 lookVec = player.getLookVec();
            double reach = 15.0; // Alcance longo
            
            double targetX = player.posX + lookVec.xCoord * reach;
            double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
            double targetZ = player.posZ + lookVec.zCoord * reach;
            
            // Criar área de efeito
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                targetX - 1.5, targetY - 1.5, targetZ - 1.5,
                targetX + 1.5, targetY + 1.5, targetZ + 1.5
            );
            
            // Obter todas as entidades na área
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            // Aplicar dano massivo e fogo prolongado
            for (Entity entity : entities) {
                if (entity != player && !(entity instanceof EntityPlayer)) {
                    // Calcular dano final
                    double finalDamage = getDamage();
                    
                    // Aplicar multiplicador do DBC se ativo
                    if (ConfigHandler.enableDBCIntegration) {
                        finalDamage *= ConfigHandler.dbcDamageMultiplier;
                    }
                    
                    // Causar dano alto
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    
                    // Incendiar por tempo prolongado (15 segundos)
                    entity.setFire(15);
                }
            }
        }
        
        return true;
    }
    
    /**
     * Cria o efeito visual do Amaterasu com partículas de fogo negro
     * 
     * @param world Mundo onde as partículas serão criadas
     * @param player Jogador como ponto de referência
     */
    private void createAmaterasuParticles(World world, EntityPlayer player) {
        // Calcular posição na direção do jogador
        Vec3 lookVec = player.getLookVec();
        
        int particleCount = ConfigHandler.particleDensity * 2;
        
        // Criar partículas ao longo do caminho
        for (double d = 1.0; d <= 15.0; d += 0.5) {
            double x = player.posX + lookVec.xCoord * d;
            double y = player.posY + player.getEyeHeight() + lookVec.yCoord * d;
            double z = player.posZ + lookVec.zCoord * d;
            
            // Criar cluster de partículas em cada ponto
            for (int i = 0; i < 3; i++) {
                double offsetX = (world.rand.nextDouble() - 0.5) * 0.8;
                double offsetY = (world.rand.nextDouble() - 0.5) * 0.8;
                double offsetZ = (world.rand.nextDouble() - 0.5) * 0.8;
                
                // Fumaça negra densa
                world.spawnParticle("smoke", 
                    x + offsetX, y + offsetY, z + offsetZ, 
                    0, 0.05, 0);
                
                world.spawnParticle("largesmoke", 
                    x + offsetX * 0.5, y + offsetY * 0.5, z + offsetZ * 0.5, 
                    0, 0.03, 0);
            }
            
            // Chamas ocasionais
            if (world.rand.nextInt(3) == 0) {
                world.spawnParticle("flame", x, y, z, 0, 0, 0);
            }
            
            // Lava para intensidade
            if (world.rand.nextInt(4) == 0) {
                world.spawnParticle("lava", x, y, z, 0, 0, 0);
            }
        }
    }
}
