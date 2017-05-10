/**
 * on page load initiate diverse JavaScript objects to handle
 * logic to be performed within the cockpit
 */
$(document).ready(function(){
    console.log("cockpit loaded.");
    
    user = new User();
    user.init();
    
    profileButton = new ProfileButton();
    profileButton.init();

    receiveCallButton = new ReceiveCallButton();
    receiveCallButton.init();
    
    taskBrowser = new TaskBrowser();
    taskBrowser.init();
    
    setTimeout(taskBrowser.refreshTasks(), 1000);
});

/**
 * user object holds all information about the authenticated user.
 * information is shipped from the server inside some <div/> holding user data.
 */
function User() {}

User.prototype.init = function() {
    this.masterKey = $("#userdata #masterKey").text();
    this.email = $("#userdata #email").text();
    this.phone = $("#userdata #phone").text();
    this.businessAddress = $("#userdata #businessAddress").text();
    this.loginId = $("#userdata #loginId").text();
};

/**
 * Implements functionality for the TaskBrowser
 * @constructor
 */
function TaskBrowser() {}

TaskBrowser.prototype.init = function() {
    this.tasklistWebserviceUrl = "${profile.taskservice.hostname}${profile.taskservice.tasklist.path}" + user.masterKey;
    this.finishActivityFormUrl = "${profile.taskservice.hostname}${profile.taskservice.finishActivityForm}";
    this.finishActivityUrl = "${profile.taskservice.registerActivity}${profile.taskservice.finishActivity}";
    this.currentActivityId = "dummy";
};

TaskBrowser.prototype.refreshTasks = function() {
    var me = this;
    var taskbrowserPanel = $("div#taskbrowser_panel");
    taskbrowserPanel.remove();
    $("div#taskbrowser_container").prepend($("<div id='taskbrowser_panel' class='my-flipster'></div>"));
    $.get(
        this.tasklistWebserviceUrl,
        function(data){
            var taskbrowserPanel = $("div#taskbrowser_panel");
            taskbrowserPanel.empty();
            taskbrowserPanel.append(data.firstChild);
            taskbrowserPanel.flipster();
            me.registerButtonListeners();
        });
};

TaskBrowser.prototype.registerButtonListeners = function() {
    $(".acceptTaskButton").on("click", taskBrowser.acceptTaskHandler.bind(this));
    $(".flagTaskButton").on("click", taskBrowser.flagTaskHandler.bind(this));
    // $(".acceptTaskButton").click(taskBrowser.acceptTaskHandler.bind(this));
    // $(".flagTaskButton").click(taskBrowser.flagTaskHandler.bind(this));
};

TaskBrowser.prototype.acceptTaskHandler = function() {
    console.log("User is accepting task.");
    // $("li.flipster__item--current").attr("style", "left: -240px; width: 100%; z-index: 100")
    var currentTaskHandle = $("li.flipster__item--current");
    var taskBrowserPanel = $("div#taskbrowser_panel");
    taskBrowserPanel.empty();
    taskBrowserPanel.prepend(currentTaskHandle);
    currentTaskHandle.animate({"margin-right": "0px", width: "823px"});

    var acceptTaskButton = $("div.acceptTaskButton");
    acceptTaskButton.addClass("selected");
    acceptTaskButton.off("click", taskBrowser.acceptTaskHandler);
    acceptTaskButton.on("click", taskBrowser.stopTaskHandler.bind(this));
    
    var flagTaskButton = $("div.flagTaskButton");
    flagTaskButton.addClass("invisible");
    flagTaskButton.off("click", taskBrowser.flagTaskHandler);
    
    // Inform server of new activity
    var activityUrl = "${profile.taskservice.hostname}${profile.taskservice.registerActivity}";
    activityUrl = activityUrl.replace("{taskId}", $("div#taskInfo > div#taskId").text())
        .replace("{teamMemberId}", $("div#taskInfo > div#projectTeamMemberId").text());
    console.log("Sending activity info to url " + activityUrl + ".");

    var me = this;
    $.ajax({
        type: "GET",
        url: activityUrl,
        success: function(data){
            me.currentActivityId = data;
            $("div#currentActivityId").html(data);
        },
        dataType: "text"
    });
    
    this.timer = new TimeoTimer();
    this.timer.init();
    this.timer.startClock();
};

TaskBrowser.prototype.stopTaskHandler = function() {
    $("div.acceptTaskButton").off("click", taskBrowser.stopTaskHandler);

    this.timer.pauseClock();
    var me = this;
    
    console.log("User clicked stopTask");
    var finishActivityFormUrl = this.finishActivityFormUrl.replace("{activityId}", this.currentActivityId);
    console.log("Retrieving finish-actitivity-form from this URL: " + finishActivityFormUrl);
    $.ajax({
        type: "GET",
        url: finishActivityFormUrl,
        success: function (data) {
            var finishActivityFormContainer = $("div#finishActivityFormContainer");
            finishActivityFormContainer.empty();
            finishActivityFormContainer.prepend(data);
            finishActivityFormContainer.toggleClass("visible invisible");

            // update time investment field
            var timeInvestedInputField = $("input[name='timeInvested']");
            timeInvestedInputField.attr("value", Math.floor(me.timer.timePassed));
            
            // register custom submit handler
            $("#finishActivityForm").submit(function(submitEvent){
                // suppress redirect to server response
                submitEvent.preventDefault();
                var formUrl = $(this).closest("form").attr("action");
                console.log("Found this action URL for the form: ", + formUrl);
                $.ajax({
                    url: formUrl,
                    type: "post",
                    data: $("#finishActivityForm").serialize(),
                    success: function(data){
                        alert("Server replied: " + data);
                        finishActivityFormContainer.empty();
                        finishActivityFormContainer.toggleClass("visible invisible");
                        taskBrowser.refreshTasks();
                    }
                });
            });
        },
        dataType: "html"
    });
};

TaskBrowser.prototype.flagTaskHandler = function(){
    alert("So you don't like working, right?");
};

/**
 * Implements a simple timer to capture the time invested
 * into an activity
 */
function TimeoTimer () {
}

TimeoTimer.prototype.init = function() {
    this.startTime = new Date();
    // numbers of second passed when pauseClock was called
    this.timePassed = 0;
    // status can be running or paused.
    // if paused, the timer will not count seconds, otherwise it will
    this.status = "paused";
    // the timer will use JS function setInterval() to start counting
    // the time intervals that pass. To be able to pause the timer again,
    // it stores the intervalId in this instance variable.
    this.intervalId = "";
};

TimeoTimer.prototype.updateTimeDisplay = function() {
    // console.log("Updating task clock display.");
    var taskClock = $("div#taskClock");
    taskClock.empty();
    taskClock.append($("<span>" + this.getTime() + "</span>"));
}

TimeoTimer.prototype.startClock = function() {
    console.log("Starting task clock.");
    this.startTime = new Date();
    this.status = "running";
    this.intervalId = setInterval(this.updateTimeDisplay.bind(this), 100);
};

TimeoTimer.prototype.pauseClock = function() {
    console.log("Pausing task clock.");
    this.timePassed = (new Date().getTime() - this.startTime.getTime()) / 1000;
    this.status = "paused";
    clearInterval(this.intervalId);
};

TimeoTimer.prototype.getTime = function() {
    // console.log("Creating a string containing the time passed since the task was accepted.");
    var currentTime = new Date();
    var secondsPassed = this.status == "running" ?
    ( currentTime.getTime() - this.startTime.getTime() ) / 1000 :
        this.timePassed;
    var minutesPassed = Math.floor(secondsPassed / 60);
    var secondsRemainder = Math.floor(secondsPassed % 60);
    var secondsOutput = secondsRemainder < 10 ? "0" + secondsRemainder : secondsRemainder;
    var timeValue = minutesPassed + ":" + secondsOutput;
    // console.log("Delivering current task clock string: " + timeValue);
    return timeValue;
};

/**
 * Implements functionality of the ProfileButton
 * @constructor
 */
function ProfileButton() {
    // status can be active, inactive, mouseover or selected
    this.status = "active";
}

/**
 * initializes the UserButton handler
 */
ProfileButton.prototype.init = function() {
    var me = this;
    var profileButton = $("div#profile_button");
    var image = $("div#profile_button img");
    image.mouseover(me.handleMouseover.bind(this));
    image.mouseout(me.handleMouseout.bind(this));
    image.click(me.handleMouseclick.bind(this));
};

ProfileButton.prototype.handleMouseover = function() {
    console.log("Mouseover profile button.");
    var image = $("div#profile_button img");
    image.attr("src", image.attr("src").replace("active", "mouseover"));
    image.attr("style", "border: 2px solid #98050D;");
};

ProfileButton.prototype.handleMouseout = function() {
    console.log("Mouseout profile button.");
    if(this.status != "selected") {
        var image = $("div#profile_button img");
        image.attr("src", image.attr("src").replace("mouseover", this.status));
        image.removeAttr("style");
    }
};

ProfileButton.prototype.handleMouseclick = function() {
    console.log("Mouseclick on profile button.");
    this.status = this.status == "active" ? "selected" : "active";
    console.log("Switched ProfileButton status to " + this.status);
    var image=$("div#profile_button img");
    var contextMenu = $("div#profileContextMenu");
    contextMenu.toggleClass("profileContextMenuHidden profileContextMenuVisible");
    // contextMenu.attr("class", "profileContextMenuVisible");
    var right = image.position().left + image.width();
    var top = image.position().top + image.height();
    var paddingSize = 8;
    var left = right - contextMenu.width() - paddingSize;
    contextMenu.css("top", top);
    contextMenu.css("left", left);
};

/**
 * Implements functionality of the ReceiveCallButton
 */
function ReceiveCallButton() {
    // status can be active, inactive, mouseover or selected
    this.status = "active";
}

ReceiveCallButton.prototype.init = function() {
    var me = this;
    var receiveCallButton = $("div#receive_call_button");
    var image = $("div#receive_call_button img"); 
    image.mouseover(me.handleMouseover.bind(this));
    image.mouseout(me.handleMouseout.bind(this));
    image.click(me.handleMouseclick.bind(this));
};

ReceiveCallButton.prototype.handleMouseover = function() {
    console.log("Mouseover receive call button.");
    var image = $("div#receive_call_button img");
    image.attr("src", image.attr("src").replace("active", "mouseover"));
    image.attr("style", "border: 2px solid #98050D;");
};

ReceiveCallButton.prototype.handleMouseout = function() {
    console.log("Mouseout receive call button.");
    if(this.status != "selected") {
        var image = $("div#receive_call_button img");
        image.attr("src", image.attr("src").replace("mouseover", this.status));
        image.removeAttr("style");
    }
};

ReceiveCallButton.prototype.handleMouseclick = function() {
    console.log("Mouseclick registered on phone button.");
    this.status = this.status == "active" ? "selected" : "active";
    var image = $("div#receive_call_button img");
    var phoneCallDialog = $("div#phoneCallDialog");
    phoneCallDialog.toggleClass("phoneCallDialogHidden phoneCallDialogVisible");
    var right = image.position().left + image.width();
    var top = image.position().top + image.height();
    var paddingSize = 8;
    var left = right - phoneCallDialog.width() - paddingSize;
    phoneCallDialog.css("top", top);
    phoneCallDialog.css("left", left);
};
