This plugin is downloaded from
http://digitalbush.com/projects/masked-input-plugin/

Masked Input Plugin
This is a masked input plugin for the jQuery javascript library.
It allows a user to more easily enter fixed width input where you would
like them to enter the data in a certain format (dates,phone numbers, etc).
It has been tested on Internet Explorer 6/7, Firefox 1.5/2/3, Safari, Opera, and Chrome.
A mask is defined by a format made up of mask literals and mask definitions.
Any character not in the definitions list below is considered a mask literal.
Mask literals will be automatically entered for the user as they type and will
not be able to be removed by the user.The following mask definitions are predefined:

    * a - Represents an alpha character (A-Z,a-z)
    * 9 - Represents a numeric character (0-9)
    * * - Represents an alphanumeric character (A-Z,a-z,0-9)


USAGE
First, include the jQuery and masked input javascript files.
<script src="jquery.js" type="text/javascript"></script>
<script src="jquery.maskedinput.js" type="text/javascript"></script>

Next, call the mask function for those items you wish to have masked.
jQuery(function($){
   $("#date").mask("99/99/9999");
   $("#phone").mask("(999) 999-9999");
   $("#tin").mask("99-9999999");
   $("#ssn").mask("999-99-9999");
});


Optionally, if you are not satisfied with the underscore ('_') character as a placeholder,
you may pass an optional argument to the maskedinput method.
jQuery(function($){
   $("#product").mask("99/99/9999",{placeholder:" "});
});


Optionally, if you would like to execute a function once the mask has been completed,
you can specify that function as an optional argument to the maskedinput method.
jQuery(function($){
   $("#product").mask("99/99/9999",{completed:function(){alert("You typed the following: "+this.val());}});
});


You can now supply your own mask definitions.
jQuery(function($){
   $.mask.definitions['~']='[+-]';
   $("#eyescript").mask("~9.99 ~9.99 999");
});
