<%@ page import="com.docu.commons.Message" %>
<%--
  Created by IntelliJ IDEA.
  User: nasrin
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
    <div class="buttons">
        <a class="btnAdd" href="#" id="add_to_fav" onclick="addLinkAsFavourite()">Add</a>
        %{--<a class="btnAdd ui-corner-all mar_left10" style="" href="#" id="add_to_fav" onclick="addLinkAsFavourite()"></a>--}%
    </div>
    <div class="buttons">
        <a class="btnAdd" href="${request.contextPath}/images/sample.pdf" id="sample_pdf" target="_new">Pdf</a>
    </div>
</div>
