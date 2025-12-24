package com.godarjuna.narutojutsu.config;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

/**
 * Gerenciador de Configurações do Mod
 * 
 * Esta classe carrega e gerencia todas as configurações personalizáveis
 * do mod, incluindo danos, cooldowns, partículas e integrações.
 * 
 * @author GodArjuna
 */
public class ConfigHandler {
    
    // Objeto de configuração do Forge
    public static Configuration config;
    
    // ==================== CONFIGURAÇÕES DE ITENS ====================
    
    /** Item usado para ativar jutsus (formato: modid:itemname ou modid:itemname:metadata) */
    public static String jutsuActivationItem = "minecraft:paper";
    
    /** Se true, o jogador precisa ter o item de ativação para usar jutsus */
    public static boolean requireItemToUse = true;
    
    // ==================== CONFIGURAÇÕES DE DANO ====================
    
    /** Dano causado pelo Rasengan */
    public static double rasenganDamage = 15.0;
    
    /** Dano causado pelo Chidori */
    public static double chidoriDamage = 18.0;
    
    /** Dano causado pelo Fireball Jutsu */
    public static double fireballDamage = 12.0;
    
    /** Dano causado por cada Shadow Clone */
    public static double shadowCloneDamage = 5.0;
    
    /** Dano causado pelo Amaterasu */
    public static double amaterasuDamage = 25.0;
    
    /** Dano causado pelo Eight Gates (auto-dano) */
    public static double eightGatesDamage = 5.0;
    
    /** Dano causado pelo Water Dragon */
    public static double waterDragonDamage = 20.0;
    
    /** Dano causado pelo Shinra Tensei */
    public static double shinraTenseiDamage = 18.0;
    
    /** Dano causado pelo Bansho Tenin */
    public static double banshoTeninDamage = 8.0;
    
    /** Dano causado pelo Asura Attack */
    public static double asuraAttackDamage = 15.0;
    
    /** Dano causado pelo Animal Summon */
    public static double animalSummonDamage = 10.0;
    
    /** Cura do Chakra Absorption */
    public static double chakraAbsorptionHeal = 10.0;
    
    /** Dano causado pelo Soul Rip */
    public static double soulRipDamage = 30.0;
    
    // ==================== CONFIGURAÇÕES DE PARTÍCULAS ====================
    
    /** Ativa/desativa efeitos de partículas */
    public static boolean enableParticles = true;
    
    /** Densidade de partículas (1-100, quanto maior mais partículas) */
    public static int particleDensity = 20;
    
    // ==================== CONFIGURAÇÕES DE COOLDOWN ====================
    
    /** Cooldown do Rasengan em ticks (20 ticks = 1 segundo) */
    public static int rasenganCooldown = 100;
    
    /** Cooldown do Chidori em ticks */
    public static int chidoriCooldown = 120;
    
    /** Cooldown do Fireball Jutsu em ticks */
    public static int fireballCooldown = 60;
    
    /** Cooldown do Shadow Clone Jutsu em ticks */
    public static int shadowCloneCooldown = 200;
    
    /** Cooldown do Substitution Jutsu em ticks */
    public static int substitutionCooldown = 150;
    
    /** Cooldown do Amaterasu em ticks */
    public static int amaterasuCooldown = 300;
    
    /** Cooldown do Eight Gates em ticks */
    public static int eightGatesCooldown = 600;
    
    /** Cooldown do Water Dragon em ticks */
    public static int waterDragonCooldown = 200;
    
    /** Cooldown do Shinra Tensei em ticks */
    public static int shinraTenseiCooldown = 250;
    
    /** Cooldown do Bansho Tenin em ticks */
    public static int banshoTeninCooldown = 180;
    
    /** Cooldown do Asura Attack em ticks */
    public static int asuraAttackCooldown = 220;
    
    /** Cooldown do Animal Summon em ticks */
    public static int animalSummonCooldown = 240;
    
    /** Cooldown do Chakra Absorption em ticks */
    public static int chakraAbsorptionCooldown = 300;
    
    /** Cooldown do Soul Rip em ticks */
    public static int soulRipCooldown = 350;
    
    // ==================== INTEGRAÇÃO DRAGON BLOCK C ====================
    
    /** Ativa integração com Dragon Block C (se instalado) */
    public static boolean enableDBCIntegration = true;
    
    /** Multiplicador de dano quando Dragon Block C está ativo */
    public static double dbcDamageMultiplier = 1.0;
    
    /**
     * Inicializa e carrega o arquivo de configuração
     * 
     * @param configFile Arquivo de configuração sugerido pelo Forge
     */
    public static void init(File configFile) {
        config = new Configuration(configFile);
        
        try {
            // Iniciar carregamento do arquivo
            config.load();
            
            // ==================== CATEGORIA: ITENS ====================
            jutsuActivationItem = config.getString("JutsuActivationItem", "items", 
                "minecraft:paper", 
                "Item usado para ativar Jutsus (formato: modid:itemname ou modid:itemname:metadata)");
            
            requireItemToUse = config.getBoolean("RequireItemToUse", "items", 
                true, 
                "Se true, jogadores precisam ter o item de ativação para usar Jutsus");
            
            // ==================== CATEGORIA: DANO ====================
            rasenganDamage = config.getFloat("RasenganDamage", "damage", 
                15.0f, 0.0f, 100.0f, 
                "Dano causado pelo Rasengan");
            
            chidoriDamage = config.getFloat("ChidoriDamage", "damage", 
                18.0f, 0.0f, 100.0f, 
                "Dano causado pelo Chidori");
            
            fireballDamage = config.getFloat("FireballDamage", "damage", 
                12.0f, 0.0f, 100.0f, 
                "Dano causado pelo Fireball Jutsu");
            
            shadowCloneDamage = config.getFloat("ShadowCloneDamage", "damage", 
                5.0f, 0.0f, 100.0f, 
                "Dano causado por cada Shadow Clone");
            
            amaterasuDamage = config.getFloat("AmaterasuDamage", "damage", 
                25.0f, 0.0f, 100.0f, 
                "Dano causado pelo Amaterasu");
            
            eightGatesDamage = config.getFloat("EightGatesDamage", "damage", 
                5.0f, 0.0f, 100.0f, 
                "Auto-dano causado pelo Eight Gates");
            
            waterDragonDamage = config.getFloat("WaterDragonDamage", "damage", 
                20.0f, 0.0f, 100.0f, 
                "Dano causado pelo Water Dragon");
            
            shinraTenseiDamage = config.getFloat("ShinraTenseiDamage", "damage", 
                18.0f, 0.0f, 100.0f, 
                "Dano causado pelo Shinra Tensei");
            
            banshoTeninDamage = config.getFloat("BanshoTeninDamage", "damage", 
                8.0f, 0.0f, 100.0f, 
                "Dano causado pelo Bansho Tenin");
            
            asuraAttackDamage = config.getFloat("AsuraAttackDamage", "damage", 
                15.0f, 0.0f, 100.0f, 
                "Dano causado pelo Asura Attack");
            
            animalSummonDamage = config.getFloat("AnimalSummonDamage", "damage", 
                10.0f, 0.0f, 100.0f, 
                "Dano causado pelo Animal Summon");
            
            chakraAbsorptionHeal = config.getFloat("ChakraAbsorptionHeal", "damage", 
                10.0f, 0.0f, 100.0f, 
                "Cura fornecida pelo Chakra Absorption");
            
            soulRipDamage = config.getFloat("SoulRipDamage", "damage", 
                30.0f, 0.0f, 100.0f, 
                "Dano causado pelo Soul Rip");
            
            // ==================== CATEGORIA: PARTÍCULAS ====================
            enableParticles = config.getBoolean("EnableParticles", "particles", 
                true, 
                "Ativar efeitos de partículas para Jutsus");
            
            particleDensity = config.getInt("ParticleDensity", "particles", 
                20, 1, 100, 
                "Densidade de partículas (quanto maior, mais partículas)");
            
            // ==================== CATEGORIA: COOLDOWNS ====================
            rasenganCooldown = config.getInt("RasenganCooldown", "cooldowns", 
                100, 0, 1000, 
                "Cooldown do Rasengan em ticks (20 ticks = 1 segundo)");
            
            chidoriCooldown = config.getInt("ChidoriCooldown", "cooldowns", 
                120, 0, 1000, 
                "Cooldown do Chidori em ticks");
            
            fireballCooldown = config.getInt("FireballCooldown", "cooldowns", 
                60, 0, 1000, 
                "Cooldown do Fireball Jutsu em ticks");
            
            shadowCloneCooldown = config.getInt("ShadowCloneCooldown", "cooldowns", 
                200, 0, 1000, 
                "Cooldown do Shadow Clone Jutsu em ticks");
            
            substitutionCooldown = config.getInt("SubstitutionCooldown", "cooldowns", 
                150, 0, 1000, 
                "Cooldown do Substitution Jutsu em ticks");
            
            amaterasuCooldown = config.getInt("AmaterasuCooldown", "cooldowns", 
                300, 0, 2000, 
                "Cooldown do Amaterasu em ticks");
            
            eightGatesCooldown = config.getInt("EightGatesCooldown", "cooldowns", 
                600, 0, 2000, 
                "Cooldown do Eight Gates em ticks");
            
            waterDragonCooldown = config.getInt("WaterDragonCooldown", "cooldowns", 
                200, 0, 2000, 
                "Cooldown do Water Dragon em ticks");
            
            shinraTenseiCooldown = config.getInt("ShinraTenseiCooldown", "cooldowns", 
                250, 0, 2000, 
                "Cooldown do Shinra Tensei em ticks");
            
            banshoTeninCooldown = config.getInt("BanshoTeninCooldown", "cooldowns", 
                180, 0, 2000, 
                "Cooldown do Bansho Tenin em ticks");
            
            asuraAttackCooldown = config.getInt("AsuraAttackCooldown", "cooldowns", 
                220, 0, 2000, 
                "Cooldown do Asura Attack em ticks");
            
            animalSummonCooldown = config.getInt("AnimalSummonCooldown", "cooldowns", 
                240, 0, 2000, 
                "Cooldown do Animal Summon em ticks");
            
            chakraAbsorptionCooldown = config.getInt("ChakraAbsorptionCooldown", "cooldowns", 
                300, 0, 2000, 
                "Cooldown do Chakra Absorption em ticks");
            
            soulRipCooldown = config.getInt("SoulRipCooldown", "cooldowns", 
                350, 0, 2000, 
                "Cooldown do Soul Rip em ticks");
            
            // ==================== CATEGORIA: INTEGRAÇÃO DRAGON BLOCK C ====================
            enableDBCIntegration = config.getBoolean("EnableDBCIntegration", "integration", 
                true, 
                "Ativar integração com Dragon Block C (se instalado)");
            
            dbcDamageMultiplier = config.getFloat("DBCDamageMultiplier", "integration", 
                1.0f, 0.1f, 10.0f, 
                "Multiplicador de dano quando Dragon Block C está ativo");
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar configuração do Naruto Jutsu Mod!");
            e.printStackTrace();
        } finally {
            // Salvar o arquivo se houver mudanças
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
