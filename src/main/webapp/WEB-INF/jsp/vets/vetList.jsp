<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="vets">
    <h2>Veterinarios</h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especialidades</th>

            <th>Añadir especialidad</th>
            <th>Actualizar veterinario</th>

            <th>Borrar veterinario</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">Ninguna</c:if>
                </td>

                <td>
                    <a href='<spring:url value="/vets/${vet.id}/specialties/edit" htmlEscape="true"/>'>Añadir especialidad</a>
                </td>
                <td>
                    <spring:url value="/vets/{vetId}/edit" var="vetUpdateUrl">
                        <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vetUpdateUrl)}">Actualizar veterinario</a>
                </td>

                <td><spring:url value="/vets/{vetId}/delete" var="vetDeleteUrl">
                    <spring:param name="vetId" value="${vet.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(vetDeleteUrl)}">Borrar veterinario</a></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">Ver como XML</a>
            </td>  
            <td>
                <div class="col-sm-offset-2 col-sm-10">
                    <a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'>Añadir veterinario</a>
                </div>
            </td>            
        </tr>
        
    </table>
</petclinic:layout>
