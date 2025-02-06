🌱 LifeGreen - Aplicação Mobile para Venda de Hortaliças

LifeGreen é uma aplicação mobile desenvolvida para facilitar a compra de hortaliças, consumindo uma API própria construída em Java.
********************************************************************************************************************************************************************************************************************
🚀 Tecnologias Utilizadas

Backend - API em Java

A API foi desenvolvida utilizando as seguintes tecnologias:

Spring Boot - Framework principal

DevTools - Facilita o desenvolvimento

Lombok - Reduz a verbosidade do código

SQL Server - Banco de dados relacional

JDBC - Para manipulação direta do banco de dados

Funcionalidades da API

✅ Busca de dados do cliente pelo CPF (Nome, CPF, Telefone, Email, Senha, Endereço completo)
✅ Cadastro de novos clientes
✅ Atualização de dados do cliente
✅ Exclusão de conta
✅ Listagem de produtos (Endpoint específico no ProdutoController)
✅ Integração da API de CEP dentro da aplicação para maior segurança e responsividade
✅ Tentativa de recuperação de senha via Mailgun (não concluída devido a problemas com o domínio do site)

Frontend - Aplicação Android

A aplicação foi desenvolvida no Android Studio e conta com as seguintes telas:

Tela de Login → Permite login via CPF e senha

Cadastro de Usuário → Formulário para criação de conta

Recuperação de Senha → Atualmente indisponível

Tela Inicial → Exibe produtos disponíveis

Perfil do Usuário → Exibição e edição dos dados pessoais

Carrinho de Compras → Listagem dos produtos adicionados com total da compra

Revisão do Pedido → Confirmação dos produtos antes da finalização da compra

Finalização da Compra → Conclui o pedido e retorna à tela inicial
********************************************************************************************************************************************************************************************************************
📌 Desafios e Aprendizados

Desenvolvimento no Android Studio

Criar telas responsivas foi um grande desafio devido à pouca experiência prévia

Ajustar os elementos para diferentes tamanhos de tela demandou muito tempo

Construção da API em Java

Primeira API desenvolvida → Exigiu muito aprendizado

Utilização de JDBC ao invés de JPA → Demandou escrever consultas SQL manualmente, aumentando a complexidade

Resolução de bugs e lógica quebrada foi desafiador, mas proporcionou grande aprendizado

📸 Screenshots do Projeto

Tela Login:

![Captura de tela 2024-12-06 165930](https://github.com/user-attachments/assets/ebff57b2-318d-40cc-9f8d-2111537a2f0d)    

Tela home:

![Captura de tela 2025-02-06 163501](https://github.com/user-attachments/assets/ffdc3717-80a4-4fb1-95be-06ff6e27905b)

Tela com os dados do Usuário: 

![Captura de tela 2025-02-06 163801](https://github.com/user-attachments/assets/655e4a7b-3ccb-409a-b132-397714d31b6d)    

Tela do carrinho:

![Captura de tela 2024-11-28 163025](https://github.com/user-attachments/assets/0424191d-66a3-4f62-9f47-412ae00c330c)

Tela de resumo do pedido:

![Captura de tela 2025-02-06 163840](https://github.com/user-attachments/assets/54d9006b-9af9-4455-b339-c21a19078c55)













********************************************************************************************************************************************************************************************************************
🛠️ Como Executar o Projeto

Backend (API Java)

Clone o repositório:

git clonehttps://github.com/Cleitonviana/lifegreenMobile

Configure o banco de dados no application.properties

spring.datasource.url=jdbc:sqlserver://localhost;databaseName=lifegreen
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

Execute o projeto:

mvn spring-boot:run

Frontend (Android Studio)

Importe o projeto no Android Studio

Configure a conexão com a API

Execute o aplicativo em um emulador ou dispositivo físico
********************************************************************************************************************************************************************************************************************
🤝 Conclusão

Este projeto representou um grande desafio técnico, mas proporcionou uma enorme evolução no desenvolvimento tanto para APIs em Java quanto para aplicações móveis no Android Studio. Hoje, a aplicação está funcionando corretamente e pronta para ser expandida no futuro.
********************************************************************************************************************************************************************************************************************
📧 Contato: cleiton_inacio@outlook.com | LinkedIn: www.linkedin.com/in/cleitoninacio
