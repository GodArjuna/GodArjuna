# Sistema de Automação n8n Self-Hosted para Negócio de Pudins 🍮

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![n8n](https://img.shields.io/badge/n8n-self--hosted-EA4B71)](https://n8n.io)
[![Windows](https://img.shields.io/badge/Windows-Ready-0078D6)](README-WINDOWS.md)
[![Portuguese](https://img.shields.io/badge/Language-Portuguese-green)](README.md)

## 📋 Sobre o Projeto

Sistema completo de automação usando **n8n self-hosted (GRATUITO)** para gestão de pequenos negócios de venda de pudins sob encomenda.

**🎯 Ideal para:** Pequenos comerciantes, confeiteiros, empreendedores que vendem produtos sob encomenda

**💰 Investimento:** R$ 0,00 (100% GRATUITO - Self-Hosted no seu computador)

**⏱️ Tempo para configurar:** 15 minutos (instalação automática)

**📚 Conhecimento necessário:** Básico (usar Windows e seguir instruções)

**🪟 Foco:** Windows 10/11 com Docker Desktop

---

## 🚀 INÍCIO RÁPIDO - WINDOWS

### 👉 **[WINDOWS-SETUP.md](WINDOWS-SETUP.md)** - Guia Completo Windows + VSCode

**Instalação em 3 passos:**
1. Instale Docker Desktop
2. Execute `.\scripts\setup.ps1`
3. Acesse http://localhost:5678

**100% GRATUITO • EXECUÇÕES ILIMITADAS • SEM CUSTOS MENSAIS**

---

## ✨ Por Que Self-Hosted?

### ❌ n8n Cloud (NÃO recomendado - PAGO)
- Limite de 5.000 execuções/mês
- Após limite: $20-50/mês (R$ 100-250)
- Custos crescentes conforme uso

### ✅ n8n Self-Hosted (ESTE PROJETO - GRATUITO)
- **Execuções ILIMITADAS** 
- **R$ 0,00 de mensalidade**
- Roda no seu computador Windows
- Controle total dos dados
- Funciona offline

**Economia: R$ 1.680/ano!** 💰

## 🎯 Funcionalidades

### 1. 💰 Automação de Vendas
- Recebimento de pedidos via formulários online
- Registro automático em planilhas
- Notificações via WhatsApp/Telegram/Email
- Controle de status dos pedidos
- Integração com meios de pagamento

### 2. 📦 Gestão de Estoque
- Controle automático de ingredientes
- Alertas de estoque baixo
- Relatórios semanais de consumo
- Atualização automática após vendas

### 3. 🏪 Monitoramento de Promoções
- Acompanhamento de preços em supermercados
- Relatórios comparativos semanais
- Notificações de promoções relevantes

### 4. 🤝 Relacionamento com Clientes
- Pesquisas de satisfação automáticas
- Publicação automática em redes sociais
- Marketing personalizado

## 📁 Estrutura do Projeto

```
n8n-automacao-pudins/
├── 📄 README.md (este arquivo)
├── 📄 WINDOWS-SETUP.md ⭐ GUIA WINDOWS + VSCODE
├── 📄 README-WINDOWS.md (resumo Windows)
├── 📄 PROJETO-PRONTO.md (informações necessárias)
│
├── 📁 docker/ 🐳 PROJETO DOCKER PRONTO
│   ├── docker-compose.yml ✅
│   ├── .env.example ✅
│   └── scripts/
│       ├── setup.ps1 ⭐ EXECUTE ESTE (Windows)
│       ├── backup.ps1 (backup Windows)
│       └── health-check.sh
│
├── 📁 docs/ (documentação detalhada)
│   ├── 01-introducao-n8n.md
│   ├── 02-instalacao-configuracao.md
│   ├── 03-boas-praticas.md
│   ├── 04-solucao-problemas.md
│   ├── 05-servidor-notebook.md
│   └── 06-recomendacoes-especialista.md
│
├── 📁 workflows/ (automações prontas)
│   ├── README-vendas.md 💰
│   ├── README-estoque.md 📦
│   ├── README-precos.md 🏪
│   └── README-marketing.md 🤝
│
└── 📁 examples/ (templates)
    ├── planilhas-templates/
    └── mensagens-templates/

```

## 🚀 Início Rápido - Windows

### 🪟 **[WINDOWS-SETUP.md](WINDOWS-SETUP.md)** ⭐ COMECE AQUI

**Guia completo Windows com VSCode:**
- Como baixar e instalar
- Como abrir no VSCode  
- Terminal integrado
- Comandos úteis no Windows
- Dicas de produtividade

### ⚡ Instalação Ultra-Rápida (3 passos)

```powershell
# 1. Baixe o projeto (Git ou ZIP)
git clone https://github.com/GodArjuna/GodArjuna.git
cd GodArjuna\n8n-automacao-pudins\docker

# 2. Execute o instalador Windows
.\scripts\setup.ps1

# 3. Acesse no navegador
# http://localhost:5678
```

**Tempo:** 15 minutos  
**Custo:** R$ 0,00  
**Limite:** NENHUM!

---

## 💻 Como Abrir no VSCode

### Método Rápido
```powershell
cd C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins
code .
```

### Pelo VSCode
```
File → Open Folder → Selecione a pasta n8n-automacao-pudins
```

**Guia completo:** [WINDOWS-SETUP.md](WINDOWS-SETUP.md)

---
- ✅ R$ 0,00 de custo

Ou siga o caminho completo:

1. **[Introdução ao n8n](docs/01-introducao-n8n.md)** - Entenda o que é e como funciona
2. **Escolha:** [Cloud](docs/02-instalacao-configuracao.md) ou [Self-hosted](docs/05-servidor-notebook.md) ([Compare](CLOUD-VS-SELFHOSTED.md))
3. **[Escolha seus Workflows](workflows/)** - Comece com o que você precisa
4. **[Boas Práticas](docs/03-boas-praticas.md)** - Mantenha tudo organizado
5. **[Recomendações de Especialista](docs/06-recomendacoes-especialista.md)** - Otimize seu sistema

---

## 📚 Documentação

### 📖 Guias Principais

- **[🚀 Início Rápido](INICIO-RAPIDO.md)** - Comece em 30 minutos
- **[📘 Introdução ao n8n](docs/01-introducao-n8n.md)** - O que é n8n e como funciona

**Instalação:**
- **[☁️ n8n Cloud](docs/02-instalacao-configuracao.md)** - Setup em cloud (gratuito com limites)
- **[🖥️ Self-Hosted](docs/05-servidor-notebook.md)** - Servidor em notebook (100% gratuito e ilimitado)

**Manutenção:**
- **[✨ Boas Práticas](docs/03-boas-praticas.md)** - Dicas para manter suas automações organizadas
- **[🔍 Solução de Problemas](docs/04-solucao-problemas.md)** - Troubleshooting comum
- **[🎓 Recomendações de Especialista](docs/06-recomendacoes-especialista.md)** - Otimizações avançadas

### 🤖 Workflows Disponíveis

1. **[💰 Automação de Vendas](workflows/README-vendas.md)**
   - Recebimento automático de pedidos
   - Notificações para cliente e comerciante
   - Controle de status e pagamentos
   - Integração com Google Forms e WhatsApp/Telegram

2. **[📦 Gestão de Estoque](workflows/README-estoque.md)**
   - Atualização automática após vendas
   - Alertas de estoque baixo
   - Relatórios semanais de consumo
   - Histórico de movimentações

3. **[🏪 Monitoramento de Preços](workflows/README-precos.md)**
   - Comparação de preços entre supermercados
   - Alertas de promoções relevantes
   - Relatórios comparativos semanais
   - Sugestões de economia

4. **[🤝 Marketing e CRM](workflows/README-marketing.md)**
   - Pesquisas de satisfação automáticas
   - Campanhas de marketing segmentadas
   - Publicação em redes sociais
   - Programa de fidelidade

### 📋 Templates e Exemplos

- **[Planilhas Google Sheets](examples/planilhas-templates/README.md)** - Estruturas prontas
- **[Mensagens](examples/mensagens-templates/README.md)** - Templates de comunicação

## 🔧 Pré-requisitos

### Obrigatório (Gratuito)
- ✅ Conta no [n8n.cloud](https://n8n.cloud) (plano gratuito)
- ✅ Conta Google (para Sheets e Forms)
- ✅ Número de telefone para WhatsApp ou conta Telegram

### Opcional (Recomendado)
- 📱 WhatsApp Business API ou Evolution API
- 💳 Conta Mercado Pago ou PagSeguro (para pagamentos)
- 📧 Email profissional (Gmail funciona)

### Conhecimentos Necessários
- Usar WhatsApp e aplicativos de celular ✅
- Usar Google Planilhas básico ✅
- Seguir tutoriais passo a passo ✅
- **Não precisa programar!** 🎉

## 💡 Suporte e Contribuições

### 🆘 Precisa de Ajuda?

1. **Documentação:** Leia o [guia completo](docs/)
2. **Problemas Comuns:** Veja [solução de problemas](docs/04-solucao-problemas.md)
3. **Comunidade n8n:** [Forum oficial](https://community.n8n.io)
4. **Vídeos:** Busque tutoriais no YouTube

### 🤝 Como Contribuir

Este projeto é de código aberto! Contribuições são bem-vindas:

- 📝 Melhorar documentação
- 🐛 Reportar bugs
- 💡 Sugerir melhorias
- 🌟 Compartilhar sua experiência

### 💬 Feedback

Usou este sistema? Conte sua experiência! Isso ajuda outros empreendedores.

## ⚖️ Licença

Este projeto está sob a licença MIT - veja detalhes em [LICENSE](LICENSE).

Livre para usar, modificar e distribuir para fins comerciais ou pessoais.

## 👨‍💻 Autor

Desenvolvido com ❤️ para ajudar pequenos empreendedores a automatizar seus negócios e focar no que realmente importa: fazer produtos incríveis!

---

## 🌟 Agradecimentos

- Comunidade n8n pela ferramenta incrível
- Pequenos empreendedores que inspiraram este projeto
- Todos que contribuíram com feedback e melhorias

---

## 📞 Contato

Dúvidas ou sugestões? Entre em contato através do [GitHub Issues](../../issues)

---

**⭐ Se este projeto te ajudou, considere dar uma estrela no GitHub! Isso motiva a continuar melhorando. ⭐**

---

**Desenvolvido com ❤️ para pequenos empreendedores** | [⬆ Voltar ao topo](#sistema-de-automação-n8n-para-negócio-de-pudins-)

