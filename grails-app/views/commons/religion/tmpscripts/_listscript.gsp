


<script type="text/javascript">
    $(document).ready(function() {
         $("#jqgrid-grid-religion").jqGrid({
      url:'${resource(dir:'religion', file:'listJSON')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',

        
        'Religion Name',
        'Description'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},

        
                
                    {name:'religionName', index:'religionName',width:100,align:'left'}
                
        ,
                
                    {name:'description', index:'description',width:100,align:'left'}
                
        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-religion',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"Religion list",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
            executeEditReligion(rowid);
      }
    });
    $("#jqgrid-grid-religion").jqGrid('navGrid', '#jqgrid-pager-religion', { add:false, edit:false, del:false, search:true, refresh:true });
       });
</script>
