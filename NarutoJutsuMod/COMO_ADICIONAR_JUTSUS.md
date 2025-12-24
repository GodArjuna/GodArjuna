# 📘 Guia: Como Adicionar Novos Jutsus

Este guia explica passo-a-passo como adicionar novos jutsus ao Naruto Jutsu Mod.

---

## 📋 Visão Geral

Adicionar um novo jutsu envolve **4 passos principais**:

1. **Criar a classe do jutsu** (estende `BaseJutsu`)
2. **Adicionar configurações** ao `ConfigHandler`
3. **Registrar o jutsu** no `JutsuRegistry`
4. **Testar o jutsu** no jogo

---

## 🎯 PASSO 1: Criar a Classe do Jutsu

### 1.1 Criar o arquivo

Crie um novo arquivo em: `src/main/java/com/godarjuna/narutojutsu/jutsu/`

**Exemplo:** `ByakuganJutsu.java`

### 1.2 Estrutura básica

```java
package com.godarjuna.narutojutsu.jutsu;

import com.godarjuna.narutojutsu.config.ConfigHandler;
import com.godarjuna.narutojutsu.core.BaseJutsu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Byakugan - Olho Branco
 * 
 * [Descrição do que o jutsu faz]
 * 
 * Efeitos Visuais:
 * - [Liste as partículas usadas]
 * 
 * @author Seu Nome
 */
public class ByakuganJutsu extends BaseJutsu {
    
    /**
     * Construtor do Byakugan
     */
    public ByakuganJutsu() {
        super(
            "Byakugan",                          // Nome do jutsu
            "Descrição do jutsu",                // Descrição
            ConfigHandler.byakuganCooldown,      // Cooldown (referência config)
            15.0,                                // Custo de chakra
            ConfigHandler.byakuganDamage         // Dano (referência config)
        );
    }
    
    /**
     * Executa o Byakugan
     * 
     * Aqui você implementa o que acontece quando o jutsu é usado
     */
    @Override
    public boolean execute(World world, EntityPlayer player) {
        // Verificar se pode usar
        if (!canUse(player)) {
            return false;
        }
        
        // Executar apenas no servidor
        if (!world.isRemote) {
            
            // 1. Criar partículas (se habilitado)
            if (ConfigHandler.enableParticles) {
                createByakuganParticles(world, player);
            }
            
            // 2. Implementar a lógica do jutsu
            // [Seu código aqui]
            
        }
        
        return true;
    }
    
    /**
     * Método auxiliar para criar partículas
     */
    private void createByakuganParticles(World world, EntityPlayer player) {
        int particleCount = ConfigHandler.particleDensity;
        
        for (int i = 0; i < particleCount; i++) {
            // Calcular posições
            double x = player.posX + (world.rand.nextDouble() - 0.5) * 2.0;
            double y = player.posY + world.rand.nextDouble() * 2.0;
            double z = player.posZ + (world.rand.nextDouble() - 0.5) * 2.0;
            
            // Spawnar partícula
            world.spawnParticle("portal", x, y, z, 0, 0, 0);
        }
    }
}
```

---

## ⚙️ PASSO 2: Adicionar Configurações

### 2.1 Abra o ConfigHandler

Arquivo: `src/main/java/com/godarjuna/narutojutsu/config/ConfigHandler.java`

### 2.2 Adicione as variáveis

```java
// Na seção de DANO
/** Dano causado pelo Byakugan */
public static double byakuganDamage = 10.0;

// Na seção de COOLDOWN
/** Cooldown do Byakugan em ticks */
public static int byakuganCooldown = 80;
```

### 2.3 Adicione o carregamento no método `init()`

```java
// Na categoria "damage"
byakuganDamage = config.getFloat("ByakuganDamage", "damage", 
    10.0f, 0.0f, 100.0f, 
    "Dano causado pelo Byakugan");

// Na categoria "cooldowns"
byakuganCooldown = config.getInt("ByakuganCooldown", "cooldowns", 
    80, 0, 1000, 
    "Cooldown do Byakugan em ticks");
```

---

## 📝 PASSO 3: Registrar o Jutsu

### 3.1 Abra o JutsuRegistry

Arquivo: `src/main/java/com/godarjuna/narutojutsu/core/JutsuRegistry.java`

### 3.2 Adicione o import

```java
import com.godarjuna.narutojutsu.jutsu.ByakuganJutsu;
```

### 3.3 Registre no método `registerJutsus()`

```java
public static void registerJutsus() {
    // Jutsus existentes...
    registerJutsu(new RasenganJutsu());
    registerJutsu(new ChidoriJutsu());
    registerJutsu(new FireballJutsu());
    registerJutsu(new ShadowCloneJutsu());
    registerJutsu(new SubstitutionJutsu());
    
    // Seu novo jutsu
    registerJutsu(new ByakuganJutsu());
}
```

---

## 🧪 PASSO 4: Testar o Jutsu

### 4.1 Compile o mod

```bash
cd NarutoJutsuMod/
./gradlew build
```

### 4.2 Teste no Minecraft

1. Coloque o .jar na pasta `mods/`
2. Inicie o Minecraft com Forge 1.7.10
3. Use os comandos:

```
/jutsu list                    # Ver se o jutsu aparece
/jutsu give Byakugan <nome>   # Dar o jutsu
/jutsu use Byakugan           # Usar o jutsu
```

---

## 💡 Exemplos de Implementações

### Exemplo 1: Jutsu de Dano Simples

```java
@Override
public boolean execute(World world, EntityPlayer player) {
    if (!canUse(player)) return false;
    
    if (!world.isRemote) {
        // Partículas
        if (ConfigHandler.enableParticles) {
            spawnParticles(world, player, "flame", 20);
        }
        
        // Dano em área
        Vec3 lookVec = player.getLookVec();
        double reach = 5.0;
        
        double targetX = player.posX + lookVec.xCoord * reach;
        double targetY = player.posY + player.getEyeHeight();
        double targetZ = player.posZ + lookVec.zCoord * reach;
        
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
            targetX - 2, targetY - 2, targetZ - 2,
            targetX + 2, targetY + 2, targetZ + 2
        );
        
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
        
        for (Entity entity : entities) {
            if (entity != player) {
                double finalDamage = getDamage();
                if (ConfigHandler.enableDBCIntegration) {
                    finalDamage *= ConfigHandler.dbcDamageMultiplier;
                }
                entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)finalDamage);
            }
        }
    }
    
    return true;
}
```

### Exemplo 2: Jutsu de Buff/Efeito

```java
@Override
public boolean execute(World world, EntityPlayer player) {
    if (!canUse(player)) return false;
    
    if (!world.isRemote) {
        // Partículas
        if (ConfigHandler.enableParticles) {
            for (int i = 0; i < 30; i++) {
                double angle = (i / 30.0) * Math.PI * 2;
                double x = player.posX + Math.cos(angle) * 1.5;
                double y = player.posY + 1.0;
                double z = player.posZ + Math.sin(angle) * 1.5;
                world.spawnParticle("enchantmenttable", x, y, z, 0, 0.1, 0);
            }
        }
        
        // Aplicar efeito de poção
        player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 400, 0));
        player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 400, 1));
    }
    
    return true;
}
```

### Exemplo 3: Jutsu de Teleporte

```java
@Override
public boolean execute(World world, EntityPlayer player) {
    if (!canUse(player)) return false;
    
    if (!world.isRemote) {
        // Posição original
        double originalX = player.posX;
        double originalY = player.posY;
        double originalZ = player.posZ;
        
        // Calcular nova posição
        Vec3 lookVec = player.getLookVec();
        double distance = 10.0;
        
        double newX = player.posX + lookVec.xCoord * distance;
        double newY = player.posY;
        double newZ = player.posZ + lookVec.zCoord * distance;
        
        // Ajustar Y para não ficar dentro de blocos
        while (newY > 0 && !world.isAirBlock((int)newX, (int)newY - 1, (int)newZ)) {
            newY += 1.0;
        }
        
        // Teleportar
        player.setPosition(newX, newY, newZ);
        
        // Partículas em ambas posições
        if (ConfigHandler.enableParticles) {
            createTeleportParticles(world, originalX, originalY, originalZ);
            createTeleportParticles(world, newX, newY, newZ);
        }
    }
    
    return true;
}
```

---

## 🎨 Tipos de Partículas Disponíveis

Use estas strings no método `world.spawnParticle()`:

### Partículas de Fogo/Energia
- `"flame"` - Chama
- `"lava"` - Lava
- `"fireworksSpark"` - Faísca de fogos

### Partículas Mágicas
- `"spell"` - Feitiço (branco)
- `"instantSpell"` - Feitiço instantâneo
- `"magicCrit"` - Crítico mágico (azul)
- `"enchantmenttable"` - Mesa de encantamento

### Partículas de Combate
- `"crit"` - Crítico
- `"angryVillager"` - Aldeão bravo
- `"heart"` - Coração

### Partículas de Fumaça
- `"smoke"` - Fumaça
- `"largesmoke"` - Fumaça grande
- `"townaura"` - Aura de cidade
- `"explode"` - Explosão

### Partículas Especiais
- `"portal"` - Portal (roxo)
- `"reddust"` - Pó vermelho
- `"snowballpoof"` - Bola de neve
- `"slime"` - Slime
- `"splash"` - Respingo

### Partículas de Blocos
- `"blockcrack_X_Y"` - Quebra de bloco (X=ID, Y=metadata)
- `"blockdust_X_Y"` - Pó de bloco

---

## 📊 Parâmetros de Partículas

```java
world.spawnParticle(
    String particleType,  // Tipo de partícula
    double x,             // Posição X
    double y,             // Posição Y
    double z,             // Posição Z
    double velX,          // Velocidade X
    double velY,          // Velocidade Y
    double velZ           // Velocidade Z
);
```

**Dica:** Use velocidades pequenas (0.0 a 0.5) para movimentos suaves.

---

## 🔧 Dicas e Boas Práticas

### 1. Sempre verifique `!world.isRemote`
```java
if (!world.isRemote) {
    // Seu código aqui
}
```
Isso garante que o código só executa no servidor, evitando duplicação.

### 2. Use ConfigHandler para valores ajustáveis
```java
// BOM ✓
ConfigHandler.meuJutsuDamage

// RUIM ✗
15.0  // Valor fixo no código
```

### 3. Crie métodos auxiliares para partículas
```java
private void createMyParticles(World world, EntityPlayer player) {
    // Código de partículas aqui
}
```

### 4. Comente seu código em português
```java
// Criar espiral de partículas
for (int i = 0; i < count; i++) {
    // Calcular ângulo
    double angle = (i / (double)count) * Math.PI * 2;
    // ...
}
```

### 5. Teste com diferentes densidades de partículas
Ajuste `particleDensity` no config para ver o melhor valor.

---

## 🐛 Problemas Comuns

### Jutsu não aparece na lista
- ✓ Verificar se registrou no `JutsuRegistry`
- ✓ Verificar se o import está correto
- ✓ Recompilar o mod

### Partículas não aparecem
- ✓ Verificar `ConfigHandler.enableParticles`
- ✓ Verificar se está usando `!world.isRemote`
- ✓ Verificar se as partículas estão ativadas no Minecraft

### Dano não funciona
- ✓ Verificar se a entidade não é o próprio jogador
- ✓ Verificar o `AxisAlignedBB` (área de efeito)
- ✓ Verificar se o dano está configurado no ConfigHandler

### Cooldown não funciona
O sistema de cooldown já está implementado nos handlers. Certifique-se de:
- ✓ Usar `/jutsu use` ou ativar via item
- ✓ O cooldown está configurado corretamente

---

## 📚 Recursos Adicionais

### Arquivos de Referência
- `RasenganJutsu.java` - Jutsu de dano em área
- `ChidoriJutsu.java` - Jutsu de dano em linha
- `FireballJutsu.java` - Jutsu com projétil
- `ShadowCloneJutsu.java` - Jutsu com múltiplos ataques
- `SubstitutionJutsu.java` - Jutsu de teleporte

### Classes Importantes
- `BaseJutsu` - Classe base para herdar
- `IJutsu` - Interface com métodos obrigatórios
- `ConfigHandler` - Gerenciador de configurações
- `JutsuRegistry` - Registro de jutsus

---

## ✅ Checklist de Novo Jutsu

Antes de considerar seu jutsu completo, verifique:

- [ ] Classe criada em `jutsu/`
- [ ] Estende `BaseJutsu`
- [ ] Construtor chama `super()` com valores corretos
- [ ] Método `execute()` implementado
- [ ] Partículas funcionando
- [ ] Configurações adicionadas ao `ConfigHandler`
- [ ] Carregamento de config no método `init()`
- [ ] Registrado no `JutsuRegistry`
- [ ] Compilação sem erros
- [ ] Testado no jogo
- [ ] Código comentado em português
- [ ] Documentação JavaDoc adicionada

---

## 🎓 Exemplos Práticos de Novos Jutsus

### 1. Amaterasu (Fogo Negro)
```java
public class AmaterasuJutsu extends BaseJutsu {
    public AmaterasuJutsu() {
        super("Amaterasu", "Fogo negro que nunca se apaga", 300, 50.0, 25.0);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        // Partículas pretas (smoke + fireworks)
        // Incendiar inimigos por longo tempo
        // Alto dano contínuo
    }
}
```

### 2. Eight Gates (Portões Internos)
```java
public class EightGatesJutsu extends BaseJutsu {
    public EightGatesJutsu() {
        super("EightGates", "Aumenta força e velocidade drasticamente", 600, 100.0, 0.0);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        // Partículas vermelhas (reddust)
        // Aplicar Speed III e Strength III
        // Causar dano ao jogador após efeito (custo)
    }
}
```

### 3. Water Dragon (Dragão de Água)
```java
public class WaterDragonJutsu extends BaseJutsu {
    public WaterDragonJutsu() {
        super("WaterDragon", "Dragão feito de água que ataca inimigos", 200, 40.0, 20.0);
    }
    
    @Override
    public boolean execute(World world, EntityPlayer player) {
        // Partículas splash + portal
        // Projétil que causa dano e knockback
        // Aplicar slowness nos inimigos
    }
}
```

---

**Pronto! Agora você sabe como adicionar novos jutsus ao mod! 🍥**

**Dúvidas?** Consulte os exemplos existentes ou a documentação no README.md
