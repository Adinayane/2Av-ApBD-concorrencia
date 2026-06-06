Instituto Federal de Educação, Ciência e Tecnologia do Pará - Campus Belém
Discente: Adinayane Souza

Atividade Avaliativa para a disciplina de Aplicação de Banco de Dados - 06/06/2026

Sobre o projeto:
 Aplicação em Spring Boot para teste prático do conceito de concorrência em banco de dados.

Especificações:
 - Java 17
 - Spring Boot versão 3.5.14
 - H2 Database versão 2.3.232

Ferramentas usadas:
 - Apache JMeter versão 5.6.3

Conteúdo essencial:
  - Pasta src com as classes e interfaces da aplicação
  - arquivo pom.xml
  - cenários de teste JMeter

Como rodar a aplicação:
 1. Baixe o conteúdo deste repositório e importe para a IDE de sua escolha.
 2. Localize a classe ConcorrenciaApplication.java e rode-a pela opção "Run As" -> "Java Application"
 3. A aplicação estará ativa em http://localhost:8080.

Rotas:
 As requisições devem ser feitas via URL:
   -  /h2-console - abre a interface gráfica do banco H2 Database
   -  /contas - ponto de partida da versão sem controle de concorrência.
   -  /contas-versionadas - ponto de partida versão com controle de concorrência.
   -  (Método POST) /inicializar - Cria a conta 1 com saldo de R$1000.00.
   -  (Método POST) /id/deposito?valor=xxxx.xx - Operação para depositar xxxx.xx na conta id.
   -  (Método POST) /id/saque?valor=xxxx.xx - Operação para sacar xxxx.xx da conta id.

Para testar com Apache JMeter:
 1. Obtenha o Apache JMeter e, após a executá-lo, vá na opção "Abrir", localize e clique no cenário.
 2. Para rodar o cenário, aperte no triângulo verde (Play).

######### RELATÓRIO DE CONCLUSÃO #########

O teste simulou as requisições simultâneas de saques e depósitos de R$100.00 na Conta 1 das tabelas.
(7 usuários simultâneos em loop).
<img width="422" height="232" alt="Config_usuarios" src="https://github.com/user-attachments/assets/186298b5-dbd9-4f25-80d6-1c1390195426" />

Inicialmente, foi testado o cenário de concorrência sem bloqueios. A Conta 1 possuia saldo inicial de R$1000.00 e após o fim de do teste, o saldo alterou para R$1100.00, onde todas as requisições solicitadas (14 no total, sendo 7 depósitos e 7 saques) foram aceitas pelo banco. Matematicamente, o saldo final deveria ser R$1000.00, uma vez que a quantidade de depósitos e saques anulam-se entre si. Devido a diferença de R$100,00, fica claro que a condição de corrida (race condition) causou a perda de atualizações (lost updates). Como várias requisições leram o saldo original ao mesmo tempo, uma thread atropelou a outra e sobrescreveu o valor final, corrompendo a integridade dos dados no banco.
<img width="306" height="353" alt="parte1_BD_comeco" src="https://github.com/user-attachments/assets/e7125537-6f26-4796-b344-c455d966fa33" /> <img width="708" height="555" alt="parte1_requisicoes" src="https://github.com/user-attachments/assets/91103164-3ac1-427c-accb-9d4493c3938a" /> <img width="397" height="334" alt="parte1_BD_posTestes" src="https://github.com/user-attachments/assets/bb0d0c5f-d7f4-48b4-aa9f-52d5979622ff" />



