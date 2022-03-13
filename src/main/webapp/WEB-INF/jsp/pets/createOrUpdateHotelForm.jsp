<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="owners">
	<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#date").datepicker({
											dateFormat : 'yy/mm/dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>
			<c:if test="${hotel['new']}"> </c:if>RESERVA UNA HABITACIÓN PARA TU MASCOTA</h2>

        <b>Mascota</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Nombre</th>
                <th>Fecha de nacimiento</th>
                <th>Tipo</th>
                <th>Dueño</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${hotel.pet.name}" /></td>
                <td><petclinic:localDate
						date="${hotel.pet.birthDate}" pattern="yyyy/MM/dd" /></td>
                <td><c:out value="${hotel.pet.type.name}" /></td>
                <td><c:out
						value="${hotel.pet.owner.firstName} ${hotel.pet.owner.lastName}" /></td>
            </tr>
        </table>

        <form:form modelAttribute="hotel" class="form-horizontal">
            <div class="form-group has-feedback">
 				  <div class="control-group">
                    <petclinic:selectField name="room" label="Habitaciones disponibles " names="${rooms}" size="4" />
                </div>
                <petclinic:inputField label="Fecha de entrada de tu mascota" name="date1"   />
                <petclinic:inputField  label="Fecha de salida de tu mascota" name="date2"  />
               
                
            </div>
            

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petid"
						value="${hotel.pet.id}" />
                    <button class="btn btn-default" type="submit">¡Añade tu reserva!</button>
                </div>
            </div>
        </form:form>

        <br />
        <b>Reservas anteriores</b>
        <table class="table table-striped">
            <tr>
                <th>Habitación</th>
                <th>Fecha de entrada</th>
                <th>Fecha de salida</th>
            </tr>
            <c:forEach var="hotel" items="${hotel.pet.hotels}">
                <c:if test="${!hotel['new']}">
                    <tr>
                    <td><c:out value="${hotel.room}" /></td>
                    <td><petclinic:localDate date="${hotel.date1}"
								pattern="yyyy-MM-dd" /></td>   
                    <td><petclinic:localDate date="${hotel.date2}"
								pattern="yyyy-MM-dd" /></td>   
                       
                       
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>
