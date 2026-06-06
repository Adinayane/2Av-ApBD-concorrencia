Instituto Federal de Educação, Ciência e Tecnologia do Pará - Campus Belém

Discente: Adinayane Souza

### Atividade Avaliativa para a disciplina de Aplicação de Banco de Dados - 06/06/2026 ###

Sobre o projeto:

- Aplicação em Spring Boot para teste prático do conceito de concorrência em banco de dados.

Especificações:
 - Java 17
 - Spring Boot v.3.5.14
 - H2 Database v.2.3.232

Ferramentas usadas:
 - Apache JMeter v.5.6.3

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

<img width="1030" height="289" alt="{FBB9AA57-7BC0-463A-9CB5-3B0BFE729B83}" src="https://github.com/user-attachments/assets/43671f42-5ce3-4d88-8cab-f700a7e4c8d4" />

Para testar o cenário de concorrência com lock otimista, foi criada uma segunda tabela com as mesmas características da primeira, com a adição da coluna "version" e então foi inicializada igualmente a tabela anterior: Conta 1 com saldo inicial de R$1000.00 e a coluna version iniciou em "0". A tabela foi submetida aos testes via JMeter utilizando as mesmas configurações do cenário anterior. Após as requisições realizadas (14 requisições: 7 depósitos e 7 saques), constatou o saldo de R$1100,00. No entanto, o destaque vai para as falhas de requisições no JMeter (11 foram rejeitadas) e para a version, que alterou de 0 para 3 (a mesma quantidade de requisições aceitas). Em termos matemáticos, o cálculo é condizente com o formato das requisições (sendo 1 saque e 2 depósitos). É notório o efeito do lock otimista nesta situação.

<img width="1035" height="300" alt="{24EBE515-08A7-455D-B271-58C67264BB1B}" src="https://github.com/user-attachments/assets/d374eb3d-2922-425f-aadd-d69625f2a621" />

Diante dos resultados apresentados, conclui-se que a ausência de mecanismos de controle em ambientes altamente concorrentes gera falhas críticas de integridade e perdas invisíveis de dados. A implementação do bloqueio otimista via anotação @Version mostrou-se uma solução extremamente eficaz e de baixo custo computacional para o cenário testado. O mecanismo garantiu que apenas transações baseadas em estados válidos e atualizados fossem consolidadas, rejeitando com segurança os acessos simultâneos destrutivos e assegurando a total consistência matemática e financeira do banco de dados.
