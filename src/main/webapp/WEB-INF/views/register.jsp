<%@ include file="/WEB-INF/views/general/include.jsp" %>


	<section class="container">
		<h1>REGISTER</h1>
		
		<form name="formRegister">
			
			<div class="form-group" ng-class="{'has-error': formRegister.name.$invalid, 'has-success': formRegister.name.$valid}">
				<label for="name">Band Name</label>
				<input type="text" id=name name="name" class="form-control" placeholder="Band Name" ng-model="band.name" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.cbMusicGenre.$invalid, 'has-success': formRegister.cbMusicGenre.$valid}">
				<label for="cbMusicGenre">Genre</label>
				<select id="cbMusicGenre" name="cbMusicGenre" class="form-control" placeholder="Genre" ng-model="band.genre" required>
					<option value="">Please select a music genre</option>
				</select>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.cbCountry.$invalid, 'has-success': formRegister.cbCountry.$valid}">
				<label for="cbCountry">Country</label>
				<select id="cbCountry" name="cbCountry" class="form-control" placeholder="Country" ng-model="band.country" required> 
					<option value="">Please select a country</option>
				</select>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.cbCity.$invalid, 'has-success': formRegister.cbCity.$valid}">
				<label for="cbCity">City</label>
				<select id="cbCity" name = "cbCity" class="form-control" placeholder="City" ng-model="band.city" required>
					<option value="">Please select a city</option>
				</select>
			</div>
			
			<div class="form-group">
				<label for="address">Address</label>					
				<input type="text" id="address" name="address" class="form-control" placeholder="Address" ng-model="band.address" />
			</div>
			
			<div class="form-group">
				<label for="phone">Phone</label>					
				<input type="text" id="phone" name="phone" class="form-control" placeholder="Phone" ng-model="band.phone" />
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.birthday.$invalid, 'has-success': formRegister.birthday.$valid}">
				<label for="birthday">Birthday</label>
				<input type="date" id="birthday" name="birthday" class="form-control" placeholder="Birthday" ng-model="band.birthday" required/>
			</div>
		
			<div class="form-group" ng-class="{'has-error': formRegister.email.$invalid, 'has-success': formRegister.email.$valid}">
				<label for="email">Email</label>
				<input type="email" id="email" name="email" class="form-control" placeholder="Email" ng-model="band.email" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.confirmEmail.$invalid, 'has-success': formRegister.confirmEmail.$valid}">
				<label for="email">Confirm Email</label>
				<input type="email" id="confirmEmail" name="confirmEmail" class="form-control" placeholder="Confirm Email" ng-model="band.confirmEmail" required/>
			</div>
			
			<div class="form-group">
				<label for="facebook">Facebook</label>
				<input type="url" id="facebook" name="facebook" class="form-control" placeholder="Facebook" ng-model="band.facebook"/>
			</div>
			
			<div class="form-group">
				<label for="website">Website</label>
				<input type="url" id="website" name="website" class="form-control" placeholder="Website" ng-model="band.website" />
			</div>			
		
			<div class="form-group">
				<label for="record">Record</label>
				<input type="text" id="record" name="record" class="form-control" placeholder="Record" ng-model="band.record" />
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.user.$invalid, 'has-success': formRegister.user.$valid}">
				<label for="user">Admin User</label>
				<input type="text" id="user" name="user" class="form-control" placeholder="Admin User" ng-model="band.user" required/>
			</div>			
			
			<div class="form-group" ng-class="{'has-error': formRegister.password.$invalid, 'has-success': formRegister.password.$valid}">
				<label for="password">Password</label>
				<input type="password" id="password" name="password" class="form-control" placeholder="Password" ng-model="band.password" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.confirmPassword.$invalid, 'has-success': formRegister.confirmPassword.$valid}">
				<label for="confirmPassword">Confirm Password</label>
				<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Confirm Password" ng-model="band.confirmPassword" required/>
			</div>
			
			<button class="btn btn-success" ng-disabled="formRegister.$invalid">Register</button>
						
		</form>
	</section>
</body>
</html>