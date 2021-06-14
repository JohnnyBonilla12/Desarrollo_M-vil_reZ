<?php 
include 'connection.php';

$email = $_POST['email'];
$password = $_POST['password'];

$sql = "SELECT count(*) as existe FROM usuarios WHERE email = '$email' AND password = '$password'";
//echo "SQL:   ".$sql."<br>";

$query = mysqli_query($con, $sql);
$data = array();

if ($query) {
	$data = $query->fetch_assoc();
	if ($data['existe'] == '1') {
		//echo "Acceso consedido"."<br>";
		$response["error"] = FALSE;
		$response["email"] = $email;
		echo json_encode($response);
	}
	else{
		$response["error"] = TRUE;
		echo "error de usuario o contraseña";
		echo json_encode($response);
	}
	
} else{
	$response["error"] = TRUE;
	$response["message"] = $con->error;
	echo json_encode($response);
}


 ?>