<?php
include 'connection.php';

$email = $_POST['email'];
$password = $_POST['password'];

$response = array();
$query = mysqli_query($con, "INSERT INTO `usuarios` (`email`, `password`) VALUES ('$email','$password')");

if($query){
  $response["error"] = FALSE;
  $response["message"] = 'Data Inserted Successfully';
}else{
  $response["error"] = TRUE;
  $response["message"] = 'Data Insertion Failed';
}

echo json_encode($response);
?>
