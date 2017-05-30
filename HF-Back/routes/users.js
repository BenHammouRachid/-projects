var express = require('express');
var exports = require('../app.js');

var User = exports.user;
var router = express.Router();


router.post('/registerwithFb', function (req,res) {

    console.log(req.body.facebookId.toString());
   

        User.findOne({$or:[{facebookId :req.body.facebookId}]}).exec(function(err,usr){
            if(err){
                res.statusCode = 404;
                res.json({message: "Error Found"});
            }else if(usr){
                
                usr.name = req.body.name;

                usr.save(function(err,usr){
                    res.json(usr);
                });
            }else{
                var usr = new User;
                usr.name = req.body.name;

                usr.facebookId = req.body.facebookId;

                usr.save(function(err,usr){
                    res.json(usr);
                });
            }
        });

});


module.exports = router;