# Navegador Cliente e Servidor em Java 
Projeto de Redes: Implementar servidor e cliente simulando requisições GET / HTTP/1.0

<h4>Desenvolver um servidor HTTP que:</h4>
• Recebe requisições GET;<br>
• Caso o endereço seja omitido, deve-se enviar o “index.htm”, se existir;<br>
- Ex.:<br>
GET /teste.htm HTTP/1.0 (retorna o conteúdo do arquivo “teste.htm”)<br>
GET / HTTP/1.0 (retorna o conteúdo do arquivo “index.htm”)<br>

• A primeira linha da resposta deve conter o protocolo, sua versão, um código e seu
respectivo status;<br>

- Ex.:<br>
HTTP/1.0 200 OK<br>
HTTP/1.0 404 Not Found<br>

• Use strings (simulando um arquivo .htm);<br>
• Devem começar com a tag \<HTM\> e terminar com a tag \</HTM\>;<br>
• Podem conter as seguintes tags:<br>
- \<NEG\> \</NEG\> : para negrito;<br>
- \<ITA\> \</ITA\> : para itálico;<br>
- \<SUB\> \</SUB\> : para sublinhado;<br>
- \<TAM num\> \</TAM\>: para tamanho da fonte (\<TAM 12\> \</TAM\>);<br>
- \<COR cor\> \</COR\> : para cor da fonte (\<COR verde\> \</COR\>);<br>

<h4>Desenvolver um cliente HTTP que:</h4>
• Envia requisições GET;<br>
• Recebe dados da requisição solicitada;<br>
• Interpreta as tags htm e mostra na tela o texto recebido de modo apropriado;<br>

- Ex.:<br>
\<NEG\> testando o negrito \</NEG\>: <b>testando o negrito</b><br>
\<COR verde\> testando a cor verde \</COR\>: O texto deve aparecer com a cor verde.<br>

• A 1ª linha da resposta recebida deve ser no seguinte formato: HTTP/\<versão\> \<código\> \<status\><br>

- Ex.: HTTP/1.0 200 OK<br>

• Em uma requisição bem sucedida, essa linha deve ser omitida na tela;<br>
• Caso o código seja de erro (400 ou 404), uma mensagem de erro significativa e amigável deve ser exibida;<br>

- Ex.: Desculpe, mas não conseguimos encontrar a página solicitada.<br>

• A comunicação entre o cliente e o servidor deve ser realizada usando o TCP (sockets);<br>
• O projeto deve ser desenvolvido em dupla;<br>
• A dupla deve apresentar, executar e defender o projeto.
