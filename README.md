# Busca de Palavras-chave em Arquivos de Texto

Aplicação em Java que indexa arquivos `.txt` de uma pasta (e de suas subpastas) e permite pesquisar uma ou mais palavras, mostrando os documentos em que elas aparecem. Projeto acadêmico da disciplina de Estruturas de Dados, feito para praticar listas encadeadas e tabelas hash implementadas do zero, sem usar as coleções prontas do Java.

## Funcionalidades

- Indexa todos os arquivos `.txt` do diretório informado, incluindo subdiretórios
- Ignora diferença entre maiúsculas e minúsculas e descarta a pontuação
- Só indexa palavras com 3 ou mais letras, e ignora as formadas apenas por números
- Salva o índice em disco e o carrega nas próximas execuções, sem reindexar
- Busca por uma palavra (mostra os documentos que a contêm) ou por várias (mostra só os documentos que contêm todas)
- Interface gráfica simples (Java Swing) para digitar a pesquisa e ver os resultados

## Como funciona

**Indexação.** Cada linha é convertida para minúsculas, os caracteres que não são letras nem números viram espaços, e o texto é dividido em palavras. As palavras válidas entram no índice, associadas à lista de documentos em que aparecem. Cada documento é registrado uma única vez por palavra.

**Tabela hash (`MapaDispersao`).** O índice é um mapa de dispersão com tratamento de colisões por **encadeamento**: cada posição do vetor guarda uma lista encadeada de pares (palavra, lista de documentos). A função de hash é polinomial, com multiplicador 31. Quando o fator de carga chega a 0,75, a tabela é redimensionada para `2 * tamanho + 1` e todos os elementos são reinseridos (*rehash*).

**Persistência.** O índice é gravado no arquivo `indice.txt`, uma palavra por linha:

```
palavra|documento1.txt;documento2.txt
```

Na execução seguinte, se o arquivo existir, ele é lido e o índice é reconstruído em memória, e a busca acontece só em memória.

**Busca com várias palavras.** O programa pega a lista de documentos da primeira palavra e mantém só os documentos presentes também nas listas das demais, ou seja, a interseção.

## Estrutura do projeto

```
buscapalavras/
├── Main.java            ponto de entrada; carrega ou cria o índice
├── Indexador.java       percorre as pastas, lê os arquivos e monta o índice
├── MapaDispersao.java   tabela hash com encadeamento e redimensionamento
├── ListaEncadeada.java  lista encadeada genérica
├── NoLista.java         nó da lista encadeada
├── NoMapa.java          par (chave, valor) guardado na tabela
├── Pesquisa.java        busca por uma ou várias palavras
└── TelaPesquisa.java    interface gráfica (Swing)
```

## Como executar

Requisito: JDK 8 ou superior.

**Pelo Eclipse:** importe o projeto e execute a classe `Main` (*Run As → Java Application*).

**Pelo terminal**, na pasta que contém a pasta `buscapalavras`:

```
javac buscapalavras/*.java
java buscapalavras.Main
```

Na **primeira execução**, o programa pede o caminho da pasta com os arquivos `.txt` (por exemplo, `exemplos`), cria o índice e salva o `indice.txt`. Em seguida abre a janela de pesquisa. Nas execuções seguintes, o índice é carregado do disco.

Para **reindexar** outra pasta, apague o arquivo `indice.txt` e execute de novo.

## Limitações conhecidas

- Os documentos são identificados pelo **nome do arquivo**, e não pelo caminho. Dois arquivos com o mesmo nome em subpastas diferentes contam como um só.
- Palavras com hífen são separadas em duas (por exemplo, `guarda-chuva` vira `guarda` e `chuva`).
- A leitura usa a codificação padrão do sistema, então arquivos em UTF-8 com acentos podem aparecer com caracteres errados em sistemas que usem outra codificação.
- Palavras da pesquisa com menos de 3 letras nunca são encontradas, porque não são indexadas. Em uma busca com várias palavras, uma palavra curta faz o resultado ficar vazio.
- Nomes de arquivo que contenham `;` ou `|` quebram o formato do `indice.txt`.

## O que aprendi

- Implementar uma lista encadeada genérica e uma tabela hash com tratamento de colisões
- Entender o papel do fator de carga e do redimensionamento na eficiência da busca
- Percorrer diretórios de forma recursiva
- Salvar e carregar uma estrutura de dados em arquivo
- Separar a lógica (indexação e busca) da interface gráfica

## Autor

Victor Nascimento · [LinkedIn](https://www.linkedin.com/in/victor-a-nascimento) · [GitHub](https://github.com/victor-a-nascimento)
