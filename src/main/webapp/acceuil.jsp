<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Accueil</title>
<style>
body {
  font-family: 'Helvetica Neue', sans-serif;
  background-color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
}

.container {
  width: 320px;
  padding: 30px;
  background-color: #ffffff;
  border: 3px solid #000;
  border-radius: 2px;
}

h1 {
  text-align: center;
  text-transform: uppercase;
  font-size: 24px;
  letter-spacing: 2px;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 25px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  font-size: 14px;
  text-transform: uppercase;
}

input[type="text"], input[type="password"] {
  width: 100%;
  height: 46px;
  padding: 0 10px;
  border: 2px solid #000;
  border-radius: 0;
  background-color: #fff;
  box-sizing: border-box;
  font-size: 16px;
}

input[type="text"]:focus, input[type="password"]:focus {
  outline: none;
  border-color: #555;
}

button[type="submit"] {
  width: 100%;
  height: 46px;
  background-color: #000;
  color: #fff;
  border: 2px solid #000;
  font-size: 16px;
  text-transform: uppercase;
  letter-spacing: 1px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

button[type="submit"]:hover {
  background-color: #fff;
  color: #000;
}
</style>
</head>
<body>
<div class="container">
<h1>Accueil</h1>
<form action="acceuil" method="post">
<div class="form-group">
<label for="login">Login</label>
<input type="text" id="login" name="login" placeholder="Entrez votre login">
</div>
<div class="form-group">
<label for="password">Mot de passe</label>
<input type="password" id="password" name="password" placeholder="Entrez votre mot de passe">
</div>
<button type="submit">Se connecter</button>
</form>
</div>
</body>
</html>