<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="changelog">
    <h1>Cambios implementados</h1>
    <br>
    <h3>A raíz del último sprint realizado, PetClinic ha implementado una serie de nuevos cambios en el servicio:</h3>
    <div class="row">
        <div class="col-md-12">
           <h4>-Hemos añadido una página de soporte a la web en la que se proporciona el contacto mediante teléfono y email de todos los desarrolladores.
           </h4>
           
           <h4>-Hemos implementado nuevos planes de mejoras a contratar que ofrecen nuevas funcionalidades en función del precio, (Básico, Avanzado, Pro)</h4>
           
           <h4>-Agregación de nuevas funciones destinadas al uso de los nuevos planes de mejora detallados</h4>
         
           <h4>-Página de información en la que se detallan los cambios implementados en la web y los nuevos servicios que este otorga tras el nuevo sprint.</h4>
        </div>
    </div>
</petclinic:layout>
