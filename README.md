# API Todolist

![NPM](https://img.shields.io/npm/l/react)

API REST desenvolvida usando **Java, Java Spring, PostgreSQL, Maven, Flyway Migrations, Springdoc e Testes com JUnit e Mockito.**

Esta API simula uma Lista de Tarefas oferecendo funcionalidades como criar, atualizar, pesquisar e excluir tarefas. Além disso, permite buscar as tarefas por título, categoria e prazo.

## Instalação

Pré-requisito: Java 17

1. Clone o repositório:

```bash
git clone https://github.com/isinhah/todolist.git
```

2. Instale as dependências com Maven

3. Instale o [PostgreSQL](https://www.postgresql.org/)

## Execução

1. Inicie a aplicação com Maven no Terminal

```bash
mvn spring-boot:run
```

2. A API será acessível em http://localhost:8080
3. Documentação do Springdoc em http://localhost:8080/swagger-ui/index.html

## Endpoints

A API possui os seguintes Endpoints:

```markdown
GET /tasks - Retorna todas as tarefas.

GET /tasks/list - Retorna uma lista paginada de todas as tarefas.

PUT /tasks - Atualiza uma tarefa existente.

POST /tasks - Cria uma nova tarefa.

GET /tasks/{id} - Retorna uma tarefa pelo ID.

DELETE /tasks/{id} - Exclui uma tarefa pelo ID.

GET /tasks/find/by-title - Retorna tarefas pelo título.

GET /tasks/find/by-category - Retorna tarefas pela categoria.

GET /tasks/find/by-deadline - Retorna tarefas pelo prazo.
```

## Autor

Isabel Henrique

https://www.linkedin.com/in/isabel-henrique/