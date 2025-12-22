# 🎮 Minecraft Anime Skills System
## Sistema de Criação de Skills de Minecraft 1.7.10 Baseadas em Animes

[![Minecraft](https://img.shields.io/badge/Minecraft-1.7.10-green)](https://minecraft.net)
[![Skript](https://img.shields.io/badge/Skript-2.2+-blue)](https://github.com/SkriptLang/Skript)
[![AI Powered](https://img.shields.io/badge/AI-GitHub%20Copilot-purple)](https://github.com/features/copilot)

### 📋 Descrição

Sistema completo de skills baseadas em animes para Minecraft 1.7.10, desenvolvido com Skript e integração de IA (GitHub Copilot). O sistema permite criar e gerenciar habilidades inspiradas em animes populares como Naruto, One Piece, Dragon Ball, My Hero Academia e outros.

### ✨ Características

- 🤖 **Integração com IA** - Geração automática de skills usando GitHub Copilot
- ⚡ **Sistema de Mana** - Gerenciamento de recursos para uso de habilidades
- 🎯 **Sistema de Combo** - Encadeie habilidades para bônus de dano
- 📈 **Sistema de Níveis** - Ganhe XP e suba de nível para desbloquear mais poder
- 🎨 **Efeitos Visuais** - Partículas e sons customizados para cada habilidade
- 🔧 **Totalmente Configurável** - Ajuste cooldowns, danos, custos de mana, etc.
- 🌐 **Suporte PT-BR** - Interface completa em português brasileiro

### 📦 Requisitos

- Minecraft 1.7.10
- Spigot/Bukkit 1.7.10
- Skript 2.2 ou superior
- Python 3.8+ (para gerador de skills IA)

### 🚀 Instalação

1. **Instale o Skript no seu servidor:**
   ```bash
   # Baixe Skript 2.2 para Minecraft 1.7.10
   # Coloque o arquivo .jar na pasta plugins/
   ```

2. **Copie os arquivos do sistema:**
   ```bash
   # Copie os arquivos .sk para plugins/Skript/scripts/
   cp minecraft-anime-skills/scripts/*.sk /path/to/server/plugins/Skript/scripts/
   cp minecraft-anime-skills/skills/*.sk /path/to/server/plugins/Skript/scripts/
   ```

3. **Configure o sistema:**
   ```bash
   # Edite o arquivo de configuração conforme necessário
   nano minecraft-anime-skills/config/skills-config.yml
   ```

4. **Recarregue o Skript:**
   ```
   /skript reload all
   ```

### 🎯 Skills Disponíveis

#### 🔴 Naruto
- **Rasengan** (`/rasengan`) - Esfera de chakra rotativo
  - Custo: 40 mana | Cooldown: 8s | Dano: 12 ❤️
  
- **Chidori** (`/chidori`) - Relâmpago cortante
  - Custo: 50 mana | Cooldown: 10s | Dano: 15 ❤️
  
- **Shadow Clone** (`/shadowclone`) - Clones de sombra
  - Custo: 60 mana | Cooldown: 30s | Buff: 20s

#### 🔵 One Piece
- **Gomu Gomu no Pistol** (`/gomupistol`) - Soco elástico
  - Custo: 25 mana | Cooldown: 5s | Dano: 8 ❤️

#### 🟡 Dragon Ball
- **Kamehameha** (`/kamehameha`) - Onda de energia
  - Custo: 70 mana | Cooldown: 15s | Dano: 4-15 ❤️ (beam)

#### 🟢 My Hero Academia
- **One For All** (`/oneforall`) - Poder supremo
  - Custo: 80 mana | Cooldown: 60s | Buff: 30s

### 🎮 Como Usar

1. **Verificar suas skills:**
   ```
   /skills
   ```

2. **Verificar mana:**
   ```
   /mana
   ```

3. **Verificar nível:**
   ```
   /skilllevel
   ```

4. **Usar uma habilidade:**
   ```
   /rasengan
   /kamehameha
   /oneforall
   ```

5. **Ajuda:**
   ```
   /animeskills-help
   ```

### 🤖 Gerador de Skills com IA

O sistema inclui um gerador de skills Python que usa IA para criar novas habilidades:

```bash
# Navegar até o diretório
cd minecraft-anime-skills/ai-integration/

# Executar o gerador
python3 skill_generator.py
```

#### Gerando Skills Customizadas

```python
from skill_generator import AnimeSkillGenerator

generator = AnimeSkillGenerator()

# Gerar skill específica
skill = generator.generate_skill_template(
    anime="naruto",
    skill_name="Sexy Jutsu",
    skill_type="buff"
)

# Salvar skill
generator.save_skill(skill, "naruto_sexy_jutsu.sk")

# Gerar em lote
skills = [
    {"anime": "bleach", "name": "Getsuga Tenshou", "type": "attack"},
    {"anime": "demon-slayer", "name": "Water Breathing", "type": "attack"},
]
generator.batch_generate(skills)
```

### 🎨 Sistema de Combos

Encadeie skills em sequência (dentro de 5 segundos) para ativar combos:

```
Rasengan → Chidori = +50% dano
Gomu Gomu Pistol → Gomu Gomu Pistol = Gatling Mode
```

### 📊 Sistema de Progressão

- **Ganhe XP** usando skills
- **Suba de nível** (100 XP = 1 nível)
- **Aumente mana máxima** (+10 por nível)
- **Desbloqueie skills avançadas** (baseado em nível)

### 🛠️ Configuração Avançada

Edite `config/skills-config.yml` para customizar:

```yaml
skill-settings:
  defaults:
    cooldown: 10        # segundos
    mana-cost: 50       # pontos
    damage: 10.0        # corações
    range: 20           # blocos
```

### 🔐 Permissões

```
animeskills.admin           # Acesso aos comandos administrativos
animeskills.naruto.*        # Todas as skills de Naruto
animeskills.naruto.rasengan # Rasengan específico
animeskills.one-piece.*     # Todas as skills de One Piece
animeskills.dragon-ball.*   # Todas as skills de Dragon Ball
animeskills.my-hero-academia.* # Todas as skills de MHA
```

### 💡 Dicas de Uso

1. **Gerencie sua mana** - Mana regenera 5 pontos a cada 5 segundos
2. **Use combos** - Combine skills para dano extra
3. **Suba de nível** - Mais nível = mais mana
4. **Experimente** - Cada skill tem mecânicas únicas

### 🤝 Contribuindo

Para adicionar novas skills:

1. Use o gerador de skills AI
2. Ou crie manualmente seguindo o template
3. Teste no servidor
4. Envie um pull request

### 📝 Estrutura de Arquivos

```
minecraft-anime-skills/
├── scripts/
│   └── anime-skills-core.sk       # Sistema principal
├── skills/
│   ├── naruto_rasengan.sk         # Skills individuais
│   ├── naruto_chidori.sk
│   ├── dragonball_kamehameha.sk
│   └── ...
├── ai-integration/
│   └── skill_generator.py         # Gerador AI
├── config/
│   └── skills-config.yml          # Configurações
└── docs/
    └── README.md                   # Esta documentação
```

### 🐛 Troubleshooting

**Skill não funciona:**
- Verifique se tem a permissão correta
- Confirme que tem mana suficiente
- Verifique se o cooldown já passou

**Mana não regenera:**
- Reinicie o script core: `/skript reload anime-skills-core.sk`

**Efeitos visuais não aparecem:**
- Verifique a versão do Spigot/Bukkit
- Algumas partículas podem não estar disponíveis em 1.7.10

### 🎯 Roadmap Futuro

- [ ] Mais animes (Bleach, Attack on Titan, Demon Slayer)
- [ ] Sistema de transformações
- [ ] Skills combinadas (2+ jogadores)
- [ ] Modo PvP balanceado
- [ ] Interface gráfica (inventário)
- [ ] Integração com economia
- [ ] Sistema de clãs/equipes

### 📄 Licença

Este projeto é open source e está disponível sob a licença MIT.

### 👤 Autor

Desenvolvido por **Kawan Villar** (@GodArjuna)
- 🌐 [LinkedIn](https://www.linkedin.com/in/kawan-villar-6306b7285/)
- 🤖 Powered by GitHub Copilot

### 🙏 Agradecimentos

- Comunidade Skript
- GitHub Copilot
- Fãs de anime que inspiraram as skills
- Minecraft modding community

---

**⚡ Desenvolvido com GitHub Copilot e ❤️ para a comunidade de Minecraft**
