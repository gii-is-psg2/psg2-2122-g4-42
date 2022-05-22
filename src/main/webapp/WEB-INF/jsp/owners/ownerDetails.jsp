<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2 >Información del dueño</h2>


    <table class="table table-striped" >
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th>Ciudad</th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th>Número de teléfono</th>
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar dueño</a>

    <spring:url value="{ownerId}/delete" var="deleteUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Eliminar dueño</a>
  
    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir nueva mascota</a>

    <br/>
    <br/>
    <br/>
    <h2>Mascotas Y Visitas</h2>

    <table class="table table-striped" >
        <c:forEach var="pet" items="${owner.pets}">
            
            <tr >
                <td valign="top">
                    <spring:url value="{ownerId}/pets/{petId}/notif" var="notifUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                        <spring:param name="petId" value="${pet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(notifUrl)}" class="btn btn-default">
                        <span class="glyphicon glyphicon-bell" aria-hidden="true"></span>
                    </a>
                </td>
                <td valign="top" >
                    <dl class="dl-horizontal">
                        <dt>¿Eliminar mascota?</dt>
                        <dd><spring:url value="/owners/{ownerId}/pets/{petId}/delete" var="petUrl">
                                <spring:param name="ownerId" value="${owner.id}"/>
                                <spring:param name="petId" value="${pet.id}"/>
                            </spring:url>
                            <a href="${fn:escapeXml(petUrl)}">Eliminar</a></dd>
                            
                        <dt>Nombre</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Fecha de nacimiento</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Tipo</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top" >
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Fecha de visita</th>
                            <th>Descripción</th>
                            <th>Eliminar visita</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                <td><spring:url value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete" var="visitDeleteUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                    <spring:param name="visitId" value="${visit.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitDeleteUrl)}">Eliminar visita</a></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td >
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Editar mascota</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}">Añadir visita</a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>
    
    <h2>Reserva de habitaciones</h2>

    <table class="table table-striped" >
        <c:forEach var="pet" items="${owner.pets}">

            <tr >
                <td valign="top" >
                    <dl class="dl-horizontal">
                        <dt>Nombre</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Fecha de nacimiento</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Tipo</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top" >
                    <table class="table-condensed">
                        <thead>
                        <tr>
                        	<th>Habitación</th>
                            <th>Fecha de entrada</th>
                            <th>Fecha de salida</th>
                            
                        </tr>
                        </thead>
                        <c:forEach var="hotel" items="${pet.hotels}">
                            <tr>
                                
                                <td><c:out value="${hotel.room}"/></td>
                                <td><petclinic:localDate date="${hotel.date1}" pattern="yyyy-MM-dd"/></td>
                                <td><petclinic:localDate date="${hotel.date2}" pattern="yyyy-MM-dd"/></td>
                                
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/hotel/new" var="hotelUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(hotelUrl)}">¡Reserva una habitación para esta mascota!</a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
