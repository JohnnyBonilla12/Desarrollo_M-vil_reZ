<?php
include '../connection.php';

if(!empty($_POST['correo'])){
	$correo = $_POST['correo'];
	$consulta = 
	"SELECT r.* 
	FROM receta r
	JOIN usuarios u
	ON u.email = r.usuario_email
	where r.usuario_email = '$correo'";

	$query = mysqli_query($con, $consulta);
	$data = array();
	$qry_array = array();
	$i = 0;
	$total = mysqli_num_rows($query);

	while ($row = mysqli_fetch_array($query)) {
		$data['id'] = $row['id'];
		$file = file_get_contents($row['imagen']);
		$data['imagen'] = base64_encode($file);
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
		$response['message'] = 'Recetas Loaded Successfully';
		$response['total'] = $total;
		$response['data'] = $qry_array;
	}else{
		$response['success'] = 'false';
		$response['message'] = 'Data Loading Failed';
	}
} else {
	$response['success'] = 'false';
	$response['message'] = 'No se envío un correo';
}

echo json_encode($response);
?>