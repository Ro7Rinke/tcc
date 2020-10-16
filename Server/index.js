var net = require('net');
  
var HOST = '192.168.0.35'; // parametrizar o IP do Listen
var PORT = 3000; // porta TCP LISTEN
  
// Cria a instância do Server e aguarda uma conexão
net.createServer(function(sock) {
  
    // console.log(`1`)
    // Opa, recebemos uma conexão - um objeto socket é associado à conexão automaticamente
    console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);
//   console.log('2')
    // Adiciona um 'data' - "event handler" nesta instância do socket
    sock.on('data', function(data) {
        // console.log('3')
        // dados foram recebidos no socket 
        // Escreve a mensagem recebida de volta para o socket (echo)
        try{
            console.log(`Mensagem recebida, retornando`)
            sock.write(data);
        }catch{
            console.log('catch')
        }
        // console.log('4')
    });
    // console.log('5')
  
    // Adiciona um 'close' - "event handler" nesta instância do socket
    sock.on('close', function(data) {
        // console.log('6')
        // conexão fechada
        console.log('CLOSED: ' + sock.remoteAddress +' '+ sock.remotePort);
        // console.log('7')
    });
    // console.log('8')
  
}).listen(PORT, HOST);
  
console.log('Server listening on ' + HOST +':'+ PORT);