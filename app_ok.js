var port = 3001;
process.argv.forEach(function (val, index, array) {
  if(index == 2){
    port = val;
  }
});
console.log("Starting in port "+port);

var http = require('http');
var fs = require('fs');    
var server = http.createServer(handler).listen(port);

var io = require('socket.io').listen(server, {log:false});

var redis = require("redis");
var pub = redis.createClient();

io.sockets.on('connection', function(socket){
   var sub = redis.createClient();
   sub.subscribe('userConnected')                                             
   sub.subscribe('userDisconnected')
   sub.subscribe('fromJava')
   console.log('connected');
   sub.on('message', function(channel, message){
      socket.emit(channel, {name: message});          
  });
   socket.on('setName', function(data){
      socket.set('name', (data.name));
      pub.publish('userConnected', data.name+'-'+port);      
   });
   
   socket.on('disconnect', function(){
      sub.quit();
      socket.get('name', function(err, name){
        pub.publish('userDisconnected', name);
      });
   });
});

function handler(req, resp){
    console.log('req '+req.url)
      
    fs.readFile("./palestra/index_ok.html", "utf8", function (err, data) {
        if (err) throw err;
        resp.write(data);
        resp.end();
    });
    
}

