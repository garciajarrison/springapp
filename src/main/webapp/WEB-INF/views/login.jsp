<%@ include file="/WEB-INF/views/general/include.jsp" %>
	
	<center>
    <div align="center">
    	 <h1><fmt:message key="login"/></h1>
	    <p><fmt:message key="login.message"/></p>
	    <br/>
	    <!--  <c:out value="${model.now}"/> -->
	    <section class="container">
		
		<form name="formLogin">
			
			<div class="form-group" ng-class="{'has-error': formLogin.name.$invalid, 'has-success': formLogin.name.$valid}">
				<label for="name"></label>
				<input type="text" id="name" name="name" class="form-control" placeholder="<fmt:message key="login.username"/>" ng-model="band.name" required/>
			</div>
			
			<div class="form-group" ng-class="{'has-error': formLogin.password.$invalid, 'has-success': formLogin.password.$valid}">
				<label for="password"><fmt:message key="login.password"/></label>
				<input type="password" id="password" name="password" class="form-control" ng-model="user.password" required/>
			</div>
			
			<button class="btn btn-success" ng-disabled="formLogin.$invalid"><fmt:message key="login.singin"/></button>
			<a href="<c:url value="register.htm"/>"><fmt:message key="login.register"/></button></a>
		</form>
		</section>
	    <br>
    
    </div>
    </center>
    
   
  </body>
</html>