# 🪟 Guia Completo Windows - n8n Self-Hosted + VSCode

## 🎯 Instalação Rápida (Windows)

### Pré-requisitos

✅ **Docker Desktop for Windows** instalado
✅ **VSCode** instalado (opcional, mas recomendado)
✅ **PowerShell** (já vem no Windows)

---

## 📦 Passo 1: Baixar o Projeto

### Opção A: Com Git (Recomendado)

1. **Instale o Git para Windows:**
   - Baixe de: https://git-scm.com/download/win
   - Execute o instalador

2. **Clone o repositório:**
   ```powershell
   cd C:\Users\SeuNome\Documents
   git clone https://github.com/GodArjuna/GodArjuna.git
   cd GodArjuna\n8n-automacao-pudins
   ```

### Opção B: Download Direto (Mais Simples)

1. **Baixe o ZIP:**
   - Vá para: https://github.com/GodArjuna/GodArjuna
   - Clique em "Code" → "Download ZIP"
   
2. **Extraia:**
   - Clique com botão direito no ZIP
   - "Extrair Tudo..."
   - Escolha `C:\Users\SeuNome\Documents\`

---

## 💻 Passo 2: Abrir no VSCode

### 2.1. Abrir a Pasta

**Método 1 - Pelo VSCode:**
```
1. Abra o VSCode
2. File → Open Folder (Ctrl+K Ctrl+O)
3. Navegue até: C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins
4. Clique em "Selecionar Pasta"
```

**Método 2 - Pelo Explorer:**
```
1. Vá para a pasta: C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins
2. Clique com botão direito em qualquer espaço vazio
3. Selecione "Open with Code" (se aparecer)
   OU
   Abra VSCode e arraste a pasta para dentro
```

**Método 3 - Pelo Terminal:**
```powershell
cd C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins
code .
```

### 2.2. Estrutura que Você Verá no VSCode

```
n8n-automacao-pudins/
├── 📄 WINDOWS-SETUP.md (este arquivo)
├── 📄 README.md
├── 📁 docker/
│   ├── 📄 docker-compose.yml
│   ├── 📄 .env.example
│   └── 📁 scripts/
│       └── 📄 setup.ps1 ⭐ EXECUTE ESTE
├── 📁 docs/
├── 📁 workflows/
└── 📁 examples/
```

---

## 🐳 Passo 3: Configurar e Iniciar n8n

### 3.1. Abrir Terminal no VSCode

```
1. No VSCode, pressione: Ctrl + `  (acento grave)
   OU
   Menu: Terminal → New Terminal

2. Certifique-se que está em PowerShell (não CMD)
   Se não estiver, clique na setinha para baixo ao lado do "+" e escolha "PowerShell"
```

### 3.2. Navegar para a Pasta Docker

```powershell
cd docker
```

### 3.3. Executar o Instalador Automático

```powershell
.\scripts\setup.ps1
```

### 3.4. Responder às Perguntas

O script vai perguntar:

**1. Usuário n8n:**
```
Digite o usuário admin para n8n (padrão: admin): admin
```

**2. Senha:**
```
Digite uma senha forte para n8n (mínimo 8 caracteres): Pudim2024!
Confirme a senha: Pudim2024!
```

**3. IP Local:**
```
Usar IP local (192.168.1.100) para webhooks? (S/n): S
```

**4. Confirmar:**
```
Confirmar e iniciar instalação? (S/n): S
```

### 3.5. Aguardar Instalação

O script vai:
- ✅ Criar arquivo de configuração (.env)
- ✅ Baixar imagem do n8n (pode demorar 5-10 minutos)
- ✅ Iniciar containers Docker
- ✅ Verificar se está funcionando

**Aguarde até ver:**
```
============================================
  INSTALAÇÃO CONCLUÍDA! 🎉
============================================
```

---

## 🌐 Passo 4: Acessar n8n

### 4.1. Abrir no Navegador

Abra seu navegador (Chrome, Edge, Firefox) e vá para:

```
http://localhost:5678
```

### 4.2. Fazer Login

Use as credenciais que você configurou:
- **Usuário:** admin (ou o que você escolheu)
- **Senha:** Pudim2024! (ou a que você escolheu)

### 4.3. Primeira Tela

Você verá a interface do n8n! 🎉

---

## ⚙️ Configurações no VSCode (Opcional mas Útil)

### Extensões Recomendadas

No VSCode, instale estas extensões (opcional):

1. **Docker** (by Microsoft)
   - Gerenciar containers pelo VSCode
   - Ctrl+Shift+X → busque "Docker" → Install

2. **Markdown All in One**
   - Ler documentação melhor
   - Ctrl+Shift+X → busque "Markdown All in One" → Install

3. **PowerShell** (by Microsoft)
   - Melhor suporte para .ps1
   - Ctrl+Shift+X → busque "PowerShell" → Install

### Workspace Settings (VSCode)

Crie um arquivo `.vscode/settings.json` na pasta do projeto:

**No VSCode:**
1. Pressione `Ctrl+Shift+P`
2. Digite: "Preferences: Open Workspace Settings (JSON)"
3. Cole isso:

```json
{
  "files.exclude": {
    "**/.git": true,
    "**/node_modules": true,
    "docker/data": true,
    "docker/backups/*.tar.gz": true,
    "docker/logs/*.log": true
  },
  "files.associations": {
    "*.yml": "yaml",
    "*.yaml": "yaml",
    ".env*": "properties"
  },
  "editor.formatOnSave": true,
  "terminal.integrated.defaultProfile.windows": "PowerShell",
  "[markdown]": {
    "editor.wordWrap": "on"
  }
}
```

---

## 🔧 Comandos Úteis no VSCode Terminal

### Ver Status do n8n

```powershell
# Ir para pasta docker
cd docker

# Ver se está rodando
docker-compose ps

# Ver logs
docker-compose logs -f
# (Pressione Ctrl+C para sair)
```

### Parar e Iniciar

```powershell
# Parar n8n
docker-compose down

# Iniciar n8n
docker-compose up -d

# Reiniciar
docker-compose restart
```

### Fazer Backup

```powershell
# Backup manual
.\scripts\backup.ps1
```

### Verificar Saúde

```powershell
# No VSCode terminal (PowerShell):
Invoke-WebRequest http://localhost:5678/healthz

# Deve retornar: StatusCode: 200
```

---

## 📝 Editando Arquivos no VSCode

### Arquivo .env (Suas Configurações)

1. **Abrir:**
   - No VSCode, vá para: `docker/.env`
   
2. **Editar:**
   ```env
   N8N_USER=admin
   N8N_PASSWORD=SuaNovaSenha123!
   WEBHOOK_URL=http://192.168.1.100:5678/
   ```

3. **Salvar:**
   - `Ctrl+S`

4. **Aplicar mudanças:**
   ```powershell
   docker-compose down
   docker-compose up -d
   ```

### Workflows (Criando no VSCode)

Você pode editar os workflows em:
- `workflows/README-vendas.md`
- `workflows/README-estoque.md`
- `workflows/README-precos.md`
- `workflows/README-marketing.md`

**Dica:** Use `Ctrl+P` para buscar arquivos rapidamente no VSCode!

---

## 🎯 Fluxo de Trabalho Diário

### 1. Abrir Projeto

```powershell
# Abrir VSCode na pasta
cd C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins
code .
```

### 2. Verificar n8n

```powershell
# No terminal do VSCode
cd docker
docker-compose ps
```

### 3. Ver Logs (se necessário)

```powershell
docker-compose logs -f n8n
```

### 4. Acessar Interface

Abra navegador: http://localhost:5678

### 5. Trabalhar nos Workflows

- Criar/editar workflows na interface n8n
- Documentar mudanças nos arquivos .md do VSCode

---

## 🆘 Problemas Comuns

### "Docker não está rodando"

**Solução:**
1. Abra o **Docker Desktop**
2. Aguarde iniciar (ícone baleia na bandeja do sistema)
3. Tente novamente

### "Não consigo executar scripts .ps1"

**Solução:**
```powershell
# Execute como Administrador (uma vez apenas):
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### "Porta 5678 já em uso"

**Solução:**
1. Abra `docker/.env` no VSCode
2. Mude para:
   ```env
   N8N_PORT=5679
   ```
3. Reinicie:
   ```powershell
   docker-compose down
   docker-compose up -d
   ```
4. Acesse: http://localhost:5679

### "VSCode não abre terminal PowerShell"

**Solução:**
1. `Ctrl+Shift+P`
2. Digite: "Terminal: Select Default Profile"
3. Escolha "PowerShell"
4. Feche e abra o terminal (`Ctrl+\``)

---

## 🚀 Próximos Passos

### 1. Configurar Integrações

Siga os guias em `docs/` para configurar:
- Google Sheets
- Google Forms
- Telegram
- WhatsApp
- Mercado Pago

### 2. Importar Workflows Prontos

Veja workflows em `workflows/`:
- Vendas
- Estoque
- Preços
- Marketing

### 3. Personalizar

Adapte os workflows para seu negócio específico.

---

## 💡 Dicas do VSCode

### Atalhos Úteis

```
Ctrl+P         → Buscar arquivos
Ctrl+Shift+F   → Buscar em todos arquivos
Ctrl+`         → Abrir/fechar terminal
Ctrl+B         → Mostrar/ocultar sidebar
Ctrl+Shift+E   → Explorador de arquivos
Ctrl+Shift+G   → Git
F11            → Tela cheia
```

### Buscar Texto nos Arquivos

1. `Ctrl+Shift+F`
2. Digite o que procura (ex: "webhook", "senha", "email")
3. Veja resultados em todos arquivos

### Terminal Dividido

1. No terminal, clique no ícone **"Split Terminal"** (⊞)
2. Rode comandos em duas janelas paralelas

---

## 📂 Organização no VSCode

### Abas Recomendadas

Mantenha abertas:
1. `WINDOWS-SETUP.md` (este guia)
2. `docker/.env` (configurações)
3. `docker/docker-compose.yml` (estrutura)
4. Workflow que está trabalhando

### Favoritos

Clique com botão direito em arquivos importantes → "Add to Favorites"

---

## 🔐 Segurança

### ⚠️ Arquivos que NÃO devem ser compartilhados:

- `docker/.env` (tem sua senha!)
- `docker/data/` (dados sensíveis)
- `docker/backups/` (backups privados)

**Esses já estão no .gitignore**, mas cuidado ao compartilhar tela!

---

## 📊 Monitoramento no VSCode

### Extensão Docker

Com a extensão Docker instalada:

1. **Ver containers:**
   - Clique no ícone Docker na sidebar (⛵)
   - Veja container "n8n-server"
   
2. **Ações rápidas:**
   - Botão direito no container:
     - Start/Stop
     - Restart
     - View Logs
     - Open in Browser

3. **Logs em tempo real:**
   - Botão direito → "View Logs"
   - Abre aba com logs atualizando

---

## ✅ Checklist Final

Depois de configurar tudo:

- [ ] Docker Desktop rodando
- [ ] VSCode instalado
- [ ] Projeto aberto no VSCode
- [ ] Terminal funcionando (PowerShell)
- [ ] n8n instalado e rodando
- [ ] Consigo acessar http://localhost:5678
- [ ] Fiz login com sucesso
- [ ] Extensões do VSCode instaladas
- [ ] Sei usar comandos básicos
- [ ] Entendo onde estão os arquivos importantes

---

## 🎓 Resumo Rápido

```
1. Baixar projeto → C:\Users\SeuNome\Documents\
2. Abrir VSCode → Open Folder
3. Abrir Terminal → Ctrl+`
4. Executar → cd docker; .\scripts\setup.ps1
5. Acessar → http://localhost:5678
6. Login → admin / SuaSenha
7. Usar! 🎉
```

---

## 💰 Custo Total

- **n8n Self-Hosted:** R$ 0,00 (GRATUITO!)
- **Execuções:** Ilimitadas (sem limite!)
- **Docker:** Gratuito
- **VSCode:** Gratuito
- **Energia:** ~R$ 10/mês (computador ligado 24/7)

**Total:** R$ 10/mês (apenas energia)

**vs n8n Cloud Pago:** Economiza R$ 1.680/ano! 💸

---

## 📞 Precisa de Ajuda?

### Documentação
- `README.md` - Visão geral
- `docker/README-INSTALACAO.md` - Instalação detalhada
- `docs/` - Guias completos

### Comandos Rápidos
- `REFERENCIA-RAPIDA.md` - Cheat sheet

### Comunidade
- Forum n8n: https://community.n8n.io
- Docs oficiais: https://docs.n8n.io

---

**🎉 Pronto! Você agora tem n8n self-hosted rodando no Windows com VSCode!**

**100% GRATUITO e SEM LIMITES! 🚀**
