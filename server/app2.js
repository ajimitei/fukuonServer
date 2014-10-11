{
	var jsonServer = require('json-server');
	//var server_ip = "192.168.1.178";
	jsonServer.low.db = { 
	  user: [
	    {id:1,name:'たろう',photo:'http://192.168.1.178:8080/1.png',show_name:"けいおん！ 第99話",message:"けいおん! 全部俺"},
	    {id:2,name:'はなこ',photo:'http://192.168.1.178:8080/2.png',show_name:"けいおん！ 第100話",message:"けいおん! わたしが歌います" }
	  ]
	}
	jsonServer.listen(3000);
}

//画像サーバー
{
	var http  = require("http"),
		url   = require("url"),
		path  = require("path"),
		fs    = require("fs"),
		parse = require('url').parse,
		join = require('path').join,
		mime = require('mime');
	var root = __dirname;
	var resourceServer = http.createServer(function(req, res){
	  var url = parse(req.url);
	  var path = join(root, url.pathname);
	  fs.stat(path, function(err, stat){
	    if (err) {
	      if ('ENOENT' == err.code) {
	        res.statusCode = 404;
	        res.end('Not Found');
	      }
	      else {
	        res.statusCode = 500;
	        res.end('Internal Server Error');
	      }
	    }
	    else {
	      res.setHeader('Content-Type', mime.lookup(path));
	      res.setHeader('Content-Length', stat.size);
	      var stream = fs.createReadStream(path);
	      stream.pipe(res);
	      stream.on('error', function(err){
	        res.statusCode = 500;
	        res.end('Internal Server Error');
	      });
	    }
	  });
	});
	resourceServer.listen(1337);
}