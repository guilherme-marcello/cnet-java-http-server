Este ficheiro presenta alguns exemplos de como pode ser preparado o ficheiro de documentação Readme.txt do mini-projeto1. 

  

EXEMPLO NúMERO 1: 

  

***** Instruções para a compilação, execução e testagem ***** 

  

Este projeto contém exclusivamente as duas classes que foram pedidas no enunciado. Para testar e avaliar este projeto é suficiente seguir as instruções presentes na secção "Como Testar o Próprio Código" do enunciado, que também são reportadas aqui em baixo. 

  

Passo1: Compilar as duas classes, “$ javac MyHttpClient.java MyHttpServer.java”  

  

Passo2: Se a compilação correr bem, colocar no mesmo diretório e compilar a classe TestMp1 “$ javac TestMp1.java”  

  

Passo3: Abrir um terminal e lançar a classe servidor “$ java MyHttpServer 5555” (neste exemplo a classe MyHttpServer pede um número de porto TCP onde o servidor ficará aceitando conexões como parâmetro).  

  

Passo4: Abrir um outro terminal e lançar a classe de teste “$ java TestMp1 localhost 5555”  

  

Passo5: Usar o menu interativo da classe de teste para gerar os pedidos do cliente e as respostas do servidor, e depois de cada troca pedido/resposta analisar os respetivos logs na consola (stdout) para verificar que os comportamentos sejam conformes aos esperados.   

  

Passo6: Para testar a funcionalidade 1.f do servidor. Sugerimos lançar diferentes instâncias da classe TestMp1 até atingir o limite especificado acima e tentar lançar e usar uma ulterior instância da classe para interagir com o servidor. Um servidor deverá reagir com um erro aos pedidos da última instância lancada até que todas as outras estejam ativas.  

  

***** Funcionalidade ***** 

  

O projeto implementa corretamente todas as funcionalidades pedidas no enunciado. 

  

--------------------------------------------------------------------------------------------------------------------------------------------------------- 

  

EXEMPLO NúMERO 2: 

  

***** Instruções para a compilação, execução e testagem ***** 

  

Este projeto contém as classes MyHttpClient.java, MyHttpServer.java, HttpRequest.java. A classe HttpRequest é usada para as classes servidor e cliente, portanto, é preciso compilar o ficheiro HttpRequest.java antes de compilar os outros dois ficheiros. Para testar e avaliar este projeto, basta seguir as instruções na seção "Como testar seu próprio código" do enunciado, mas realizando o seguinte como primeiro passo: 

Passo1: Compilar as três classes, primeiro "javac HttpRequest.java" e depois “$ javac MyHttpClient.java MyHttpServer.java”  

  

***** Funcionalidade ***** 

Todas a funcionalidades pedidas no enunciado estão implementadas nas classes entregues com as seguintes duas exceções: 

o servidor não deteta corretamente uma mensagem mal formatada de tipo (ii), 

o servidor neste projeto não é multi-threaded, portanto não é possível testar e avaliar a funcionalidade 1.f pedida no enunciado. 

  

------------------------------------------------------------------------------------------------------------------------------------------ 
