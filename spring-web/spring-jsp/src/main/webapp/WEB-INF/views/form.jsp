<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form >
    <p>Name: <form:input path="name" /></p>

    <p>Role:
        <form:select path="role">
            <form:options items="${roles }" />
        </form:select>
    </p>
    <p><form:button type="submit">저장</form:button></p>
</form:form>