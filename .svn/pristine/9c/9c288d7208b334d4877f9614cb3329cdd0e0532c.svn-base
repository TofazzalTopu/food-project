$(document).ready(function(){
    $("#member-information-tabs").tabs({
        //fx: { opacity: 'toggle', duration:'fast'},
        //fx: [{opacity:'toggle', duration:'normal'}, {opacity:'toggle', duration:'fast'}],
        select: function(event, ui) {
          //ui.tab
        }
    });
    //.find(".ui-tabs-nav").sortable({axis:'x'});
    
    $('#member-information-tabs input, textarea').each(function(){
        $(this).one('keyup', function() {
            reLabelTab()
        });
    });

    $('#member-information-tabs select').each(function(){
        $(this).one('change', function() {
            reLabelTab()
        });
    });

    $('#familyInfo-isFamilyLoan').click(function(){
        if(this.checked){
            $('#family-loan-section').fadeIn()
        }else{
            $('#family-loan-section').fadeOut() 
        }
    });
});

function reLabelTab(){
    var selectedTabIndex = $('#member-information-tabs').tabs('option', 'selected');
    $tab = $('#member-information-tabs ul li:nth-child('+(selectedTabIndex+1)+') a')
    $tab.addClass('icon-save');
}