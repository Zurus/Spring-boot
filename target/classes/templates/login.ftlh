<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <h3>Login page</h3>
    ${message?ifExists}
    <@l.login "/login" false/>
    <a href="http://localhost:8081/registration" class="btn btn-warning">Регистрация нового пользователя</a>
</@c.page>
