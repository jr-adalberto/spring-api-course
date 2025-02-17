# 🚀 API de Cadastro de Usuários

Bem-vindo à API de Cadastro de Usuários! Este projeto é uma aplicação Spring Boot que permite o cadastro, autenticação e gerenciamento de usuários, utilizando JWT para segurança, deploy no Heroku e AWS, e integração com S3 para upload de arquivos.

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Deploy no Heroku](#-deploy-no-heroku)
- [Upload de Arquivos para o S3](#-upload-de-arquivos-para-o-s3)
- [CI/CD](#-cicd)
- [Melhorias Futuras](#-melhorias-futuras)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

---

## 🌟 Visão Geral

Esta API foi desenvolvida como parte de um curso de Spring Boot. Ela permite o cadastro de usuários, autenticação via JWT (JSON Web Token), gerenciamento de perfis de usuários, e upload de arquivos para o Amazon S3. O projeto segue boas práticas de desenvolvimento, como o padrão MVC, tratamento de erros personalizado, deploy no Heroku e AWS, e integração com CI/CD.

---

## 🛠 Funcionalidades

- **Cadastro de Usuários**: Criação de novos usuários com email, senha e perfil.
- **Autenticação JWT**: Geração de tokens JWT para autenticação segura.
- **Gerenciamento de Perfis**: Atualização de informações do usuário e atribuição de roles (ex: ADMIN, USER).
- **Tratamento de Erros**: Mensagens de erro personalizadas para diferentes cenários.
- **Upload de Arquivos para o S3**: Upload de arquivos (ex: imagens de perfil) para o Amazon S3.
- **Testes com Postman**: Coleção de endpoints para testes via Postman.
- **Deploy no Heroku e AWS**: Configuração para deploy em ambientes cloud.
- **CI/CD**: Integração contínua e entrega contínua com GitHub Actions.

---

## 🚀 Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot
- **Autenticação**: JWT (JSON Web Token)
- **Banco de Dados**: MySQL (gerenciado via MySQL Workbench)
- **Armazenamento de Arquivos**: Amazon S3
- **Deploy**: Heroku e AWS (Elastic Beanstalk, EC2, etc.)
- **CI/CD**: GitHub Actions
- **Testes**: Postman
- **Outras Bibliotecas**:
  - Spring Data JPA
  - Spring Security
  - Lombok
  - Hibernate Validator
  - AWS SDK for Java (para integração com S3)

---

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- Java 17
- Maven
- MySQL (banco de dados)
- MySQL Workbench (ferramenta de gerenciamento do banco de dados)
- Postman (para testes)
- Conta na AWS (para S3 e deploy)
- Conta no Heroku (para deploy)
- GitHub (para CI/CD)

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/jr-adalberto/spring-api-course.git
