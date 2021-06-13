<?php
include 'connection.php';

$id=$_POST["id"];

$response = array();
$query = mysqli_query($con, "INSERT INTO favoritos (id_favoritos, id_receta) VALUES (0,$id)");

if($query){
    $response['success'] = 'true';
    $response['message'] = 'Data Insertion Success';
  }else{
    $response['success'] = 'false';
    $response['message'] = 'Data Insertion Failed';
  }
  
  echo json_encode($response);

?>