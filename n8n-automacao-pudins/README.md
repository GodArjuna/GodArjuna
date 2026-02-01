# Sistema de Automação n8n para Negócio de Pudins 🍮

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![n8n](https://img.shields.io/badge/n8n-automation-EA4B71)](https://n8n.io)
[![Portuguese](https://img.shields.io/badge/Language-Portuguese-green)](README.md)

## 📋 Sobre o Projeto

Este é um sistema completo de automação desenvolvido com **n8n** para facilitar a gestão de um pequeno negócio de venda de pudins sob encomenda.

**🎯 Ideal para:** Pequenos comerciantes, confeiteiros, empreendedores que vendem produtos sob encomenda

**💰 Investimento:** Gratuito (plano free n8n) ou baixo custo

**⏱️ Tempo para configurar:** 30 minutos (básico) a 3 horas (completo)

**📚 Conhecimento necessário:** Básico (se usa WhatsApp e planilhas, você consegue!)

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
├── README.md                          # Este arquivo
├── INICIO-RAPIDO.md                   # Guia rápido para começar em 30min
├── REFERENCIA-RAPIDA.md               # Comandos essenciais
├── CLOUD-VS-SELFHOSTED.md             # Comparação de opções
├── docker/                            # 🆕 Projeto Docker pronto! ⭐
│   ├── README.md                      # Guia do projeto Docker
│   ├── README-INSTALACAO.md           # Instalação passo a passo
│   ├── FORMULARIO-CONFIGURACAO.md     # Preencha suas informações
│   ├── docker-compose.yml             # ✅ Arquivo Docker pronto
│   ├── .env.example                   # ✅ Template de configuração
│   └── scripts/                       # Scripts automatizados
│       ├── setup.sh                   # ✅ Instalador Linux/Mac
│       ├── setup.ps1                  # ✅ Instalador Windows
│       ├── backup.sh/ps1              # ✅ Backup automático
│       └── health-check.sh            # ✅ Verificação de saúde
├── docs/                              # Documentação completa
│   ├── 01-introducao-n8n.md          # Introdução ao n8n
│   ├── 02-instalacao-configuracao.md # Setup inicial (Cloud)
│   ├── 03-boas-praticas.md           # Melhores práticas
│   ├── 04-solucao-problemas.md       # Troubleshooting
│   ├── 05-servidor-notebook.md       # Self-hosting em notebook
│   └── 06-recomendacoes-especialista.md # Dicas avançadas
├── workflows/                         # Workflows n8n prontos
│   ├── README-vendas.md              # 💰 Automação de vendas
│   ├── README-estoque.md             # 📦 Gestão de estoque
│   ├── README-precos.md              # 🏪 Monitor de preços
│   └── README-marketing.md           # 🤝 CRM e marketing
└── examples/                          # Exemplos e templates
    ├── planilhas-templates/          # Templates Google Sheets
    └── mensagens-templates/          # Templates de mensagens

```

## 🚀 Início Rápido

**Quer começar AGORA?** 

### 🎁 NOVO: Projeto Docker Completo e Pronto!

👉 **[Pasta docker/](docker/)** - Tudo pronto para instalar em 10 minutos!
- ✅ docker-compose.yml configurado
- ✅ Scripts de instalação automática (Windows/Linux/Mac)
- ✅ Backups automatizados
- ✅ Reinicialização agendada
- ✅ Formulário de configuração guiado

**Execute e pronto:**
```bash
cd docker/
./scripts/setup.sh    # Linux/Mac
# ou
.\scripts\setup.ps1   # Windows
```

---

### 🤔 Não Sabe Qual Escolher?
👉 **[Cloud vs Self-Hosted - Comparação Completa](CLOUD-VS-SELFHOSTED.md)** - Descubra qual é melhor para você!

### 🌐 Opção 1: Cloud (Recomendado para Iniciantes)
👉 **[Guia de Início Rápido](INICIO-RAPIDO.md)** - Comece em 30 minutos usando n8n Cloud gratuito!
- ✅ Setup em 5 minutos
- ✅ Sem manutenção
- ✅ 5.000 execuções/mês grátis

### 🖥️ Opção 2: Self-Hosted (100% Gratuito e Ilimitado)
👉 **[Servidor em Notebook](docs/05-servidor-notebook.md)** - Transforme seu notebook em servidor n8n com reinicialização automática!
- ✅ Execuções ilimitadas
- ✅ Controle total
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

