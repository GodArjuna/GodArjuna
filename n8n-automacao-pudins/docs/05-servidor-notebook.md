# Servidor n8n em Notebook - Guia Completo 🖥️

## 📋 Visão Geral

Este guia ensina como transformar seu **notebook em um servidor n8n** totalmente gratuito, incluindo automação de reinicialização e manutenção preventiva.

**💰 Custo:** R$ 0,00 (100% Gratuito!)  
**⏱️ Tempo de Setup:** 30-60 minutos  
**🔧 Dificuldade:** Intermediária (guia passo a passo)

---

## 🎯 O Que Você Vai Conseguir

✅ n8n rodando 24/7 no seu notebook  
✅ Execuções ilimitadas (sem limite do plano free)  
✅ Acesso via navegador de qualquer dispositivo da rede  
✅ Reinicialização automática a cada X dias  
✅ Backup automático dos workflows  
✅ Monitoramento de saúde do sistema  
✅ Recuperação automática de falhas  

---

## 📊 Requisitos do Notebook

### Mínimo (Funciona)
- **Processador:** Intel Core i3 ou equivalente
- **RAM:** 4GB
- **Armazenamento:** 10GB livres
- **Sistema:** Windows 10/11, Ubuntu 20.04+, ou macOS

### Recomendado (Melhor Performance)
- **Processador:** Intel Core i5 ou superior
- **RAM:** 8GB
- **Armazenamento:** 20GB+ livres (SSD preferível)
- **Internet:** Conexão estável (cabo ethernet ideal)

### ⚡ Dica de Energia
Configure para:
- Não hibernar quando a tampa estiver fechada
- Nunca desligar automaticamente
- Tela pode desligar (economiza energia)

---

## 🚀 Método 1: Docker (RECOMENDADO)

**Por que Docker?**
- ✅ Mais fácil de instalar
- ✅ Mais fácil de atualizar
- ✅ Isolado do sistema
- ✅ Backup simplificado
- ✅ Multiplataforma

### Passo 1: Instalar Docker

#### No Windows:

1. **Baixar Docker Desktop:**
   - Acesse: https://www.docker.com/products/docker-desktop
   - Baixe versão para Windows
   - Instale (requer reinicialização)

2. **Verificar instalação:**
   ```cmd
   docker --version
   ```
   Deve mostrar: `Docker version 24.x.x`

#### No Ubuntu/Linux:

```bash
# Atualizar sistema
sudo apt update && sudo apt upgrade -y

# Instalar Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Adicionar seu usuário ao grupo docker
sudo usermod -aG docker $USER

# Reiniciar para aplicar mudanças
sudo reboot

# Após reiniciar, verificar
docker --version
```

#### No macOS:

1. Baixe Docker Desktop: https://www.docker.com/products/docker-desktop
2. Instale o .dmg
3. Verifique: `docker --version`

### Passo 2: Criar Estrutura de Pastas

```bash
# Criar pasta para n8n
mkdir -p ~/n8n-server
cd ~/n8n-server

# Criar subpastas
mkdir -p data backups logs
```

### Passo 3: Criar docker-compose.yml

Crie o arquivo `docker-compose.yml`:

```yaml
version: '3.8'

services:
  n8n:
    image: n8nio/n8n:latest
    container_name: n8n-server
    restart: unless-stopped
    ports:
      - "5678:5678"
    environment:
      # Configurações básicas
      - N8N_BASIC_AUTH_ACTIVE=true
      - N8N_BASIC_AUTH_USER=admin
      - N8N_BASIC_AUTH_PASSWORD=SuaSenhaForte123!
      
      # Timezone (Brasil)
      - GENERIC_TIMEZONE=America/Sao_Paulo
      - TZ=America/Sao_Paulo
      
      # Webhook URL (ajuste com seu IP local)
      - WEBHOOK_URL=http://192.168.1.100:5678/
      
      # Executar workflows após restart
      - EXECUTIONS_PROCESS=main
      - EXECUTIONS_MODE=regular
      
      # Histórico de execuções
      - EXECUTIONS_DATA_SAVE_ON_ERROR=all
      - EXECUTIONS_DATA_SAVE_ON_SUCCESS=all
      - EXECUTIONS_DATA_PRUNE=true
      - EXECUTIONS_DATA_MAX_AGE=168
      
    volumes:
      - ./data:/home/node/.n8n
      - ./backups:/backups
      - ./logs:/logs
    
    # Limites de recursos (ajuste conforme seu notebook)
    deploy:
      resources:
        limits:
          cpus: '2'
          memory: 2G
        reservations:
          cpus: '0.5'
          memory: 512M
    
    # Healthcheck
    healthcheck:
      test: wget --no-verbose --tries=1 --spider http://localhost:5678/healthz || exit 1
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
```

**⚠️ IMPORTANTE:** Altere:
- `N8N_BASIC_AUTH_PASSWORD`: Use senha forte!
- `WEBHOOK_URL`: Coloque o IP local do seu notebook (descubra com `ipconfig` no Windows ou `ip a` no Linux)

### Passo 4: Iniciar n8n

```bash
# Iniciar n8n
docker-compose up -d

# Verificar se está rodando
docker-compose ps

# Ver logs (opcional)
docker-compose logs -f
```

**Pronto!** Acesse: http://localhost:5678 (ou http://SEU_IP:5678 de outro dispositivo)

### Passo 5: Configurar Início Automático

O Docker já reinicia automaticamente com `restart: unless-stopped`, mas vamos garantir que inicie com o sistema:

#### Windows:

1. Abra "Gerenciador de Tarefas" → Aba "Inicializar"
2. Certifique-se que "Docker Desktop" está habilitado
3. ✅ Pronto! Docker inicia automaticamente

#### Linux:

```bash
# Habilitar Docker no boot
sudo systemctl enable docker

# Verificar status
sudo systemctl status docker
```

#### macOS:

1. Docker Desktop → Preferences → General
2. Marque "Start Docker Desktop when you log in"

---

## 🔄 Automação de Reinicialização

### Script de Reinicialização Automática

Crie o arquivo `~/n8n-server/restart-scheduler.sh`:

```bash
#!/bin/bash

# Script de Reinicialização Automática do n8n
# Executar a cada X dias para prevenir travamentos

LOG_FILE="$HOME/n8n-server/logs/restart.log"

echo "==================================" >> $LOG_FILE
echo "Reinicialização Automática" >> $LOG_FILE
echo "Data/Hora: $(date)" >> $LOG_FILE
echo "==================================" >> $LOG_FILE

# Ir para diretório do n8n
cd $HOME/n8n-server

# Fazer backup antes de reiniciar
echo "Criando backup..." >> $LOG_FILE
docker-compose exec -T n8n n8n export:workflow --all --output=/backups/backup-$(date +%Y%m%d-%H%M%S).json

# Reiniciar container
echo "Reiniciando n8n..." >> $LOG_FILE
docker-compose restart n8n

# Aguardar inicialização
sleep 30

# Verificar se está rodando
if docker-compose ps | grep -q "Up"; then
    echo "✅ n8n reiniciado com sucesso!" >> $LOG_FILE
else
    echo "❌ ERRO: n8n não iniciou corretamente!" >> $LOG_FILE
    # Tentar iniciar novamente
    docker-compose up -d n8n
fi

echo "" >> $LOG_FILE
```

**Dar permissão de execução:**
```bash
chmod +x ~/n8n-server/restart-scheduler.sh
```

### Agendar Reinicialização Automática

#### Linux/macOS - Usando Cron:

```bash
# Editar crontab
crontab -e

# Adicionar linha para reiniciar a cada 7 dias às 3h da manhã
0 3 */7 * * /home/seu_usuario/n8n-server/restart-scheduler.sh

# Ou a cada 3 dias:
0 3 */3 * * /home/seu_usuario/n8n-server/restart-scheduler.sh

# Salvar e sair
```

**Verificar agendamento:**
```bash
crontab -l
```

#### Windows - Usando Agendador de Tarefas:

1. **Criar script PowerShell** `restart-n8n.ps1`:

```powershell
# Script de Reinicialização Automática - Windows
$logFile = "$env:USERPROFILE\n8n-server\logs\restart.log"
$dataHora = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

Add-Content -Path $logFile -Value "=================================="
Add-Content -Path $logFile -Value "Reinicialização Automática"
Add-Content -Path $logFile -Value "Data/Hora: $dataHora"
Add-Content -Path $logFile -Value "=================================="

# Ir para diretório
Set-Location "$env:USERPROFILE\n8n-server"

# Backup
Add-Content -Path $logFile -Value "Criando backup..."
docker-compose exec -T n8n n8n export:workflow --all --output=/backups/backup-$(Get-Date -Format "yyyyMMdd-HHmmss").json

# Reiniciar
Add-Content -Path $logFile -Value "Reiniciando n8n..."
docker-compose restart n8n

# Aguardar
Start-Sleep -Seconds 30

# Verificar
$status = docker-compose ps
if ($status -match "Up") {
    Add-Content -Path $logFile -Value "✅ n8n reiniciado com sucesso!"
} else {
    Add-Content -Path $logFile -Value "❌ ERRO: Tentando iniciar novamente..."
    docker-compose up -d n8n
}

Add-Content -Path $logFile -Value ""
```

2. **Agendar no Windows:**

- Abra "Agendador de Tarefas"
- Criar Tarefa Básica
- Nome: "Reiniciar n8n"
- Gatilho: Semanal (ou personalizado para 3 dias)
- Ação: Iniciar programa
  - Programa: `powershell.exe`
  - Argumentos: `-ExecutionPolicy Bypass -File "C:\Users\SeuUsuario\n8n-server\restart-n8n.ps1"`
- Configurações avançadas:
  - ✅ Executar mesmo se usuário não estiver conectado
  - ✅ Executar com privilégios mais altos

---

## 📊 Monitoramento de Saúde

### Script de Monitoramento

Crie `~/n8n-server/health-check.sh`:

```bash
#!/bin/bash

# Verificação de Saúde do n8n
# Executar a cada hora

LOG_FILE="$HOME/n8n-server/logs/health.log"
WEBHOOK_TELEGRAM="https://api.telegram.org/botSEU_TOKEN/sendMessage"
CHAT_ID="SEU_CHAT_ID"

# Verificar se container está rodando
if ! docker-compose ps | grep -q "Up"; then
    echo "$(date): ❌ n8n não está rodando! Tentando reiniciar..." >> $LOG_FILE
    
    # Tentar reiniciar
    docker-compose up -d n8n
    
    # Notificar via Telegram (opcional)
    curl -s -X POST $WEBHOOK_TELEGRAM \
         -d chat_id=$CHAT_ID \
         -d text="🚨 ALERTA: n8n parou de funcionar e foi reiniciado automaticamente!"
    
    exit 1
fi

# Verificar uso de memória
MEMORY_USAGE=$(docker stats --no-stream --format "{{.MemPerc}}" n8n-server | sed 's/%//')

if (( $(echo "$MEMORY_USAGE > 90" | bc -l) )); then
    echo "$(date): ⚠️ Uso de memória alto: ${MEMORY_USAGE}%" >> $LOG_FILE
    
    # Notificar
    curl -s -X POST $WEBHOOK_TELEGRAM \
         -d chat_id=$CHAT_ID \
         -d text="⚠️ n8n usando ${MEMORY_USAGE}% de memória. Considere reiniciar."
fi

# Verificar acesso HTTP
if ! curl -f -s http://localhost:5678/healthz > /dev/null; then
    echo "$(date): ❌ n8n não responde! Reiniciando..." >> $LOG_FILE
    docker-compose restart n8n
    
    curl -s -X POST $WEBHOOK_TELEGRAM \
         -d chat_id=$CHAT_ID \
         -d text="🔄 n8n não respondia e foi reiniciado."
fi

echo "$(date): ✅ n8n está saudável" >> $LOG_FILE
```

**Agendar verificação a cada hora:**
```bash
# No crontab
0 * * * * /home/seu_usuario/n8n-server/health-check.sh
```

---

## 💾 Backup Automático

### Script de Backup Completo

Crie `~/n8n-server/backup-daily.sh`:

```bash
#!/bin/bash

# Backup Diário Completo do n8n

BACKUP_DIR="$HOME/n8n-server/backups"
DATA_DIR="$HOME/n8n-server/data"
DATE=$(date +%Y%m%d-%H%M%S)
BACKUP_NAME="n8n-backup-$DATE"

echo "Iniciando backup em $(date)"

# Criar pasta do backup
mkdir -p "$BACKUP_DIR/$BACKUP_NAME"

# 1. Exportar todos os workflows
echo "Exportando workflows..."
docker-compose exec -T n8n n8n export:workflow --all --output=/backups/$BACKUP_NAME/workflows.json

# 2. Exportar credenciais (criptografadas)
echo "Exportando credenciais..."
docker-compose exec -T n8n n8n export:credentials --all --output=/backups/$BACKUP_NAME/credentials.json

# 3. Copiar dados brutos
echo "Copiando dados..."
cp -r "$DATA_DIR" "$BACKUP_DIR/$BACKUP_NAME/"

# 4. Comprimir tudo
echo "Comprimindo backup..."
cd "$BACKUP_DIR"
tar -czf "$BACKUP_NAME.tar.gz" "$BACKUP_NAME"
rm -rf "$BACKUP_NAME"

# 5. Limpar backups antigos (manter últimos 14 dias)
find "$BACKUP_DIR" -name "n8n-backup-*.tar.gz" -mtime +14 -delete

# 6. Opcional: Copiar para nuvem (Google Drive, Dropbox, etc)
# rclone copy "$BACKUP_NAME.tar.gz" gdrive:n8n-backups/

echo "✅ Backup concluído: $BACKUP_NAME.tar.gz"
echo "Tamanho: $(du -h "$BACKUP_NAME.tar.gz" | cut -f1)"
```

**Agendar backup diário às 2h:**
```bash
0 2 * * * /home/seu_usuario/n8n-server/backup-daily.sh >> /home/seu_usuario/n8n-server/logs/backup.log 2>&1
```

---

## 🔒 Segurança

### 1. Firewall

**Permitir apenas rede local:**

#### Linux (UFW):
```bash
# Bloquear acesso externo à porta 5678
sudo ufw deny 5678
sudo ufw allow from 192.168.1.0/24 to any port 5678
sudo ufw enable
```

#### Windows:
```powershell
# PowerShell como Administrador
New-NetFirewallRule -DisplayName "n8n Local Only" -Direction Inbound -LocalPort 5678 -Protocol TCP -Action Allow -RemoteAddress LocalSubnet
```

### 2. HTTPS com Caddy (Opcional)

Se quiser acesso externo seguro:

```bash
# Instalar Caddy
curl -fsSL https://getcaddy.com | sh

# Criar Caddyfile
cat > ~/n8n-server/Caddyfile << EOF
n8n.seudominio.com {
    reverse_proxy localhost:5678
}
EOF

# Iniciar Caddy
caddy start --config ~/n8n-server/Caddyfile
```

### 3. Atualizar Senha Regularmente

```bash
# Parar n8n
docker-compose down

# Editar docker-compose.yml com nova senha
nano docker-compose.yml

# Reiniciar
docker-compose up -d
```

---

## 🚨 Troubleshooting

### n8n não inicia

```bash
# Ver logs
docker-compose logs -f n8n

# Reiniciar do zero
docker-compose down
docker-compose up -d

# Se persistir, reconstruir
docker-compose down
docker-compose pull
docker-compose up -d
```

### Falta de memória

```bash
# Ver uso de recursos
docker stats n8n-server

# Aumentar limite no docker-compose.yml
# Ou limpar execuções antigas via interface n8n
```

### Não acessa de outro dispositivo

1. Verificar IP do notebook: `ipconfig` (Windows) ou `ip a` (Linux)
2. Testar conectividade: `ping 192.168.1.X`
3. Verificar firewall
4. Atualizar WEBHOOK_URL no docker-compose.yml

### Backup falha

```bash
# Verificar permissões
ls -la ~/n8n-server/backups

# Criar manualmente
docker-compose exec n8n n8n export:workflow --all --output=/backups/manual-backup.json
```

---

## 📈 Otimizações

### 1. SSD para Melhor Performance

Se possível, coloque a pasta `~/n8n-server/data` em um SSD.

### 2. Limitar Histórico de Execuções

No `docker-compose.yml`:
```yaml
- EXECUTIONS_DATA_MAX_AGE=72  # 3 dias ao invés de 7
```

### 3. Usar Proxy Reverso

Para múltiplos serviços no mesmo notebook:
- Nginx Proxy Manager
- Traefik
- Caddy

### 4. Monitorar Temperatura

```bash
# Linux
sensors

# Manter notebook com boa ventilação!
```

---

## 💡 Dicas Extras

### Economizar Energia

- Use modo "Economizador de Energia" mas desabilite suspensão
- Mantenha tela desligada quando não usar
- Feche programas desnecessários

### Acesso Remoto Seguro

**Opção 1: Tailscale (VPN grátis)**
```bash
# Instalar Tailscale
curl -fsSL https://tailscale.com/install.sh | sh

# Conectar
sudo tailscale up

# Acessar n8n via IP do Tailscale de qualquer lugar!
```

**Opção 2: Ngrok (temporário)**
```bash
ngrok http 5678
# Gera URL pública temporária
```

### Múltiplas Instâncias

Se quiser testar sem afetar produção:

```yaml
# docker-compose-test.yml
services:
  n8n-test:
    image: n8nio/n8n:latest
    ports:
      - "5679:5678"  # Porta diferente
    volumes:
      - ./data-test:/home/node/.n8n
```

```bash
docker-compose -f docker-compose-test.yml up -d
```

---

## 📊 Resumo de Comandos

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

# Backup manual
docker-compose exec n8n n8n export:workflow --all --output=/backups/backup.json

# Ver uso de recursos
docker stats n8n-server
```

---

## ✅ Checklist Final

- [ ] Docker instalado e funcionando
- [ ] docker-compose.yml criado e configurado
- [ ] n8n acessível via navegador
- [ ] Senha forte configurada
- [ ] Script de reinicialização criado e agendado
- [ ] Script de monitoramento configurado
- [ ] Backup automático agendado
- [ ] Firewall configurado (apenas rede local)
- [ ] Testado acesso de outro dispositivo
- [ ] Documentação dos scripts salva

---

## 🎓 Próximos Passos

Agora que seu servidor está rodando:

1. **Importe os workflows** da documentação principal
2. **Configure as integrações** (Google, Telegram, etc.)
3. **Teste com dados reais**
4. **Monitore por 1 semana**
5. **Ajuste conforme necessário**

---

**Voltar:** [README Principal](../README.md) | [Instalação Cloud](02-instalacao-configuracao.md)
