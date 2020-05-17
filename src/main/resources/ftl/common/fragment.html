<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<!-- 分页模块 -->
<th:block th:fragment="page">
    <div class="layui-row" th:if="${#pageUtil.pageInit(page,#request)}">
        <div class="layui-col-sm6">
            <div class="page-info">
                <span>显示 [[${(page.number*page.size)+1}]]/[[${(page.number*page.size)+page.numberOfElements}]] 条，共 [[${page.totalElements}]] 条记录</span>
                <select class="timo-select page-number">
                    <option th:value="${num*10}" th:selected="${page.size} eq ${num*10}" th:each="num:${#numbers.sequence(1,3)}">[[${num*10}]] 条/页</option>
                </select>
            </div>
        </div>
        <div class="layui-col-sm6">
            <ul class="pagination list-page">
                <li class="page-item" th:if="${#pageUtil.isPrevious(page)}"><a class="page-link"
                    th:href="@{${#pageUtil.pageHref(page.number)}}">上一页</a>
                </li>
                <li th:class="'page-item'+ ${#pageUtil.pageActive(page, pageCode, 'active')}"
                    th:each="pageCode:${#pageUtil.pageCode(page)}">
                    <a class="page-link" th:if="${#pageUtil.isCode(pageCode)} ne true"
                       th:href="@{${#pageUtil.pageHref(pageCode)}}">[[${pageCode}]]</a>
                    <a class="page-link breviary" th:if="${#pageUtil.isCode(pageCode)}">…</a>
                </li>
                <li class="page-item" th:if="${#pageUtil.isNext(page)}"><a class="page-link"
                     th:href="@{${#pageUtil.pageHref(page.number+2)}}">下一页</a>
                </li>
            </ul>
        </div>
    </div>
</th:block>

<!-- 日志展示模块 -->
<th:block th:fragment="log(entity)">
    <table class="layui-table timo-detail-table">
        <tbody>
        <tr>
            <th>操作时间</th>
            <th>操作人</th>
            <th>日志名称</th>
            <th>日志消息</th>
        </tr>
        <tr th:each="item:${#logs.entityList(entity)}">
            <td th:text="${#dates.format(item.createDate, 'yyyy-MM-dd HH:mm:ss')}"></td>
            <td th:text="${item.operName}"></td>
            <td th:text="${item.name}"></td>
            <td th:text="${item.message}"></td>
        </tr>
        </tbody>
    </table>
</th:block>