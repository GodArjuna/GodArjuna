package com.godarjuna.narutojutsu;

import com.godarjuna.narutojutsu.commands.JutsuCommand;
import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.JutsuRegistry;
import com.godarjuna.narutojutsu.handlers.JutsuEventHandler;
import com.godarjuna.narutojutsu.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe Principal do Naruto Jutsu Mod
 * 
 * Este mod adiciona um sistema completo de Jutsus do anime Naruto ao Minecraft,
 * utilizando apenas partículas nativas para os efeitos visuais.
 * 
 * @author GodArjuna (Kawan Villar)
 * @version 1.0.0
 */
@Mod(modid = NarutoJutsuMod.MODID, name = NarutoJutsuMod.NAME, version = NarutoJutsuMod.VERSION)
public class NarutoJutsuMod {
    
    // Identificadores do Mod
    public static final String MODID = "narutojutsu";
    public static final String NAME = "Naruto Jutsu Mod";
    public static final String VERSION = "1.0.0";
    
    // Instância singleton do mod
    @Instance(MODID)
    public static NarutoJutsuMod instance;
    
    // Proxy para separar código cliente/servidor
    @SidedProxy(clientSide = "com.godarjuna.narutojutsu.proxy.ClientProxy", 
                serverSide = "com.godarjuna.narutojutsu.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    // Logger para debug e informações
    public static Logger logger = LogManager.getLogger(MODID);
    
    /**
     * Fase de Pré-Inicialização
     * Carrega configurações e registra os jutsus disponíveis
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.info("Naruto Jutsu Mod - Pré-Inicialização");
        
        // Carregar configurações do arquivo de config
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        
        // Registrar todos os Jutsus disponíveis
        JutsuRegistry.registerJutsus();
        
        proxy.preInit(event);
    }
    
    /**
     * Fase de Inicialização
     * Registra os manipuladores de eventos
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Naruto Jutsu Mod - Inicialização");
        
        // Registrar manipuladores de eventos do Forge
        MinecraftForge.EVENT_BUS.register(new JutsuEventHandler());
        
        proxy.init(event);
    }
    
    /**
     * Fase de Pós-Inicialização
     * Finaliza o carregamento e verifica integrações
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Naruto Jutsu Mod - Pós-Inicialização");
        
        proxy.postInit(event);
    }
    
    /**
     * Quando o servidor inicia
     * Registra os comandos personalizados
     */
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // Registrar comando /jutsu
        event.registerServerCommand(new JutsuCommand());
        logger.info("Comandos de Jutsu registrados com sucesso!");
    }
}
