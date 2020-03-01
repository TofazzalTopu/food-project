<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
    CursorListener.setFocusOnFirstField();
    EnterKeyListener.init();
    textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
      $(textboxes).keypress(checkForEnter);
    } else {
      $(textboxes).keydown(checkForEnter);
    }

    $("#gFormRelationship").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormRelationship").validationEngine('attach');
    reset_form("#gFormRelationship");
    $("#jqgrid-grid-relationship").jqGrid({
      %{--url:'${resource(dir:'relationship', file:'list')}',--}%
      url:"${request.contextPath}/${params.controller}/list",
      datatype: "json",
      colNames:[
        'Sl',
        'ID',

        'Relationship Name',
        'Description'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},


        {name:'name', index:'name',width:100,align:'left'}

        ,

        {name:'description', index:'description',width:100,align:'left'}
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-relationship',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Relationship Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
        var ids = $("#jqgrid-grid-relationship").jqGrid('getDataIDs');
        for (key in ids) {
          var rowId = '"' + ids[key] + '"';
          var relationshipId = jQuery("#jqgrid-grid-relationship").getCell(ids[key], 'id')
          // var isIssuedLetter = jQuery("#jqgrid-grid-employeeCoreInfoDeploy").getCell(ids[key], 'isIssuedLetter')
          // var isJoined = jQuery("#jqgrid-grid-employeeCoreInfoDeploy").getCell(ids[key], 'isJoined')
          jQuery("#jqgrid-grid-relationship").jqGrid('setRowData', ids[key], {sl: "<a style='text-decoration: underline;' href='javascript:void(0)' onclick='executeEditRelationship(" + relationshipId + ")'>Select</a>"});
        }
        }
//      ,
//     onSelectRow: function(rowid, status) {
//      executeEditRelationship();
//     }
    });
    $("#jqgrid-grid-relationship").jqGrid('navGrid', '#jqgrid-pager-relationship', {edit:false,add:false,del:false,search:false});

      $('#isRelative').attr('checked', 'checked');
  });
  function getSelectedRelationshipId() {
    var relationshipId = null;
    var rowid = $("#jqgrid-grid-relationship").jqGrid('getGridParam', 'selrow');
    if (rowid) {
      relationshipId = $("#jqgrid-grid-relationship").jqGrid('getCell', rowid, 'id');
    }
    if (relationshipId == null) {
      relationshipId = $('#gFormRelationship input[name = id]').val();
    }
    return relationshipId;
  }
  function executePreConditionRelationship() {
    // trim field vales before process.
    trim_form();
    if ($("#gFormRelationship").validate().form({onfocusout: false}) == false) {
      return false;
    }
    if (!$("#gFormRelationship").validationEngine('validate')) {
      return false;
    }
    return true;
  }
  function executeAjaxRelationship() {
    if (!executePreConditionRelationship()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormRelationship input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormRelationship").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          SubmissionLoader.hideFrom();
        executePostConditionRelationship(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid-relationship").trigger("reloadGrid");
              reset_form('#gFormRelationship');

              MessageRenderer.renderErrorText("Network Problem: Time out");
          }else{
              MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
          }
      },
      complete:function() {
          $('#isRelative').attr('checked', 'checked');
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionRelationship(result) {
    if (result.type == 1) {
      $("#jqgrid-grid-relationship").trigger("reloadGrid");
      reset_form('#gFormRelationship');
    }
    MessageRenderer.render(result);
  }
  function deleteAjaxRelationship() {    // Delete record
    var relationshipId = getSelectedRelationshipId();
    if (executePreConditionForDeleteRelationship(relationshipId)) {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-relationship").dialog({
        resizable: false,
        height:150,
        modal: true,
        title: 'Delete alert',
        buttons: {
          'Delete item(s)': function() {
            $(this).dialog('close');
            $.ajax({
              type: "POST",
              dataType: "json",
              data:jQuery("#gFormRelationship").serialize(),
              url: "${request.contextPath}/${params.controller}/delete",

              success: function(message) {
                executePostConditionForDeleteRelationship(message);
              }
            });
          },
          Cancel: function() {
            $(this).dialog('close');
          }
        }
      }); //end of dialog
    }
  }

  function executePreConditionForEditRelationship(relationshipId) {
    if (relationshipId == null) {
      alert("Please select a relationship to edit");
      return false;
    }
    return true;
  }
  function executeEditRelationship(relationshipId) {
   // var relationshipId = getSelectedRelationshipId();
    if (executePreConditionForEditRelationship(relationshipId)) {
      $.ajax({
        type: "POST",
        url: "${request.contextPath}/${params.controller}/edit?id=" + relationshipId,

        success: function(entity) {
          executePostConditionForEditRelationship(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditRelationship(data) {
    if (data == null) {
      alert('Selected relationship might have been deleted by someone');  //Message renderer
    } else {
      showRelationship(data);
    }
  }
  function executePreConditionForDeleteRelationship(relationshipId) {
    if (relationshipId == null) {
      alert("Please select a relationship to delete");
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteRelationship(data) {
    if (data.type == 1) {
      $("#jqgrid-grid-relationship").trigger("reloadGrid");
      reset_form('#gFormRelationship');
    }
    MessageRenderer.render(data)
  }
  function showRelationship(entity) {
    $('#gFormRelationship input[name = id]').val(entity.id);
    $('#gFormRelationship input[name = version]').val(entity.version);

    $('#name').val(entity.name);
    $('#description').val(entity.description);
//    if (entity.status) {
//      $('#status').val(entity.status.id).attr("selected", "selected");
//    }
//    else {
//      $('#status').val(entity.status);
//    }
    $('#create-button-relationship').attr('value', 'Update');
    $('#delete-button-relationship').show();
  }
  function executeSearchRelationship(fieldName, fieldValue) {
    if (executePreConditionForSearchRelationship(fieldName, fieldValue)) {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'relationship', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
        success: function(entity) {
          executePostConditionForSearchRelationship(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchRelationship(fieldName, fieldValue) {
    // trim field vales before process.
    $('#' + fieldName).val($.trim($('#' + fieldName).val()));
    if (fieldValue == '' || fieldValue == null) {
      reset_form("#gFormRelationship");
      return false;
    }
    return true;
  }
  function executePostConditionForSearchRelationship(data, fieldName, fieldValue) {
    if (data == null) {
      reset_form("#gFormRelationship"); // Clear Form
      $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
    } else {
      showRelationship(data);
    }
  }

  function reset_form(formName) {
    $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
      if (this.type == 'hidden') {
        this.value = "";
      } else {
        this.value = this.defaultValue;
      }
    });
    $('input[type=checkbox]').attr('checked', false);
    $(formName + ' input[name = create-button]').attr('value', 'Create');
    $(formName + ' input[name = delete-button]').hide();
  }

  function trim_form() {
    $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
      this.value = $.trim(this.value);
    });
  }
</script>