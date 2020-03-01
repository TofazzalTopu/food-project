<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 1/13/2019
  Time: 03:17 PM
--%>

<meta name="layout" content="docuThemeRollerLayout"/>
<title>Create External Product Stock </title>

<div class="main_container">
    <div class="content_container">
        <h1>Create External Product Stock </h1>

        <h3>External Product Stock Details</h3>

        <div style="width:100%;">
            <g:render template="create" model="[externalProductList: externalProductList,list:list, enterpriseList: enterpriseList]"/>
            <br/>
            <g:render template="script" model="[externalProductList: externalProductList,list:list, enterpriseList: enterpriseList]"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>

        <div id="dialog-confirm-externalProductStock" title="Cancellation alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Selected order(s) and all associated data will be permanently cancelled/deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>


