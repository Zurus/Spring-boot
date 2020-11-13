<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div>
    <@l.logout />
</div>
<form method="post" action="filter">
    <input type="text" name="anons"/>
    <button type="submit">Найти</button>
    <input type="hidden" name="_csrf" value="{_csrf.token}" />
</form>
<section class="jumbotron text-center">
    <div class="container mt-5">
        <#list posts as post>
            <div>
                <h3>${post.anons}</h3>
                <p>${post.full_text}</p>
                <strong>${post.authorName}</strong>
                <div>
                    <#if post.fileName??>
                        <img src="/img/${post.fileName}">
                    </#if>
                </div>
            </div>
        </#list>
    </div>
</section>
</@c.page>

