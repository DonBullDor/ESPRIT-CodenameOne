<?php
$login = $_GET['login'];
$pass = $_GET['password'];
if ($login == 'login' && $pass == '12345'){
echo "welcome";
}
else
echo "mot de passe invalide";

