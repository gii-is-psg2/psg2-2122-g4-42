<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">

    <h2>Hacer Donacion</h2>
      <table class="table table-striped">
        <tr>
         
        <tr>
            <th>Presupuesto objetivo</th>
            <td><c:out value="${cause.budgetTarget}"/></td>
             <td><c:out value="${donation.client}"/></td>
        </tr>
        
    </table>
    


        <form:form modelAttribute="donation" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
        <h3>Depositar cantidad:</h3>
            <petclinic:inputField label="Cantidad" name="amount"/>
        </div>
        <div class="form-group has-feedback">
        <h3>Nombre Cliente:</h3>
            <petclinic:inputField label="Nombre" name="client"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Transmitar Donacion</button>
            </div>
        </div>

    </form:form>


</petclinic:layout>