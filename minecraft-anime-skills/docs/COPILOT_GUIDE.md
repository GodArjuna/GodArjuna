# 🤖 Guia de Integração com GitHub Copilot

## Como usar GitHub Copilot para gerar Skills

Este guia explica como usar o GitHub Copilot para criar novas skills de anime para o sistema.

### 🎯 Pré-requisitos

- Visual Studio Code com extensão GitHub Copilot instalada
- Conhecimento básico de Skript
- Familiaridade com animes (para criar skills coerentes)

### 📝 Método 1: Comentários Descritivos

O Copilot funciona melhor com comentários descritivos em inglês ou português:

```skript
# Criar uma skill do Sasuke que usa Amaterasu
# Fogo negro que queima eternamente
# Custo: 80 mana, Cooldown: 45 segundos
# Causa dano contínuo por 10 segundos
command /amaterasu:
    # O Copilot irá sugerir o código aqui
```

### 🎨 Método 2: Templates com IA

Crie um template e deixe o Copilot preencher:

```skript
# [NOME DO ANIME] - [NOME DA SKILL]
# Tipo: [attack/defense/movement/buff]
# Descrição: [descrição detalhada]

command /[nome-da-skill]:
    permission: animeskills.[anime].[skill]
    cooldown: [X] seconds
    trigger:
        if {player_mana_%player%} >= [CUSTO]:
            # Efeito da skill - Copilot sugere aqui
```

### 🔥 Exemplos Práticos

#### Exemplo 1: Skill de Ataque (Attack on Titan)

```skript
# Attack on Titan - ODM Gear Slash
# Permite ao jogador fazer um ataque aéreo rápido com equipamento de manobra
# Tipo: movement + attack
command /odmslash:
    permission: animeskills.aot.odm-slash
    cooldown: 12 seconds
    trigger:
        if {player_mana_%player%} >= 45:
            # O Copilot irá sugerir mecânicas de movimento + ataque
            send "§7[ODM Gear] §fAtivando equipamento de manobra..."
            
            # Deixe o Copilot completar com:
            # - Movimento aéreo
            # - Ataque em círculo
            # - Partículas de lâminas
            # - Dano em área
```

#### Exemplo 2: Skill Defensiva (Demon Slayer)

```skript
# Demon Slayer - Water Breathing: Dead Calm
# Defesa absoluta que bloqueia ataques ao redor
# Tipo: defense
command /deadcalm:
    permission: animeskills.demon-slayer.dead-calm
    cooldown: 30 seconds
    trigger:
        if {player_mana_%player%} >= 60:
            # Copilot sugere mecânicas defensivas como:
            # - Criar barreira de água
            # - Bloquear projéteis
            # - Refletir ataques
            # - Partículas de água
```

### 💡 Dicas para Melhores Resultados

1. **Seja Específico nos Comentários**
   ```skript
   # BAD: criar ataque forte
   # GOOD: criar ataque que dispara 5 projéteis em leque, cada um causa 7 de dano
   ```

2. **Use Referências Contextuais**
   ```skript
   # Baseado no Rasengan, mas com elemento trovão em vez de vento
   # Similar ao Chidori mas com menor dano e menor cooldown
   ```

3. **Descreva Efeitos Visuais**
   ```skript
   # Partículas: chamas vermelhas girando em espiral
   # Sons: explosão seguida de som de fogo
   # Efeito: empurrar inimigos para trás com força 3
   ```

4. **Mencione Mecânicas Existentes**
   ```skript
   # Usar o sistema de combo existente
   # Consumir mana usando o sistema padrão
   # Adicionar XP com add_skill_xp()
   ```

### 🔧 Workflow Recomendado

1. **Planejamento**
   - Escolha o anime e a skill
   - Pesquise como a skill funciona no anime
   - Defina custos, cooldown e dano

2. **Estrutura Base**
   ```skript
   # [Anime] - [Skill Name]
   # Type: [type]
   # Mana: [X] | Cooldown: [Y]s | Damage: [Z]
   
   command /[skillname]:
       permission: animeskills.[anime].[skill]
       cooldown: [X] seconds
       trigger:
           if {player_mana_%player%} >= [COST]:
               # Fase 1: Carregamento
               # Fase 2: Execução
               # Fase 3: Efeitos
               # Fase 4: Consumo de recursos
   ```

3. **Deixar Copilot Completar**
   - Escreva comentários detalhados
   - Pressione Enter e espere sugestões
   - Use Tab para aceitar
   - Use Alt+] para ver próxima sugestão

4. **Refinamento**
   - Teste no servidor
   - Ajuste valores
   - Adicione efeitos visuais
   - Balance a skill

### 🎯 Prompts Efetivos para Copilot

#### Para Skills de Dano
```
# Skill que causa dano em área de 5 blocos
# Aplica efeito de queimadura por 5 segundos
# Partículas de fogo ao redor do alvo
# Som de explosão
```

#### Para Skills de Buff
```
# Skill que aplica 3 efeitos positivos
# Duração de 30 segundos
# Efeitos visuais de aura ao redor do jogador
# Mensagem de ativação épica
```

#### Para Skills de Movimento
```
# Skill de teleporte que move o jogador 10 blocos à frente
# Deixa rastro de partículas
# Som de "whoosh"
# Não atravessa blocos sólidos
```

### 🌟 Skills Sugeridas para Criar com Copilot

#### Bleach
- Getsuga Tenshou (ataque de onda)
- Bankai (transformação/buff)
- Sonido (movimento rápido)

#### Attack on Titan
- ODM Gear (movimento 3D)
- Thunder Spear (projétil explosivo)
- Hardening (defesa)

#### Demon Slayer
- Hinokami Kagura (combo de ataques)
- Water Breathing Forms (variações de ataque)
- Sun Breathing (ultimate)

#### JoJo's Bizarre Adventure
- Star Platinum (barreira de socos)
- Time Stop (controle de tempo)
- Stand Rush (ataque rápido)

### 🐛 Debugging com Copilot

Se uma skill não funcionar, pergunte ao Copilot:

```skript
# Esta skill não está funcionando corretamente
# Problema: [descrever o problema]
# Esperado: [comportamento esperado]
# Atual: [comportamento atual]

# Copilot irá sugerir correções
```

### 📊 Balanceamento com IA

Use Copilot para sugerir valores balanceados:

```python
# Baseado nas skills existentes, sugerir valores para:
# Skill Name: Getsuga Tenshou
# Tipo: Ranged Attack
# Similar a: Kamehameha (custo: 70, cooldown: 15s, dano: 4-15)

# O Copilot pode sugerir valores apropriados
```

### 🔄 Iteração e Melhoria

1. Crie a skill base com Copilot
2. Teste no servidor
3. Peça ao Copilot para melhorar:
   ```
   # Melhorar esta skill adicionando:
   # - Mais efeitos visuais
   # - Sistema de carga (hold shift)
   # - Variante mais forte se combo ativo
   ```

### 🎓 Exercícios Práticos

**Exercício 1:** Crie uma skill de Bleach
```skript
# Criar Getsuga Tenshou do Ichigo
# Deve disparar uma onda de energia azul
# Causa dano em linha reta
# Custo médio de mana
```

**Exercício 2:** Crie uma skill defensiva
```skript
# Criar Susanoo do Sasuke
# Cria uma armadura de chakra ao redor do jogador
# Reduz dano recebido em 70%
# Duração de 15 segundos
```

**Exercício 3:** Crie uma skill de movimento
```skript
# Criar Flying Thunder God do Minato
# Jogador pode marcar locais com /ftgmark
# Teleportar instantaneamente com /ftg
# Partículas amarelas no rastro
```

### 🎯 Conclusão

O GitHub Copilot é uma ferramenta poderosa para criar skills de anime. Quanto mais descritivo você for nos comentários, melhores serão as sugestões. Experimente, itere e divirta-se criando habilidades incríveis!

---

**💡 Dica Final:** Mantenha este arquivo aberto enquanto desenvolve para referência rápida dos padrões e técnicas.
