#!/bin/bash

# Script de Verificação de Saúde do n8n
# Execute: ./health-check.sh

echo "============================================"
echo "  VERIFICAÇÃO DE SAÚDE DO N8N"
echo "============================================"
echo ""

cd "$(dirname "$0")/.."

# Cores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

SUCCESS=0
WARNINGS=0
ERRORS=0

# Função para verificar
check() {
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓${NC} $1"
        SUCCESS=$((SUCCESS + 1))
    else
        echo -e "${RED}✗${NC} $1"
        ERRORS=$((ERRORS + 1))
    fi
}

warn() {
    echo -e "${YELLOW}⚠${NC} $1"
    WARNINGS=$((WARNINGS + 1))
}

echo "1. Verificando Docker..."
docker --version > /dev/null 2>&1
check "Docker instalado"

docker info > /dev/null 2>&1
check "Docker rodando"

echo ""
echo "2. Verificando n8n..."
if docker-compose ps | grep -q "Up"; then
    check "Container n8n rodando"
else
    echo -e "${RED}✗${NC} Container n8n NÃO está rodando"
    ERRORS=$((ERRORS + 1))
    echo ""
    echo "Tente iniciar com: docker-compose up -d"
    exit 1
fi

echo ""
echo "3. Verificando conectividade..."
HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:5678/healthz 2>/dev/null || echo "000")
if [ "$HTTP_CODE" = "200" ]; then
    check "n8n respondendo (HTTP 200)"
else
    echo -e "${RED}✗${NC} n8n não está respondendo (HTTP $HTTP_CODE)"
    ERRORS=$((ERRORS + 1))
fi

echo ""
echo "4. Verificando arquivos..."
[ -f ".env" ] && check ".env existe" || warn ".env não encontrado"
[ -f "docker-compose.yml" ] && check "docker-compose.yml existe" || echo -e "${RED}✗${NC} docker-compose.yml não encontrado" && ERRORS=$((ERRORS + 1))
[ -d "data" ] && check "Diretório data/ existe" || warn "Diretório data/ não existe"
[ -d "backups" ] && check "Diretório backups/ existe" || warn "Diretório backups/ não existe"

echo ""
echo "5. Verificando recursos..."
MEMORY_USAGE=$(docker stats --no-stream --format "{{.MemPerc}}" n8n-server 2>/dev/null | sed 's/%//')
if [ ! -z "$MEMORY_USAGE" ]; then
    if (( $(echo "$MEMORY_USAGE < 80" | bc -l 2>/dev/null || echo "1") )); then
        check "Uso de memória OK (${MEMORY_USAGE}%)"
    else
        warn "Uso de memória alto (${MEMORY_USAGE}%)"
    fi
fi

CPU_USAGE=$(docker stats --no-stream --format "{{.CPUPerc}}" n8n-server 2>/dev/null | sed 's/%//')
if [ ! -z "$CPU_USAGE" ]; then
    if (( $(echo "$CPU_USAGE < 80" | bc -l 2>/dev/null || echo "1") )); then
        check "Uso de CPU OK (${CPU_USAGE}%)"
    else
        warn "Uso de CPU alto (${CPU_USAGE}%)"
    fi
fi

echo ""
echo "6. Verificando espaço em disco..."
DISK_USAGE=$(df -h . | awk 'NR==2 {print $5}' | sed 's/%//')
if [ $DISK_USAGE -lt 90 ]; then
    check "Espaço em disco OK (${DISK_USAGE}% usado)"
else
    warn "Espaço em disco baixo (${DISK_USAGE}% usado)"
fi

echo ""
echo "7. Verificando backups..."
BACKUP_COUNT=$(ls -1 backups/*.tar.gz 2>/dev/null | wc -l || echo "0")
if [ $BACKUP_COUNT -gt 0 ]; then
    check "Backups encontrados ($BACKUP_COUNT)"
    LAST_BACKUP=$(ls -t backups/*.tar.gz 2>/dev/null | head -n1)
    if [ ! -z "$LAST_BACKUP" ]; then
        BACKUP_AGE=$(( ($(date +%s) - $(stat -f%m "$LAST_BACKUP" 2>/dev/null || stat -c%Y "$LAST_BACKUP")) / 86400 ))
        if [ $BACKUP_AGE -le 7 ]; then
            check "Último backup recente (${BACKUP_AGE} dias atrás)"
        else
            warn "Último backup antigo (${BACKUP_AGE} dias atrás)"
        fi
    fi
else
    warn "Nenhum backup encontrado"
fi

echo ""
echo "============================================"
echo "  RESULTADO"
echo "============================================"
echo -e "${GREEN}✓ Sucessos: $SUCCESS${NC}"
echo -e "${YELLOW}⚠ Avisos: $WARNINGS${NC}"
echo -e "${RED}✗ Erros: $ERRORS${NC}"
echo ""

if [ $ERRORS -eq 0 ] && [ $WARNINGS -eq 0 ]; then
    echo -e "${GREEN}🎉 Tudo OK! Sistema saudável.${NC}"
    exit 0
elif [ $ERRORS -eq 0 ]; then
    echo -e "${YELLOW}⚠ Sistema OK mas com avisos.${NC}"
    exit 0
else
    echo -e "${RED}❌ Sistema com problemas. Verifique os erros acima.${NC}"
    exit 1
fi
