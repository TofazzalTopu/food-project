<%@ page import="com.docu.accesscontrol.ModuleInfo ; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 2/29/12
  Time: 6:15 PM
  To change this template use File | Settings | File Templates.
--%>

<title>Menu Group Sorting</title>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<g:render plugin="docuaccesscontrol" template='/accesscontrol/common/pluginmessage'/>
<meta name="layout" content='${pluginStyle}'/>
<style type="text/css">
.sort-item-css {
    background-color: #E8F3FB;
    border: 1px solid #92B7D0;
    border-radius: 7px 7px 7px 7px;
    margin: 3px 0 0;
    padding: 3px;
    cursor: pointer;
    list-style: none;
}
</style>
<script type="text/javascript">
     $(document).ready(function(){
        $("#moduleList").val(${moduleInformatonId});
        $("#moduleList").change();
    });

    var MenuGroupSort = {
        onModuleInfoChange: function(moduleInfoId) {
            if (moduleInfoId == 'null') {
                $('#list-html').html('');
                return false;
            }
            MenuGroupSort.listMenuGroup(moduleInfoId);
        },

        listMenuGroup: function(moduleInfoId) {
            var url = "${request.contextPath}/${params.controller}/ajaxMenuGroupListByModuleInfo";
            var data = {moduleInfoId: moduleInfoId};
            MenuGroupSort.ajax(url, data, function(html) {
                $('#list-html').html(html);
            });
        },

        listMenuItem: function(menuGroupId) {
            var url = "${request.contextPath}/${params.controller}/ajaxMenuItemListByMenuGroup";
            var data = {menuGroupId: menuGroupId};
            MenuGroupSort.ajax(url, data, function(html) {
                $('#list-html').html(html);
            });
        },

        editMenuGroup: function(menuGroupId, title) {
            location.href = "${request.contextPath}/${params.controller}/edit?id=" + menuGroupId + "&moduleInfoId=" + $('#moduleList').val() + "&title=" + title
        },

        reIndexPosition: function(inputSelector) {
            $('.' + inputSelector).each(function(index, element) {
                $(element).val(index + 1);
            });
        },

        save: function() {
            var url = "${request.contextPath}/${params.controller}/" + $('#cmd').val();
            var data = $('#menu-item-form').serialize();
            MenuGroupSort.json(url, data, function(html) {
                MessageRenderer.render(html.message)
            });
        },

        ajax: function(url, data, callback) {
            callback = typeof callback == "function" ? callback : function () {
            };
            $.ajax({
                        url: url,
                        data:data,
                        dataType: "html",
                        type:"post",
                        success: function(response) {
                            callback(response);
                        }
                    });
        },

        json: function(url, data, callback) {
            callback = typeof callback == "function" ? callback : function () {
            };
            $.ajax({
                        url: url,
                        data:data,
                        dataType: "json",
                        type:"post",
                        success: function(response) {
                            callback(response);
                        }
                    });
        }
    }
</script>
<g:if test="${flash.message}">
    <script type="text/javascript">
        $(document).ready(function(){
            MessageRenderer.render(${flash.message})
        });
    </script>
</g:if>

<h1>Menu Group Sorting</h1>

<div style="width:800px;">
    <form id="menu-item-form">
        <fieldset>
            <legend>Menu Group</legend>
            <fieldset>
                <div>
                    <div class="block-title">
                        <div class="element-title">Module</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">
                        <div class="element-input"><g:select name="moduleInfoId" id="moduleList"
                                                             from="${ModuleInfo.list()}" optionKey="id"
                                                             noSelection="['null':'-Select Module-']"
                                                             onchange="MenuGroupSort.onModuleInfoChange(this.value)"/></div>

                        <div class="clear"></div>
                    </div>
                </div>
            </fieldset>
            <br/>

            <div id="list-html"></div>

        </fieldset>
        <br/>

        <div class="buttons">
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="menuGroup.saveButton.label" default="Save"/>"
                   onclick="MenuGroupSort.save()"/>
        </div>

    </form>
</div>