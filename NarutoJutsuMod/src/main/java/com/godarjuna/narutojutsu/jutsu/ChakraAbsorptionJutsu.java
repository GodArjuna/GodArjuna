package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Chakra Absorption - Absorção de Chakra (Preta Path)
 * 
 * Técnica do Caminho Preta que absorve energia e chakra.
 * Regenera vida e remove efeitos negativos do usuário.
 * 
 * Efeitos Visuais:
 * - Partículas "heart" (cura)
 * - Partículas "happyVillager" (regeneração)
 * - Partículas "enchantmenttable" (absorção de energia)
 * 
 * @author GodArjuna
 */
public class ChakraAbsorptionJutsu extends BaseJutsu {
    
    /**
     * Construtor do Chakra Absorption
     */
    public ChakraAbsorptionJutsu() {
        super("ChakraAbsorption", 
              "Absorve energia ao redor curando o usuário e removendo efeitos negativos", 
              ConfigHandler.chakraAbsorptionCooldown, 
              30.0, 
              0.0); // Não causa dano, apenas cura
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Partículas
            if (ConfigHandler.enableParticles) {
                createChakraAbsorptionParticles(world, player);
            }
            
            // Curar o jogador
            float healAmount = (float)ConfigHandler.chakraAbsorptionHeal;
            player.heal(healAmount);
            
            // Aplicar regeneração
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 1));
            
            // Aplicar absorção (corações amarelos extras)
            player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 200, 0)); // absorption
            
            // Remover efeitos negativos
            player.removePotionEffect(Potion.poison.id);
            player.removePotionEffect(Potion.wither.id);
            player.removePotionEffect(Potion.moveSlowdown.id);
            player.removePotionEffect(Potion.weakness.id);
            player.removePotionEffect(Potion.blindness.id);
            player.removePotionEffect(Potion.confusion.id);
            
            // Apagar fogo
            player.extinguish();
        }
        
        return true;
    }
    
    private void createChakraAbsorptionParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity * 3;
        
        // Partículas convergindo para o jogador
        for (int i = 0; i < particleCount; i++) {
            double angle = (i / (double)particleCount) * Math.PI * 2;
            double radius = 3.0 + world.rand.nextDouble() * 2.0;
            
            double x = player.posX + Math.cos(angle) * radius;
            double y = player.posY + 1.0 + world.rand.nextDouble() * 2.0;
            double z = player.posZ + Math.sin(angle) * radius;
            
            // Velocidade em direção ao jogador
            double velX = -Math.cos(angle) * 0.2;
            double velZ = -Math.sin(angle) * 0.2;
            
            world.spawnParticle("enchantmenttable", x, y, z, velX, -0.1, velZ);
            
            if (world.rand.nextInt(2) == 0) {
                world.spawnParticle("heart", x, y, z, 0, 0.2, 0);
            }
            
            if (world.rand.nextInt(3) == 0) {
                world.spawnParticle("happyVillager", x, y, z, 0, 0, 0);
            }
        }
        
        // Aura ao redor do jogador
        for (int i = 0; i < 20; i++) {
            double angle = (i / 20.0) * Math.PI * 2;
            double x = player.posX + Math.cos(angle) * 1.5;
            double y = player.posY + 1.0;
            double z = player.posZ + Math.sin(angle) * 1.5;
            
            world.spawnParticle("happyVillager", x, y, z, 0, 0, 0);
        }
    }
}
