# MotoScan - Sistema de Mapeamento e Monitoramento de Pátios (Mottu)

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql)
![DaisyUI](https://img.shields.io/badge/daisyUI-151A32?style=for-the-badge&logo=daisyui)
![Status](https://img.shields.io/badge/status-concluído-brightgreen?style=for-the-badge)

## Alunos
* **Guilherme Francisco** - RM 557648
* **Larissa de Freitas** - RM 555136

---

## 📖 Sobre o Projeto

O **MotoScan** é uma aplicação web completa desenvolvida com Spring Boot para resolver o desafio da Mottu de gerenciar frotas de motos em pátios. A solução centraliza o monitoramento em **zonas inteligentes** (Livre, Manutenção, B.O.), proporcionando clareza operacional em tempo real.

A plataforma cumpre todos os requisitos técnicos de forma coesa e escalável, pronta para ser implementada nas mais de 100 filiais.

---

## 🚀 Aplicação Online

A aplicação está hospedada na plataforma Render e conectada a um banco de dados PostgreSQL, garantindo a persistência dos dados.

**Link de Acesso (Online):** [**[https://mottu-webapp-java.onrender.com/](https://mottu-webapp-java.onrender.com)**]
---

## 🎥 Vídeo de Demonstração

Assista à demonstração completa que cobre todos os requisitos e integrações multidisciplinares.

**Link do Vídeo:** [**[Link do YouTube](https://youtu.be/HChoPhokYgE)**]

---

## ✨ Funcionalidades Principais

* **Dashboard com Zonas Inteligentes:** Visualização em tempo real das motos posicionadas e coloridas nas suas zonas corretas (Livre, Manutenção, B.O.).
* **Controle de Acesso (Spring Security):** Proteção de rotas e diferenciação de permissões entre **Admin** e **Operador**.
* **Gestão Automatizada de Ativos:** Ao cadastrar uma nova moto, um `TrackingDevice` com um UUID aleatório é gerado e associado automaticamente.
* **Fluxo de Troca de Dispositivo:** O Operador pode realizar a **Troca / Manutenção** de um dispositivo quebrado por um novo do estoque, sem perder o registro da moto.
* **CRUD de Frotas e Dispositivos:** Administração completa de motos, incluindo validação de placas duplicadas, e gestão de estoque de dispositivos.

---

## 🛠️ Tecnologias e Conformidade

| Categoria | Tecnologia / Requisito | Status |
| :--- | :--- | :--- |
| **Backend** | Java 17, Spring Boot, Spring Security | ✅ **Completo** |
| **Banco de Dados** | **PostgreSQL (Prod)**, **Flyway** (V1 a V5) | ✅ **Conforme** |
| **Frontend** | **Thymeleaf**, **daisyUI** (Tema `emerald`), Fragmentos | ✅ **Completo** |
| **Fluxos Avançados** | Troca de Dispositivo, Posicionamento Agendado (`@Scheduled`) | ✅ **Completo** |

---

## 🔑 Acesso e Credenciais

* **URL da Aplicação:** [**https://mottu-webapp-java.onrender.com/**](https://mottu-webapp-java.onrender.com/)

* **Credenciais de Acesso:**

| Perfil | Usuário | Senha |
| :--- | :--- | :--- |
| 👨‍💼 **Admin** | `admin` | `adminpass` |
| 👷 **Operador** | `operator` | `operatorpass` |

---
