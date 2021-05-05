# # Simulador de autômato finito não determinístico - AFND

  

## Descrição

Este algoritmo realiza a leitura das informações do autômato e de varias entradas, com isso realiza o processamento do autômato finito não determinístico e escreve tanto os passos quanto os resultados (rejeitado ou aceito) em arquivos.
 
## Detalhes

Na pasta "resource" existem quatro arquivos .txt (detalhes logo abaixo), onde serão lidas e escritas as informações dos processamentos. Para alterações de dados e entradas, devem ser alterados os arquivos "automato.txt" e "entrada.txt", não são permitidos outros arquivos. Abaixo um detalhamento dos arquivos:

 ### automato.txt
 É o arquivo responsável por informar a estrutura e dados do autômato, deve respeitar o seguinte padrão:
```
q1 q2 q3 q4 #Linha com o conjunto de estados, separados por espaço 
0 1 #Linha com o alfabeto
q1 #TABELA[0][0]
q1 q2 #TABELA[0][1]
* #TABELA[0][2]
q3 #TABELA[1][0]
* #TABELA[1][1]
q3 #TABELA[1][2]
* #TABELA[2][0]
q4 #TABELA[2][1]
* #TABELA[2][2]
q1 #estado inicial
q4 #estados finais
```
**OBS:** As partes com "#" são apenas para comentários e explicação da estrutura, não devem ser incluídas no arquivo.
**OBS2:** A separação de um caractere para outro deve ser por espaços.
**OBS3:** O caractere **'*'** representa "vazio".

### entrada.txt
É o arquivo responsável por informar as entradas que serão processadas, sendo possível processar mais de uma entrada de uma vez. O arquivo deve respeitar o seguinte padrão:
```
0 1
1 0
0 1 1
```
O sistema irá quebrar por espaços cada linha e ira processar. 

**OBS:** Cada linha é um processamento separado e independente do outro.

### passos.txt
É o arquivo que receberá todos os passos realizados no fluxo, este arquivo é o sistema quem ira preencher e servirá apenas para consulta do fluxo de processamento, segue um exemplo:
```
Estado inicial -> q1
Símbolo lido -> 0
Estados correntes -> q1
Símbolo lido -> 1
Estados correntes -> q1 q2 q3
--------------------------------
```
Cada final de linha terá este separados "------", com o objetivo de facilitar a identificação de cada fluxo de processamento (lembrando que cada linha da entrada, é um processamento separado e independe).

### resultado.txt
É o arquivo responsável por gravar os resultados dos processamentos, ira receber "ACEITO" ou "REJEITADO", para cada fluxo de processamento da entrada.
```
REJEITADO
REJEITADO
ACEITO
```
Cada linha representa cada linha da entrada, respectivamente.

**OBS:** Para ser aceito, o fluxo precisa finalizar com pelo menos uma thread em um estado final.
