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

/**
 * This JS is used for menu related javascript functionality
 * User: smziaur.rahman
 * Date: Oct 24, 2010
 * Time: 6:20:00 PM
 *
 */
var MunuLoader = {
    load: function(file){
        MunuLoader.showAjaxLoader('ui-layout-west');
        $.post('menu/loadLeftMenu?file='+file, function(data) {
            $('.ui-layout-west').html(data);
            /*
            $('#ajax-loader').fadeOut('slow', function(){
                $('.ui-layout-west').html(data);
                $('.ui-layout-west').hide();
                $('.ui-layout-west').fadeIn('slow');
            })
            */
        });

    },

    showAjaxLoader: function(class){
        $('.'+class).html("<div id='ajax-loader' style='text-align: center;'><img src='images/layout/ajax-loader.gif'></div>");
    },

    clearAjaxLoader: function(class){
      $('.'+class).html("");
    }
};