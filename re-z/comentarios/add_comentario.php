<?php
include '../connection.php';

  $id = $_POST['id'];
  $correo = $_POST['correo'];
  $comentario = $_POST['comentario'];

  $response = array();
  $query = mysqli_query($con, "INSERT INTO comentarios (id_receta, email, comentario) VALUES ('$id','$correo','$comentario')");

  if($query){
    $response['success'] = 'true';
    $response['message'] = 'Comentario publicado';
  }else{
    $response['success'] = 'false';
    $response['message'] = 'Error al intentar comentar';
  }


echo json_encode($response);
?>