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
 * Rasengan - Esfera Espiral
 * 
 * Técnica icônica criada pelo Quarto Hokage e dominada por Naruto.
 * Cria uma esfera rotativa de chakra concentrado que causa alto dano
 * e empurra os inimigos para trás.
 * 
 * Efeitos Visuais:
 * - Partículas "magicCrit" em espiral
 * - Partículas "crit" no centro
 * 
 * @author GodArjuna
 */
public class RasenganJutsu extends BaseJutsu {
    
    /**
     * Construtor do Rasengan
     * Inicializa com valores configuráveis
     */
    public RasenganJutsu() {
        super("Rasengan", 
              "Uma esfera rotativa de chakra que causa grande dano ao inimigo", 
              ConfigHandler.rasenganCooldown, 
              20.0, 
              ConfigHandler.rasenganDamage);
    }
    
    /**
     * Executa o Rasengan
     * 
     * 1. Verifica se pode usar
     * 2. Cria partículas em espiral na frente do jogador
     * 3. Causa dano em área aos inimigos próximos
     * 4. Aplica knockback (empurrão) nos inimigos atingidos
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
            // Criar partículas em espiral
            if (ConfigHandler.enableParticles) {
                createRasenganParticles(world, player);
            }
            
            // Calcular posição alvo na direção que o jogador está olhando
            Vec3 lookVec = player.getLookVec();
            double reach = 3.0; // Alcance em blocos
            
            double targetX = player.posX + lookVec.xCoord * reach;
            double targetY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
            double targetZ = player.posZ + lookVec.zCoord * reach;
            
            // Criar área de efeito (bounding box)
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
                targetX - 2, targetY - 2, targetZ - 2,
                targetX + 2, targetY + 2, targetZ + 2
            );
            
            // Obter todas as entidades na área
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
            
            // Aplicar dano e knockback
            for (Entity entity : entities) {
                // Não danificar o próprio jogador nem outros jogadores
                if (entity != player && entity instanceof EntityPlayer == false) {
                    // Calcular dano final
                    double finalDamage = getDamage();
                    
                    // Aplicar multiplicador do DBC se ativo
                    if (ConfigHandler.enableDBCIntegration) {
                        finalDamage *= ConfigHandler.dbcDamageMultiplier;
                    }
                    
                    // Causar dano
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
                    
                    // Empurrar o inimigo (knockback)
                    double knockbackX = lookVec.xCoord * 1.5;
                    double knockbackY = 0.5;
                    double knockbackZ = lookVec.zCoord * 1.5;
                    entity.addVelocity(knockbackX, knockbackY, knockbackZ);
                }
            }
        }
        
        return true;
    }
    
    /**
     * Cria o efeito visual do Rasengan com partículas em espiral
     * 
     * @param world Mundo onde as partículas serão criadas
     * @param player Jogador como ponto de referência
     */
    private void createRasenganParticles(World world, EntityPlayer player) {
        // Calcular posição na frente do jogador
        Vec3 lookVec = player.getLookVec();
        double reach = 1.5;
        
        double centerX = player.posX + lookVec.xCoord * reach;
        double centerY = player.posY + player.getEyeHeight() + lookVec.yCoord * reach;
        double centerZ = player.posZ + lookVec.zCoord * reach;
        
        // Criar espiral de partículas
        int particleCount = ConfigHandler.particleDensity;
        for (int i = 0; i < particleCount; i++) {
            // Calcular ângulo e raio para espiral
            double angle = (i / (double)particleCount) * Math.PI * 4;
            double radius = 0.5 + (i / (double)particleCount) * 0.5;
            
            // Calcular offsets em espiral
            double offsetX = Math.cos(angle) * radius;
            double offsetY = (i / (double)particleCount) * 2 - 1;
            double offsetZ = Math.sin(angle) * radius;
            
            // Partículas mágicas brilhantes (azul/branco)
            world.spawnParticle("magicCrit", 
                centerX + offsetX, 
                centerY + offsetY, 
                centerZ + offsetZ, 
                -offsetX * 0.1, 0, -offsetZ * 0.1);
            
            // Partículas de crítico no centro
            world.spawnParticle("crit", 
                centerX + offsetX * 0.5, 
                centerY + offsetY * 0.5, 
                centerZ + offsetZ * 0.5, 
                0, 0, 0);
        }
    }
}
