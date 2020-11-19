<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<form method="post" action="filter">
    <input type="text" name="anons"/>
    <button type="submit">Найти</button>
    <input type="hidden" name="_csrf" value="{_csrf.token}" />
</form>
<section class="jumbotron text-center">
    <div class="container mt-5">
        <#list posts as post>
            <div class="card my-3" style="width: 18rem;">
                <#if post.fileName??>
                    <img src="/img/${post.fileName}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <h3>${post.anons}</h3>
                    <p>${post.full_text}</p>
                    <div class="card-footer text-muted">
                        ${post.authorName}
                    </div>
                </div>
            </div>
        </#list>
    </div>
</section>
</@c.page>

