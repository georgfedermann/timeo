/**
 * Created by georg on 27/02/2017.
 * Script to update the project team member list in the create task form when the user
 * selected the respective project.
 */

$(document).ready(initPage);

function initPage(){
    console.log("So far, so good.");
    $("#_project_id").click(projectListEventHandler);
}

function projectListEventHandler(){
    // this function is executed when the user clicks the project list to have the 
    // projectTeamMember list updated.
    var projectId = $("#_project_id").next().attr("value");
    console.log("Loading project team members for project " + projectId + " now.");
    $.ajax(
        {
            cache : false,
            url : "${profile.task.webservice.hostname}${profile.task.webservice.projectTeamMemberWidgetForProject}" + projectId,
            type : "GET",
            data: ""
        }
    ).then(function(data){
        console.log("It appears that the data was loaded: " + (new XMLSerializer()).serializeToString(data));
        $("div#_c_org_poormanscastle_products_timeo_task_domain_Task_projectTeamMember_id").remove();
        $("form#task > div[id$='_submit']").before((new XMLSerializer()).serializeToString(data) + "<br/>");
        setTimeout(function(){$("form#task > div[id$='_submit']").prev().prev().prev().remove();}, 500);
    });

}
