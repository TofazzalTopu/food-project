/**
 * Created by IntelliJ IDEA.
 * User: selim
 * Date: 6/15/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
function reloadCookie(scname, uri){
    removeCookie(scname);
    setCookie(scname, uri);
}
function setCookie(scname, uri){
    var cookieData = $.jStorage.get(scname);
    if(cookieData == null){
        var newOptions = {
            expiresAt: new Date(2015, 1, 1),
            secure: true
        }
        DocuAjax.json(uri, {}, function(data) {
            $.jStorage.set(scname,data, newOptions);
        });
        //alert('Cookie set succesfully');
    } else{
        //alert('cookie is already set..');
    }
}

function removeCookie(scname, uri){
    var cookieData = $.jStorage.get(scname);
    if(cookieData == null){
        //alert('cookie is null');
    }else{
        $.jStorage.flush();
        $.jStorage.deleteKey(scname)
    }
}

//function viewCookie(){
//    var data= $.jStorage.get('empInfo');
//    console.debug(data);
//}


