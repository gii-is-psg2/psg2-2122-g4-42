<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ page
pageEncoding="UTF-8"%> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="petclinic"
tagdir="/WEB-INF/tags" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="adoptions">
  <h2>Solicitudes de la adopción</h2>
  <div class="cards">
    <c:forEach items="${requestAdoptions}" var="request">
      <div class="card card-1">
        <div class="card__icon">
          <spring:url value="/owners/{ownerId}" var="ownerUrl">
            <spring:param name="ownerId" value="${request.owner.id}" />
          </spring:url>
          <a style="color:white" href="${fn:escapeXml(ownerUrl)}"
            ><c:out value="${request.status} - ${request.owner.firstName}"
          /></a>
        </div>
        <p class="card__exit"><i class="fas fa-times"></i></p>
        <h2 class="card__title">
          <c:out value="${request.description}" />
        </h2>
        <p class="card__apply">
          <span
            ><spring:url
              value="/adoptions/acceptRequest/{requestAdoptionId}"
              var="adoptionRequestAcceptUrl"
            >
              <spring:param name="requestAdoptionId" value="${request.id}" />
            </spring:url>
            <a
              class="card__link"
              href="${fn:escapeXml(adoptionRequestAcceptUrl)}"
              >ACEPTAR</a
            ></span
          >
          <span style="padding-left: 30rem">
            <spring:url
              value="/adoptions/denyRequest/{requestAdoptionId}"
              var="adoptionRequestDenyUrl"
              ><spring:param name="requestAdoptionId" value="${request.id}" />
            </spring:url>
            <a class="card__link" href="${fn:escapeXml(adoptionRequestDenyUrl)}"
              >DENEGAR</a
            ></span
          >
        </p>
      </div>
    </c:forEach>
  </div>
</petclinic:layout>
