<?php
include '../connection.php';
$email=$_POST["email"];
$query = mysqli_query($con, "SELECT * FROM favoritos  JOIN receta  
ON id=favoritos.id_receta WHERE favoritos.email='$email'"  );

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

  $file = file_get_contents($row['imagen']);
  $data['imagen'] = base64_encode($file);

  //$data['imagen'] = file_get_contents($row['imagen']);

  $qry_array[$i] = $data;
  $i++;
}

if($query){
  $response['success'] = 'true';
  $response['message'] = 'Recetas Loaded Successfully';
  $response['total'] = $total;
  $response['data'] = $qry_array;
}else{
  $response['success'] = 'false';
  $response['message'] = 'Data Loading Failed';
}

echo json_encode($response);
?>
