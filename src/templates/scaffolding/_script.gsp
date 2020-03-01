<% import grails.persistence.Event %>
<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<script type="text/javascript" language="Javascript">
  \$(document).ready(function() {
      textboxes = \$("input:visible:not([disabled],[readonly]),select,button");
    if (\$.browser.mozilla) {
        \$(textboxes).keypress (checkForEnter);
    } else {
        \$(textboxes).keydown (checkForEnter);
    }

    \$("#gForm${className}").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    \$("#gForm${className}").validationEngine('attach');
    reset_form("#gForm${className}");
    \$("#jqgrid-grid-${propertyName}").jqGrid({
      url:'\${resource(dir:'${propertyName}', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        <%  excludedProps = Event.allEvents.toList() << 'version' << 'test' << 'id' << 'dateCreated' << 'lastUpdated' << 'userCreated' << 'userUpdated'
        props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
        props.eachWithIndex  { p, index ->
            if (!Collection.class.isAssignableFrom(p.type)) {
                cp = domainClass.constrainedProperties[p.name]
                display = (cp ? cp.display : true)
                if (display) { %>
        '${p.naturalName}'<% if(props.size() -1 > index){ %>,<%} }   }   } %>
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        <%  excludedProps = Event.allEvents.toList() << 'version' << 'test' << 'id' << 'dateCreated' << 'lastUpdated' << 'userCreated' << 'userUpdated'
        props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
        props.eachWithIndex  { p, index ->
            if (!Collection.class.isAssignableFrom(p.type)) {
                cp = domainClass.constrainedProperties[p.name]
                display = (cp ? cp.display : true)
                if (display) { %>
                <%if(p.type == Date.class){%>
                    {name:'${p.name}', index:'${p.name}',width:100,align:'left', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}}
                <% }else if(p.type == double || p.type == Double.class || p.type == float || p.type == Float.class){ %>
                    {name:'${p.name}', index:'${p.name}',width:100,align:'right', formatter:'number', formatoptions :{thousandsSeparator:","}}
                <% }else if(p.type == int || p.type == Integer.class || p.type == long || p.type == Long.class || p.type == byte || p.type == Byte.class){ %>
                    {name:'${p.name}', index:'${p.name}',width:100,align:'right'}
                <%}else{%>
                    {name:'${p.name}', index:'${p.name}',width:100,align:'left'}
                <% } %>
        <% if(props.size() -1 > index){ %>,<%} }   }   } %>
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-${propertyName}',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"${domainClass.naturalName} List",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEdit${className}();
      }
    });

  });
  function getSelected${className}Id()
  {
    var ${propertyName}Id = null;
    var rowid = \$("#jqgrid-grid-${propertyName}").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      ${propertyName}Id = \$("#jqgrid-grid-${propertyName}").jqGrid('getCell', rowid, 'id');
    }
    if(${propertyName}Id == null){
      ${propertyName}Id = \$('#gForm${className} input[name = id]').val();
    }
    return ${propertyName}Id;
  }
  function executePreCondition${className}() {
      // trim field vales before process.
      trim_form();
      if (!\$("#gForm${className}").validationEngine('validate')) {
          return false;
      }
      return true;
  }
  function executeAjax${className}() {
    if(!executePreCondition${className}()) {
      return false;
    }
    var actionUrl = null;
    if (\$('#gForm${className} input[name = id]').val()) {
      actionUrl = "\${request.contextPath}/\${params.controller}/update";
    } else {
      actionUrl = "\${request.contextPath}/\${params.controller}/create";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gForm${className}").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostCondition${className}(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostCondition${className}(result) {
      if (result.type == 1) {
        \$("#jqgrid-grid-${propertyName}").trigger("reloadGrid");
        reset_form('#gForm${className}');
      }
      MessageRenderer.render(result);
  }
  function deleteAjax${className}() {    // Delete record
    var ${propertyName}Id = getSelected${className}Id();
    if(executePreConditionForDelete${className}(${propertyName}Id))
    {
      \$("#dialog").dialog("destroy");
      \$("#dialog-confirm-${propertyName}").dialog({
        resizable: false,
        height:150,
        modal: true,
        title: 'Delete alert',
        buttons: {
          'Delete item(s)': function() {
            \$(this).dialog('close');
            \$.ajax({
              type: "POST",
              dataType: "json",
              data:jQuery("#gForm${className}").serialize(),
              url: "\${resource(dir:'${propertyName}', file:'delete')}",
              success: function(message) {
                executePostConditionForDelete${className}(message);
              }
            });
          },
          Cancel: function() {
            \$(this).dialog('close');
          }
        }
      }); //end of dialog
    }
  }

  function executePreConditionForEdit${className}(${propertyName}Id) {
    if(${propertyName}Id == null)
    {
      alert("Please select a ${propertyName} to edit") ;
      return false;
    }
    return true;
  }
  function executeEdit${className}() {
    var ${propertyName}Id = getSelected${className}Id();
    if(executePreConditionForEdit${className}(${propertyName}Id))
    {
      \$.ajax({
        type: "POST",
        url: "\${resource(dir:'${propertyName}', file:'edit')}?id="+ ${propertyName}Id,
        success: function(entity) {
          executePostConditionForEdit${className}(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEdit${className}(data) {
      if (data == null) {
        alert('Selected ${propertyName} might have been deleted by someone');  //Message renderer
      } else {
        show${className}(data);
      }
  }
  function executePreConditionForDelete${className}(${propertyName}Id) {
    if(${propertyName}Id == null)
    {
      alert("Please select a ${propertyName} to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDelete${className}(data) {
      if (data.type ==1) {
        \$("#jqgrid-grid-${propertyName}").trigger("reloadGrid");
         reset_form('#gForm${className}');
      }
      MessageRenderer.render(data)
  }
  function show${className}(entity) {
    \$('#gForm${className} input[name = id]').val(entity.id);
    \$('#gForm${className} input[name = version]').val(entity.version);
    <%  excludedProps = Event.allEvents.toList() << 'version' << 'test' << 'id' << 'dateCreated' << 'lastUpdated' << 'userCreated' << 'userUpdated'
    props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
    Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
    props.eachWithIndex  { p, index ->
        if (!Collection.class.isAssignableFrom(p.type)) {
            cp = domainClass.constrainedProperties[p.name]
            display = (cp ? cp.display : true)
            if (display) { if(p.type == Boolean.class || p.type == boolean.class){%>
    \$('#${p.name}').attr('checked',entity.${p.name});<%}else if(p.manyToOne || p.oneToOne) {%>
    if(entity.${p.name}){
       \$('#${p.name}').val(entity.${p.name}.id).attr("selected","selected");
    }
    else{
        \$('#${p.name}').val(entity.${p.name});
    }<%}else{%>
    \$('#${p.name}').val(entity.${p.name});<% } }   }   } %>
    \$('#create-button-${propertyName}').attr('value', 'Update');
    \$('#delete-button-${propertyName}').show();
  }
  function executeSearch${className}(fieldName, fieldValue){
    if(executePreConditionForSearch${className}(fieldName, fieldValue))
    {
      \$.ajax({
        type: "POST",
        url: "\${resource(dir:'${propertyName}', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearch${className}(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearch${className}(fieldName,fieldValue) {
      // trim field vales before process.
      \$('#'+fieldName).val(\$.trim(\$('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gForm${className}");
          return false;
      }
      return true;
  }
  function executePostConditionForSearch${className}(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gForm${className}"); // Clear Form
          \$('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        show${className}(data);
      }
  }

   function reset_form(formName) {
       \$(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
           if (this.type == 'hidden') {
               this.value = "";
           } else {
               this.value = this.defaultValue;
           }
       });
       \$('input[type=checkbox]').attr('checked',false);
       \$(formName +' input[name = create-button]').attr('value', 'Create');
       \$(formName +' input[name = delete-button]').hide();
   }

    function trim_form() {
        \$(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = \$.trim(this.value);
        });
    }
</script>