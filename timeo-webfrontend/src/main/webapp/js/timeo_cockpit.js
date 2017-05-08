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
    var acceptTaskButton = $("div.acceptTaskButton");
    // $("li.flipster__item--current").attr("style", "left: -240px; width: 100%; z-index: 100")
    var currentTaskHandle = $("li.flipster__item--current");
    var taskBrowserPanel = $("div#taskbrowser_panel");
    taskBrowserPanel.empty();
    taskBrowserPanel.prepend(currentTaskHandle);
    currentTaskHandle.animate({"margin-right": "0px", width: "823px"});
    $("li.flipster__item--current div.acceptTaskButton").addClass("selected");
    
    $("acceptTaskButton").off("click", taskBrowser.acceptTaskHandler);
};

TaskBrowser.prototype.flagTaskHandler = function(){
    alert("So you don't like working, right?");
};

/**
 * Implements a simple timer to capture the time invested
 * into an activity
 */
function TimeoTimer () {
    this.startTime = new Date();
    // numbers of second passed when pauseClock was called
    this.timePassed = 0;
    // status can be running or paused.
    // if paused, the timer will not count seconds, otherwise it will
    this.status = paused;
}

TimeoTimer.prototype.init = function() {
    this.startTime = new Date();
};

TimeoTimer.prototype.startClock = function() {
    this.startTime = new Date();
    this.status = running;
};

TimeoTimer.prototype.pauseClock = function() {
    this.timePassed = (new Date().getTime() - startDate.getTime()) / 1000;
    this.status = paused;
};

TimeoTimer.prototype.getTime = function() {
    var currentTime = new Date();
    var secondsPassed = this.status == "running" ?
    ( currentTime.getTime() - startDate.getTime() ) / 1000 :
        this.timePassed;
    var minutesPassed = Math.floor(secondsPassed / 60);
    var secondsRemainder = secondsPassed % 60;
    var secondsOutput = secondsRemainder < 10 ? "0" + secondsRemainder : secondsRemainder;
    return minutesPassed + ":" + secondsOutput;
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



