%{----}%


<script type="text/javascript">
  function isInnerObject(objToTest) {
    if(objToTest.id != null)
    return true;
  }

  $(document).ready(function(){
      $("#jqgrid-grid").jqGrid({
          url:'${resource(dir:'applicationUserAuthority', file:'getApplicationUserAuthorityGridJSON')}/',
          datatype: "json",
          colNames:[
              'SL',
              'ID',
              
			  'Application User',
			  'Authority'
          ],
          colModel:[
              {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
              {name:'id',index:'id', width:0, hidden:true},
              
			  {name:'applicationUser', index:'applicationUser', width:150, sortable:true, align:'left'},
			  {name:'authority', index:'authority', width:150, sortable:true, align:'left'}
          ],
          rowNum:10,
          rowList:[10,20,30],
          pager: '#jqgrid-pager',
          sortname: 'id',
          viewrecords: true,
          sortorder: "desc",
          caption:"All  Application User Authoritys",
          autowidth: true,
          height:243,
          scrollOffset: 0,
          loadComplete: function(){
          },
          onSelectRow: function(rowid, status){
            var applicationuserauthorityId = $("#jqgrid-grid").getCell(rowid, 'id');
            $.ajax({
                type: "POST",
                url: "${resource(dir:'applicationUserAuthority', file:'getApplicationUserAuthorityJSON')}?id="
                        + applicationuserauthorityId,
                success: function(data) {
                    if (data.entity == null) {
                        alert('Selected ApplicationUserAuthority might have been deleted by someone');
                    } else {
                        var entity = data.entity;
                        $('#id').val(entity.id);
                        $('#version').val(data.version);

                        
                        if(isInnerObject(entity.applicationUser)){
                           $('#applicationUser').val(entity.applicationUser.id);
                        }else{
                          $('#applicationUser').val(entity.applicationUser);
                        }
                        
                        if(isInnerObject(entity.authority)){
                           $('#authority').val(entity.authority.id);
                        }else{
                          $('#authority').val(entity.authority);
                        }
                        

                        $('#create').attr('value', 'Update ApplicationUserAuthority');
                        $('#delete-button').show();
                    }
                },
                dataType:'json'
            });
            // End of ajax
          }
      });
      $("#jqgrid-grid").jqGrid('navGrid','#jqgrid-pager',{edit:false,add:false,del:false});
  });
</script>

<div class="jqgrid-container">
  <table id="jqgrid-grid"></table>
  <div id="jqgrid-pager"></div>
</div>
<div id="dialog-confirm" title="Delete alert" style="display: none">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
