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
           Añadir especialidad al veterinario ${ vet.firstName } ${ vet.lastName }
    </h2>
    <form:form modelAttribute="specialty" class="form-horizontal" id="editSpecialties">
        <div class="form-group has-feedback">
            <div class="control-group"> 
                <petclinic:selectField name="name" label="Especialidades" names="${specialtiesList}" size="5"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Añadir especialidad</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>