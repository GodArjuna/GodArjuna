# Naruto Jutsu Mod para Minecraft 1.7.10

## 📖 Sobre o Projeto

Este repositório contém o **Naruto Jutsu Mod**, um mod completo para Minecraft 1.7.10 que adiciona o sistema de Jutsus do anime Naruto ao jogo.

### ✨ Características Principais

- **5 Jutsus Implementados**: Rasengan, Chidori, Fireball, Shadow Clone e Substitution
- **Efeitos de Partículas**: Todos os jutsus usam apenas partículas nativas do Minecraft (sem modelos 3D)
- **Sistema de Comandos**: Comandos personalizados para dar e usar jutsus
- **Configuração Flexível**: Arquivo de config para ajustar danos, cooldowns e partículas
- **Integração Dragon Block C**: Suporte para integração com o mod Dragon Block C
- **Custom NPC Compatibility**: Funciona perfeitamente com Custom NPCs para criar mestres ninja

## 📂 Estrutura do Repositório

```
GodArjuna/
├── ContaTerminal.java           # Projeto anterior (terminal bancário)
├── NarutoJutsuMod/              # MOD PRINCIPAL DO MINECRAFT
│   ├── src/main/java/           # Código fonte do mod
│   │   └── com/godarjuna/narutojutsu/
│   │       ├── NarutoJutsuMod.java      # Classe principal
│   │       ├── core/                     # Sistema base de jutsus
│   │       ├── jutsu/                    # Implementações dos jutsus
│   │       ├── commands/                 # Comandos do mod
│   │       ├── handlers/                 # Event handlers
│   │       ├── config/                   # Configurações
│   │       ├── integration/              # Integração DBC
│   │       └── proxy/                    # Cliente/Servidor proxy
│   ├── src/main/resources/      # Recursos do mod
│   ├── build.gradle             # Script de build
│   ├── README.md               # Documentação completa do mod
│   └── INSTALLATION.md         # Guia de instalação
└── README.md                    # Este arquivo
```

## 🚀 Como Usar o Mod

### Para Desenvolvedores

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/GodArjuna/GodArjuna.git
   cd GodArjuna/NarutoJutsuMod
   ```

2. **Configure o ambiente de desenvolvimento**:
   ```bash
   ./gradlew setupDecompWorkspace
   ./gradlew idea  # ou ./gradlew eclipse
   ```

3. **Compile o mod**:
   ```bash
   ./gradlew build
   ```

4. **O arquivo .jar estará em**: `build/libs/NarutoJutsuMod-1.0.0.jar`

### Para Jogadores

Consulte o arquivo [NarutoJutsuMod/INSTALLATION.md](NarutoJutsuMod/INSTALLATION.md) para instruções detalhadas de instalação e uso.

## 🎮 Jutsus Disponíveis

| Jutsu | Descrição | Dano Padrão | Cooldown |
|-------|-----------|-------------|----------|
| **Rasengan** | Esfera rotativa de chakra | 15.0 | 5s |
| **Chidori** | Técnica de raio concentrada | 18.0 | 6s |
| **Fireball** | Bola de fogo incendiária | 12.0 | 3s |
| **Shadow Clone** | Clones que atacam inimigos | 5.0 | 10s |
| **Substitution** | Teleporte com fumaça | 0.0 | 7.5s |

## 💻 Requisitos

### Para Compilar
- Java JDK 7 ou 8
- Gradle (incluído via wrapper)
- Minecraft Forge 1.7.10

### Para Jogar
- Minecraft 1.7.10
- Minecraft Forge 1.7.10-10.13.4.1614 ou superior
- (Opcional) Dragon Block C para integração
- (Opcional) Custom NPCs para NPCs mestres

## 📚 Documentação Completa

Para documentação detalhada sobre o mod, incluindo:
- Todos os comandos disponíveis
- Guia de configuração
- Integração com outros mods
- Exemplos de uso
- Dicas e estratégias

Acesse: [NarutoJutsuMod/README.md](NarutoJutsuMod/README.md)

## 🔧 Configuração

O mod é altamente configurável! Você pode ajustar:
- Dano de cada jutsu
- Cooldowns
- Densidade de partículas
- Item de ativação
- Integração com Dragon Block C

Arquivo de config: `config/narutojutsu.cfg`

## 🎯 Comandos Principais

```bash
# Dar um jutsu a um jogador
/jutsu give <jutsu> <jogador>

# Listar todos os jutsus
/jutsu list

# Usar um jutsu
/jutsu use <jutsu>
```

## 🤝 Contribuindo

Contribuições são bem-vindas! Se você tem ideias para novos jutsus ou melhorias:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovoJutsu`)
3. Commit suas mudanças (`git commit -m 'Adiciona novo jutsu'`)
4. Push para a branch (`git push origin feature/NovoJutsu`)
5. Abra um Pull Request

## 💡 Ideias Futuras

- [ ] Mais jutsus (Byakugan, Sharingan, Rasen Shuriken, etc.)
- [ ] Sistema de níveis para jutsus
- [ ] Sistema de clãs (Uchiha, Hyuga, Uzumaki)
- [ ] Chakra Nature System (Fogo, Água, Raio, Terra, Vento)
- [ ] Kekkei Genkai (Wood Style, Ice Style, etc.)
- [ ] Sistema de hand signs
- [ ] Summons (invocações)

## 📝 Licença

Este projeto é fornecido como está, para uso pessoal e em servidores. Sinta-se livre para modificar conforme necessário.

## 👨‍💻 Autor

**GodArjuna** (Kawan Villar)
- GitHub: [@GodArjuna](https://github.com/GodArjuna)
- LinkedIn: [Kawan Villar](https://www.linkedin.com/in/kawan-villar-6306b7285/)

## 🌟 Agradecimentos

- Comunidade Minecraft Forge
- Masashi Kishimoto (criador de Naruto)
- Todos os jogadores e testadores

---

**Dattebayo! 🍥**
