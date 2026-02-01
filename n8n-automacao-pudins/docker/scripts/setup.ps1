# ============================================
# Script de Configuração Automática do n8n
# Sistema de Automação para Negócio de Pudins
# Windows PowerShell
# ============================================

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  INSTALADOR AUTOMÁTICO N8N - PUDINS      " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se está rodando como Administrador
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
if (-not $isAdmin) {
    Write-Host "⚠ AVISO: Recomenda-se executar como Administrador" -ForegroundColor Yellow
    Write-Host "Pressione Enter para continuar mesmo assim ou Ctrl+C para cancelar..."
    Read-Host
}

# Verificar Docker
Write-Host "Verificando pré-requisitos..." -ForegroundColor Yellow

try {
    $dockerVersion = docker --version
    Write-Host "✓ Docker instalado: $dockerVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker não está instalado!" -ForegroundColor Red
    Write-Host "Instale o Docker Desktop: https://www.docker.com/products/docker-desktop" -ForegroundColor Yellow
    exit 1
}

try {
    $composeVersion = docker-compose --version
    Write-Host "✓ Docker Compose instalado: $composeVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker Compose não está instalado!" -ForegroundColor Red
    Write-Host "Instale o Docker Compose ou atualize o Docker Desktop" -ForegroundColor Yellow
    exit 1
}

# Verificar se Docker está rodando
try {
    docker info | Out-Null
    Write-Host "✓ Docker está rodando" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker não está rodando!" -ForegroundColor Red
    Write-Host "Inicie o Docker Desktop e tente novamente." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  COLETA DE INFORMAÇÕES" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Detectar IP local
$localIP = (Get-NetIPAddress -AddressFamily IPv4 | Where-Object {$_.IPAddress -like "192.168.*" -or $_.IPAddress -like "10.*"} | Select-Object -First 1).IPAddress
if (-not $localIP) {
    $localIP = "localhost"
}

Write-Host "ℹ IP local detectado: $localIP" -ForegroundColor Cyan
Write-Host ""

# Coletar informações
$n8nUser = Read-Host "Digite o usuário admin para n8n (padrão: admin)"
if ([string]::IsNullOrWhiteSpace($n8nUser)) {
    $n8nUser = "admin"
}

do {
    $n8nPassword = Read-Host "Digite uma senha forte para n8n (mínimo 8 caracteres)" -AsSecureString
    $n8nPasswordPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($n8nPassword))
    
    if ($n8nPasswordPlain.Length -lt 8) {
        Write-Host "✗ Senha muito curta. Mínimo 8 caracteres." -ForegroundColor Red
        continue
    }
    
    $n8nPasswordConfirm = Read-Host "Confirme a senha" -AsSecureString
    $n8nPasswordConfirmPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($n8nPasswordConfirm))
    
    if ($n8nPasswordPlain -eq $n8nPasswordConfirmPlain) {
        break
    } else {
        Write-Host "✗ Senhas não coincidem. Tente novamente." -ForegroundColor Red
    }
} while ($true)

Write-Host ""
$useLocalIP = Read-Host "Usar IP local ($localIP) para webhooks? (S/n)"
if ($useLocalIP -eq "" -or $useLocalIP -eq "S" -or $useLocalIP -eq "s") {
    $webhookUrl = "http://${localIP}:5678/"
} else {
    $webhookUrl = Read-Host "Digite a URL completa para webhooks (ex: http://192.168.1.100:5678/)"
    if ([string]::IsNullOrWhiteSpace($webhookUrl)) {
        $webhookUrl = "http://localhost:5678/"
    }
}

Write-Host ""
Write-Host "ℹ Configurações:" -ForegroundColor Cyan
Write-Host "  - Usuário: $n8nUser"
Write-Host "  - Senha: ********"
Write-Host "  - Webhook URL: $webhookUrl"
Write-Host ""

$confirm = Read-Host "Confirmar e iniciar instalação? (S/n)"
if ($confirm -eq "n" -or $confirm -eq "N") {
    Write-Host "⚠ Instalação cancelada." -ForegroundColor Yellow
    exit 0
}

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  CRIANDO ESTRUTURA DE DIRETÓRIOS" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Criar diretórios
$dirs = @("data", "backups", "logs", "config", "scripts")
foreach ($dir in $dirs) {
    if (-not (Test-Path $dir)) {
        New-Item -ItemType Directory -Path $dir | Out-Null
    }
}

Write-Host "✓ Diretórios criados" -ForegroundColor Green

# Criar arquivo .env
Write-Host ""
Write-Host "Criando arquivo de configuração (.env)..." -ForegroundColor Yellow

$envContent = @"
# Gerado automaticamente em $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
N8N_PORT=5678
N8N_USER=$n8nUser
N8N_PASSWORD=$n8nPasswordPlain
WEBHOOK_URL=$webhookUrl
N8N_HOST=0.0.0.0
N8N_PROTOCOL=http
TIMEZONE=America/Sao_Paulo
EXECUTIONS_MAX_AGE=168
LOG_LEVEL=info
CPU_LIMIT=2
MEMORY_LIMIT=2G
CPU_RESERVE=0.5
MEMORY_RESERVE=512M
"@

$envContent | Out-File -FilePath ".env" -Encoding UTF8

Write-Host "✓ Arquivo .env criado" -ForegroundColor Green

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  BAIXANDO E INICIANDO N8N" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Baixar imagem
Write-Host "ℹ Baixando imagem do n8n (pode demorar alguns minutos)..." -ForegroundColor Cyan
docker-compose pull

Write-Host "✓ Imagem baixada" -ForegroundColor Green

# Iniciar n8n
Write-Host "ℹ Iniciando n8n..." -ForegroundColor Cyan
docker-compose up -d

# Aguardar inicialização
Write-Host "ℹ Aguardando inicialização (30 segundos)..." -ForegroundColor Cyan
Start-Sleep -Seconds 30

# Verificar se está rodando
$status = docker-compose ps
if ($status -match "Up") {
    Write-Host "✓ n8n está rodando!" -ForegroundColor Green
} else {
    Write-Host "✗ n8n não iniciou corretamente" -ForegroundColor Red
    Write-Host "Verifique os logs com: docker-compose logs" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  INSTALAÇÃO CONCLUÍDA! 🎉" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "✓ n8n foi instalado e está rodando!" -ForegroundColor Green
Write-Host ""
Write-Host "📱 ACESSO:" -ForegroundColor Yellow
Write-Host "   Local: http://localhost:5678"
Write-Host "   Rede: $webhookUrl"
Write-Host ""
Write-Host "🔐 LOGIN:" -ForegroundColor Yellow
Write-Host "   Usuário: $n8nUser"
Write-Host "   Senha: (a que você configurou)"
Write-Host ""
Write-Host "📝 PRÓXIMOS PASSOS:" -ForegroundColor Yellow
Write-Host "   1. Acesse o n8n no navegador"
Write-Host "   2. Faça login com as credenciais acima"
Write-Host "   3. Importe os workflows da documentação"
Write-Host "   4. Configure as integrações (Google, Telegram, etc)"
Write-Host ""
Write-Host "🔧 COMANDOS ÚTEIS:" -ForegroundColor Yellow
Write-Host "   Parar:      docker-compose down"
Write-Host "   Reiniciar:  docker-compose restart"
Write-Host "   Logs:       docker-compose logs -f"
Write-Host "   Status:     docker-compose ps"
Write-Host ""
Write-Host "📚 DOCUMENTAÇÃO:" -ForegroundColor Yellow
Write-Host "   Ver: ..\docs\ e ..\README.md"
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan

# Oferecer configurar agendamento
Write-Host ""
$configSchedule = Read-Host "Deseja configurar reinicialização automática agora? (S/n)"
if ($configSchedule -eq "" -or $configSchedule -eq "S" -or $configSchedule -eq "s") {
    Write-Host ""
    Write-Host "Configurando reinicialização automática..." -ForegroundColor Yellow
    
    # Criar script de reinicialização
    $restartScript = @'
$logFile = "$PSScriptRoot\..\logs\restart.log"
$dockerPath = Split-Path -Parent $PSScriptRoot

Add-Content -Path $logFile -Value "=================================="
Add-Content -Path $logFile -Value "Reinicialização Automática"
Add-Content -Path $logFile -Value "Data/Hora: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
Add-Content -Path $logFile -Value "=================================="

Set-Location $dockerPath
docker-compose exec -T n8n n8n export:workflow --all --output=/backups/backup-$(Get-Date -Format 'yyyyMMdd-HHmmss').json 2>&1 | Add-Content -Path $logFile
docker-compose restart n8n 2>&1 | Add-Content -Path $logFile

Start-Sleep -Seconds 30

$status = docker-compose ps
if ($status -match "Up") {
    Add-Content -Path $logFile -Value "✅ n8n reiniciado com sucesso!"
} else {
    Add-Content -Path $logFile -Value "❌ ERRO: n8n não iniciou!"
    docker-compose up -d n8n 2>&1 | Add-Content -Path $logFile
}

Add-Content -Path $logFile -Value ""
'@

    $restartScript | Out-File -FilePath "scripts\restart-scheduler.ps1" -Encoding UTF8
    
    Write-Host ""
    Write-Host "Escolha a frequência de reinicialização:" -ForegroundColor Yellow
    Write-Host "1) A cada 3 dias"
    Write-Host "2) A cada 7 dias (recomendado)"
    Write-Host "3) A cada 14 dias"
    $cronOption = Read-Host "Opção (1-3)"
    
    switch ($cronOption) {
        "1" { $days = 3 }
        "2" { $days = 7 }
        "3" { $days = 14 }
        default { $days = 7 }
    }
    
    Write-Host ""
    Write-Host "Para configurar o Agendador de Tarefas do Windows:" -ForegroundColor Yellow
    Write-Host "1. Abra o 'Agendador de Tarefas' (Task Scheduler)"
    Write-Host "2. Clique em 'Criar Tarefa Básica'"
    Write-Host "3. Nome: 'Reiniciar n8n'"
    Write-Host "4. Gatilho: 'Diariamente' ou 'Semanal'"
    Write-Host "5. Repetir a cada: $days dias"
    Write-Host "6. Hora: 03:00 (3h da manhã)"
    Write-Host "7. Ação: 'Iniciar programa'"
    Write-Host "8. Programa: powershell.exe"
    Write-Host "9. Argumentos: -ExecutionPolicy Bypass -File `"$(Join-Path $PWD 'scripts\restart-scheduler.ps1')`""
    Write-Host ""
    Write-Host "✓ Script de reinicialização criado em: scripts\restart-scheduler.ps1" -ForegroundColor Green
    Write-Host "Configure manualmente no Agendador de Tarefas conforme instruções acima." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "✓ Setup completo! Bom trabalho! 🍮" -ForegroundColor Green
Write-Host ""
Write-Host "Pressione Enter para sair..."
Read-Host
