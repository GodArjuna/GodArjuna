package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SubstitutionJutsu extends BaseJutsu {
    
    public SubstitutionJutsu() {
        super("Substitution", 
              "Técnica de Substituição - Teleporta o jogador para trás, deixando fumaça no lugar original", 
              ConfigHandler.substitutionCooldown, 
              10.0, 
              0.0); // Não causa dano
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        if (!canUse(player)) {
            return false;
        }
        
        if (!world.isRemote) {
            // Guardar posição original
            double originalX = player.posX;
            double originalY = player.posY;
            double originalZ = player.posZ;
            
            // Calcular nova posição (atrás do jogador)
            Vec3 lookVec = player.getLookVec();
            double teleportDistance = 5.0;
            
            double newX = player.posX - lookVec.xCoord * teleportDistance;
            double newY = player.posY;
            double newZ = player.posZ - lookVec.zCoord * teleportDistance;
            
            // Ajustar Y para não ficar dentro de blocos
            while (newY > 0 && !world.isAirBlock((int)newX, (int)newY - 1, (int)newZ)) {
                newY += 1.0;
            }
            
            // Teleportar jogador
            player.setPosition(newX, newY, newZ);
            
            // Criar partículas na posição original
            if (ConfigHandler.enableParticles) {
                createSubstitutionParticles(world, originalX, originalY, originalZ);
                // Criar partículas na nova posição também
                createSubstitutionParticles(world, newX, newY, newZ);
            }
        }
        
        return true;
    }
    
    private void createSubstitutionParticles(World world, double x, double y, double z) {
        int particleCount = ConfigHandler.particleDensity * 3;
        
        for (int i = 0; i < particleCount; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 2.0;
            double offsetY = world.rand.nextDouble() * 2.5;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 2.0;
            
            // Fumaça densa
            world.spawnParticle("explode", 
                x + offsetX, 
                y + offsetY, 
                z + offsetZ, 
                0, 0.1, 0);
            
            world.spawnParticle("smoke", 
                x + offsetX * 0.5, 
                y + offsetY * 0.5, 
                z + offsetZ * 0.5, 
                offsetX * 0.2, 0.3, offsetZ * 0.2);
            
            world.spawnParticle("largesmoke", 
                x + offsetX * 0.3, 
                y + offsetY * 0.7, 
                z + offsetZ * 0.3, 
                offsetX * 0.1, 0.2, offsetZ * 0.1);
        }
        
        // Criar efeito de "tronco" substituto usando partículas
        for (int i = 0; i < 20; i++) {
            double offsetY = (i / 20.0) * 2.0;
            world.spawnParticle("blockcrack_17_0", x, y + offsetY, z, 0, 0, 0);
        }
    }
}
