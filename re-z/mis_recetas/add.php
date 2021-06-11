<?php
include '../connection.php';

if(empty($_POST['imagen'])){
  $response['success'] = 'false';
  $response['message'] = 'Selecciona una Imagen';
} else {
  $imagen = $_POST['imagen'];
  $titulo = $_POST['titulo'];
  $dificultad = $_POST['dificultad'];
  $porciones = $_POST['porciones'];
  $duracion = $_POST['duracion'];
  $ingredientes = $_POST['ingredientes'];
  $preparacion = $_POST['preparacion'];
  $email = $_POST['email'];

  $id = uniqid('image_');
  $path = "uploads/$id.png";
  $actualpath = "http://localhost/android/re-z/mis_recetas/$path";

  $response = array();
  $query = mysqli_query($con, "INSERT INTO receta (id, imagen, titulo, dificultad, porciones, duracion, ingredientes, preparacion, usuario_email) VALUES (0,'$actualpath','$titulo','$dificultad','$porciones','$duracion','$ingredientes', '$preparacion', '$email')");

  if($query){
    file_put_contents($path, base64_decode($imagen));
    $response['success'] = 'true';
    $response['message'] = 'Receta Agregada';
  }else{
    $response['success'] = 'false';
    $response['message'] = 'Error al intentar agregar receta, intente más tarde';
  }
}

echo json_encode($response);
?>