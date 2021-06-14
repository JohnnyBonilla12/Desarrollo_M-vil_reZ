<?php
include '../connection.php';

$id = $_POST['id'];
$comentario = $_POST['comentario'];


$response = array();
$query =  mysqli_query($con,	"UPDATE comentarios SET comentario = '$comentario' WHERE id_comentario = '$id' ");

if($query){
  $response['success'] = 'true';
  $response['message'] = 'Comentario Editado';
}else{
  $response['success'] = 'false';
  $response['message'] = 'Fallo en la edición del comentario';
}

echo json_encode($response);
?>
