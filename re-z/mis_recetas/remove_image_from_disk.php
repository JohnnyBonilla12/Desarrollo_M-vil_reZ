<?php
$query_image = mysqli_query($con, "SELECT imagen FROM receta WHERE id = '$id'");
while ($row = mysqli_fetch_array($query_image)) {
  $imagen = $row['imagen'];
}
$imagen = substr($imagen, 42);
unlink($imagen);
?>