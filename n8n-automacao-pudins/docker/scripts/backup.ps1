# Script de Backup Manual do n8n - Windows
# Execute: .\backup.ps1

$backupDir = "$PSScriptRoot\..\backups"
$date = Get-Date -Format "yyyyMMdd-HHmmss"
$backupName = "manual-backup-$date"
$dockerPath = Split-Path -Parent $PSScriptRoot

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  BACKUP MANUAL DO N8N" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

Set-Location $dockerPath

Write-Host "Criando backup..." -ForegroundColor Yellow

# Criar diretório temporário
New-Item -ItemType Directory -Path "$backupDir\$backupName" -Force | Out-Null

# Exportar workflows
Write-Host "1/3 Exportando workflows..." -ForegroundColor Yellow
docker-compose exec -T n8n n8n export:workflow --all --output=/backups/$backupName/workflows.json

# Exportar credenciais
Write-Host "2/3 Exportando credenciais..." -ForegroundColor Yellow
docker-compose exec -T n8n n8n export:credentials --all --output=/backups/$backupName/credentials.json

# Copiar dados
Write-Host "3/3 Copiando dados..." -ForegroundColor Yellow
if (Test-Path "data") {
    Copy-Item -Path "data" -Destination "$backupDir\$backupName\" -Recurse
}

# Comprimir
Write-Host "Comprimindo backup..." -ForegroundColor Yellow
Compress-Archive -Path "$backupDir\$backupName" -DestinationPath "$backupDir\$backupName.zip"
Remove-Item -Path "$backupDir\$backupName" -Recurse -Force

Write-Host ""
Write-Host "✓ Backup concluído!" -ForegroundColor Green
Write-Host ""
Write-Host "Arquivo: $backupDir\$backupName.zip"
$size = (Get-Item "$backupDir\$backupName.zip").Length / 1MB
Write-Host "Tamanho: $([math]::Round($size, 2)) MB"
Write-Host ""
Write-Host "Para restaurar:" -ForegroundColor Yellow
Write-Host "  Expand-Archive $backupName.zip"
Write-Host "  docker-compose exec -T n8n n8n import:workflow --input=/backups/$backupName/workflows.json"
Write-Host ""
