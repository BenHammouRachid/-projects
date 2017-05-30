var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');
var logger = require('morgan');
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var app = express();

var http = require('http');
var connect = require('connect');
var port     = process.env.PORT || 8080;


var busboy = require('connect-busboy');

mongoose.connect('mongodb://127.0.0.1:27017/dbhfalbums');

app.use(logger('dev'));
app.use(bodyParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(busboy());
// Configuration
app.use(connect.cookieParser());

app.use(connect.bodyParser());

app.use(connect.json());
app.use(connect.urlencoded());

require('./routes/routes.js')(app);

app.listen(port);
//Init Schemas
var userSchema = new Schema({
    name:String,
    facebookId:String});



//Init Modals
var User = mongoose.model('User',userSchema);

module.exports.user = User;

module.exports.app = app;

//Routes
var userRoutes = require('./routes/users');

app.use("/users",userRoutes);

app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.json({
            message: err.message,
            error: err
        });
    });
}
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.json({
        message: err.message,
        error: {}
    });
});

