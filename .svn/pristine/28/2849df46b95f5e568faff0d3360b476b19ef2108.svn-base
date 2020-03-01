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
        <a  href="${request.contextPath}/agent/show" id="agent_show">Add Agent</a>

    </div>
    <div >
        <a  href="${request.contextPath}/agent/showAgentDetailSearch" id="agent_showAgentDetailSearch">Agent Detail</a>

    </div>
    <div >
        <a  href="${request.contextPath}/agent/listAgent" id="agent_listAgent">Agent List</a>

    </div>
    <div >
        <a  href="${request.contextPath}/agent/submitAgentToChecker" id="agent_submitAgentToChecker">Submit Agent To Cheker</a>

    </div>
    <div >
        <a  href="${request.contextPath}/agent/checkAgent" id="agent_checkAgent">Check Agent</a>

    </div>
    <div >
        <a  href="${request.contextPath}/agent/approveAgent" id="agent_approveAgent">Approve Agent</a>

    </div>

    %{--<div class="buttons">--}%
    %{--<a class="btnAdd" href="${request.contextPath}/images/sample.pdf" id="sample_pdf" target="_new">Pdf</a>--}%
    %{--</div>--}%
</div>
