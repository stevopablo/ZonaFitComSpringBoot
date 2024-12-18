# ZonaFitComSpringBoot

# Zona Fit - Gerenciamento de Clientes

Um sistema simples para gerenciar informações de clientes, incluindo nome, sobrenome e plano de assinatura (membresia). Este projeto utiliza **Java Swing** para a interface gráfica e integrações com o **Spring Framework** para gerenciar os serviços.

## 📋 Funcionalidades

- Adicionar novos clientes.
- Listar todos os clientes.
- Editar informações de clientes existentes.
- Excluir clientes.
- Interface gráfica interativa para gerenciamento.

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Swing** (para a interface gráfica)
- **Spring Framework** (para injeção de dependências e lógica de serviço)
- **Maven** (para gerenciamento de dependências)

## 📂 Estrutura do Projeto

```plaintext
src/
├── gm/
│   ├── zona_fit/
│   │   ├── gui/              # Código da interface gráfica
│   │   │   ├── ZonaFitForma.java
│   │   ├── modelo/           # Classe representando o modelo de cliente
│   │   │   ├── Cliente.java
│   │   ├── servico/          # Serviços e lógica de negócios
│   │   │   ├── ClienteServico.java
│   │   │   ├── IClienteServicos.java
pom.xml                       # Arquivo de configuração do Maven
