# SMMP - Sistema de Mapeamento e Monitoramento de Pátios (Mottu)

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf)
![daisyUI](https://img.shields.io/badge/daisyUI-151A32?style=for-the-badge&logo=daisyui)
![Status](https://img.shields.io/badge/status-concluído-brightgreen?style=for-the-badge)
## Alunos - Guilherme Francisco -RM 557648 Larissa de Freitas -Rm555136
## 📖 Sobre o Projeto

O **SMMP** é uma aplicação web completa desenvolvida como solução para o desafio da **Mottu**. O projeto ataca o problema da gestão manual e imprecisa de frotas de motos em pátios, propondo uma solução moderna e escalável para mapear e monitorar os veículos de forma automatizada.

A plataforma permite que operadores de pátio e administradores tenham uma visão clara e em tempo real da disposição das motos, associem dispositivos de rastreamento e gerenciem o status da frota através de uma interface visual intuitiva e responsiva.

---

## 🎥 Vídeo de Demonstração

Assista a uma demonstração completa da aplicação, apresentando as principais funcionalidades, fluxos de usuário e a tecnologia por trás do projeto.

**[[➡️ Link para o Vídeo de Demonstração](https://youtu.be/EfWiuC3ZdVM) (Clique Aqui)]**

---

## ✨ Funcionalidades Principais

* ** autenticação e Autorização:** Sistema de login seguro com Spring Security, com diferentes níveis de acesso para **Administradores** e **Operadores**.
* **🗺️ Dashboard em Tempo Real:** Uma grade visual que representa o pátio e exibe a posição de cada moto, com atualização automática para simular dados de sensores.
* **🎨 Status Visual por Cores:** As motos são coloridas de acordo com seu status (Disponível, Em Manutenção, Alugada), permitindo uma identificação rápida da situação da frota.
* **🏍️ Gestão de Frota (Admin):** Funcionalidades de CRUD (Criar, Ler, Atualizar, Excluir) completas para o cadastro de novas motos.
* **📡 Vinculação de Dispositivos (Operador):** Interface simples para associar um dispositivo de rastreamento a uma moto que chega ao pátio.
* **🎨 Interface Moderna:** Construída com **daisyUI**, um framework baseado em Tailwind CSS que oferece componentes elegantes e um design limpo.
* **🗃️ Versionamento de Banco de Dados:** Utilização do Flyway para gerenciar a evolução do schema do banco de dados de forma automática e segura.

---

## 🛠️ Tecnologias Utilizadas

| Categoria      | Tecnologia                                                                                             |
|----------------|--------------------------------------------------------------------------------------------------------|
| **Backend** | **Java 17**, **Spring Boot 3**, Spring Security, Spring Data JPA, Lombok, Validation                      |
| **Frontend** | **Thymeleaf**, **daisyUI**, Tailwind CSS, JavaScript (Fetch API)                                        |
| **Banco de Dados** | **H2 (In-Memory)** para desenvolvimento, **Flyway** para migrações                               |
| **Build/Dependências** | **Maven** |

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar a aplicação em seu ambiente local.

### Pré-requisitos

* **Java Development Kit (JDK) 17 ou superior.**
* **Apache Maven 3.8 ou superior.**

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd seu-repositorio
    ```

3.  **Execute a aplicação com o Maven Wrapper:**
    * No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    * No Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```

A aplicação será iniciada e estará pronta para uso!

---

## 🔑 Acesso e Credenciais

Após iniciar a aplicação, utilize os seguintes recursos para acessá-la e testá-la.

* **URL da Aplicação:** [**http://localhost:8080**](http://localhost:8080)

* **Console do Banco de Dados H2:**
    * **URL:** [**http://localhost:8080/h2-console**](http://localhost:8080/h2-console)
    * **JDBC URL:** `jdbc:h2:mem:mottudb`
    * **User Name:** `sa`
    * **Password:** `password`

* **Credenciais de Acesso:**

| Perfil      | Usuário      | Senha        |
|-------------|--------------|--------------|
| 👨‍💼 **Admin** | `admin`      | `adminpass`  |
| 👷 **Operador** | `operator`   | `operatorpass`|

---
