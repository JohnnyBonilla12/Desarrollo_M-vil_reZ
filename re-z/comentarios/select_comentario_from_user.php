<?php 
include '../connection.php';

if(!empty($_POST['correo'])){
	$correo = $_POST['correo'];
	$consulta = 
	"SELECT c.*, r.imagen, r.titulo
	FROM comentarios c
	JOIN receta r
	ON c.id_receta = r.id
	where c.email = '$correo'";

	$query = mysqli_query($con, $consulta);
	$data = array();
	$qry_array = array();
	$i = 0;
	$total = mysqli_num_rows($query);

	while ($row = mysqli_fetch_array($query)) {
		$data['id'] = $row['id_comentario'];
		$file = file_get_contents($row['imagen']);
		$data['imagen'] = base64_encode($file);
		$data['usuario'] = $row['email'];
		$data['fecha'] = $row['posted'];
		$data['comentario'] = $row['comentario'];
		$data['receta'] = $row['titulo'];
		$qry_array[$i] = $data;
		$i++;
	}

	if($query){
		$response['success'] = 'true';
		$response['message'] = 'Comentarios Loaded Successfully';
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