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
            <th>Nombre de la causa</th>
            <td><c:out value="${cause.name}"/></td>
        </tr>
        <tr>
            <th>Presupuesto objetivo</th>
            <td><c:out value="${cause.budgetTarget}"/></td>
        </tr>
        
    </table>

        <form:form modelAttribute="donation" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
        <h3>Depositar cantidad:</h3>
            <p style="font-size: 0.35cm;">Como máximo se va a donar la siguiente cantidad: <c:out value="${moneyLeft}"/></p>
            <petclinic:inputField label="Cantidad" name="amount"/>
            
        </div>
        <div class="form-group has-feedback">
        <h3>Nombre cliente:</h3>
            <petclinic:inputField label="Nombre" name="client"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Tramitar donacion</button>
            </div>
        </div>

    </form:form>


</petclinic:layout>