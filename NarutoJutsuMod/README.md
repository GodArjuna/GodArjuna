# Naruto Jutsu Mod - Minecraft 1.7.10

Um mod completo de Jutsus do anime Naruto para Minecraft 1.7.10, com sistema de partículas, comandos personalizados e integração com Dragon Block C.

## 🎮 Características

### Jutsus Implementados

1. **Rasengan**
   - Esfera rotativa de chakra com partículas em espiral
   - Causa dano em área e empurra inimigos
   - Partículas: `magicCrit` e `crit`

2. **Chidori**
   - Técnica de raio concentrada com partículas elétricas
   - Dano em linha reta com forte knockback
   - Partículas: `spell` e `instantSpell`

3. **Fireball Jutsu (Katon)**
   - Bola de fogo que incendeia inimigos
   - Lança projétil com efeitos de fogo
   - Partículas: `flame`, `smoke` e `lava`

4. **Shadow Clone Jutsu (Kage Bunshin)**
   - Cria clones que atacam inimigos próximos
   - Efeitos de fumaça e explosão
   - Partículas: `explode`, `smoke` e `crit`

5. **Substitution Jutsu (Kawarimi)**
   - Teleporta o jogador para trás
   - Deixa fumaça na posição original
   - Partículas: `explode`, `smoke` e `largesmoke`

## 📦 Instalação

1. Certifique-se de ter o **Minecraft Forge 1.7.10** instalado
2. Baixe o arquivo `NarutoJutsuMod-1.0.0.jar`
3. Coloque o arquivo na pasta `mods` do Minecraft
4. Inicie o jogo

## 🎯 Como Usar

### Comandos

#### Dar Jutsu a um Jogador
```
/jutsu give <nome_jutsu> <jogador>
```
Exemplos:
- `/jutsu give Rasengan Steve`
- `/jutsu give Chidori Alex`
- `/jutsu give Fireball Notch`

#### Listar Jutsus Disponíveis
```
/jutsu list
```
Mostra todos os jutsus disponíveis com descrições e estatísticas.

#### Usar Jutsu (via comando)
```
/jutsu use <nome_jutsu>
```
Exemplo: `/jutsu use Rasengan`

### Uso com Itens

1. Configure o item de ativação no arquivo de configuração
2. Use o comando `/jutsu give` para dar um jutsu ao jogador
3. O jogador pode usar o jutsu clicando com o botão direito enquanto segura o item configurado

## ⚙️ Configuração

O arquivo de configuração está localizado em: `config/narutojutsu.cfg`

### Configurações de Itens
```
items {
    S:JutsuActivationItem=minecraft:paper
    B:RequireItemToUse=true
}
```
- **JutsuActivationItem**: Item usado para ativar jutsus (formato: `modid:itemname` ou `modid:itemname:metadata`)
- **RequireItemToUse**: Se `true`, requer o item para usar jutsus

### Configurações de Dano
```
damage {
    S:RasenganDamage=15.0
    S:ChidoriDamage=18.0
    S:FireballDamage=12.0
    S:ShadowCloneDamage=5.0
}
```

### Configurações de Partículas
```
particles {
    B:EnableParticles=true
    I:ParticleDensity=20
}
```
- **EnableParticles**: Ativa/desativa efeitos de partículas
- **ParticleDensity**: Quantidade de partículas (1-100)

### Configurações de Cooldown
```
cooldowns {
    I:RasenganCooldown=100
    I:ChidoriCooldown=120
    I:FireballCooldown=60
    I:ShadowCloneCooldown=200
    I:SubstitutionCooldown=150
}
```
Valores em ticks (20 ticks = 1 segundo)

### Integração Dragon Block C
```
integration {
    B:EnableDBCIntegration=true
    S:DBCDamageMultiplier=1.0
}
```
- **EnableDBCIntegration**: Ativa integração com DBC
- **DBCDamageMultiplier**: Multiplicador de dano quando DBC está ativo

## 🔧 Integração com Custom NPC

Este mod foi projetado para funcionar com o mod Custom NPC. Você pode configurar NPCs para dar jutsus aos jogadores usando os comandos do mod.

### Exemplo de Script para NPC
```
/jutsu give Rasengan @p
```

## 🐉 Integração Dragon Block C

O mod detecta automaticamente se o Dragon Block C está instalado e ajusta o sistema de dano de acordo. Quando o DBC está presente:

- O dano dos jutsus é multiplicado pelo valor configurado
- O sistema respeita as mecânicas de poder do DBC
- Possibilidade de usar Ki como custo de chakra (implementação futura)

## 🎨 Efeitos Visuais

Todos os jutsus usam apenas **partículas nativas do Minecraft**, sem necessidade de modelos 3D:

- **magicCrit**: Partículas mágicas brilhantes
- **crit**: Partículas de crítico
- **spell**: Partículas de feitiço
- **instantSpell**: Partículas de feitiço instantâneo
- **flame**: Partículas de chama
- **smoke**: Partículas de fumaça
- **largesmoke**: Fumaça grande
- **explode**: Partículas de explosão
- **lava**: Partículas de lava

## 📝 Notas de Desenvolvimento

### Estrutura do Código
```
com.godarjuna.narutojutsu
├── NarutoJutsuMod.java          # Classe principal do mod
├── config/
│   └── ConfigHandler.java        # Gerenciador de configurações
├── core/
│   ├── IJutsu.java               # Interface de Jutsu
│   ├── BaseJutsu.java            # Classe base para jutsus
│   └── JutsuRegistry.java        # Registro de jutsus
├── jutsu/
│   ├── RasenganJutsu.java
│   ├── ChidoriJutsu.java
│   ├── FireballJutsu.java
│   ├── ShadowCloneJutsu.java
│   └── SubstitutionJutsu.java
├── commands/
│   └── JutsuCommand.java         # Comandos do mod
├── handlers/
│   ├── JutsuEventHandler.java    # Gerenciador de eventos
│   └── PlayerJutsuData.java      # Dados de jutsu dos jogadores
├── integration/
│   └── DBCIntegration.java       # Integração com DBC
└── proxy/
    ├── CommonProxy.java
    └── ClientProxy.java
```

## 🔮 Melhorias Futuras (Sugestões)

1. **Sistema de Níveis**
   - Jutsus evoluem com uso
   - Versões mais fortes de cada jutsu

2. **Mais Jutsus**
   - Byakugan
   - Sharingan
   - Rasen Shuriken
   - Amaterasu
   - Susanoo (versão simplificada)

3. **Sistema de Clãs**
   - Uchiha, Hyuga, Uzumaki, etc.
   - Jutsus exclusivos por clã

4. **Chakra Nature**
   - Sistema de elementos (Fogo, Água, Raio, Terra, Vento)
   - Combinações de elementos

5. **Kekkei Genkai**
   - Habilidades de linhagem sanguínea
   - Wood Style, Ice Style, etc.

6. **Sistema de Treinamento**
   - NPCs mestres que ensinam jutsus
   - Missões para desbloquear técnicas

7. **Hand Signs**
   - Sistema de sequência de teclas para ativar jutsus
   - Mais imersivo e desafiador

8. **Summons**
   - Invocações como Gamabunta, Katsuyu, Manda
   - Usando partículas para simular as criaturas

## 🛠️ Compilação

### Requisitos
- Java JDK 7 ou 8
- Gradle (incluído no projeto via wrapper)
- Minecraft Forge 1.7.10

### Compilar o Mod
```bash
cd NarutoJutsuMod
./gradlew build
```

O arquivo `.jar` compilado estará em: `build/libs/NarutoJutsuMod-1.0.0.jar`

## 🐛 Suporte e Bugs

Se encontrar algum bug ou tiver sugestões, por favor abra uma issue no repositório.

## 📄 Licença

Este mod é fornecido como está, para uso pessoal e em servidores. Sinta-se livre para modificar e adaptar conforme necessário.

## 👨‍💻 Autor

Desenvolvido por **GodArjuna** (Kawan Villar)

---

**Boa sorte, Shinobi! 🍥**
