<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        ${hello}
    </title>
</head>
<body>

    学生信息：<br>
    ID：${student.id}<br>
    姓名：${student.name}<br>
    年龄：${student.age}<br>
    <br>
    学生列表：<br>
    <table border="1">
        <tr>
            <th>序号</th>
            <th>ID</th>
            <th>姓名</th>
            <th>年龄</th>
        </tr>
        <#list students as student>
            <#if student_index % 2 == 0>
                <tr bgcolor="red">
            <#else>
                <tr bgcolor="blue">
            </#if>
                <td>${student_index}</td>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.age}</td>
            </tr>
        </#list>
    </table>
    <br>
    当前日期：${date?date}<br>
    null值的处理：${null!"值为null"}<br>
    判断值是否为null：
    <#if null??>
        不为null
    <#else >
        为null
    </#if>
    引用模板：<br>
    <#include "hello.ftl">

</body>