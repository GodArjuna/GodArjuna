# ============================================
# Script de Instalação Automática - n8n Windows
# ============================================

$ErrorActionPreference = "Stop"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  INSTALAÇÃO AUTOMÁTICA N8N - WINDOWS      " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Pasta de destino
$destinationPath = "C:\Users\kawan\Desktop\n8n"

# Verificar se Docker está rodando
Write-Host "✓ Verificando Docker Desktop..." -ForegroundColor Yellow
try {
    docker --version | Out-Null
    Write-Host "✓ Docker encontrado!" -ForegroundColor Green
} catch {
    Write-Host "✗ ERRO: Docker não encontrado!" -ForegroundColor Red
    Write-Host "  Por favor, instale o Docker Desktop primeiro:" -ForegroundColor Yellow
    Write-Host "  https://www.docker.com/products/docker-desktop" -ForegroundColor Yellow
    exit 1
}

# Criar pasta de destino
Write-Host ""
Write-Host "✓ Criando pasta: $destinationPath" -ForegroundColor Yellow
if (Test-Path $destinationPath) {
    Write-Host "  Pasta já existe. Deseja sobrescrever? (S/N)" -ForegroundColor Yellow
    $resposta = Read-Host
    if ($resposta -ne "S" -and $resposta -ne "s") {
        Write-Host "✗ Instalação cancelada." -ForegroundColor Red
        exit 1
    }
    Remove-Item -Path $destinationPath -Recurse -Force
}
New-Item -ItemType Directory -Path $destinationPath -Force | Out-Null
Write-Host "✓ Pasta criada!" -ForegroundColor Green

# Baixar projeto
Write-Host ""
Write-Host "✓ Baixando projeto do GitHub..." -ForegroundColor Yellow
$zipUrl = "https://github.com/GodArjuna/GodArjuna/archive/refs/heads/main.zip"
$zipPath = "$env:TEMP\n8n-project.zip"

try {
    # Download do ZIP
    Invoke-WebRequest -Uri $zipUrl -OutFile $zipPath -UseBasicParsing
    Write-Host "✓ Download concluído!" -ForegroundColor Green
    
    # Extrair ZIP
    Write-Host "✓ Extraindo arquivos..." -ForegroundColor Yellow
    Expand-Archive -Path $zipPath -DestinationPath "$env:TEMP\n8n-extract" -Force
    
    # Copiar pasta n8n-automacao-pudins para destino
    $sourcePath = "$env:TEMP\n8n-extract\GodArjuna-main\n8n-automacao-pudins"
    if (Test-Path $sourcePath) {
        Copy-Item -Path "$sourcePath\*" -Destination $destinationPath -Recurse -Force
        Write-Host "✓ Arquivos copiados!" -ForegroundColor Green
    } else {
        Write-Host "✗ ERRO: Estrutura do projeto não encontrada!" -ForegroundColor Red
        exit 1
    }
    
    # Limpar arquivos temporários
    Remove-Item -Path $zipPath -Force
    Remove-Item -Path "$env:TEMP\n8n-extract" -Recurse -Force
    
} catch {
    Write-Host "✗ ERRO ao baixar projeto: $_" -ForegroundColor Red
    exit 1
}

# Verificar estrutura
Write-Host ""
Write-Host "✓ Verificando estrutura..." -ForegroundColor Yellow
if (Test-Path "$destinationPath\docker\scripts\setup.ps1") {
    Write-Host "✓ Estrutura OK!" -ForegroundColor Green
} else {
    Write-Host "✗ ERRO: Estrutura incompleta!" -ForegroundColor Red
    exit 1
}

# Executar setup
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  CONFIGURANDO N8N                          " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

Set-Location "$destinationPath\docker"

# Executar o setup.ps1
Write-Host "✓ Iniciando configuração do n8n..." -ForegroundColor Yellow
Write-Host ""

& "$destinationPath\docker\scripts\setup.ps1"

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  INSTALAÇÃO FINALIZADA! 🎉                " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "📁 Projeto instalado em: $destinationPath" -ForegroundColor Green
Write-Host "🌐 Acesse: http://localhost:5678" -ForegroundColor Green
Write-Host ""
Write-Host "Pressione qualquer tecla para abrir a pasta..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
explorer $destinationPath
