/**
 * Created by georg on 25/03/2017.
 */

var mouseX;
var mouseY;
$(document).mousemove(function (e) {
    mouseX = e.pageX;
    mouseY = e.pageY;
});

$(document).ready(findStakeholderRefs);

function findStakeholderRefs() {
    // find DIVs annotated with class stakeholderRef
    // console.log("Initialize stakeholderRef event handlers.");
    $("td.stakeholderRef").each(addBusinessCardMouseOver);
}

function addBusinessCardMouseOver() {
    // console.log("adding BusinessCard mouseOver event handler");
    setTimeout(function () {
        $("td.stakeholderRef").each(function () {
            var email = $(this).html();
            // console.log("Found email: " + email);
            var timer;
            $(this).mouseover(function () {
                timer = setTimeout(function () {
                    // console.log("Mouse over td.stakeholderRef");
                    // console.log("Going to ajax businessCard from " + "http://localhost:8080/stakeholdermatrix/pages/matrix/businesscardForEmail/" + email);
                    $.get("${profile.stakeholder.webservice.hostname}${profile.stakeholder.webservice.businesscard.path}" + email,
                        function (data) {
                            // console.log("Received data from WS: " + data);
                            $("div#businessCard").html(data);
                        });
                    $("div#businessCard").css({'top': mouseY, 'left': mouseX}).fadeIn('slow');
                }, 1000);
            });
            $(this).mouseout(
                function(){
                    clearTimeout(timer);
                    $("div#businessCard").fadeOut('slow');
                }
            );
        });
    }, 1000);

}
