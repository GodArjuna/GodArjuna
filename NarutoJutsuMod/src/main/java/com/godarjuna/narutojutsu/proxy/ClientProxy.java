package com.godarjuna.narutojutsu.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Proxy do Cliente
 * 
 * Contém código que só deve ser executado no lado do cliente.
 * Usado para registrar renderizadores, texturas, modelos, etc.
 * 
 * @author GodArjuna
 */
public class ClientProxy extends CommonProxy {
    
    /**
     * Pré-inicialização do cliente
     * Registrar recursos do cliente aqui
     */
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // Código específico do cliente para pré-inicialização
    }
    
    /**
     * Inicialização do cliente
     * Registrar renderizadores e handlers de cliente
     */
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // Código específico do cliente para inicialização
        // Registrar renderizadores, se necessário
    }
    
    /**
     * Pós-inicialização do cliente
     * Finalizações específicas do cliente
     */
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        // Código específico do cliente para pós-inicialização
    }
}
