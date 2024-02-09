# LibraryManager

Sistema de gerenciamento para bibliotecas. Atividade avaliativa da disciplina de MI- Programação, no curso de Engenharia de Computação da Universidade Estadual de Feira de Santana (UEFS).

## Conhecimentos adquiridos:

Diagramação UML para sistemas
Orientação a Objetos
Padrão de Projeto DAO (Data Access Object)
Demais padrões de projeto (devido ao uso de Dependency Inversions do SOLID, Singletons do CEC, etc)
Padrão arquitetural MVC
Persistência de dados em Java

## Requisitos do sistema

✨ Registro de Livros: O sistema deve permitir o registro de novos livros no sistema, incluindo informações como título, autor, editora, ISBN, ano de publicação e categoria.

✨ Pesquisa de Livros: Os usuários devem ser capazes de pesquisar livros por título, autor, ISBN ou categoria, a fim de encontrar informações sobre disponibilidade, localização e outras informações relevantes.

✨ Empréstimo e Devolução: O sistema deve permitir o registro de empréstimos de livros para os usuários da biblioteca. Isso inclui a possibilidade de registrar a data de empréstimo, a data de devolução esperada e a identificação do usuário que realizou o empréstimo. Além disso, o sistema deve permitir o registro da devolução dos livros e a atualização da disponibilidade do livro.

✨ Reserva de Livros: Os usuários devem ter a opção de reservar livros que estejam emprestados por outros usuários. O sistema deve registrar a reserva por ordem de solicitação.

✨ Renovação de Empréstimos: O sistema deve permitir a renovação dos empréstimos de livros, desde que não haja outras reservas para o mesmo livro e o limite de renovações não tenha sido atingido.

✨ Controle de Usuários: O sistema deve permitir o cadastro de novos usuários, com informações como nome, endereço, telefone e número de identificação. Além disso, deve ser possível bloquear uma conta, não permitindo que o usuário faça empréstimos e renovação.

✨ Relatórios e Estatísticas: O sistema deve ser capaz de gerar relatórios e estatísticas, como número de livros emprestados, atrasados e reservados; histórico de empréstimos de um usuário específico; e livros mais populares.

✨ Gerenciamento de Multas: O sistema deve ser capaz de calcular e registrar multas por atraso na devolução de livros. O usuário deverá ser multado com o dobro de dias em atraso.

✨ Gerenciamento de Acervo: O sistema deve permitir o gerenciamento do acervo da biblioteca, incluindo adição, remoção e atualização de informações sobre os livros, além do controle de estoque.

✨ Controle de operadores do sistema: O sistema deve permitir o cadastro de novos operadores, com informações como nome, número de identificação, cargo e senha de acesso. Os cargos podem ser do tipo Administrador ou Bibliotecário. O Bibliotecário só terá acesso às funcionalidades #1, #2 e #3.

## Diagrama de Classes

![Diagrama de Classes](https://github.com/ValmirNogFilho/LibraryManager/blob/main/docs/DiagramaPNG.png)

## Diagrama de Casos de Uso

![Diagrama de Casos de Uso](https://github.com/ValmirNogFilho/LibraryManager/blob/main/docs/CasosDeUsoKevinValmir.jpg)

## Instalação
Execute o seguinte comando no terminal do seu dispositivo:

```https://github.com/ValmirNogFilho/LibraryManager.git```

## Bateria de testes

Os testes unitários e de integração encontram-se no pacote `test`. IDEs como o IntelliJ IDEA podem executar todos testes de uma vez a partir do comando Run All Tests, selecionável após o clique com o botão direito no pacote de testes.

## Ferramentas

IntelliJ IDEA Community - IDE utilizado para desenvolvimento do sistema

Scene Builder - Confecção das UIs

Gaphor - Para desenvolvimento da diagramação
