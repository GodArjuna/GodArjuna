# Guia de Instalação e Uso - Naruto Jutsu Mod

## 🚀 Início Rápido

### Para Jogadores

1. **Instale o Minecraft Forge 1.7.10**
   - Baixe em: https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.7.10.html
   - Execute o instalador e selecione "Install Client"

2. **Instale o Mod**
   - Coloque o arquivo `NarutoJutsuMod-1.0.0.jar` na pasta `mods`
   - Localização da pasta mods:
     - Windows: `%appdata%/.minecraft/mods`
     - Linux: `~/.minecraft/mods`
     - Mac: `~/Library/Application Support/minecraft/mods`

3. **Inicie o Minecraft**
   - Selecione o perfil Forge 1.7.10
   - Clique em "Play"

### Primeiros Passos

1. **Entre em um mundo ou servidor**
2. **Use o comando (requer OP):**
   ```
   /jutsu give Rasengan <seu_nome>
   ```
3. **Use o jutsu:**
   ```
   /jutsu use Rasengan
   ```
   Ou configure um item no arquivo de configuração

## 📋 Comandos Detalhados

### /jutsu give
Da um jutsu a um jogador específico.

**Sintaxe:**
```
/jutsu give <jutsu> <jogador>
```

**Exemplos:**
```
/jutsu give Rasengan Steve
/jutsu give Chidori @p          # Dá para o jogador mais próximo
/jutsu give Fireball @a         # Dá para todos os jogadores
```

**Jutsus Disponíveis:**
- `Rasengan` - Esfera de chakra rotativa
- `Chidori` - Técnica de raio
- `Fireball` - Bola de fogo
- `ShadowClone` - Clone das sombras
- `Substitution` - Substituição

### /jutsu list
Lista todos os jutsus disponíveis com descrições.

**Sintaxe:**
```
/jutsu list
```

**Saída:**
```
=== Jutsus Disponíveis ===
Rasengan - Uma esfera rotativa de chakra...
  Dano: 15.0 | Cooldown: 5s
Chidori - Técnica de raio concentrada...
  Dano: 18.0 | Cooldown: 6s
...
```

### /jutsu use
Usa um jutsu que você possui.

**Sintaxe:**
```
/jutsu use <jutsu>
```

**Exemplo:**
```
/jutsu use Rasengan
```

## ⚙️ Configuração Avançada

### Arquivo de Configuração
Localização: `config/narutojutsu.cfg`

### Personalizando o Item de Ativação

Por padrão, o mod usa papel (`minecraft:paper`) como item de ativação. Você pode mudar isso:

**Exemplos:**
```properties
# Usar kunai do mod Naruto C (se instalado)
S:JutsuActivationItem=naruto:kunai

# Usar bússola
S:JutsuActivationItem=minecraft:compass

# Usar item com metadata específica
S:JutsuActivationItem=minecraft:dye:4
```

### Balanceamento de Dano

Ajuste o dano de cada jutsu:

```properties
damage {
    # Dano mais alto para servidor PvP
    S:RasenganDamage=25.0
    S:ChidoriDamage=30.0
    
    # Dano reduzido para servidor PvE
    S:FireballDamage=8.0
    S:ShadowCloneDamage=3.0
}
```

### Ajustando Cooldowns

Controle a frequência de uso:

```properties
cooldowns {
    # Cooldowns mais longos (valores em ticks)
    I:RasenganCooldown=200    # 10 segundos
    I:ChidoriCooldown=240     # 12 segundos
    
    # Cooldowns mais curtos
    I:FireballCooldown=40     # 2 segundos
}
```

### Otimização de Performance

Se o servidor estiver com lag devido às partículas:

```properties
particles {
    B:EnableParticles=true
    I:ParticleDensity=10      # Reduza para 10 (padrão é 20)
}
```

Ou desative completamente:
```properties
particles {
    B:EnableParticles=false
}
```

## 🎮 Integração com Custom NPC

### Criando um NPC Mestre de Jutsu

1. **Instale o Custom NPCs Mod**
2. **Crie um NPC**
3. **Configure o diálogo:**

**Opção 1: Dar Jutsu Diretamente**
```
Menu: "Gostaria de aprender um jutsu?"
  - Opção 1: "Sim, ensine-me o Rasengan!"
    Comando: /jutsu give Rasengan @p
```

**Opção 2: Sistema de Missões**
```
Menu: "Complete esta missão para aprender um jutsu!"
  - Ao completar: /jutsu give Chidori @p
```

**Opção 3: Sistema de Compra**
```
Menu: "Vendo pergaminhos de jutsu!"
  - Custo: 1000 moedas
  - Ao comprar: /jutsu give Fireball @p
```

### Script Avançado de NPC

Exemplo de NPC que ensina jutsus baseado no nível:

```
if (@p.level >= 10) {
    /jutsu give Rasengan @p
    /tellraw @p {"text":"Você aprendeu o Rasengan!","color":"gold"}
} else {
    /tellraw @p {"text":"Você precisa ser nível 10+","color":"red"}
}
```

## 🎯 Dicas de Uso

### Estratégias de Combate

1. **Rasengan**
   - Use em curta distância
   - Ótimo para knockback
   - Combine com Shadow Clone para distração

2. **Chidori**
   - Melhor em linha reta
   - Use para atravessar grupos
   - Alto dano em alvo único

3. **Fireball**
   - Ataques de longa distância
   - Área de efeito
   - Incendeia inimigos

4. **Shadow Clone**
   - Distrai inimigos
   - Dano em área
   - Bom para grupos pequenos

5. **Substitution**
   - Escapar de perigo
   - Reposicionar em combate
   - Confundir oponentes

### Combos Sugeridos

1. **Combo de Aproximação**
   - Shadow Clone → Rasengan
   - Distraia com clones, ataque com Rasengan

2. **Combo de Distância**
   - Fireball → Chidori
   - Enfraqueça à distância, finalize de perto

3. **Combo de Fuga**
   - Substitution → Shadow Clone
   - Escape e deixe clones como distração

## 🔧 Solução de Problemas

### O mod não aparece no jogo
- Verifique se o Forge 1.7.10 está instalado
- Confirme que o arquivo .jar está na pasta `mods`
- Verifique os logs em `.minecraft/logs/fml-client-latest.log`

### Comandos não funcionam
- Certifique-se de ter permissões de operador (`/op seu_nome`)
- Verifique se o nome do jutsu está correto (use `/jutsu list`)

### Partículas não aparecem
- Verifique as configurações de vídeo do Minecraft
- Certifique-se de que partículas não estão desativadas
- Verifique `EnableParticles=true` no config

### Cooldown não funciona
- Cooldowns são por jogador e resetam ao reiniciar o servidor
- Use comandos para resetar: reinicie o jogo

### Incompatibilidade com Dragon Block C
- Desative a integração: `EnableDBCIntegration=false`
- Ajuste o multiplicador de dano

## 📊 Valores Recomendados por Tipo de Servidor

### Servidor PvP Competitivo
```properties
damage {
    S:RasenganDamage=20.0
    S:ChidoriDamage=22.0
    S:FireballDamage=15.0
    S:ShadowCloneDamage=6.0
}
cooldowns {
    I:RasenganCooldown=140
    I:ChidoriCooldown=160
    I:FireballCooldown=80
    I:ShadowCloneCooldown=240
    I:SubstitutionCooldown=180
}
```

### Servidor PvE/Aventura
```properties
damage {
    S:RasenganDamage=25.0
    S:ChidoriDamage=30.0
    S:FireballDamage=20.0
    S:ShadowCloneDamage=8.0
}
cooldowns {
    I:RasenganCooldown=80
    I:ChidoriCooldown=100
    I:FireballCooldown=40
    I:ShadowCloneCooldown=160
    I:SubstitutionCooldown=120
}
```

### Servidor Roleplay
```properties
damage {
    S:RasenganDamage=15.0
    S:ChidoriDamage=18.0
    S:FireballDamage=12.0
    S:ShadowCloneDamage=5.0
}
cooldowns {
    I:RasenganCooldown=200
    I:ChidoriCooldown=240
    I:FireballCooldown=120
    I:ShadowCloneCooldown=300
    I:SubstitutionCooldown=200
}
particles {
    I:ParticleDensity=30    # Mais partículas para efeito visual
}
```

## 📞 Suporte

Para ajuda adicional ou reportar bugs:
- Abra uma issue no GitHub
- Entre em contato com o desenvolvedor

---

**Bom jogo! 🍥**
