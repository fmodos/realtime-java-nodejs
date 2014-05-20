var http = require('http');

function handler(req, res){
    res.end("Ola browser");
    
    while(true){}
}

http.createServer(handler).listen(3001);

function hello(){
 var v = "oi";
  setTimeout(function(){
      v = "abc";
  },0);
  console.log(v);

}

hello();