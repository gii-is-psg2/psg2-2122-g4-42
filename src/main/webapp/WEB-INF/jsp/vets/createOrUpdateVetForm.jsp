<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <h2>
        Añadir/Actualizar veterinario
    </h2>
    <form:form modelAttribute="vet" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="firstName"/>
            <petclinic:inputField label="Apellidos" name="lastName"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${vets['new']}">
                        <button class="btn btn-default" type="submit">Añadir veterinario</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Añadir/Actualizar veterinario</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
