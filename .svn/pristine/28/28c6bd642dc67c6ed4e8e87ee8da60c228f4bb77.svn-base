<%--
  Created by IntelliJ IDEA.
  User: feroz
  Date: 11/26/2014
  Time: 5:19 PM
--%>

<%
List<Map> levelOneMenuList = menuGenerator.getLevelOneMenu(1).unique()
List<Map> levelTwoMenuList = []
List<Map> levelThreeMenuList = []
Map first = [:]
%>
<table border="0" cellpadding="0" cellspacing="0" class="container">
    <tr>
        <td class="myMenu" align="right">
            <table class="rootVoices" cellspacing='0' cellpadding='0' border='0'>
                <tr>
                    <g:each in="${levelOneMenuList}" var="menuGroup">
                        <td class="rootVoice {menu: '${menuGroup.id}-${menuGroup.name.toString().replace(" ", "")}'}">${menuGroup.name}</td>
                        <g:if test="${(levelOneMenuList.size()-1) != levelOneMenuList.indexOf(menuGroup)}">
                            <td style="background: #fff; width: 2px; font-size: 0;"></td>
                        </g:if>
                    </g:each>
                </tr>
            </table>
        </td>
    </tr>
</table>

<g:each in="${levelOneMenuList}" var="menuGroup">
    <div id="${menuGroup.id}-${menuGroup.name.toString().replace(" ", "")}" class="menu">
        <% levelTwoMenuList = menuGenerator.getLevelTwoMenu(menuGroup.id).unique() %>
        <g:if test="${levelTwoMenuList.size()}">
            <g:each in="${levelTwoMenuList}" var="menuItem">
                <% levelThreeMenuList = menuGenerator.getLevelThreeMenu(menuItem.id).unique() %>
                <g:if test="${levelThreeMenuList.size() > 1}">
                    <a class="{menu:'${menuItem.id}-${menuItem.name.toString().replace(" ", "")}'}">
                        <span class="icon_${menuItem.imageCss}">
                            <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                        </span>
                        <span class="menuTxtSpan">${menuItem.name}</span>
                    </a>
                </g:if>
                <g:else>
                <% first = levelThreeMenuList[0] %>
                <a href="${request.contextPath}/${first.menuUrl}">
                    <span class="icon_${menuItem.imageCss}">
                        <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                    </span>
                    <span class="menuTxtSpan">${first.name}</span>
                </a>
                </g:else>
            </g:each>
        </g:if>
    </div>
</g:each>

<g:each in="${levelOneMenuList}" var="menuGroup">
    <% levelTwoMenuList = menuGenerator.getLevelTwoMenu(menuGroup.id).unique() %>
    <g:if test="${levelTwoMenuList.size()}">
        <g:each in="${levelTwoMenuList}" var="menuItem">
            <div id="${menuItem.id}-${menuItem.name.toString().replace(" ", "")}" class="menu">
                <% levelThreeMenuList = menuGenerator.getLevelThreeMenu(menuItem.id).unique() %>
                <g:if test="${levelThreeMenuList.size() > 1}">
                    <g:each in="${levelThreeMenuList}" var="featureAction">
                        <a href="${request.contextPath}/${featureAction.menuUrl}">
                            <span class="icon_${featureAction.imageCss}">
                                <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                            </span>
                            <span class="menuTxtSpan">${featureAction.name}</span>
                        </a>
                    </g:each>
                </g:if>
            </div>
        </g:each>
    </g:if>
</g:each>