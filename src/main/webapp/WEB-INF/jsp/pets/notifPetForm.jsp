<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="owners">
    <jsp:body>
        <h2>Nuevo mensaje</h2>
        <form:form modelAttribute="notification"
                   class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Mensaje" name="message"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Mandar notificación</button>                    
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
