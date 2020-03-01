<%@ page import="org.springframework.beans.factory.annotation.Autowired;" %>

<%
    List<Map> levelOneMenuList = menuGenerator.getLevelOneMenu(1).unique()
    List<Map> levelTwoMenuList = []
    List<Map> levelThreeMenuList = []
    String[] urlParts
    Map first = [:]
    // MenuController menuController = new MenuController()
    List list = [controllerName, actionName]
    if (params.searchConfigurationId) {
        list.add(params.searchConfigurationId)
    }
    if ("${isDateEnabled}" == 'true') {
        list.add("${isDateEnabled}")
    }
%>

<style type="text/css">
.active-link {
    color: #E17009 !important;
    font-weight: bold !important;
}
</style>

<script type="text/javascript">
    function getCurrentMenuItemIndex() {
        var index = $("#left-menu-accordion h3").index($("#${list.join('_')}").closest(".ui-accordion-content").prev('h3'));
        return index;
    }

    $(document).ready(function () {
        $("#left-menu-accordion").accordion({
            collapsible: true,
            autoHeight: false,
            active: false
        });
        $("#${list.join('_')}").addClass('active-link');
        $("#left-menu-accordion").accordion('activate', getCurrentMenuItemIndex());
    });
    function searchKeyPress(e) {
        if (typeof e == 'undefined' && window.event) { e = window.event; }
        if (e.keyCode == 13)
        {
            DocuAjax.json("${request.contextPath}/menu/quickSearch", {quick_search : $("#quick_search").val()}, function(result){
                if(result.actionUrl != ""){
                    location.href = '${request.contextPath}/'+result.actionUrl;
                }
            });
        }
    }
    function addLinkAsFavourite(){
        DocuAjax.html('${request.contextPath}/menu/addUserFavouriteLink', {url : document.URL}, function(result){
            $("#favouriteLink").html(result);
        });
    }
    function addAgent(){
        DocuAjax.html('${request.contextPath}/menu/addAgent', {url : document.URL}, function(result){
            $("#favouriteLink").html(result);
        });
    }

    function deleteLinkAsFavourite(link){
        DocuAjax.html('${request.contextPath}/menu/deleteFavouriteLink', {url : link, isDeleted : true}, function(result){
            $("#favouriteLink").html(result);
        });
    }

</script>
<div class="left_nav">
    <div id="left-menu-accordion">
        <g:each in="${levelOneMenuList}" var="menuGroup">
            <h3>
                <a href="javascript:void(0)" tabindex="-1">${menuGroup.name.toString()}</a>
            </h3>
            <div id="${menuGroup.id}-menu">
                <div class="left-menu-section-items">
                    <% levelTwoMenuList = menuGenerator.getLevelTwoMenu(menuGroup.id).unique() %>
                    <g:if test="${levelTwoMenuList.size() > 0}">
                        <g:each in="${levelTwoMenuList}" var="menuItem">
                            <% levelThreeMenuList = menuGenerator.getLevelThreeMenu(menuItem.id).unique() %>
                            <g:if test="${levelThreeMenuList.size() > 1}">
                                <g:each in="${levelThreeMenuList}" var="featureAction">
                                    <div>
                                        <%
                                            urlParts = featureAction.menuUrl.split("/")
                                        %>
                                        <a href="${request.contextPath}/${featureAction.menuUrl}" id="${urlParts[0]}_${urlParts[1]}">
                                            <span class="icon_${menuItem.imageCss}">
                                                <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                                            </span>
                                            <span class="menuTxtSpan">${featureAction.name}</span>
                                        </a>
                                    </div>
                                </g:each>
                            </g:if>
                            <g:else>
                                <div>
                                    <%
                                        first = levelThreeMenuList[0]
                                        urlParts = first.menuUrl.split("/")
                                    %>
                                    <a href="${request.contextPath}/${first.menuUrl}" id="${urlParts[0]}_${urlParts[1]}">
                                        <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                                        ${first.name}
                                    </a>
                                </div>
                            </g:else>
                        </g:each>
                    </g:if>
                </div>
            </div>
        </g:each>
        <h3>
            <a href="javascript:void(0)" tabindex="-1">Most Visited</a>
        </h3>
<div class="left-menu-section-items">
        <% levelThreeMenuList = menuGenerator.getLevelMostVisitedMenu() %>
        <g:if test="${levelThreeMenuList.size() > 1}">
            <g:each in="${levelThreeMenuList}" var="featureAction">
                <div>
                    <%
                        urlParts = featureAction.menuUrl.split("/")
                    %>
                    <a href="${request.contextPath}/${featureAction.menuUrl}" id="${urlParts[0]}_${urlParts[1]}">
                        <span class="icon">
                            <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                        </span>
                        <span class="menuTxtSpan">${featureAction.name}</span>
                    </a>
                </div>
            </g:each>
        </g:if>
        <g:else>
            <div>
                <%
                    first = levelThreeMenuList[0]
                    urlParts = first.menuUrl.split("/")
                %>
                <a href="${request.contextPath}/${first.menuUrl}" id="${urlParts[0]}_${urlParts[1]}">
                    <img src="${request.contextPath}/images/leftmenu/leftmenu_blank.png" alt="icon" class="icon"/>
                    ${first.name}
                </a>
            </div>
        </g:else>
    </div>
</div>

</div>