<?php
include '../connection.php';

$id=$_POST["id"];
$email = $_POST['email'];

$response = array();

include 'validacion_favoritos.php';

if(empty($data['id'])){
  $data['id']='a';
}

if($id==$data['id'] && $email==$data['email']){
  $response['success'] = 'false';
  $response['message'] = 'Se Encontro la receta en favoritos';
}else{
  $query = mysqli_query($con, "INSERT INTO favoritos (id_favoritos, id_receta,email) VALUES (0,$id,'$email')");
  
  if($query){
    $response['success'] = 'true';
    $response['message'] = 'Data Insertion Success';
  }else{
    $response['success'] = 'error';
    $response['message'] = 'Data Insertion Failed';
  }
}


  
  echo json_encode($response);

?>