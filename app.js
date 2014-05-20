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
var io = require('socket.io').listen(server);
var redis = require('redis');

var pub = redis.createClient();


io.on('connection', function(socket){
  var sub = redis.createClient();
  sub.subscriber('userConnected');
  socket.on('setName', function(data){
    socket.set('name', data.name);
    
  });

});

function handler(req, resp){
    console.log('req '+req.url)
      
    fs.readFile("./palestra/index.html", "utf8", function (err, data) {
        if (err) throw err;
        resp.write(data);
        resp.end();
    });
    
}

