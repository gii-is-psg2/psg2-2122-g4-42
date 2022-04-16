<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">

    <h2 >Detalles de la causa</h2>


    <table class="table table-striped" >
        <tr>
            <th>Descripción</th>
            <td><b><c:out value="${cause.description}"/></b></td>
        </tr>
        <tr>
            <th>Organización</th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
        <tr>
            <th>Presupuesto objetivo</th>
            <td><c:out value="${cause.budgetTarget}"/></td>
        </tr>
    </table>

    <table class="table table-striped" >
        
            <h2 >Donaciones</h2>
            <tr>
                <th>Número de donación</th>
                <th>Cantidad</th>
                <th>Número de donación</th>
                <th>nombre</th>
            </tr>
            <c:forEach var="donation" items="${cause.donations}">
                <tr>
                    <td>
                        <c:out value="${donation.id}"/>
                    </td>
                    <td>
                        <c:out value="${donation.client}"/>
                    </td>
                    <td>
                        <c:out value="${donation.date}"/>
                    </td>
                    <td>
                        <c:out value="${donation.amount}"/>
                    </td>
                </tr>
            </c:forEach>
        
    </table>

    <spring:url value="/donation/new" var="addUrl">
        <spring:param name="causeId" value="${cause.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir donación</a>

    <br/>
    <br/>
    <br/>

</petclinic:layout>
