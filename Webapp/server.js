const express = require('express');
var bodyParser = require('body-parser');
var http = require("http");
const fs = require('fs');
var cookieParser = require('cookie-parser');

const app = express();

process.env["NODE_TLS_REJECT_UNAUTHORIZED"] = 0;

const server = http.createServer(app);
app.use(cookieParser());

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.static('build'));

process.on('uncaughtException', function (err) {
    console.log(err);
}); 

app.get("/", function (req, res) {
    res.send(JSON.stringify({ Hello: "World"}));
});

app.get("/login", function(req, res){
    var setCookie = req.headers.cookie;
    if(setCookie == null){
        res.sendFile(__dirname + "/public/login.html");
    }else{
        res.redirect("/home");
    }
})

app.get("/home", function(req, res){
    console.log("Hello")
    var setCookie = req.headers.cookie;
    if(setCookie == null){
        console.log("User Needs To Log In Again");
        res.redirect("/login");
    }else{
        res.sendFile(__dirname + "/build/index.html");
    }
});

app.post("/login", function(req, resp){
    var options = {
        host: "warm-shelf-72548.herokuapp.com",
        path: "/login",
        method: "GET",
        headers:{
            "content-type": "application/json"
        }
    };
    var sessionid = "";

    var string = req.body.name+":"+req.body.password;
    var encodedString = 'Basic ' + Buffer.from(string).toString('base64');
    options.headers.Authorization = encodedString;

    var request = http.request(options, function (res) {
        var status = res.statusCode
        console.log(status);
        res.on("data", function (data) {
            sessionid+=data
        });
        res.on("close", function(){
            if(status == 401){
                
                resp.redirect('/login?');
            }
            if(status == 202){
                resp.cookie('set-cookie' , res.headers['set-cookie']);
                console.log(res.headers['set-cookie']);
                resp.redirect('/home');
            }    
        })
    });
    request.write("parameters");
    request.end();
})

app.post("/sendreq", function(req, res){
    var options2 = {
        host: "warm-shelf-72548.herokuapp.com",
        path: "/"+req.headers['path'],
        method: "POST",
        headers:{
            "content-type": "application/json"
        }
    };

    options2.headers['Cookie'] = req.headers['set-cookie'];
    console.log(options2);
    var str = "";

    var request = http.request(options2, function (res) {
        res.on("data", function (data) {
            str+=data
        });
        res.on("close", function(){
            console.log(str);
        })
    });
    console.log(JSON.stringify(req.body));
    request.write(JSON.stringify(req.body));
    request.end();
})
app.get("/register", function(req, res){
    res.sendFile(__dirname + "/public/register.html");
});

app.post("/register", function(req, resp){
    var options = {
        host: "warm-shelf-72548.herokuapp.com",
        path: "/register",
        method: "POST",
        headers:{
            "content-type": "application/json"
        }
    };

    var obj = {
        name: req.body.name,
        username: req.body.username,
        password: req.body.password,
        country: req.body.country
    }
    var str = ""
    var request = http.request(options, function (res) {
        
        res.on("data", function (data) {
            str+=data;
        });
        res.on("close", function(){
            console.log(str);
            resp.redirect('/login');
        })
    });
    request.write(JSON.stringify(obj));
    request.end();
});

app.get("/sendreq", function(req, resp){
    console.log("Get Request");
    var options2 = {
        host: "warm-shelf-72548.herokuapp.com",
        path: "/"+req.headers['path'],
        method: "GET",
        headers:{
            "content-type": "application/json"
        }
    };

    options2.headers['Cookie'] = req.headers['set-cookie'];
    console.log(options2);
    var str = "";

    var request = http.request(options2, function (res) {
        res.on("data", function (data) {
            str+=data
        });
        res.on("close", function(){
            console.log("String: "+str);
            resp.send(str);
        })
    });
    request.write("");
    request.end();
})

app.use(express.static(__dirname + "/public"));

server.listen(process.env.PORT, () => console.log(`Listening on port ${process.env.PORT}!`));
