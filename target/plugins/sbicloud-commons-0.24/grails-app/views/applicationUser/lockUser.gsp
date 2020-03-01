<%--
  Created by IntelliJ IDEA.
  User: Bipul
  Date: 7/3/13
  Time: 10:26 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title> <g:message code="applicationUser.lockUser.title" default="Lock User"/> </title>

<script type="text/javascript">

    $(document).ready(function(){
        applicationUserEditor.initializeApplicationUserGrid()
    });
 var applicationUserEditor = {

     loadOfficeDropDown : function(){
         var countryId = $('#countryId').val();
         if(countryId == "" || countryId == "null"){
             return false;
         }
         var url = "${request.contextPath}/${params.controller}/getPhysicalOfficeByCountry";
         var paramdata = {countryId:$("#countryId").val()}
         DocuAjax.json(url, paramdata, function (response) {
             officeFlexBox.setData(response);
             applicationUserEditor.onChangeFlexBox(0);
         });
     } ,

     initializeApplicationUserGrid : function(){

         jQuery("#applicationUserGrid").jqGrid({
             url:"",
             datatype: "local",
             colNames:['', 'User Name','Full Name', 'Designation',  'Pin No','Account Locked','Create Date','applicationUserId'],
             colModel:[
                 {name:'id',index:'id',sortable:'false', width:50},
                 {name:'userName',index:'userName', align: 'left',sortable: false},
                 {name:'fullName',index:'fullName', align: 'left', width:200,sortable: false},
                 {name:'designation',index:'designation', align: 'left',width:150,sortable: false},
                 {name:'pinNo',index:'pinNo', align: 'left',width:100,sortable: false},
                 {name:'accountLocked',index:'accountLocked',width:100, align: 'left',sortable: false } ,
                 {name:'dateCreated',index:'dateCreated', align: 'left',width:100 ,hidden: true,formatter: 'date', formatoptions: {newformat: 'd-m-Y'},sortable: false } ,
                 {name:'applicationUserId',index:'applicationUserId', align: 'left',width:100,hidden: true}
             ],
             rowNum:-1,
             autowidth: true,
             pager: '#applicationUserPager',
             sortname: 'id',
             viewrecords: true,
             resizable : false,
             grouping:true,
             altRows:true,
             height: 250,
             width:800,
             cellEdit:true,
             caption:"Unapproved Procurement Plan List ",
             loadComplete:function() {
                 var ids = jQuery("#applicationUserGrid").jqGrid('getDataIDs');
                 for (key in ids) {
                     jQuery("#applicationUserGrid").jqGrid('setRowData', ids[key], {id:'<input type="checkbox" class="applicationUser_checkbox"" value="' + ids[key] + '" />'});
                 }
             }


         });

         jQuery("#applicationUserGrid").jqGrid('navGrid', '#applicationUserPager', {edit:false,add:false,del:false}, {}, {}, {}, {multipleSearch:true});
     },

     onChangeFlexBox : function(officeInfoId){
         jQuery("#applicationUserGrid").jqGrid('setGridParam', {url:"${request.contextPath}/${params.controller}/getGridDataByOffice?officeInfoId=" + officeInfoId , datatype:'json',page:1}).trigger("reloadGrid");
     } ,

     lockUser : function(){
         var allData = {}
         var i = 0
         $('.applicationUser_checkbox').each(function() {
             if (this.checked) {
                 var userId = $(this).val();
                 var rowObj = $('#requisitionApprovalGrid').jqGrid("getRowData", userId)
                 allData['items.applicationUser[' + i + '].id'] = userId

                 i++
             }
         })

         if (i < 1) {
             MessageRenderer.render({"messageBody":"Please select at least one user.", "messageTitle":"Application User", "type":"0"});
             return false
         }
         SubmissionLoader.showTo();
         DocuAjax.json("${request.contextPath}/${params.controller}/lock", allData, function(result) {
             MessageRenderer.render(result);
             applicationUserEditor.onChangeFlexBox($("#physicalOfficeInfoId").val());
             SubmissionLoader.hideFrom();
         });
     },
     unlockUser : function(){
         var allData = {}
         var i = 0
         $('.applicationUser_checkbox').each(function() {
             if (this.checked) {
                 var userId = $(this).val();
                 var rowObj = $('#requisitionApprovalGrid').jqGrid("getRowData", userId)
                 allData['items.applicationUser[' + i + '].id'] = userId

                 i++
             }
         })

         if (i < 1) {
             MessageRenderer.render({"messageBody":"Please select at least one user.", "messageTitle":"Application User", "type":"0"});
             return false
         }
         SubmissionLoader.showTo();
         DocuAjax.json("${request.contextPath}/${params.controller}/unlock", allData, function(result) {
             MessageRenderer.render(result);
             applicationUserEditor.onChangeFlexBox($("#physicalOfficeInfoId").val());
             SubmissionLoader.hideFrom();
         });
     }

    }
</script>

<div class="main_container">
    <h1><g:message code="applicationUser.lockUser.title" default="Lock User"/></h1>

    <div class="content_container">
        <g:form id="formApplicationUser_lock" name="formApplicationUser_lock">
            <div class="element_row_content_container lightColorbg pad_bot0">
                <label class="txtright bold hight1x width1x">
                    <g:message code='applicationUser.country.label' default='Country'/>
                </label>

                <div class='element-input inputContainer setup-css-numeric-currency'>

                    <g:select name="countryId" id="countryId" from="${countryList}"
                              optionKey="id"
                              class="validate[required,funcCall[isDropdownSelected]] width173 mar_left14"
                              noSelection="['null': 'Select country']"
                              onchange="applicationUserEditor.loadOfficeDropDown()"/>
                </div>
            </div>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <label class="txtright bold hight1x width1x">
                    <g:message code='applicationUser.officeInfo.label' default='Office'/>
                </label>

                <div class='element-input inputContainer width200'>
                    <docu:flaxBox id="physicalOfficeInfoId" name="officeInfo.id" var="officeFlexBox" optionKey="id"
                                  optionValue="officeName" data="${physicalOfficeInfoList}">
                        containerClass: "ffb width200",
                        inputClass: "ffb-input width150 validate[required]",
                        onSelect: function(map){
                        %{--clearOfficeData();--}%
                        applicationUserEditor.onChangeFlexBox(map.id);
                        }

                    </docu:flaxBox>

                </div>
            </div>

            <div class="clear height5"></div>

            <div class="jqgrid-container blue_grid">
                <table id="applicationUserGrid" width="800px"></table>

                <div id="applicationUserPager"></div>
            </div>

            <div style="padding:10px;float:left">
                <a href="javascript:void(0)"
                   onClick="JqGridManager.checkAllCheckbox('applicationUser_checkbox')">Select All</a>
                &nbsp;&nbsp;|&nbsp;
                <a href="javascript:void(0)"
                   onClick="JqGridManager.unCheckAllCheckbox('applicationUser_checkbox')">Select None</a>
            </div>

        </g:form>
    </div>
    <div class="clear"></div>
    <div class="buttons">
        <input type="button" name="post" id="lockButton"class="ui-button ui-widget ui-state-default ui-corner-all"
               onclick="applicationUserEditor.lockUser();" value="Lock"/>

        <input type="button" name="post" id="unlockButton" class="ui-button ui-widget ui-state-default ui-corner-all"
               onclick="applicationUserEditor.unlockUser();" value="Unlock"/>
    </div>
</div>