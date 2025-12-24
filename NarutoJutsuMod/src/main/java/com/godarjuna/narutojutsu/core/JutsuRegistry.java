package com.godarjuna.narutojutsu.core;

import com.godarjuna.narutojutsu.jutsu.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Registro Central de Jutsus
 * 
 * Gerencia todos os jutsus disponíveis no mod, permitindo
 * registro, busca e listagem de técnicas.
 * 
 * @author GodArjuna
 */
public class JutsuRegistry {
    
    // Mapa de jutsus registrados (nome -> instância)
    private static final Map<String, IJutsu> jutsus = new HashMap<String, IJutsu>();
    
    /**
     * Registra todos os Jutsus disponíveis no mod
     * Chamado durante a pré-inicialização do mod
     */
    public static void registerJutsus() {
        // Jutsus básicos
        registerJutsu(new RasenganJutsu());
        registerJutsu(new ChidoriJutsu());
        registerJutsu(new FireballJutsu());
        registerJutsu(new ShadowCloneJutsu());
        registerJutsu(new SubstitutionJutsu());
        
        // Jutsus avançados
        registerJutsu(new AmaterasuJutsu());
        registerJutsu(new EightGatesJutsu());
        registerJutsu(new WaterDragonJutsu());
        
        // Jutsus dos 6 Caminhos do Pain
        registerJutsu(new ShinraTenseiJutsu());    // Deva Path - Repulsão
        registerJutsu(new BanshoTeninJutsu());     // Deva Path - Atração
        registerJutsu(new AsuraAttackJutsu());     // Asura Path
        registerJutsu(new AnimalSummonJutsu());    // Animal Path
        registerJutsu(new ChakraAbsorptionJutsu());// Preta Path
        registerJutsu(new SoulRipJutsu());         // Human Path
    }
    
    /**
     * Registra um novo jutsu no sistema
     * @param jutsu Instância do jutsu a ser registrado
     */
    public static void registerJutsu(IJutsu jutsu) {
        jutsus.put(jutsu.getName().toLowerCase(), jutsu);
    }
    
    /**
     * Obtém um jutsu pelo nome
     * @param name Nome do jutsu (case-insensitive)
     * @return Instância do jutsu ou null se não encontrado
     */
    public static IJutsu getJutsu(String name) {
        return jutsus.get(name.toLowerCase());
    }
    
    /**
     * Retorna todos os jutsus registrados
     * @return Mapa com todos os jutsus (cópia para segurança)
     */
    public static Map<String, IJutsu> getAllJutsus() {
        return new HashMap<String, IJutsu>(jutsus);
    }
    
    /**
     * Verifica se um jutsu está registrado
     * @param name Nome do jutsu a verificar
     * @return true se o jutsu existe, false caso contrário
     */
    public static boolean hasJutsu(String name) {
        return jutsus.containsKey(name.toLowerCase());
    }
}
