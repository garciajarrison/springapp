<%@ include file="/WEB-INF/views/general/include.jsp" %>


	<section class="container">
		<h1>ADMIN</h1>
		
		<form name="formAdmin">
			
			<!--LOGO-->
			<section>
				<h2>Logo</h2>
				
				<div class="form-group">
					<label for="logo">Upload Logo</label>
					<input type="file" name="logo" accept="image/*">
					<button class="btn btn-success">Upload Logo</button>
				</div>
			</section>
			
			<!--BIOGRAPHY-->
			<section>
				<h2>Biography</h2>
				
				<div class="form-group">
					<label for="bandBio">Name</label>
					<textarea id="bandBio" name="bandBio" rows="4" class="form-control">
					</textarea>
					<button class="btn btn-success">Save</button>
				</div>
			</section>
						
		</form>
	</section>
</body>
</html>