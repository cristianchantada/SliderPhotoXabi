<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles/style.css">
<title>Subir foto</title>
</head>
<body>
<main>
	<div>
		<form action="${pageContext.request.contextPath}/upload" method="POST" enctype="multipart/form-data">
			<div class="input-container">		
				<label for="file">Subir una imagen</label>			
				<input type="file" id="file" name="file" value="subir imagen">		
			</div>
			<div class="input-container">
				<label for="check">¿Deseas que sea visible?</label>
				<input type="checkbox" id="check" name="check">
			</div>
			<div>
				<input type="submit" value="Subir imagen">
			</div>
		</form>
	</div>
		<div>
		<a href="<%=request.getContextPath()%>/slider-sevlet">Ir al slider</a>
	</div>
</main>
</body>
</html>