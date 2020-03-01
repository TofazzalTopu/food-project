

<script type="text/javascript">
    function isInnerObject(objToTest) {
        if(objToTest.id != null)
            return true;
    }

    $(document).ready(function(){
        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/getModuleInfoGridJSON',
            datatype: "json",
            colNames:[
                'SL',
                'ID',

                'Module Name',
                'Module Code',
                'URL',
                'File Name',
                'Description'
            ],
            colModel:[
                {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
                {name:'id',index:'id', width:0, hidden:true},

                {name:'moduleName', index:'moduleName', width:100, sortable:true, align:'left'},
                {name:'moduleCode', index:'moduleCode', width:80, sortable:true, align:'left'},
                {name:'url', index:'url', width:100, sortable:true, align:'left'},
                {name:'fileName', index:'fileName', width:100, sortable:true, align:'left'},
                {name:'description', index:'description', width:150, sortable:true, align:'left'}
            ],
            rowNum:30,
            rowList:[30,40,50],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All  Module Infos",
            autowidth: true,
            height:243,
            width:800,
            scrollOffset: 0,
            loadComplete: function(){
            },
            onSelectRow: function(rowid, status){
                var moduleinfoId = $("#jqgrid-grid").getCell(rowid, 'id');
                $.ajax({
                    type: "POST",
                    url: "${request.contextPath}/${params.controller}/getModuleInfoJSON?id="+ moduleinfoId,
                    success: function(data) {
//                        if (data.entity == null) {
//                            alert('Selected ModuleInfo might have been deleted by someone');
//                        } else {
                        //var entity = data.entity;
                        $('#id').val(data.id);
                        $('#version').val(data.version);

                        $('#moduleName').val(data.moduleName);
                        $('#moduleCode').val(data.moduleCode);
                        $('#url').val(data.url);
                        $('#gspFileName').val(data.fileName);
                        $('#description').val(data.description);

                        $('#create').attr('value', 'Update ModuleInfo');
                        $('#delete-button').show();
//                        }
                    },
                    dataType:'json'
                });
                // End of ajax
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid','#jqgrid-pager',{edit:false,add:false,del:false});
    });
</script>

<div class="jqgrid-container" style="width: 800px">
    <table id="jqgrid-grid"></table>
    <div id="jqgrid-pager"></div>
</div>
<div id="dialog-confirm" title="Delete alert" style="display: none">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
