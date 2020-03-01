<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants;com.bits.bdfp.inventory.workflow.Workflow" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Workflow Priority Sequence</title>

<script type="text/javascript" language="Javascript">
    var allElements = new Array();
    var elementIndex = 0;
    var lastSel = -1;
    var priorityText = '';
    function checkForValue(elem) {
        if (!elem) {
            return [false, "Please enter marks."];
        }
        else {
            return [true, ""];
        }
    }
    var updateOrderEditor = {
        onEnterKeyPressToGridCell: function () {
            var callback = {
                type: 'keydown',
                fn: function (e) {
                    var key = e.charCode || e.keyCode;

                    if (key == 9) {
                        if (this.name == allElements[allElements.length - 1].name) {
                            e.preventDefault();
                            jQuery('#jqgrid-grid').restoreRow(lastSel);
                            updateOrderEditor.editGridCell(lastSel)

                        }
                    }
                    else if (key == 13) {
                        jQuery('#jqgrid-grid').restoreRow(lastSel);
                        updateOrderEditor.editGridCell(lastSel)

                    }
                }
            };

            return callback;
        },

        editGridCell: function (rowid) {
            var myGrid = $("#jqgrid-grid");
            var priority = priorityText.value
            if(priority && rowid){
                if(isNaN(priority)){
                    alert("Please enter number as priority sequence");
                }
                else{
                    selRowId = myGrid.jqGrid('getGridParam', 'selrow');
                    celValue = myGrid.jqGrid('setCell', selRowId, 'priority_sequence', priority);

                }

            }

            else{
                alert("Please enter priority sequence")
            }





        }
    }
    $(document).ready(function() {

        $("#jqgrid-grid").jqGrid({
            url:'',
            datatype: "json",
            colNames:[
                'Sl',
                'Id',
                'Name',
                'Menu Name',
                'Priority Sequence'

            ],
            colModel:[
                {name:'sl',index:'sl', width:30},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'name', index:'name',width:100,align:'left'},
                {name:'menu_name', index:'menu_name',width:100,align:'left'},
                {name:'priority_sequence', index:'priority_sequence',width:70,align:'left', editable: true, edittype: 'text', resizable: true,
                    editoptions: {dataInit: function (elem) {
                        priorityText = elem;
                        $(elem).focus(function () {
                            this.select();
                        })
                    }, dataEvents: [updateOrderEditor.onEnterKeyPressToGridCell()]}, editrules: {custom: true, custom_func: checkForValue}}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Information",
            autowidth: true,
            height: 250,

            scrollOffset: 0,
            loadComplete: function() {

                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');


//                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<a  href="javascript:loadApprovalInfo(' + id + ')">' + 'Approval History' + '</a>'});



                }

            },
            onSelectRow: function(rowid, status) {
                elementIndex = 0;
                allElements = new Array();
                if (rowid && rowid !== lastSel) {

                    jQuery('#jqgrid-grid').restoreRow(lastSel);
                    $("#jqgrid-grid").editRow(rowid, true, false, false, false, false);
                    lastSel = rowid;
                }
                if (rowid == lastSel) {
                    jQuery("#jqgrid-grid").jqGrid('editRow', rowid, true);
                    lastSel = rowid;

                }
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });



    });

    function loadDataByEnterprise(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'workflow', file:'prioritySequenceList')}?id=' + $('#enterpriseConfiguration').val()
   });
        $("#jqgrid-grid").trigger("reloadGrid");

    }

    function savePrioritySequence() {

        var data = jQuery("#gFormWorkflowSearch").serialize();




        var priorityData = jQuery("#jqgrid-grid").jqGrid('getRowData');
        var dataLength = priorityData.length;
        for (var k = 0; k < dataLength; k++) {
            data = data + '&items.priority[' + k + '].id=' + priorityData[k].id;
            data = data + '&items.priority[' + k + '].priority=' + priorityData[k].priority_sequence;

        }


        jQuery.ajax({
            type:'post',
            data:data,
            url: '${request.contextPath}/${params.controller}/savePrioritySequence',
            success:function(result, textStatus) {
                if (result.type == 1) {
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormWorkflowSearch');
                }
                MessageRenderer.render(result);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
            },
            complete:function(){
            },
            dataType:'json'
        });
        return false;
    }


</script>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="workflow.create.label" default=" Workflow Priority Sequence"/></h1>
        <h3><g:message code="workflow.info.label" default="Workflow Priority Sequence Information"/></h3>
        <form name='gFormWorkflowSearch' id='gFormWorkflowSearch'>

            <div id="remote-content-workflow"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x">
                                <g:message code='workflow.enterprise.label' default='Enterprise' />

                            </label>

                        </td>
                        <td>
                            <g:if test="${list}">
                                <g:if test="${list.size()==1}">
                                    <div  class='element-input inputContainer'>

                                        <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                                        <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="${list[0].id}" />
                                    </div>
                                </g:if>
                                <g:else>
                                    <div  class='element-input inputContainer'>
                                        <div id="enterpriseList" style="width: 350px;"></div>
                                        <script type="text/javascript">

                                            jQuery(document).ready(function () {
                                                var data=${result}
                                                        $("#enterpriseList").empty();
                                                $("#enterpriseList").flexbox(data , {
                                                    watermark: "Select Enterprise",
                                                    width: 260,
                                                    onSelect: function() {

                                                        $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());

                                                    }
                                                });
                                                $('#enterpriseList_input').blur(function() {
                                                    if($('#enterpriseList_hidden').val() == ''){
                                                        $("#enterpriseList").val("");
                                                    }
                                                });

                                            });
                                        </script>

                                        <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />


                                    </div>
                                </g:else>

                            </g:if>
                            <g:else>
                                <div  class='element-input inputContainer'>
                                    <g:textField name="enterPriseName" readonly="readonly" value="" />
                                    <script type="text/javascript">
                                        jQuery(document).ready(function () {
                                            MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.",0)
                                        });
                                    </script>
                                </div>
                            </g:else>

                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="loadDataByEnterprise();"/></span>

                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="jqgrid-grid"></table>

                    <div id="jqgrid-pager"></div>
                </div>
            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="save-button" id="save-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                            value="Save"
                                            onclick="savePrioritySequence();"/></span>

            </div>
        </form>
    </div>
</div>