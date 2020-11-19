<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout/>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="title" placeholder="Введите титульное сообщение"/>
            <input type="text" name="anons" placeholder="Введите аннос"/>
            <input type="text" name="full_text" placeholder="Введите текст"/>
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>
</@c.page>

