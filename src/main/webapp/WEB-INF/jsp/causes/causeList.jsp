<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">

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


</petclinic:layout>