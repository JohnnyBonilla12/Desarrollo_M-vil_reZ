<?php
include '../connection.php';

$id = $_POST['id'];
$query = mysqli_query($con, "SELECT * FROM receta WHERE id = '$id'");
$data = array();
$qry_array = array();
$i = 0;
$total = mysqli_num_rows($query);

while ($row = mysqli_fetch_array($query)) {
  $data['id'] = $row['id'];
  $data['titulo'] = $row['titulo'];
  $data['dificultad'] = $row['dificultad'];
  $data['porciones'] = $row['porciones'];
  $data['duracion'] = $row['duracion'];
  $data['ingredientes'] = $row['ingredientes'];
  $data['preparacion'] = $row['preparacion'];
  $data['email'] = $row['usuario_email'];
  $qry_array[$i] = $data;
  $i++;
}

if($query){
  $response['success'] = 'true';
  $response['message'] = 'Receta Loaded Successfully';
  $response['total'] = $total;
  $response['data'] = $qry_array;
}else{
  $response['success'] = 'false';
  $response['message'] = 'Data Loading Failed';
}

echo json_encode($response);
?>
