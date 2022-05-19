<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
	<jsp:body>
	<h2>Búsqueda externa</h2>
        <form:form modelAttribute="search"
                   class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Búsqueda" name="text"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Buscar</button>                    
                </div>
            </div>
        </form:form>
	<div style="text-align: center;">
		<c:forEach items="${results}" var="result">
				<c:set var="searched" value="${fn:split(result,',')}"/>
				<a href="${searched[0]}"><c:out value="${searched[1]}"/></a><br/>
			</c:forEach>
	</div>
		
	<br/>
	<h2>Causas</h2>

	<table id="causesTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 120px">Presupuesto total logrado</th>
				<th style="width: 120px">Objetivo del presupuesto</th>
				<th style="width: 120px">Hacer donacion</th>
				<th style="width: 120px">Detalles de la causa</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${causes}" var="cause">
				<tr>
					<td><c:out value="${cause.name} " /></td>
					
					<td><c:out value="${cause.moneyRaised} " /></td>
					
						
					<td><c:out value="${cause.budgetTarget} " /></td>

					<td><spring:url value="/causes/{causeId}/donations/new" var="causeUpdateUrl">
                            <spring:param name="causeId" value="${cause.id}" />
                        </spring:url> <a href="${fn:escapeXml(causeUpdateUrl)}">Hacer una donación</a>
                    </td>

					<td><spring:url value="/causes/detail/{causeId}" var="causeDetailUrl">
							<spring:param name="causeId" value="${cause.id}" />
						</spring:url> <a href="${fn:escapeXml(causeDetailUrl)}">Ver detalles de la
							causa</a></td>
			</c:forEach>
		</tbody>

	</table>
	<table class="table-buttons">
        <tr>
            <td>
                <div class="col-sm-offset-2 col-sm-10">
                    <a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'>Añadir Causa</a>
                </div>
            </td>            
        </tr>
        
    </table>

</jsp:body>
</petclinic:layout>