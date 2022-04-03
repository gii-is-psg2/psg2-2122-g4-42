<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ page
pageEncoding="UTF-8"%> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="petclinic"
tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
  <h2>Crear adopción</h2>
  <form:form modelAttribute="adoption" class="form-horizontal">
    <div class="form-group has-feedback">
      <petclinic:inputField label="Descripción" name="description" />
      <div class="control-group">
        <label class="col-sm-2 control-label">Pet </label>
        <div class="col-sm-10">
          <select name="pet" id="pet" class="form-control" size="3">
            <c:forEach items="${pets}" var="pet">
              <option value="${pet.id}">${pet.name}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button class="btn btn-default" type="submit">
          Formalizar adopción
        </button>
      </div>
    </div>
  </form:form>
</petclinic:layout>
