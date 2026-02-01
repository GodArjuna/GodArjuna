#!/bin/bash

# Script de Backup Manual do n8n
# Execute: ./backup.sh

set -e

BACKUP_DIR="$(dirname "$0")/../backups"
DATE=$(date +%Y%m%d-%H%M%S)
BACKUP_NAME="manual-backup-$DATE"

echo "============================================"
echo "  BACKUP MANUAL DO N8N"
echo "============================================"
echo ""

cd "$(dirname "$0")/.."

echo "Criando backup..."

# Criar diretório temporário
mkdir -p "$BACKUP_DIR/$BACKUP_NAME"

# Exportar workflows
echo "1/3 Exportando workflows..."
docker-compose exec -T n8n n8n export:workflow --all \
  --output=/backups/$BACKUP_NAME/workflows.json

# Exportar credenciais
echo "2/3 Exportando credenciais..."
docker-compose exec -T n8n n8n export:credentials --all \
  --output=/backups/$BACKUP_NAME/credentials.json

# Copiar dados
echo "3/3 Copiando dados..."
if [ -d "data" ]; then
    cp -r data "$BACKUP_DIR/$BACKUP_NAME/"
fi

# Comprimir
echo "Comprimindo backup..."
cd "$BACKUP_DIR"
tar -czf "$BACKUP_NAME.tar.gz" "$BACKUP_NAME"
rm -rf "$BACKUP_NAME"

echo ""
echo "✓ Backup concluído!"
echo ""
echo "Arquivo: $BACKUP_DIR/$BACKUP_NAME.tar.gz"
echo "Tamanho: $(du -h "$BACKUP_NAME.tar.gz" | cut -f1)"
echo ""
echo "Para restaurar:"
echo "  tar -xzf $BACKUP_NAME.tar.gz"
echo "  cd $BACKUP_NAME"
echo "  docker-compose exec -T n8n n8n import:workflow --input=/backups/$BACKUP_NAME/workflows.json"
echo ""
