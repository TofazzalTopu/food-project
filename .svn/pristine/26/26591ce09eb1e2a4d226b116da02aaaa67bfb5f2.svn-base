<%@ page import="com.docu.commons.Message" %>
<%--
  Created by IntelliJ IDEA.
  User: jinia
  Date: 8/8/12
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="left-menu-section-items">
    <g:each in="${session.getAttribute('userFavouriteLink')}" var="menu">
        <div><a href="${request.contextPath}/${menu.url}">
            <span class="icon_${menu.imageCss}"><img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/></span>
            <span class="menuTxtSpan">${menu.linkTitle}</span>
        </a>
            <a class="del"
               name="${request.contextPath}/${menu.url}"
               onclick="deleteLinkAsFavourite($(this).attr('name'))"></a>
        </div>
    </g:each>
    <div >
        <a  href="${request.contextPath}/subAgent/show" id="subAgent_show">Add Sub Agent</a>

    </div>
    <div >
        <a  href="${request.contextPath}/subAgent/showSubAgentDetailSearch" id="subAgent_showSubAgentDetailSearch">Sub Agent Detail</a>

    </div>
    <div >
        <a  href="${request.contextPath}/subAgent/listSubAgent" id="subAgent_listSubAgent">Sub Agent List</a>

    </div>
    <div >
        <a  href="${request.contextPath}/subAgent/submitSubAgentToChecker" id="subAgent_submitSubAgentToChecker">Submit Sub Agent To Cheker</a>

    </div>
    <div >
        <a  href="${request.contextPath}/subAgent/checkSubAgent" id="subAgent_checkSubAgent">Check Sub Agent</a>

    </div>
    <div >
        <a  href="${request.contextPath}/subAgent/approveSubAgent" id="subAgent_approveSubAgent">Approve Sub Agent</a>

    </div>


</div>
