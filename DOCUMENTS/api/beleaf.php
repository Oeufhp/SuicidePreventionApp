<?php

#********* This is my archetype source code for APIs (Using slim micro framwork) **********************

#====================== 1. initial Setting ==================================================
//1.1 set Req and Res +autoload
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require 'vendor/autoload.php';

//1.2 function connect_db
function connect_db(){
    $dbhost = "ahealth.camdtseexuim.ap-southeast-1.rds.amazonaws.com";      //$dbhost = localhost:8081/phpMyAdmin  -> FAILED
    $dbuser = "ahealth";
    $dbpass = "jarbill";
    $dbname = "suicidePrevention";
    $mysql_conn_string = "mysql:host=$dbhost;dbname=$dbname";
    $dbConnection = new PDO($mysql_conn_string, $dbuser, $dbpass);          //Start PDO connection (PDO= slim's libraey)
    $dbConnection -> exec("SET CHARACTER SET utf8");                        //=excutes ("...")
    $dbConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //Set Error Reporting
    return $dbConnection;
}

//1.3 call slim
$app = new \Slim\App(['settings' => ['displayErrorDetails' => true]]);      //$app = new \Slim\App();

#======================== 2. test Apis =======================================================
//----2.1 get----
$app->get('/gethello', function(){
    echo "Hello!";
});

$app->get('/gethello-nf/{name}', function(Request $req, Response $res){
    $name = $req->getAttribute('name');
    $result = array('Hello' => $name);
    return $res->write(json_encode($result));
});

//----2.2 post----
//must send inputs in Json {"firstName":"vip","lastName":"123"} //try this in POSTMAN
$app->post('/posthello', function(Request $req, Response $res){
    $firstName = $req->getParsedBody()['firstName'];
    $lastName = $req->getParsedBody()['lastName']; 
    
    //Bug here for 3/2 hrs !
    $result= array('firstName'=>$firstName, 'lastName'=>$lastName);
    return $res->write(json_encode($result)); 
    
    //echo "firstName= ".$firstName."  lastName= ".$lastName;
});

#===================== 3. functions ==========================================================
/*----- add user {"username":"vip", "password":"123"}
    1.get info from $req
    2.try-catch
        2.1 connect-db
        2.2 prepare
        2.3 bindParam
        2.4 execute
        2.5 null
*/
//FOR CREATING OTHER SIMILAR FUNCTIONS: apply from this function !
//3.1 CREATE AN ACCOUNT        
$app->post('/createanaccount', function(Request $req, Response $res) {
    //get info from $req
    $gender = $req->getParsedBody()['gender'];
    $surname = $req->getParsedBody()['surname'];
    $name = $req->getParsedBody()['name'];
    //----About Calendar----------------
    //$day = $req->getParsedBody()['day'];
    //$month = $req->getParsedBody()['month'];
    //$year = $req->getParsedBody()['year'];
    $birthdate = $req->getParsedBody()['birthdate'];
    //----------------------------------
    $email = $req->getParsedBody()['email'];
    $create_btn = $req->getParsedBody()['create_btn'];
    $countryflagspinner = $req->getParsedBody()['countryflagspinner'];
    $codetextview = $req->getParsedBody()['codetextview'];
    $phoneno = $req->getParsedBody()['phoneno'];

    //username
    $username = $req->getParsedBody()['username'];
    //password
    $password = $req->getParsedBody()['password'];
    

    try{
        $db = connect_db();  
        $sth = $db->prepare ("INSERT INTO user_info(gender, surname, name, birthdate, email, create_btn, countryflagspinner, codetextview, phoneno, username, password)    
                              VALUES (:gender, :surname, :name, :birthdate, :email, :create_btn, :countryflagspinner, :codetextview, :phoneno,  :username,:password)");
        $sth->bindParam(':gender', $gender, PDO::PARAM_STR);
        $sth->bindParam(':surname', $surname, PDO::PARAM_STR);
        $sth->bindParam(':name', $name, PDO::PARAM_STR);
        $sth->bindParam(':birthdate', $birthdate, PDO::PARAM_STR);
        $sth->bindParam(':email', $email, PDO::PARAM_STR);
        $sth->bindParam(':create_btn', $create_btn, PDO::PARAM_STR);
        $sth->bindParam(':countryflagspinner', $countryflagspinner, PDO::PARAM_STR);
        $sth->bindParam(':codetextview', $codetextview, PDO::PARAM_STR);
        $sth->bindParam(':phoneno', $phoneno, PDO::PARAM_STR);
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->execute();

        $result = array('create_status'=>"1");
        $db = null;
    }
    catch (PDOException $e) {
        $result = array('create_status'=>"0");
        //$res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }

    return $res->write(json_encode($result)); 
});


//3.2 LOGIN        
$app->post('/login', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    try{
        $db = connect_db();  
        $sth = $db->prepare ("SELECT username,password 
                              FROM   user_info    
                              WHERE  (username = :username) AND (password = :password)");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->execute();
        $result = $sth->fetchall(PDO::FETCH_OBJ);
        if($result){
            $result = array('login_status'=>"1");
        } else {
            $result = array('login_status'=>"0");
            //throw new PDOException('No records found.');
        }
        $db = null;
        }
    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }

    return $res->write(json_encode($result)); 
});

//3.3 editinfo        
$app->post('/editinfo', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];
    $editinfo = $req->getParsedBody()['editinfo'];

    try{
        $db = connect_db();  
        $sth = $db->prepare ("UPDATE user_info
                              SET info = :editinfo   
                              WHERE (username = :username) AND (password = :password) ");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->bindParam(':editinfo', $editinfo, PDO::PARAM_STR);  
        $sth->execute();
        
        //if($sth->execute()==1){
            $result = array('editinfo_status'=>"1");
        //} else {
        //    $result = array('editinfo_status'=>"0");
            //throw new PDOException('No records found.');
        //}
        $db = null;
        }
    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }

    return $res->write(json_encode($result)); 
});

//3.4 getinfo        
$app->post('/getinfo', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    try{
        $db = connect_db();  //HERE
        $sth = $db->prepare ("SELECT info    
                              FROM user_info 
                              WHERE (username = :username) AND (password = :password) ");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->execute();
        $result = $sth->fetch(PDO::FETCH_OBJ);
        $db = null;
        }
    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    $result= array('getinfo'=>$result);
    return $res->write(json_encode($result)); 
});

//3.5#0 sendfeeling        
$app->post('/sendfeeling', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    $sentdate = $req->getParsedBody()['sentdate'];
   // $string_sentdate = $req->getParsedBody()['sentdate'];
    //$time_sentdate = strtotime($string_sentdate);
    //$sentdate = date('y-m-d h:m:s',$time_sentdate); 

    $feeling = $req->getParsedBody()['feeling'];

    try{
        $db = connect_db();  
        $sth = $db->prepare ("INSERT INTO feeling_ans(username,password,sentdate,feeling)    
            VALUES (:username,:password,:sentdate,:feeling)");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->bindParam(':sentdate', $sentdate, PDO::PARAM_STR);
        $sth->bindParam(':feeling', $feeling, PDO::PARAM_STR);
        $sth->execute();
        $db = null;
        }
    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';

        $result = array('sendfeeling_status'=>"0");
    }
    //In comment, it will insert in DB twice !
    //if($sth->execute()==1){
            $result = array('sendfeeling_status'=>"1");
        //} else {
        //    $result = array('sendsurvey_status'=>"0");
            //throw new PDOException('No records found.');
        //}
    return $res->write(json_encode($result)); 
});

//3.5#1 seefeelingvisualization        
$app->post('/seefeelingvisualization', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    try{
        $db = connect_db();
                            //To SELECT top 5 rows from survey_ans
        $sth = $db->prepare ("SELECT username, password, sentdate, feeling FROM feeling_ans
                          WHERE (username = :username) AND (password=:password) ORDER BY sentdate DESC LIMIT 5 ");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->execute();
        $result = $sth->fetchall(PDO::FETCH_OBJ);
        $db = null;
        /*
        if($sth->execute()==1){
           $result = array('seesurveyvisualization'=>"1");
        }
        else {
            $result = array('seesurveyvisualization'=>"0");
        */

        }

    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    //$result= array('seesurveyvisualization'=>$result); 
    $result= array('seefeelingvisualization'=>$result);
    return $res->write(json_encode($result));

});


//3.5#2 sendsurvey_cage        
$app->post('/sendsurvey_cage', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    $sentdate = $req->getParsedBody()['sentdate'];
   // $string_sentdate = $req->getParsedBody()['sentdate'];
    //$time_sentdate = strtotime($string_sentdate);
    //$sentdate = date('y-m-d h:m:s',$time_sentdate); 

    $survey_cage_q1_ans = $req->getParsedBody()['survey_cage_q1_ans'];
    $survey_cage_q2_ans = $req->getParsedBody()['survey_cage_q2_ans'];
    $survey_cage_q3_ans = $req->getParsedBody()['survey_cage_q3_ans'];
    $survey_cage_q4_ans = $req->getParsedBody()['survey_cage_q4_ans'];
    $totalScore = $req->getParsedBody()['totalScore'];

    try{
        $db = connect_db();  
        $sth = $db->prepare ("INSERT INTO survey_cage_ans(username,password,sentdate,survey_cage_q1_ans,survey_cage_q2_ans, survey_cage_q3_ans, survey_cage_q4_ans, totalScore)    
            VALUES (:username,:password,:sentdate,:survey_cage_q1_ans,:survey_cage_q2_ans,:survey_cage_q3_ans,
                                      :survey_cage_q4_ans, :totalScore)");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->bindParam(':sentdate', $sentdate, PDO::PARAM_STR);
        $sth->bindParam(':survey_cage_q1_ans', $survey_cage_q1_ans, PDO::PARAM_STR);
        $sth->bindParam(':survey_cage_q2_ans', $survey_cage_q2_ans, PDO::PARAM_STR);
        $sth->bindParam(':survey_cage_q3_ans', $survey_cage_q3_ans, PDO::PARAM_STR);
        $sth->bindParam(':survey_cage_q4_ans', $survey_cage_q4_ans, PDO::PARAM_STR);
        $sth->bindParam(':totalScore', $totalScore, PDO::PARAM_STR);
        $sth->execute();
        $db = null;
        }
    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';

        $result = array('sendsurvey_cage_status'=>"0");
    }
    //In comment, it will insert in DB twice !
    //if($sth->execute()==1){
            $result = array('sendsurvey_cage_status'=>"1");
        //} else {
        //    $result = array('sendsurvey_status'=>"0");
            //throw new PDOException('No records found.');
        //}
    return $res->write(json_encode($result)); 
});

//3.6 seesurveyhistory_cage    
$app->post('/seesurveyhistory_cage', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    try{
        $db = connect_db();  //HERE
        $sth = $db->prepare ("SELECT *    
                              FROM survey_cage_ans 
                              WHERE (username = :username) AND (password = :password) ");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->execute();
        $result = $sth->fetchall(PDO::FETCH_OBJ);
        $db = null;
        }
    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    $result= array('seesurveyhistory_cage'=>$result);
    return $res->write(json_encode($result)); 
});


//3.7 seesurveyvisualization_cage        
$app->post('/seesurveyvisualization_cage', function(Request $req, Response $res) {
    //get info from $req
    $username = $req->getParsedBody()['username'];
    $password = $req->getParsedBody()['password'];

    try{
        $db = connect_db();
                            //To SELECT top 5 rows from survey_ans
        $sth = $db->prepare ("SELECT username, password, sentdate, totalScore FROM survey_cage_ans 
                              WHERE (username = :username) AND (password=:password) ORDER BY sentdate DESC LIMIT 1 ");
        $sth->bindParam(':username', $username, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':password', $password, PDO::PARAM_STR);
        $sth->execute();
        $result = $sth->fetchall(PDO::FETCH_OBJ);
        $db = null;
        /*
        if($sth->execute()==1){
           $result = array('seesurveyvisualization'=>"1");
        }
        else {
            $result = array('seesurveyvisualization'=>"0");
        */

        }

    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    //$result= array('seesurveyvisualization'=>$result); 
    $result= array('seesurveyvisualization_cage'=>$result);
    return $res->write(json_encode($result));

});

//3.8 getnearbyplaces_search       
$app->post('/getnearbyplaces_search', function(Request $req, Response $res) {
    //get info from $req
    $place_keyword_city = $req->getParsedBody()['place_keyword_city'];
    $place_keyword_country = $req->getParsedBody()['place_keyword_country'];

    try{
        $db = connect_db();
                            
        $sth = $db->prepare ("SELECT * FROM  nearby_places
                              WHERE (place_keyword_city = :place_keyword_city) AND (place_keyword_country = :place_keyword_country)");
        $sth->bindParam(':place_keyword_city', $place_keyword_city, PDO::PARAM_STR);  //Can use with VARCHAR
        $sth->bindParam(':place_keyword_country', $place_keyword_country, PDO::PARAM_STR);
        $sth->execute();
        $result = $sth->fetchall(PDO::FETCH_OBJ);
        $db = null;

        }

    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    //$result= array('seesurveyvisualization'=>$result); 
    $result= array('getnearbyplaces_search'=>$result);
    return $res->write(json_encode($result));

});

//3.9 getnearbyplaces_gps       
$app->get('/getnearbyplaces_gps', function(Request $req, Response $res) {

    try{
        $db = connect_db();
                            
        $sth = $db->prepare ("SELECT * FROM  nearby_places WHERE 1=1" );
        $sth->execute();
        $result = $sth->fetchall(PDO::FETCH_OBJ);
        $db = null;

        }

    catch (PDOException $e) {
        $res->withStatus(404);
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    //$result= array('seesurveyvisualization'=>$result); 
    $result= array('getnearbyplaces_gps'=>$result);
    return $res->write(json_encode($result));

});



 #=================== 4. run ============================================================
 $app->run();

?>