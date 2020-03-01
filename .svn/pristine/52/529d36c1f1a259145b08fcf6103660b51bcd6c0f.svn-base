%{--
- ****************************************************************
- Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
- DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
- This software is the confidential and proprietary information of
- Documentatm (TM) Limited ("Confidential Information").
- You shall not disclose such Confidential Information and shall use
- it only in accordance with the terms of the license agreement
- you entered into with Documentatm (TM) Limited.
- *****************************************************************
--}%
<!-- for jqgrid -->

<table id="feature-List-Grid"></table>

<div id="pager2"></div>


<script type="text/javascript">
    jQuery().ready(function () {
        var featureInfoGrid = jQuery("#feature-List-Grid").jqGrid({
                    url:"",
                    datatype: "local",
                    colNames:['id', 'Module Name', 'Feature Name', 'Action Name'],
                    colModel:[
                        {name:'id',index:'id', width:90,hidden: true},
                        {name:'module_name',index:'module_name', hidden: true },
                        {name:'featureName',index:'featureName', editable:true, width:200, formatter: 'showlink', formatoptions: {baseLinkUrl: 'show'}},
                        {name:'actionName',index:'actionName', width:250}
                    ],
                    rowNum:100000,
                    autowidth: true,
                    rowList:[10,20,30],
                    pager: '#pager2',
                    sortname: 'featureName',
                    viewrecords: true,
                    resizable : false,
                    grouping:true,
                    groupingView :{groupField :['featureName'],
                        groupCollapse : true},
                    height: 300,
                    caption:"Feature Info List",
                    loadComplete:function() {
                    }
                });
        featureInfoGrid.jqGrid('navGrid', '#pager2', {edit:false,add:false,del:false}, {}, {}, {}, {multipleSearch:true});
    });
    setTimeout(
            function() {
                $("#listModuleList").change();
            }, 1000);
</script>
