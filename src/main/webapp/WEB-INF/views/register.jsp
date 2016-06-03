<%@ include file="/WEB-INF/views/general/include.jsp" %>


	<section class="container">
		<h1><fmt:message key="register.title"/></h1>
		
		<form:form method="post" commandName="formRegister"  name="formRegister">
			
			<div class="form-group" ng-class="{'has-error': formRegister.name.$invalid, 'has-success': formRegister.name.$valid}">
				<label for="name"><fmt:message key="register.band.name"/></label>
				<input type="text" id=name name="name" class="form-control" placeholder="<fmt:message key='register.band.name'/>" ng-model="band.name" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.cbMusicGenre.$invalid, 'has-success': formRegister.cbMusicGenre.$valid}">
				<label for="cbMusicGenre"><fmt:message key="register.genre"/></label>
				<select id="cbMusicGenre" name="cbMusicGenre" class="form-control" placeholder="<fmt:message key='register.genre'/>" ng-model="band.genre" required>
					<option value=""><fmt:message key="general.select.option"/></option>
				</select>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.cbCountry.$invalid, 'has-success': formRegister.cbCountry.$valid}">
				<label for="cbCountry"><fmt:message key="register.country"/></label>
				<select id="cbCountry" name="cbCountry" class="form-control" ng-model="band.country" required> 
					<option value=""><fmt:message key="general.select.option"/></option>
				</select>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.cbCity.$invalid, 'has-success': formRegister.cbCity.$valid}">
				<label for="cbCity"><fmt:message key="register.city"/></label>
				<select id="cbCity" name = "cbCity" class="form-control"  ng-model="band.city" required>
					<option value=""><fmt:message key="general.select.option"/></option>
				</select>
			</div>
			
			<div class="form-group">
				<label for="address"><fmt:message key="reigster.address"/></label>					
				<input type="text" id="address" name="address" class="form-control" placeholder="<fmt:message key='reigster.address'/>" ng-model="band.address" />
			</div>
			
			<div class="form-group">
				<label for="phone"><fmt:message key="register.phone"/></label>					
				<input type="text" id="phone" name="phone" class="form-control" placeholder="<fmt:message key='register.phone'/>" ng-model="band.phone" />
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.birthday.$invalid, 'has-success': formRegister.birthday.$valid}">
				<label for="birthday"><fmt:message key="register.birhday"/></label>
				<input type="date" id="birthday" name="birthday" class="form-control" ng-model="band.birthday" required/>
			</div>
		
			<div class="form-group" ng-class="{'has-error': formRegister.email.$invalid, 'has-success': formRegister.email.$valid}">
				<label for="email"><fmt:message key="register.email"/></label>
				<input type="email" id="email" name="email" class="form-control" placeholder="Email" ng-model="band.email" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.confirmEmail.$invalid, 'has-success': formRegister.confirmEmail.$valid}">
				<label for="email"><fmt:message key="register.confirm.email"/></label>
				<input type="email" id="confirmEmail" name="confirmEmail" class="form-control" placeholder="Confirm Email" ng-model="band.confirmEmail" required/>
			</div>
			
			<div class="form-group">
				<label for="facebook"><fmt:message key="general.facebook"/></label>
				<input type="url" id="facebook" name="facebook" class="form-control" placeholder="<fmt:message key='general.facebook'/>" ng-model="band.facebook"/>
			</div>
			
			<div class="form-group">
				<label for="website"><fmt:message key="general.website"/></label>
				<input type="url" id="website" name="website" class="form-control" placeholder="<fmt:message key='general.website'/>" ng-model="band.website" />
			</div>			
		
			<div class="form-group">
				<label for="record"><fmt:message key="register.record"/></label>
				<input type="text" id="record" name="record" class="form-control" placeholder="<fmt:message key='register.record'/>" ng-model="band.record" />
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.user.$invalid, 'has-success': formRegister.user.$valid}">
				<label for="user"><fmt:message key="register.admin.user"/></label>
				<input type="text" id="user" name="user" class="form-control" placeholder="<fmt:message key='register.admin.user'/>" ng-model="band.user" required/>
			</div>			
			
			<div class="form-group" ng-class="{'has-error': formRegister.password.$invalid, 'has-success': formRegister.password.$valid}">
				<label for="password"><fmt:message key="register.password"/></label>
				<input type="password" id="password" name="password" class="form-control" placeholder="<fmt:message key='register.password'/>" ng-model="band.password" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formRegister.confirmPassword.$invalid, 'has-success': formRegister.confirmPassword.$valid}">
				<label for="confirmPassword"><fmt:message key="register.confirm.password"/></label>
				<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="<fmt:message key='register.confirm.password'/>" ng-model="band.confirmPassword" required/>
			</div>
			
			<button class="btn btn-success" ng-disabled="formRegister.$invalid"><fmt:message key="general.button.register"/></button>
		
		</form:form>		
	</section>
</body>
</html>