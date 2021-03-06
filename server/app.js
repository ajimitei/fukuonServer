{
    var port = process.env.PORT || 3000;
    var jsonServer = require('json-server');
    var jsondb = require('./db.json');
    jsonServer.low.db = jsondb;
 	jsonServer.listen(port);
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