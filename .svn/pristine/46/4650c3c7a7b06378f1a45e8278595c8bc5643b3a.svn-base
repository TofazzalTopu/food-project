


<script type="text/javascript">
    $(document).ready(function() {
         $("#jqgrid-grid-gender").jqGrid({
      url:'${resource(dir:'gender', file:'listJSON')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',

        
        'Gender Type',
        'Description'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},

        
                
                    {name:'genderType', index:'genderType',width:100,align:'left'}
                
        ,
                
                    {name:'description', index:'description',width:100,align:'left'}
                
        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-gender',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"Gender",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
            executeEditGender(rowid);
      }
    });
    $("#jqgrid-grid-gender").jqGrid('navGrid', '#jqgrid-pager-gender', { add:false, edit:false, del:false, search:true, refresh:true });
       });
</script>
