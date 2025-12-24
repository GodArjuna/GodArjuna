# 🛠️ Guia de Compilação - Naruto Jutsu Mod

Este guia explica como compilar o Naruto Jutsu Mod do código fonte.

## 📋 Pré-requisitos

### Software Necessário

1. **Java Development Kit (JDK)**
   - Versão: JDK 7 ou JDK 8
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) ou [OpenJDK](https://adoptopenjdk.net/)
   
2. **Git**
   - Para clonar o repositório
   - Download: [Git](https://git-scm.com/downloads)

3. **Gradle** (Opcional)
   - O projeto inclui o Gradle Wrapper
   - Não é necessário instalar separadamente

### Verificar Instalação do Java

```bash
java -version
javac -version
```

Você deve ver algo como:
```
java version "1.8.0_xxx"
Java(TM) SE Runtime Environment (build 1.8.0_xxx)
```

## 🚀 Passos para Compilar

### 1. Clonar o Repositório

```bash
git clone https://github.com/GodArjuna/GodArjuna.git
cd GodArjuna/NarutoJutsuMod
```

### 2. Configurar o Workspace

#### No Linux/Mac:
```bash
chmod +x gradlew
./gradlew setupDecompWorkspace
```

#### No Windows:
```cmd
gradlew.bat setupDecompWorkspace
```

Este processo pode demorar vários minutos na primeira vez, pois:
- Baixa as dependências do Forge
- Descompila o Minecraft
- Configura o ambiente de desenvolvimento

### 3. Compilar o Mod

#### No Linux/Mac:
```bash
./gradlew build
```

#### No Windows:
```cmd
gradlew.bat build
```

### 4. Localizar o Arquivo Compilado

Após a compilação bem-sucedida, o arquivo `.jar` estará em:
```
NarutoJutsuMod/build/libs/NarutoJutsuMod-1.0.0.jar
```

## 🔧 Desenvolvimento

### Configurar IDE

#### IntelliJ IDEA:
```bash
./gradlew idea
```
Depois abra o projeto no IntelliJ.

#### Eclipse:
```bash
./gradlew eclipse
```
Depois importe o projeto no Eclipse.

### Executar o Mod em Desenvolvimento

#### Cliente (para testar):
```bash
./gradlew runClient
```

#### Servidor (para testar multiplayer):
```bash
./gradlew runServer
```

## 📦 Build de Produção

Para criar um build otimizado para distribuição:

```bash
# Limpar builds anteriores
./gradlew clean

# Build completo
./gradlew build

# O arquivo estará em: build/libs/
```

## 🐛 Resolução de Problemas

### Erro: "Could not find or load main class"

**Solução:**
```bash
./gradlew clean
./gradlew setupDecompWorkspace --refresh-dependencies
./gradlew build
```

### Erro: "JAVA_HOME is not set"

**Solução no Linux/Mac:**
```bash
export JAVA_HOME=/caminho/para/jdk
export PATH=$JAVA_HOME/bin:$PATH
```

**Solução no Windows:**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_xxx
set PATH=%JAVA_HOME%\bin;%PATH%
```

### Erro: "Gradle version too old"

**Solução:**
Use o wrapper incluído no projeto:
```bash
./gradlew wrapper --gradle-version 2.0
```

### Erro de Memória ao Compilar

**Solução:**
Edite `gradle.properties` e aumente a memória:
```properties
org.gradle.jvmargs=-Xmx4G
```

## 📝 Estrutura de Build

```
NarutoJutsuMod/
├── build/                  # Pasta de build (gerada)
│   ├── classes/           # Classes compiladas
│   ├── libs/              # JARs finais
│   └── tmp/               # Arquivos temporários
├── gradle/                # Wrapper do Gradle
├── src/                   # Código fonte
├── build.gradle           # Script de build
├── gradlew               # Script Gradle (Linux/Mac)
└── gradlew.bat           # Script Gradle (Windows)
```

## 🎯 Tarefas Gradle Úteis

### Listar todas as tarefas:
```bash
./gradlew tasks
```

### Limpar build:
```bash
./gradlew clean
```

### Compilar sem testes:
```bash
./gradlew build -x test
```

### Ver dependências:
```bash
./gradlew dependencies
```

### Build e executar cliente:
```bash
./gradlew build runClient
```

## 🔍 Verificar o Build

Após compilar, verifique se o arquivo foi criado:

```bash
# Linux/Mac
ls -lh build/libs/

# Windows
dir build\libs\
```

Você deve ver:
```
NarutoJutsuMod-1.0.0.jar
NarutoJutsuMod-1.0.0-sources.jar
NarutoJutsuMod-1.0.0-dev.jar
```

O arquivo principal para distribuição é `NarutoJutsuMod-1.0.0.jar`.

## 📤 Distribuir o Mod

### Teste Antes de Distribuir:

1. Copie o `.jar` para a pasta `mods` do Minecraft
2. Execute o Minecraft com Forge 1.7.10
3. Verifique se o mod carrega sem erros
4. Teste todos os comandos e jutsus
5. Verifique os logs: `.minecraft/logs/fml-client-latest.log`

### Checklist de Qualidade:

- [ ] Mod compila sem erros
- [ ] Mod carrega no Minecraft
- [ ] Todos os jutsus funcionam
- [ ] Comandos funcionam corretamente
- [ ] Partículas aparecem
- [ ] Configuração pode ser alterada
- [ ] Sem crashes ou erros graves

## 🌐 Build Automatizado (CI/CD)

### GitHub Actions (Exemplo)

Crie `.github/workflows/build.yml`:

```yaml
name: Build Mod

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 8
      uses: actions/setup-java@v2
      with:
        java-version: '8'
        distribution: 'adopt'
    
    - name: Grant execute permission
      run: chmod +x gradlew
      working-directory: NarutoJutsuMod
    
    - name: Build with Gradle
      run: ./gradlew build
      working-directory: NarutoJutsuMod
    
    - name: Upload artifact
      uses: actions/upload-artifact@v2
      with:
        name: NarutoJutsuMod
        path: NarutoJutsuMod/build/libs/*.jar
```

## 📚 Recursos Adicionais

- [Documentação Forge 1.7.10](https://mcforge.readthedocs.io/)
- [Gradle Docs](https://docs.gradle.org/)
- [Minecraft Modding Tutorial](https://www.minecraftforge.net/forum/topic/20135-/)

## 💡 Dicas

1. **Sempre limpe antes de builds importantes:**
   ```bash
   ./gradlew clean build
   ```

2. **Use o wrapper incluído:**
   - Garante a versão correta do Gradle
   - Não precisa instalar Gradle globalmente

3. **Aumente a memória se necessário:**
   - Edite `gradle.properties`
   - Adicione: `org.gradle.jvmargs=-Xmx4G`

4. **Mantenha o JDK 8:**
   - Minecraft 1.7.10 foi feito para Java 8
   - Versões mais novas podem causar problemas

## 🆘 Suporte

Se tiver problemas ao compilar:

1. Verifique os logs em `build/` 
2. Abra uma issue no GitHub
3. Inclua:
   - Versão do Java (`java -version`)
   - Sistema operacional
   - Mensagem de erro completa
   - Log do Gradle

---

**Boa sorte com o build! 🛠️**
