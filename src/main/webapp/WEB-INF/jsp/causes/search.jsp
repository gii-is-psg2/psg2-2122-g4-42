<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
    
    <jsp:body>
        <h2>Búsqueda externa</h2>
        <form:form modelAttribute="search"
                   class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Search" name="search"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Buscar</button>                    
                </div>
            </div>
        </form:form>
    </jsp:body>

    

</petclinic:layout>