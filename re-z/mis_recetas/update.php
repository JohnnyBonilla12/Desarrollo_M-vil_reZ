<?php
include '../connection.php';

$id = $_POST['id'];

require_once 'remove_image_from_disk.php';

$imagen = $_POST['imagen'];
$titulo = $_POST['titulo'];
$dificultad = $_POST['dificultad'];
$porciones = $_POST['porciones'];
$duracion = $_POST['duracion'];
$ingredientes = $_POST['ingredientes'];
$preparacion = $_POST['preparacion'];

$image_id = uniqid('image_');
$path = "uploads/$image_id.png";
$actualpath = "http://localhost/android/re-z/mis_recetas/$path";

$response = array();
$query =  mysqli_query($con,	"UPDATE receta SET imagen = '$actualpath', titulo = '$titulo', dificultad = '$dificultad', 
          porciones = '$porciones', duracion = '$duracion', ingredientes = '$ingredientes', preparacion = '$preparacion' 
          WHERE id = '$id' ");

if($query){
  file_put_contents($path, base64_decode($imagen));
  $response['success'] = 'true';
  $response['message'] = 'Receta Editada';
}else{
  $response['success'] = 'false';
  $response['message'] = 'Fallo en la edición';
}

echo json_encode($response);
?>
