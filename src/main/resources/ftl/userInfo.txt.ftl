<#list userInfo as user>
<#-- 循环结束后默认存在换行，如果需要再次换行可以单独在模板数据中指定lineSeparator以兼容Windows/Linux-->
${user.name},${user.age}
</#list>