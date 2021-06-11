<?php
include '../connection.php';

$id = $_POST['id'];

require_once 'remove_image_from_disk.php';

$query = mysqli_query($con, "DELETE FROM receta WHERE id = '$id' ");

if($query){
  $response['success'] = 'true';
  $response['message'] = 'Data Deleted Successfully';
  $response['imagen'] = $imagen;
}else{
  $response['success'] = 'false';
  $response['message'] = 'Data Deletion Failed';
}

echo json_encode($response);
?>