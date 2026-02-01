#!/bin/bash

# ============================================
# Script de Configuração Automática do n8n
# Sistema de Automação para Negócio de Pudins
# ============================================

set -e

echo "============================================"
echo "  INSTALADOR AUTOMÁTICO N8N - PUDINS      "
echo "============================================"
echo ""

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para printar com cor
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo "ℹ $1"
}

# Verificar se Docker está instalado
echo "Verificando pré-requisitos..."
if ! command -v docker &> /dev/null; then
    print_error "Docker não está instalado!"
    echo "Instale o Docker primeiro: https://docs.docker.com/get-docker/"
    exit 1
fi
print_success "Docker instalado"

if ! command -v docker-compose &> /dev/null; then
    print_error "Docker Compose não está instalado!"
    echo "Instale o Docker Compose: https://docs.docker.com/compose/install/"
    exit 1
fi
print_success "Docker Compose instalado"

# Verificar se Docker está rodando
if ! docker info &> /dev/null; then
    print_error "Docker não está rodando!"
    echo "Inicie o Docker e tente novamente."
    exit 1
fi
print_success "Docker está rodando"

echo ""
echo "============================================"
echo "  COLETA DE INFORMAÇÕES"
echo "============================================"
echo ""

# Detectar IP local
LOCAL_IP=$(hostname -I | awk '{print $1}' 2>/dev/null || ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}' | head -n 1)
if [ -z "$LOCAL_IP" ]; then
    LOCAL_IP="localhost"
fi

print_info "IP local detectado: $LOCAL_IP"
echo ""

# Perguntar informações ao usuário
read -p "Digite o usuário admin para n8n (padrão: admin): " N8N_USER
N8N_USER=${N8N_USER:-admin}

while true; do
    read -s -p "Digite uma senha forte para n8n (mínimo 8 caracteres): " N8N_PASSWORD
    echo ""
    if [ ${#N8N_PASSWORD} -ge 8 ]; then
        read -s -p "Confirme a senha: " N8N_PASSWORD_CONFIRM
        echo ""
        if [ "$N8N_PASSWORD" = "$N8N_PASSWORD_CONFIRM" ]; then
            break
        else
            print_error "Senhas não coincidem. Tente novamente."
        fi
    else
        print_error "Senha muito curta. Mínimo 8 caracteres."
    fi
done

echo ""
read -p "Usar IP local ($LOCAL_IP) para webhooks? (s/N): " USE_LOCAL_IP
if [[ $USE_LOCAL_IP =~ ^[Ss]$ ]]; then
    WEBHOOK_URL="http://${LOCAL_IP}:5678/"
else
    read -p "Digite a URL completa para webhooks (ex: http://192.168.1.100:5678/): " WEBHOOK_URL
    WEBHOOK_URL=${WEBHOOK_URL:-http://localhost:5678/}
fi

echo ""
print_info "Configurações:"
echo "  - Usuário: $N8N_USER"
echo "  - Senha: ********"
echo "  - Webhook URL: $WEBHOOK_URL"
echo ""

read -p "Confirmar e iniciar instalação? (s/N): " CONFIRM
if [[ ! $CONFIRM =~ ^[Ss]$ ]]; then
    print_warning "Instalação cancelada."
    exit 0
fi

echo ""
echo "============================================"
echo "  CRIANDO ESTRUTURA DE DIRETÓRIOS"
echo "============================================"
echo ""

# Criar diretórios necessários
mkdir -p data backups logs config scripts

print_success "Diretórios criados"

# Criar arquivo .env
echo ""
echo "Criando arquivo de configuração (.env)..."

cat > .env << EOF
# Gerado automaticamente em $(date)
N8N_PORT=5678
N8N_USER=${N8N_USER}
N8N_PASSWORD=${N8N_PASSWORD}
WEBHOOK_URL=${WEBHOOK_URL}
N8N_HOST=0.0.0.0
N8N_PROTOCOL=http
TIMEZONE=America/Sao_Paulo
EXECUTIONS_MAX_AGE=168
LOG_LEVEL=info
CPU_LIMIT=2
MEMORY_LIMIT=2G
CPU_RESERVE=0.5
MEMORY_RESERVE=512M
EOF

print_success "Arquivo .env criado"

# Definir permissões
chmod 600 .env
print_success "Permissões de segurança aplicadas"

echo ""
echo "============================================"
echo "  BAIXANDO E INICIANDO N8N"
echo "============================================"
echo ""

# Baixar imagem do n8n
print_info "Baixando imagem do n8n (pode demorar alguns minutos)..."
docker-compose pull

print_success "Imagem baixada"

# Iniciar n8n
print_info "Iniciando n8n..."
docker-compose up -d

# Aguardar inicialização
print_info "Aguardando inicialização (30 segundos)..."
sleep 30

# Verificar se está rodando
if docker-compose ps | grep -q "Up"; then
    print_success "n8n está rodando!"
else
    print_error "n8n não iniciou corretamente"
    echo "Verifique os logs com: docker-compose logs"
    exit 1
fi

echo ""
echo "============================================"
echo "  INSTALAÇÃO CONCLUÍDA! 🎉"
echo "============================================"
echo ""
print_success "n8n foi instalado e está rodando!"
echo ""
echo "📱 ACESSO:"
echo "   Local: http://localhost:5678"
echo "   Rede: ${WEBHOOK_URL}"
echo ""
echo "🔐 LOGIN:"
echo "   Usuário: ${N8N_USER}"
echo "   Senha: (a que você configurou)"
echo ""
echo "📝 PRÓXIMOS PASSOS:"
echo "   1. Acesse o n8n no navegador"
echo "   2. Faça login com as credenciais acima"
echo "   3. Importe os workflows da documentação"
echo "   4. Configure as integrações (Google, Telegram, etc)"
echo ""
echo "🔧 COMANDOS ÚTEIS:"
echo "   Parar:      docker-compose down"
echo "   Reiniciar:  docker-compose restart"
echo "   Logs:       docker-compose logs -f"
echo "   Status:     docker-compose ps"
echo ""
echo "📚 DOCUMENTAÇÃO:"
echo "   Ver: ../docs/ e ../README.md"
echo ""
echo "============================================"

# Oferecer configurar agendamentos
echo ""
read -p "Deseja configurar reinicialização automática agora? (s/N): " CONFIG_CRON
if [[ $CONFIG_CRON =~ ^[Ss]$ ]]; then
    echo ""
    echo "Configurando reinicialização automática..."
    
    # Criar script de reinicialização
    cat > scripts/restart-scheduler.sh << 'EOFSCRIPT'
#!/bin/bash
LOG_FILE="$(dirname "$0")/../logs/restart.log"
cd "$(dirname "$0")/.."

echo "==================================" >> "$LOG_FILE"
echo "Reinicialização Automática" >> "$LOG_FILE"
echo "Data/Hora: $(date)" >> "$LOG_FILE"
echo "==================================" >> "$LOG_FILE"

docker-compose exec -T n8n n8n export:workflow --all --output=/backups/backup-$(date +%Y%m%d-%H%M%S).json >> "$LOG_FILE" 2>&1
docker-compose restart n8n >> "$LOG_FILE" 2>&1

sleep 30

if docker-compose ps | grep -q "Up"; then
    echo "✅ n8n reiniciado com sucesso!" >> "$LOG_FILE"
else
    echo "❌ ERRO: n8n não iniciou!" >> "$LOG_FILE"
    docker-compose up -d n8n >> "$LOG_FILE" 2>&1
fi

echo "" >> "$LOG_FILE"
EOFSCRIPT

    chmod +x scripts/restart-scheduler.sh
    
    echo "Escolha a frequência de reinicialização:"
    echo "1) A cada 3 dias"
    echo "2) A cada 7 dias (recomendado)"
    echo "3) A cada 14 dias"
    read -p "Opção (1-3): " CRON_OPTION
    
    case $CRON_OPTION in
        1) CRON_SCHEDULE="0 3 */3 * *" ;;
        2) CRON_SCHEDULE="0 3 */7 * *" ;;
        3) CRON_SCHEDULE="0 3 */14 * *" ;;
        *) CRON_SCHEDULE="0 3 */7 * *" ;;
    esac
    
    SCRIPT_PATH="$(pwd)/scripts/restart-scheduler.sh"
    CRON_LINE="$CRON_SCHEDULE $SCRIPT_PATH"
    
    # Adicionar ao crontab
    (crontab -l 2>/dev/null | grep -v "$SCRIPT_PATH"; echo "$CRON_LINE") | crontab -
    
    print_success "Reinicialização automática configurada!"
    echo "   Agendamento: $CRON_SCHEDULE (às 3h da manhã)"
fi

echo ""
print_success "Setup completo! Bom trabalho! 🍮"
