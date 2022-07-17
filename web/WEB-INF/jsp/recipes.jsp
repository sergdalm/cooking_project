<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>

    <title>Рецепты</title>
</head>
<body>
    <h1>Рецепты</h1>
    <table>
        <tr>
            <td>Название</td>
            <td>Калории</td>
            <td>Белки</td>
            <td>Жиры</td>
            <td>Углеводы</td>
        </tr>
        <c:forEach var="recipe" items="${requestScope.recipes}">
            <tr>
                <td>${recipe.name}</td>
                <td>${recipe.kilocalories}</td>
                <td>${recipe.protein}</td>
                <td>${recipe.fat}</td>
                <td>${recipe.carbohydrate}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
