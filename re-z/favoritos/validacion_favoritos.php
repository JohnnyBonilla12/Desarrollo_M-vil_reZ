<?php
$query1 = mysqli_query($con, "SELECT * FROM favoritos  
WHERE email='$email' AND id_receta=$id"  );

$data = array();
$qry_array = array();
$i = 0;
$total = mysqli_num_rows($query1);
while ($row = mysqli_fetch_array($query1)) {
 $data['id'] = $row['id_receta'];
 $data['email'] = $row['email'];



 $qry_array[$i] = $data;
 $i++;
}
?>
