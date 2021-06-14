<?php
include '../connection.php';

$id = $_POST['id'];

$query = mysqli_query($con, "DELETE FROM comentarios WHERE id_comentario = '$id' ");

if($query){
  $response['success'] = 'true';
  $response['message'] = 'Data Deleted Successfully';
}else{
  $response['success'] = 'false';
  $response['message'] = 'Data Deletion Failed';
}

echo json_encode($response);
?>