<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 3/15/2017
  Time: 12:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="docuThemeRollerLayout">
    <title>View Salesman Stock</title>
</head>

<body>
<div class="main_container">
    <div class="content_container">
        <h1>View Salesman Stock</h1>
        <h3>Salesman Stock Information</h3>
        <form name='gViewSalesmanStock' id='gViewSalesmanStock'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hightx width1x">
                                Salesman
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:select name="salesMan" id="salesMan" from="${customerList}"
                                      optionKey="id"
                                      optionValue="name"
                                      class="validate[required] width200"
                                      onchange="getStock(this.value);"
                                      noSelection="['':'Select Salesman...']"/>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
        <div class="clear height10"></div>
        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#gViewSalesmanStock").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gViewSalesmanStock").validationEngine('attach');

        <g:if test="${customerList.size() == 1}" >
            $('#salesMan').val(${customerList.first().id});
            $('#salesMan').attr("disabled",true);
            loadGrid($('#salesMan').val());
        </g:if>
        <g:else>
            loadGrid('');
        </g:else>
    });

    function getStock(id){
        if(!$("#gViewSalesmanStock").validationEngine('validate')){
            MessageRenderer.renderErrorText("Please fill out the required field.");
            return false;
        }
        if(id==''){
            $("#jqgrid-grid").jqGrid("clearGridData");
            return false;
        }
        var url = '${resource(dir:'salesmanStock', file:'getStockList')}'+'?salesmanId='+id;
        $("#jqgrid-grid").setGridParam({url:url});
        $("#jqgrid-grid").trigger("reloadGrid");
    }

    function loadGrid(id){
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'salesmanStock', file:'getStockList')}'+'?salesmanId='+id,
            datatype: "json",
            colNames:[
                'SL',
                'ID',
                'productId',
                'Product Name',
                'Product Code',
                'Available Quantity'
            ],
            colModel:[
                {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'productId',index:'productId', width:0, hidden:true},
                {name:'productName', index:'productName',width:120,align:'left'},
                {name:'productCode', index:'productCode',width:100,align:'left'},
                {name:'availableQuantity', index:'availableQuantity',width:120,align:'center'}
            ],
            rowNum:-1,
//            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
//            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Salesman Stock",
            autowidth: true,
            height:'auto',
         //   autoHeight:true,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    }
</script>

</body>
</html>