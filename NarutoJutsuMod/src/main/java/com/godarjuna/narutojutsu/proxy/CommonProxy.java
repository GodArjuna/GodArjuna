package com.godarjuna.narutojutsu.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Proxy Comum (Servidor e Cliente)
 * 
 * Contém código que deve ser executado tanto no cliente quanto no servidor.
 * Classes proxy permitem separar código específico de cliente/servidor.
 * 
 * @author GodArjuna
 */
public class CommonProxy {
    
    /**
     * Fase de pré-inicialização
     * Código executado antes do mod inicializar
     */
    public void preInit(FMLPreInitializationEvent event) {
        // Código comum de pré-inicialização
    }
    
    /**
     * Fase de inicialização
     * Código principal de inicialização do mod
     */
    public void init(FMLInitializationEvent event) {
        // Código comum de inicialização
    }
    
    /**
     * Fase de pós-inicialização
     * Código executado após todos os mods inicializarem
     */
    public void postInit(FMLPostInitializationEvent event) {
        // Código comum de pós-inicialização
    }
}
