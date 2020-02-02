<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<table>
    <tr>
        <th>as</th>
        <th>as</th>
        <th>as</th>
    </tr>
<tr>
    <c:forEach items="${contants}" var="contact">
    <tr>${contact.name}</tr>
    <tr>${contact.name}</tr>
    <tr>${contact.name}</tr>
    </c:forEach>
</tr>

</table>