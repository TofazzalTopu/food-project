/*
 * ****************************************************************
 * Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */
(function( $, undefined ) {

$.widget( "ui.simpletable", {
    rowIndex    : 0,
	options     : {},

    _create: function() {
        //alert(this.element.attr("id"))
        var tHeader = "";
        for(key in this.options.colModel){
            tHeader += "<td>" + this.options.colModel[key] + "</td>";
        }
        tHeader = "<tr class='"+this.element.attr("id")+"-table-row'>" + tHeader + "<td style='width:10px;'></td></tr>";
        var table = "<table id='"+this.element.attr("id")+"-table' class='simple-table-css' border='0' width='100%' cellpadding='0' cellspacing='0'>" + tHeader + "</table>";
        //table += "<div id='"+this.element.attr("id")+"-div-counter'></div>";
        table += "<input type='hidden' name='"+this.options.inputPrefix+".items' id='"+this.element.attr("id")+"-table-row-counter' value='' />";
        this.element.html(table)
    },

    renameInputIndex: function(){
        var _inputPrefix = this.options.inputPrefix;

        var inputs  = this.getInputs();
        for(i in inputs){
            var _index = 0;
            $('.'+inputs[i]).each(function(_input){
                var oldName = $(this).attr('name');
                var reName =  _inputPrefix+"["+_index+"]."+inputs[i];
                _index++;
                var oldName = $(this).attr('name', reName);
            });
        }
    },

    getInputs: function(){
        var _inputs = [];

        var index   = 0;
        for(key in this.options.colModel){
            _inputs[index] = key;
            index++;
        }

        return _inputs;
    },

	addRow: function(data){
        var inputs  = this.getInputs();
        
        var td = "";
        for(i in data){
            td += "<td>" + data[i] + "<input type='hidden' name='"+this.options.inputPrefix+"["+this.rowIndex+"]."+inputs[i]+"' class='"+inputs[i]+"' value='"+data[i]+"' /></td>";
        }
        this.rowIndex++;

        var tr = "<tr class='"+this.element.attr("id")+"-table-row'>" + td + "<td id='button-delete-"+this.rowIndex+"' class='button-delete'></td></tr>";
        $('#' + this.element.attr("id") + '-table' + ' tbody').append(tr)


        $('#button-delete-'+this.rowIndex).click(function(){
            var _rowClass = $(this).parent().get(0).className;
            var _numRows = $('#'+_rowClass+'-counter').val();
            $('#'+_rowClass+'-counter').val(_numRows-1);

            $(this).parent().remove();
        });

        this.renameInputIndex();

        var _items = $('#'+this.element.attr("id")+'-table tr').length - 1;
        $('#'+this.element.attr("id")+'-table-row-counter').val(_items);
        //$('#' + this.element.attr("id") + '-div-counter').html("<input type='text' name='"+this.options.inputPrefix+".items' id='"+this.element.attr("id")+"-table-row-counter' value='"+this.rowIndex+"' />")
    },
    
    removeRow: function(){
        alert(77)
    }
});

$.extend( $.ui.simpletable, {
	version: "1.0.0"
});

})( jQuery );
