<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="clinics">
	<jsp:body>
	<h2>Planes</h2>
		
	  <p> Usted tiene contratado el plan: <c:out value="${clinics}"/></p>

	<table id="clinicsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Nombre del plan</th>
				
				<th style="width: 150px;">Contrata algun plan</th>
			</tr>
		</thead>
	<tbody>
	<tr>
		
		<td> BASICO</td>
		<td><spring:url value="/plans/basico" var="basicoUpdateUrl">
                        </spring:url> <a href="${fn:escapeXml(basicoUpdateUrl)}">Contrata este plan</a>
                    </td>
                    </tr>
             <tr>
		<td> AVANZADO</td>
		<td><spring:url value="/plans/avanzado" var="advancedUpdateUrl">
                        </spring:url> <a href="${fn:escapeXml(advancedUpdateUrl)}">Contrata este plan</a>
                    </td>
                    </tr>
                    <tr>
		<td> PROFESIONAL</td>
		<td><spring:url value="/plans/profesional" var="proUpdateUrl">
                        </spring:url> <a href="${fn:escapeXml(proUpdateUrl)}">Contrata este plan</a>
                    </td>
                    </tr>
		
		
		
		
	

	
	</tbody>
	</table>


</jsp:body>
</petclinic:layout>