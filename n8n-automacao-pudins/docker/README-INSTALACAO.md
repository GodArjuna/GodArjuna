# Guia de Instalação Rápida - n8n Docker 🚀

## 📋 O Que Você Precisa

Antes de começar, certifique-se de ter:

- ✅ **Docker instalado** (você já tem!)
- ✅ **10 GB** de espaço livre em disco
- ✅ **4 GB** de RAM disponível (recomendado)
- ✅ **10 minutos** do seu tempo

---

## 🎯 Instalação Automática (RECOMENDADO)

### Para Windows 🪟

1. **Abra o PowerShell** (como Administrador é recomendado)
2. **Navegue** até a pasta `docker`:
   ```powershell
   cd caminho\para\n8n-automacao-pudins\docker
   ```
3. **Execute o instalador**:
   ```powershell
   .\scripts\setup.ps1
   ```
4. **Siga as instruções** na tela

### Para Linux/Mac 🐧🍎

1. **Abra o Terminal**
2. **Navegue** até a pasta `docker`:
   ```bash
   cd /caminho/para/n8n-automacao-pudins/docker
   ```
3. **Torne executável** e **execute**:
   ```bash
   chmod +x scripts/setup.sh
   ./scripts/setup.sh
   ```
4. **Siga as instruções** na tela

---

## 🔧 Instalação Manual

Se preferir configurar manualmente:

### Passo 1: Criar Arquivo de Configuração

```bash
# Na pasta docker/
cp .env.example .env
```

### Passo 2: Editar Configurações

Abra o arquivo `.env` e configure:

```bash
# OBRIGATÓRIO: Mude estas configurações!
N8N_USER=admin
N8N_PASSWORD=SuaSenhaForte123!

# IMPORTANTE: Coloque seu IP local
# Descubra com: ipconfig (Windows) ou ip a (Linux)
WEBHOOK_URL=http://192.168.1.100:5678/
```

### Passo 3: Iniciar n8n

```bash
docker-compose up -d
```

### Passo 4: Verificar

```bash
docker-compose ps
```

Deve mostrar o container "Up" (rodando).

---

## 📊 Informações que Você Precisa Fornecer

Durante a instalação, você será perguntado sobre:

### 1. 🔐 Credenciais de Acesso

- **Usuário**: Nome de usuário para login no n8n (ex: admin, seu_nome)
- **Senha**: Senha forte (mínimo 8 caracteres)
  - ✅ Bom: `Pudim@2024!Safe`
  - ❌ Ruim: `123456`, `senha`

### 2. 🌐 Configuração de Rede

- **IP do Notebook**: Endereço IP local do seu computador
  - **Como descobrir:**
    - **Windows**: Abra CMD e digite `ipconfig`
    - **Linux/Mac**: Abra Terminal e digite `ip a` ou `ifconfig`
  - Procure por algo como: `192.168.1.X` ou `10.0.0.X`

- **URL de Webhook**: 
  - Se usar só localmente: `http://localhost:5678/`
  - Se acessar de outros dispositivos: `http://SEU_IP:5678/`

### 3. ⚙️ Configurações Opcionais

- **Frequência de reinicialização**: A cada quantos dias reiniciar automaticamente (3, 7 ou 14 dias)
- **Limites de recursos**: CPU e memória (use os padrões se não souber)

---

## 📝 Checklist de Configuração

Após instalação, você terá:

- [ ] n8n rodando em http://localhost:5678
- [ ] Usuário e senha configurados
- [ ] Pode fazer login na interface
- [ ] Pastas criadas (data, backups, logs)
- [ ] Arquivo .env com suas configurações
- [ ] (Opcional) Reinicialização automática configurada

---

## 🎯 Próximos Passos Após Instalação

### 1. Acessar n8n

Abra seu navegador e acesse:
- **Local**: http://localhost:5678
- **Rede**: http://SEU_IP:5678

### 2. Fazer Login

Use o usuário e senha que você configurou.

### 3. Configurar Integrações

Siga os guias em `../docs/` para configurar:
- Google Sheets
- Google Forms
- Telegram ou WhatsApp
- Mercado Pago

### 4. Importar Workflows

Veja os workflows prontos em `../workflows/` e importe:
1. Automação de Vendas
2. Gestão de Estoque
3. Monitoramento de Preços
4. Marketing e CRM

---

## 🔍 Verificando a Instalação

### Teste 1: Container Rodando

```bash
docker-compose ps
```

Deve mostrar:
```
NAME         STATUS      PORTS
n8n-server   Up X min    0.0.0.0:5678->5678/tcp
```

### Teste 2: Acesso Web

Abra http://localhost:5678 - Deve aparecer a tela de login.

### Teste 3: Health Check

```bash
curl http://localhost:5678/healthz
```

Deve retornar: `{"status":"ok"}`

### Teste 4: Logs

```bash
docker-compose logs -f
```

Não deve ter erros (pressione Ctrl+C para sair).

---

## ❓ Problemas Comuns

### "Docker não está rodando"

**Solução:**
- Windows: Inicie o Docker Desktop
- Linux: `sudo systemctl start docker`

### "Porta 5678 já em uso"

**Solução:**
Edite `.env` e mude `N8N_PORT=5678` para outra porta (ex: 5679)

### "Não consigo acessar de outro dispositivo"

**Solução:**
1. Verifique se WEBHOOK_URL tem seu IP correto no `.env`
2. Verifique firewall (pode estar bloqueando)
3. Certifique-se que ambos dispositivos estão na mesma rede

### "Esqueci minha senha"

**Solução:**
1. Pare o n8n: `docker-compose down`
2. Edite `.env` e mude `N8N_PASSWORD`
3. Inicie novamente: `docker-compose up -d`

---

## 🛠️ Comandos Úteis

```bash
# Iniciar n8n
docker-compose up -d

# Parar n8n
docker-compose down

# Reiniciar n8n
docker-compose restart

# Ver logs
docker-compose logs -f

# Ver status
docker-compose ps

# Atualizar n8n
docker-compose pull
docker-compose up -d

# Fazer backup
docker-compose exec n8n n8n export:workflow --all --output=/backups/backup.json

# Acessar terminal do container
docker-compose exec n8n /bin/sh
```

---

## 📂 Estrutura de Arquivos Criada

```
docker/
├── .env                    # Suas configurações (NÃO compartilhe!)
├── .env.example            # Template de configuração
├── docker-compose.yml      # Configuração do Docker
├── data/                   # Dados do n8n (workflows, etc)
├── backups/                # Backups automáticos
├── logs/                   # Logs de operação
├── config/                 # Configurações extras
└── scripts/                # Scripts de manutenção
    ├── setup.sh            # Instalador Linux/Mac
    ├── setup.ps1           # Instalador Windows
    └── restart-scheduler.*  # Script de reinicialização
```

---

## 🔐 Segurança

### ⚠️ IMPORTANTE:

1. **Mude a senha padrão** imediatamente
2. **Não compartilhe** o arquivo `.env` (contém sua senha!)
3. **Use senha forte** (mínimo 8 caracteres, letras e números)
4. **Backup regular** dos seus dados
5. **Não exponha** na internet sem HTTPS (use apenas rede local ou VPN)

### Senha Forte - Exemplos:

- ✅ `Pudim@2024!Seguro`
- ✅ `N8n#Automacao2024`
- ✅ `MinhaSenha!123Forte`
- ❌ `123456`
- ❌ `senha`
- ❌ `admin`

---

## 💰 Custo

- **Software**: R$ 0,00 (100% gratuito)
- **Energia**: ~R$ 10/mês (notebook 24/7)
- **Total**: R$ 10/mês

**Economia vs Cloud**: R$ 1.680/ano! 🎉

---

## 📞 Precisa de Ajuda?

1. **Documentação Completa**: `../docs/`
2. **Referência Rápida**: `../REFERENCIA-RAPIDA.md`
3. **Comunidade n8n**: https://community.n8n.io
4. **Troubleshooting**: `../docs/04-solucao-problemas.md`

---

## ✅ Pronto para Começar?

Escolha seu método:

### 🚀 Rápido (Recomendado)
Execute o script de instalação automática:
- Windows: `.\scripts\setup.ps1`
- Linux/Mac: `./scripts/setup.sh`

### 🔧 Manual
Siga as instruções de instalação manual acima.

---

**Tempo estimado**: 10-15 minutos  
**Dificuldade**: ⭐⭐☆☆☆ (Fácil)

**Boa sorte! 🍮**
