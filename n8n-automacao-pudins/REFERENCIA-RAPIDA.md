# Cartão de Referência Rápida - n8n Self-Hosted 📋

## 🚀 Setup Inicial em 5 Minutos

```bash
# 1. Instalar Docker
curl -fsSL https://get.docker.com | sh

# 2. Criar pasta
mkdir -p ~/n8n-server && cd ~/n8n-server

# 3. Baixar docker-compose.yml
curl -o docker-compose.yml https://raw.githubusercontent.com/[SEU_REPO]/docker-compose.yml

# 4. Iniciar n8n
docker-compose up -d

# 5. Acessar
# http://localhost:5678
```

---

## 📝 Comandos Essenciais

### Gerenciamento do n8n

```bash
# Iniciar
docker-compose up -d

# Parar
docker-compose down

# Reiniciar
docker-compose restart

# Ver logs (últimas linhas)
docker-compose logs --tail=50 n8n

# Ver logs em tempo real
docker-compose logs -f n8n

# Ver status
docker-compose ps

# Ver uso de recursos
docker stats n8n-server
```

### Atualização

```bash
# Atualizar para versão mais recente
docker-compose pull
docker-compose down
docker-compose up -d
```

### Backup e Restauração

```bash
# Backup manual
docker-compose exec n8n n8n export:workflow --all \
  --output=/backups/backup-$(date +%Y%m%d).json

# Backup de credenciais
docker-compose exec n8n n8n export:credentials --all \
  --output=/backups/credentials-$(date +%Y%m%d).json

# Restaurar workflows
docker-compose exec n8n n8n import:workflow \
  --input=/backups/backup-20240201.json

# Restaurar credenciais
docker-compose exec n8n n8n import:credentials \
  --input=/backups/credentials-20240201.json
```

---

## 🔄 Scripts de Automação

### Reinicialização Automática (Cron)

```bash
# Editar crontab
crontab -e

# Reiniciar a cada 7 dias às 3h
0 3 */7 * * cd ~/n8n-server && docker-compose restart n8n

# Reiniciar a cada 3 dias às 3h
0 3 */3 * * cd ~/n8n-server && docker-compose restart n8n
```

### Backup Diário (Cron)

```bash
# Backup diário às 2h
0 2 * * * cd ~/n8n-server && docker-compose exec -T n8n n8n export:workflow --all --output=/backups/auto-$(date +\%Y\%m\%d).json
```

### Verificação de Saúde (Cron)

```bash
# Verificar a cada hora
0 * * * * curl -f http://localhost:5678/healthz || cd ~/n8n-server && docker-compose restart n8n
```

---

## 🐛 Troubleshooting Rápido

### n8n não inicia

```bash
# Ver o que está errado
docker-compose logs n8n

# Remover e recriar
docker-compose down -v
docker-compose up -d
```

### Erro de permissão

```bash
# Corrigir permissões
sudo chown -R $USER:$USER ~/n8n-server/
```

### Falta de memória

```bash
# Ver uso
docker stats

# Limpar containers parados
docker system prune

# Limpar tudo (CUIDADO!)
docker system prune -a
```

### Não acessa de outro dispositivo

```bash
# Descobrir seu IP
ip a | grep "inet 192"  # Linux
ipconfig | findstr "IPv4"  # Windows

# Testar se está acessível
curl http://SEU_IP:5678/healthz
```

### Webhook não funciona

1. Verificar WEBHOOK_URL no docker-compose.yml
2. Usar IP fixo ao invés de localhost
3. Verificar firewall

---

## 🔐 Segurança Básica

### Trocar senha

```bash
# Parar n8n
docker-compose down

# Editar docker-compose.yml
nano docker-compose.yml
# Mudar N8N_BASIC_AUTH_PASSWORD

# Reiniciar
docker-compose up -d
```

### Firewall (Linux)

```bash
# Permitir apenas rede local
sudo ufw allow from 192.168.1.0/24 to any port 5678
sudo ufw deny 5678
sudo ufw enable
```

---

## 📊 Monitoramento

### Verificar saúde

```bash
# Via curl
curl http://localhost:5678/healthz

# Deve retornar: {"status":"ok"}
```

### Ver métricas

```bash
# Uso de recursos em tempo real
docker stats n8n-server

# Espaço em disco
df -h ~/n8n-server/
```

### Logs importantes

```bash
# Últimas 100 linhas
docker-compose logs --tail=100 n8n

# Erros apenas
docker-compose logs n8n | grep -i error

# Últimas execuções
docker-compose logs n8n | grep -i execution
```

---

## 🎯 URLs Úteis

```
Interface Web:
http://localhost:5678

Ou de outro dispositivo:
http://[IP_DO_NOTEBOOK]:5678

Health Check:
http://localhost:5678/healthz

Webhook Base:
http://[IP_DO_NOTEBOOK]:5678/webhook/
```

---

## 📁 Estrutura de Arquivos

```
~/n8n-server/
├── docker-compose.yml      # Configuração principal
├── data/                   # Dados do n8n
│   ├── database.sqlite     # Banco de dados
│   └── .n8n/               # Workflows e configs
├── backups/                # Backups automáticos
├── logs/                   # Logs personalizados
└── scripts/                # Scripts de manutenção
    ├── restart.sh
    ├── backup.sh
    └── health-check.sh
```

---

## ⚙️ Variáveis de Ambiente Importantes

```yaml
# No docker-compose.yml
N8N_BASIC_AUTH_USER=admin           # Usuário
N8N_BASIC_AUTH_PASSWORD=senha       # Senha (MUDE!)
WEBHOOK_URL=http://192.168.1.X:5678 # URL pública
GENERIC_TIMEZONE=America/Sao_Paulo  # Timezone Brasil
EXECUTIONS_DATA_MAX_AGE=168         # Manter logs por 7 dias
```

---

## 🔢 Portas Usadas

| Serviço | Porta | Descrição |
|---------|-------|-----------|
| n8n UI  | 5678  | Interface web |
| n8n Webhooks | 5678 | Recebe webhooks |

---

## 📞 Ajuda Rápida

### Comandos de Emergência

```bash
# n8n travou? Reiniciar forçado
docker-compose restart -t 0 n8n

# Espaço em disco cheio?
docker system prune -a --volumes

# Perdi tudo? Restaurar backup
cd ~/n8n-server/backups/
# Escolher backup mais recente
docker-compose exec -T n8n n8n import:workflow --input=/backups/[arquivo].json
```

### Quando pedir ajuda

Sempre inclua:
1. Versão do n8n: `docker-compose exec n8n n8n --version`
2. Logs: `docker-compose logs --tail=50 n8n`
3. Sistema: `uname -a` (Linux) ou versão Windows
4. Docker: `docker --version`

---

## 🎓 Recursos Adicionais

- **Documentação Completa:** [docs/05-servidor-notebook.md](../docs/05-servidor-notebook.md)
- **Especialista:** [docs/06-recomendacoes-especialista.md](../docs/06-recomendacoes-especialista.md)
- **Comunidade n8n:** https://community.n8n.io
- **Documentação Oficial:** https://docs.n8n.io

---

## 💾 Template docker-compose.yml Mínimo

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
      - N8N_BASIC_AUTH_ACTIVE=true
      - N8N_BASIC_AUTH_USER=admin
      - N8N_BASIC_AUTH_PASSWORD=MudeEssaSenha123!
      - GENERIC_TIMEZONE=America/Sao_Paulo
      - WEBHOOK_URL=http://192.168.1.100:5678/
    volumes:
      - ./data:/home/node/.n8n
      - ./backups:/backups
```

**Salve como:** `~/n8n-server/docker-compose.yml`

---

## ✅ Checklist Diário

- [ ] n8n está respondendo? `curl http://localhost:5678/healthz`
- [ ] Uso de memória OK? `docker stats n8n-server` (< 80%)
- [ ] Espaço em disco OK? `df -h` (> 10% livre)
- [ ] Backup recente existe? `ls -lh ~/n8n-server/backups/`

---

**Imprima este cartão e cole perto do seu servidor! 📌**

---

**[⬅️ Voltar](../docs/05-servidor-notebook.md)** | **[🏠 Início](../README.md)**
