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
        <tr>
            <c:forEach var="donation" items="${cause.donations}">
                <td valign="top" >
                    <dl class="dl-horizontal">
                        <dt>Cantidad</dt>
                        <dd><c:out value="${donation.amount}"/></dd>
                        <dt>Fecha de donación</dt>
                        <dd><petclinic:localDate date="${donation.date}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Nombre del donante</dt>
                        <dd><c:out value="${donation.client}"/></dd>
                    </dl>
                </td>
            </c:forEach>
        </tr>
    </table>

    <spring:url value="/donation/new" var="addUrl">
        <spring:param name="causeId" value="${cause.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir donación</a>

    <br/>
    <br/>
    <br/>

</petclinic:layout>
