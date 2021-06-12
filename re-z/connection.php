<?php

$host = "localhost";
$user = "root";
$pass = "";
$db = "re-z";

error_reporting(E_ERROR | E_PARSE);

//$con = mysqli_connect($host,$user,$pass, $db);
$con = new mysqli($host, $user, $pass, $db);


if ($con->connect_error) {
    die(
        "{data: error}"
    );       
}
 ?>