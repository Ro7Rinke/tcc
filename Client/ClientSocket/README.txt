caso o blowfish nao funcione com chave maior q 128 bits
You need to to download JCE Unlimited Strength Jurisdiction Policy Files 
for your JDK/JRE. The unlimited policy will allow you to use key sizes 
greater than the predefined limits (128 bits for blowfish).

programa java deve ser executado com apenas um
algoritmo por vez e evitar execuções simultâneas, para o resultado final ser 
gerado é preciso tirar uma média dos resultados de varias execuções de um msm
algoritmo, o programa "generateResults.js" é uma implementação node para 
facilitar  isso e o arquivo "run.bat" é apenas um atalho para a execução do node

o programa "generateResults.js" executa a versão compilada do programa java
o netbeans salva o arquivo compilado .jar na pasta dist

o arquivo "generateResults.js" possui uma variável chamada "params" q é um array
de objetos, e para cada objeto será chamado o programa em java q criptografa
de acordo com o objeto q sera passado como argumento

os resultados sao salvos na pasta "data" com os argumentos passados como nome

os arquivos com as mensagem em vários tamanhos estão na pasta "strings"