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

    $("#gFormMaritalStatus").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormMaritalStatus").validationEngine('attach');
    reset_form("#gFormMaritalStatus");
    $("#jqgrid-grid-maritalStatus").jqGrid({
      url: actionUrl = "${request.contextPath}/${params.controller}/list",

      datatype: "json",
      colNames:[
        ' ',
        'ID',

        'Status Name',
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
      pager: '#jqgrid-pager-maritalStatus',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Marital Status Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
        var ids = $("#jqgrid-grid-maritalStatus").jqGrid('getDataIDs');
        for (key in ids) {
          var rowId = '"' + ids[key] + '"';

          var maritalStatusId = jQuery("#jqgrid-grid-maritalStatus").getCell(ids[key], 'id')
          // var isIssuedLetter = jQuery("#jqgrid-grid-employeeCoreInfoDeploy").getCell(ids[key], 'isIssuedLetter')
          // var isJoined = jQuery("#jqgrid-grid-employeeCoreInfoDeploy").getCell(ids[key], 'isJoined')
          jQuery("#jqgrid-grid-maritalStatus").jqGrid('setRowData', ids[key], {sl: "<a style='text-decoration: underline;' href='javascript:void(0)' onclick='executeEditMaritalStatus(" + maritalStatusId + ")'>Select</a>"});
        }
      }
//      ,
//      onSelectRow: function(rowid, status) {
//        executeEditMaritalStatus();
//      }
    });
    $("#jqgrid-grid-maritalStatus").jqGrid('navGrid', '#jqgrid-pager-maritalStatus', {edit:false,add:false,del:false,search:false});
  });
  function uniqueValue(field, rules, i, options) {
    var msg = "";
    var name = $("#name").val();
    if (name != "" || name != 'null') {
      var id = $("#id").val();
      var data = {name:name,id: id};
      $.ajax({
        type:"post",
        data: data,
        async:false,
        dataType:"json",
        url:"${request.contextPath}/${params.controller}/checkUniqueData",
        success: function(data) {
          if (data.isExists) {
            msg = "Value should be Unique"
          }
        }
      });
      if (msg != "") {
        return msg
      }
    }
  }

  function getSelectedMaritalStatusId() {
    var maritalStatusId = null;
    var rowid = $("#jqgrid-grid-maritalStatus").jqGrid('getGridParam', 'selrow');
    if (rowid) {
      maritalStatusId = $("#jqgrid-grid-maritalStatus").jqGrid('getCell', rowid, 'id');
    }
    if (maritalStatusId == null) {
      maritalStatusId = $('#gFormMaritalStatus input[name = id]').val();
    }
    return maritalStatusId;
  }
  function executePreConditionMaritalStatus() {
    // trim field vales before process.
    trim_form();
    if ($("#gFormMaritalStatus").validate().form({onfocusout: false}) == false) {
      return false;
    }
    if (!$("#gFormMaritalStatus").validationEngine('validate')) {
      return false;
    }
    return true;
  }
  function executeAjaxMaritalStatus() {
    if (!executePreConditionMaritalStatus()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormMaritalStatus input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormMaritalStatus").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
        SubmissionLoader.hideFrom();
        executePostConditionMaritalStatus(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function() {
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionMaritalStatus(result) {
    if (result.type == 1) {
      $("#jqgrid-grid-maritalStatus").trigger("reloadGrid");
      reset_form('#gFormMaritalStatus');
    }
    MessageRenderer.render(result);
  }
  function deleteAjaxMaritalStatus() {    // Delete record
    var maritalStatusId = getSelectedMaritalStatusId();
    if (executePreConditionForDeleteMaritalStatus(maritalStatusId)) {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-maritalStatus").dialog({
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
              data:jQuery("#gFormMaritalStatus").serialize(),
              url: "${request.contextPath}/${params.controller}/delete",

              success: function(message) {
                executePostConditionForDeleteMaritalStatus(message);
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

  function executePreConditionForEditMaritalStatus(maritalStatusId) {
    if (maritalStatusId == null) {
      alert("Please select a maritalStatus to edit");
      return false;
    }
    return true;
  }
  function executeEditMaritalStatus(maritalStatusId) {
   // var maritalStatusId = getSelectedMaritalStatusId();
    if (executePreConditionForEditMaritalStatus(maritalStatusId)) {
      $.ajax({
        type: "POST",
        url: "${request.contextPath}/${params.controller}/edit?id=" + maritalStatusId,

        success: function(entity) {
          executePostConditionForEditMaritalStatus(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditMaritalStatus(data) {
    if (data == null) {
      alert('Selected maritalStatus might have been deleted by someone');  //Message renderer
    } else {
      showMaritalStatus(data);
    }
  }
  function executePreConditionForDeleteMaritalStatus(maritalStatusId) {
    if (maritalStatusId == null) {
      alert("Please select a maritalStatus to delete");
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteMaritalStatus(data) {
    if (data.type == 1) {
      $("#jqgrid-grid-maritalStatus").trigger("reloadGrid");
      reset_form('#gFormMaritalStatus');
    }
    MessageRenderer.render(data)
  }
  function showMaritalStatus(entity) {
    $('#gFormMaritalStatus input[name = id]').val(entity.id);
    $('#gFormMaritalStatus input[name = version]').val(entity.version);

    $('#name').val(entity.name);
    $('#description').val(entity.description);
    $('#create-button-maritalStatus').attr('value', 'Update');
    $('#delete-button-maritalStatus').show();
  }
  function executeSearchMaritalStatus(fieldName, fieldValue) {
    if (executePreConditionForSearchMaritalStatus(fieldName, fieldValue)) {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'maritalStatus', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
        success: function(entity) {
          executePostConditionForSearchMaritalStatus(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchMaritalStatus(fieldName, fieldValue) {
    // trim field vales before process.
    $('#' + fieldName).val($.trim($('#' + fieldName).val()));
    if (fieldValue == '' || fieldValue == null) {
      reset_form("#gFormMaritalStatus");
      return false;
    }
    return true;
  }
  function executePostConditionForSearchMaritalStatus(data, fieldName, fieldValue) {
    if (data == null) {
      reset_form("#gFormMaritalStatus"); // Clear Form
      $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
    } else {
      showMaritalStatus(data);
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