package com.godarjuna.narutojutsu.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // Código específico do cliente para pré-inicialização
    }
    
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // Código específico do cliente para inicialização
        // Registrar renderizadores, se necessário
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        // Código específico do cliente para pós-inicialização
    }
}
