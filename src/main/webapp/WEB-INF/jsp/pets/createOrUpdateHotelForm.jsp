<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${hotel['new']}">New </c:if>Book a room</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${hotel.pet.name}"/></td>
                <td><petclinic:localDate date="${hotel.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${hotel.pet.type.name}"/></td>
                <td><c:out value="${hotel.pet.owner.firstName} ${hotel.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="hotel" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Room" name="room"/>
                <petclinic:inputField label="Start date" name="date"/>
                <petclinic:inputField label="End date" name="date2"/>
               
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petid" value="${hotel.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add Hotel</button>
                </div>
            </div>
        </form:form>

        <br/>
        <b>Previous Hotels</b>
        <table class="table table-striped">
            <tr>
                <th>Room</th>
                <th>Start Date</th>
                <th>End Date</th>
            </tr>
            <c:forEach var="hotel" items="${hotel.pet.hotels}">
                <c:if test="${!hotel['new']}">
                    <tr>
                    <td><c:out value="${hotel.room}"/></td>
                    <td><petclinic:localDate date="${hotel.date}" pattern="yyyy/MM/dd"/></td>   
                    <td><petclinic:localDate date="${hotel.date2}" pattern="yyyy/MM/dd"/></td>   
                       
                       
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>
