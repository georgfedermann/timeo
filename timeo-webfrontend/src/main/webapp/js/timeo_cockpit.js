/**
 * on page load initiate diverse JavaScript objects to handle
 * logic to be performed within the cockpit
 */
$(document).ready(function () {
    console.log("cockpit loaded.");
    // suppress caching behavior for ajax calls.
    // the conversation with the server is about dynamic contents; no static
    // resources are being queried by the cockpit
    $.ajaxSetup({cache: false});

    if (!String.prototype.startsWith) {
        String.prototype.startsWith = function (searchString, position) {
            position = position || 0;
            return this.substr(position, searchString.length) === searchString;
        };
    }

    user = new User();
    user.init();

    profileButton = new ProfileButton();
    profileButton.init();

    receiveCallButton = new ReceiveCallButton();
    receiveCallButton.init();

    taskBrowser = new TaskBrowser();
    taskBrowser.init();

    timeoCalendar = new TimeoCalendar();
    timeoCalendar.init();

    setTimeout(taskBrowser.refreshTasks.bind(taskBrowser), 1000);
    setTimeout(timeoCalendar.reloadCalendarView.bind(timeoCalendar), 1000);
    
    $(document).mousemove(function(event){
        MouseData.setMouseX(event.pageX);
        MouseData.setMouseY(event.pageY);
    });
});

/**
 * keep information about the mouse and its movements here.
 */
var MouseData = (function closure() {
    var mouseX = 0;
    var mouseY = 0;
    var currentMouseEvent = null;

    function getMouseX() {
        return mouseX;
    }

    function setMouseX(mouseXin) {
        mouseX = mouseXin;
    }

    function getMouseY() {
        return mouseY;
    }

    function setMouseY(mouseYin) {
        mouseY = mouseYin;
    }

    function setCurrentMouseEvent(event) {
        currentMouseEvent = event;
    }

    function getCurrentMouseEvent() {
        return currentMouseEvent;
    }

    function printMessage() {
        console.log("This is a message for you.");
        return 0;
    }

    function MouseData() {
    }

    MouseData.publicPrintMessage = printMessage;
    MouseData.setMouseX = setMouseX;
    MouseData.getMouseX = getMouseX;
    MouseData.setMouseY = setMouseY;
    MouseData.getMouseY = getMouseY;
    return MouseData;

})();

/**
 * The user object holds all information about the authenticated user.
 * Information is shipped from the service inside some <div></div> holding user data.
 */
var User = (function closure() {

    function User() {
        var masterKey;
        var email;
        var phone;
        var businessAddress;
        var loginId;

        this.init = function () {
            masterKey = $("#userdata #masterKey").text();
            email = $("#userdata #email").text();
            phone = $("#userdata #phone").text();
            businessAddress = $("#userdata #businessAddress").text();
            loginId = $("#userdata #loginId").text();
        };

        this.getMasterKey = function () {
            return masterKey;
        };

        this.getEmail = function () {
            return email;
        };

        this.getPhone = function () {
            return phone;
        };

        this.getBusinessAddress = function () {
            return businessAddress;
        };

        this.getLoginId = function () {
            return logindId;
        };
    }

    return User;

})();


var TimeoCalendar = (function closure() {

    function TimeoCalendar() {
        var wsUrlCalendarForYearAndCalendarWeek = null;
        var wsUrlCalendarForCurrentDate = null;
        var wsUrlGetActivityForm = null;
        var wsUrlCreateNewActivityForm = null;
        var wsUrlCreateNewActivity = null;
        var wsUrlGetTasksForProjectAndUser = null;
        var year = -1;
        // the calendarWeek for which the current view shows activities
        var calendarWeek = -1;
        // the actual current calendarWeek to which to return when the user
        // clicks the go-to-today button on the calendar view
        var actualCalendarWeek = -1;
        
        this.getYear = function() {
            return year;
        }
        
        this.getCalendarWeek = function() {
            return calendarWeek;
        }
        
        this.getActualCalendarWeek = function() {
            return actualCalendarWeek;
        }

        this.init = function () {
            wsUrlCalendarForYearAndCalendarWeek =
                "${profile.taskservice.hostname}${profile.taskservice.calendarForYearAndCalendarWeek}";
            wsUrlCalendarForCurrentDate =
                "${profile.taskservice.hostname}${profile.taskservice.calendarForCurrentDate}";
            wsUrlGetActivityForm =
                "${profile.taskservice.hostname}${profile.taskservice.finishActivityForm}";
            wsUrlCreateNewActivityForm =
                "${profile.taskservice.hostname}${profile.taskservice.createActivityForm}";
            wsUrlCreateNewActivity =
                "${profile.taskservice.hostname}${profile.taskservice.createActivity}";
            wsUrlGetTasksForProjectAndUser =
                "${profile.taskservice.hostname}${profile.taskservice.getTasksForProjectAndUser}";
            year = -1;
            calendarWeek = -1;
        }

        this.reloadCalendarView = function () {
            var me = this;
            var localWsUrl = "";
            if (year == -1 || calendarWeek == -1) {
                // load calendar week for current date
                localWsUrl = wsUrlCalendarForCurrentDate.replace("{masterKey}", user.getMasterKey());
            } else {
                // since year and calendar week have been defined, use them to load the calendar week view
                localWsUrl = wsUrlCalendarForYearAndCalendarWeek.replace("{masterKey}", user.getMasterKey())
                    .replace("{year}", year)
                    .replace("{calendarWeekNumber}", calendarWeek);
            }
            $.ajax({
                type: "GET",
                url: localWsUrl,
                success: function (data) {
                    $("div#timeoCalendar").html(data);
                    setTimeout(me.registerActivityMouseHandler.bind(me), 1000);
                    year = $("div#calendarYear").text();
                    calendarWeek = $("div#calendarWeek").text();
                    actualCalendarWeek = actualCalendarWeek == -1 ? calendarWeek : actualCalendarWeek;
                },
                dataType: "text"
            });
        };

        this.registerActivityMouseHandler = function () {
            // $("div.activityPanel").on("mouseenter", this.activityMouseEnter.bind(this));
            // $("div.activityPanel").on("mouseleave", this.activityMouseLeave.bind(this));
            $("div.activityPanel").on("click", this.activityMouseClick.bind(this));
            $("div.weekDayPanel").on("click", this.weekDayPanelClick.bind(this));
            $("div#calendarBackButton").on("click", this.calendarBackButtonClick.bind(this));
            $("div#calendarTodayButton").on("click", this.calendarTodayButtonClick.bind(this));
            $("div#calendarForwardButton").on("click", this.calendarForwardButtonClick.bind(this));
        };
        
        this.calendarBackButtonClick = function(event) {
            console.log("User clicked back to previous week in calendar view");
            if(calendarWeek <= 1){
                alert("Please stay within 2017 for now");
                return;
            }
            calendarWeek = calendarWeek - 1;
            this.reloadCalendarView();
        };
        
        this.calendarTodayButtonClick = function(event) {
            console.log("User clicked go to current week in calendar view");
            calendarWeek = actualCalendarWeek;
            this.reloadCalendarView();
        };
        
        this.calendarForwardButtonClick = function(event) {
            console.log("User clicked go to next week in calendar view");
            if(calendarWeek >= 52){
                alert("Please stay within 2017 for now");
                return;
            }
            calendarWeek = +calendarWeek + 1;
            this.reloadCalendarView();
        };
        
        this.weekDayPanelClick = function(event) {
            console.log("User clicked weekDayPanel");
            var wsUrlCreateNewActivityFormLocal = wsUrlCreateNewActivityForm.replace("{masterKey}", user.getMasterKey());
            var me = this;
            $.ajax({
                type: "GET",
                url: wsUrlCreateNewActivityFormLocal,
                success: function(data) {
                    console.log("loaded createNewActivity-form from server and going to display it on web page");
                    var activityFormContainer = $("div#activityFormContainer");
                    activityFormContainer.empty();
                    activityFormContainer.prepend(data);
                    activityFormContainer.toggleClass("visible invisible");
                    activityFormContainer.css({top: MouseData.getMouseY() + "px", left: MouseData.getMouseX() + "px"});
                    // enter default values
                    $("input#activityFormTimeInvested").attr("value", "30m");
                    $("input#activityFormStartDateTime").attr("value", $(event.target).attr("data-date") + " 09:00:00");
                    $("input#activityFormEndDateTime").attr("value", $(event.target).attr("data-date") + " 09:30:00");
                    // implement cancel button behavior
                    $("div#activityFormContainer input#cancelbutton").on("click", function(event){
                        activityFormContainer.empty();
                        activityFormContainer.toggleClass("visible invisible");
                    });
                    // register change handler on project select
                    $("select#activityFormProject").on("change", me.activityProjectSelect.bind(me));

                    // register custom submit handler
                    $("#finishActivityForm").submit(function(submitEvent){
                        // suppress redirect to server response
                        submitEvent.preventDefault();
                        var formUrl = $(this).closest("form").attr("action");
                        console.log("Found this action URL for activityForm: " + formUrl);
                        $.ajax({
                            url: formUrl,
                            type: "post",
                            data: $("#finishActivityForm").serialize(),
                            success: function(data) {
                                if(data.startsWith("SUCCESS: ")){
                                    alert("Server replied: " + data);
                                    activityFormContainer.empty();
                                    activityFormContainer.toggleClass("visible invisible");
                                    timeoCalendar.reloadCalendarView();
                                } else if (data.startsWith("FAILURE: ")) {
                                    alert("Server indicates an error: " + data);
                                } else {
                                    alert("WARNING: server sent incomprehensible gibberish. Please contact your team lead or system administrator: " + data);
                                }
                            }
                        });
                    });
                },
                dataType: "html"
            });
        }
        
        /*
         * change event handler for the createActivitity-form project select input field.
         */
        this.activityProjectSelect = function(event) {
            var projectId = $("select#activityFormProject option:selected").attr("value");
            console.log("User chose project " + projectId + " from select input");
            var wsUrlGetTasksForProjectAndUserLocal = wsUrlGetTasksForProjectAndUser
                .replace("{projectId}", projectId).replace("{masterKey}", user.getMasterKey());
            $.ajax({
                type: "GET",
                url: wsUrlGetTasksForProjectAndUserLocal,
                success: function(data) {
                    $("select#activityFormTask").replaceWith(data);
                    $("input#submitbutton").removeAttr("disabled");
                },
                dataType: "html"
            });
        }

        this.activityMouseEnter = function(event) {
            console.log("Mouse just entered");
        };

        this.activityMouseLeave = function(event) {
            console.log("Mouse just left");
        };
        
        this.activityMouseClick = function(event) {
            // avoid this event being handled by the weekDayPanel too
            event.stopPropagation();
            var activityId = $(event.target).attr("data-activityId");
            console.log("User clicked activity with id " + activityId );
            var wsUrlGetActivityFormLocal = wsUrlGetActivityForm.replace("{activityId}", activityId);
            $.ajax({
                type: "GET",
                url: wsUrlGetActivityFormLocal,
                success: function(data) {
                    console.log("loaded activityForm from server and going to display it on web page")
                    var activityFormContainer = $("div#activityFormContainer");
                    activityFormContainer.empty();
                    activityFormContainer.prepend(data);
                    activityFormContainer.toggleClass("visible invisible");
                    activityFormContainer.css({top: MouseData.getMouseY() + "px", left: MouseData.getMouseX() + "px"});

                    $("div#activityFormContainer input#cancelbutton").on("click",function(event){
                        // TODO by closure the line below should not be necessary.
                        // var activityFormContainer = $("div#activityFormContainer");
                        activityFormContainer.empty();
                        activityFormContainer.toggleClass("visible invisible");
                    });

                    // register custom submit handler
                    $("#finishActivityForm").submit(function(submitEvent){
                        // suppress redirect to server response
                        submitEvent.preventDefault();
                        var formUrl = $(this).closest("form").attr("action");
                        console.log("Found this action URL for activityForm: " + formUrl);
                        $.ajax({
                            url: formUrl,
                            type: "post",
                            data: $("#finishActivityForm").serialize(),
                            success: function(data) {
                                if(data.startsWith("SUCCESS: ")){
                                    alert("Server replied: " + data);
                                    activityFormContainer.empty();
                                    activityFormContainer.toggleClass("visible invisible");
                                    timeoCalendar.reloadCalendarView();
                                } else if (data.startsWith("FAILURE: ")) {
                                    alert("Server indicates an error: " + data);
                                } else {
                                    alert("WARNING: server sent incomprehensible gibberish. Please contact your team lead or system administrator: " + data);
                                }
                            }
                        });
                    });
                },
                dataType: "html"
            });
        }
    }

    return TimeoCalendar;
    
})();

/**
 * Implements functionality for the TaskBrowser
 * @constructor
 */
var TaskBrowser = (function closure() {

    function TaskBrowser() {
        var tasklistWebserviceUrl = null;
        var finishActivityFormUrl = null;
        var finishActivityUrl = null;
        var currentActivityId = null;
        var timer = new TimeoTimer();

        this.refreshTasks = function () {
            var taskbrowserPanel = $("div#taskbrowser_panel");
            taskbrowserPanel.remove();
            $("div#taskbrowser_container").prepend($("<div id='taskbrowser_panel' class='my-flipster'></div>"));
            
            var me = this;
            $.get(
                tasklistWebserviceUrl,
                function (data) {
                    var taskbrowserPanel = $("div#taskbrowser_panel");
                    taskbrowserPanel.empty();
                    taskbrowserPanel.append(data.firstChild);
                    taskbrowserPanel.flipster();
                    me.registerButtonListeners();
                });
        };
        
        this.init = function() {
            tasklistWebserviceUrl = "${profile.taskservice.hostname}${profile.taskservice.tasklist.path}" + user.getMasterKey();
            finishActivityFormUrl = "${profile.taskservice.hostname}${profile.taskservice.finishActivityForm}";
            finishActivityUrl = "${profile.taskservice.registerActivity}${profile.taskservice.finishActivity}";
            currentActivityId = "dummy";
            timer = new TimeoTimer;
        }

        this.registerButtonListeners = function () {
            $(".acceptTaskButton").on("click", taskBrowser.acceptTaskHandler.bind(this));
            $(".flagTaskButton").on("click", taskBrowser.flagTaskHandler.bind(this));
        };

        this.acceptTaskHandler = function () {
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

            $.ajax({
                type: "GET",
                url: activityUrl,
                success: function (data) {
                    currentActivityId = data;
                    $("div#currentActivityId").html(data);
                },
                dataType: "text"
            });

            timer = new TimeoTimer();
            timer.init();
            timer.startClock();
        };

        this.stopTaskHandler = function () {
            $("div.acceptTaskButton").off("click", taskBrowser.stopTaskHandler);

            timer.pauseClock();

            console.log("User clicked stopTask");
            var finishActivityFormUrlLocal = finishActivityFormUrl.replace("{activityId}", currentActivityId);
            console.log("Retrieving finish-actitivity-form from this URL: " + finishActivityFormUrlLocal);
            $.ajax({
                type: "GET",
                url: finishActivityFormUrlLocal,
                success: function (data) {
                    var finishActivityFormContainer = $("div#finishActivityFormContainer");
                    finishActivityFormContainer.empty();
                    finishActivityFormContainer.prepend(data);
                    finishActivityFormContainer.toggleClass("visible invisible");

                    // update time investment field
                    var timeInvestedInputField = $("input[name='timeInvested']");
                    timeInvestedInputField.attr("value", Math.floor(timer.getTimePassed()) + "s");

                    // register custom submit handler
                    $("#finishActivityForm").submit(function (submitEvent) {
                        // suppress redirect to server response
                        submitEvent.preventDefault();
                        var formUrl = $(this).closest("form").attr("action");
                        console.log("Found this action URL for the form: " + formUrl);
                        $.ajax({
                            url: formUrl,
                            type: "post",
                            data: $("#finishActivityForm").serialize(),
                            success: function (data) {
                                if (data.startsWith("SUCCESS: ")) {
                                    alert("Server replied: " + data);
                                    finishActivityFormContainer.empty();
                                    finishActivityFormContainer.toggleClass("visible invisible");
                                    taskBrowser.refreshTasks();
                                } else if (data.startsWith("FAILURE: ")) {
                                    alert("Server indicates an error: " + data);
                                } else {
                                    alert("WARNING: server sent incomprehensible gibberish. Please contact your team lead or system administrator: " + data);
                                }
                            }
                        });
                    });
                },
                dataType: "html"
            });
        };

        this.flagTaskHandler = function () {
            alert("So you don't like working, right?");
        };
    }

    return TaskBrowser;

})();

/**
 * Implements a simple timer to capture the time invested
 * into an activity
 */
var TimeoTimer = (function closure(){
    
    function TimeoTimer() {
        // the time when the timer was started for the current interval
        var startTime = null;
        // number of seconds passed when pauseClock was called
        var timePassed = 0;
        // status can have one of two values: running, paused.
        // if paused, the timer will not count seconds. When running, the timer will
        // count seconds onto the current amount.
        var status = null;
        // the timer will use JS function setInterval() to start counting
        // the time intervals that pass. To be able to pause the timer again,
        // it stores the intervalId in this instance variable.
        var intervalId = null;
        
        this.init = function() {
            startTime = new Date();
            // numbers of second passed when pauseClock was called
            timePassed = 0;
            // status can be running or paused.
            // if paused, the timer will not count seconds, otherwise it will
            status = "paused";
            // the timer will use JS function setInterval() to start counting
            // the time intervals that pass. To be able to pause the timer again,
            // it stores the intervalId in this instance variable.
            intervalId = "";
        }
        
        // updates the timer display with the current time
        this.updateTimeDisplay = function() {
            var taskClock = $("div#taskClock");
            taskClock.empty();
            taskClock.append($("<span>" + this.getTime() + "</span>"));
        }
        
        // start the clock and let it count the time
        this.startClock = function() {
            console.log("Starting task clock.");
            startTime = new Date();
            status = "running";
            intervalId = setInterval(this.updateTimeDisplay.bind(this), 100);
        }
        
        // pause the clock and stop it from counting the time
        this.pauseClock = function() {
            timePassed = (new Date().getTime() - startTime.getTime()) / 1000;
            status = "paused";
            clearInterval(intervalId);
        }
        
        this.getTime = function() {
            var currentTime = new Date();
            var secondsPassed = status == "running" ?
            ( currentTime.getTime() - startTime.getTime() ) / 1000 : timePassed;
            var minutesPassed = Math.floor(secondsPassed / 60);
            var secondsRemainder = Math.floor(secondsPassed % 60);
            var secondsOutput = secondsRemainder < 10 ? "0" + secondsRemainder : secondsRemainder;
            var timeValue = minutesPassed + ":" + secondsOutput;
            return timeValue;
        }

        this.getTimePassed = function() {
            return timePassed;
        }

    }
    
    return TimeoTimer;
    
})();


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
ProfileButton.prototype.init = function () {
    var me = this;
    var profileButton = $("div#profile_button");
    var image = $("div#profile_button img");
    image.mouseover(me.handleMouseover.bind(this));
    image.mouseout(me.handleMouseout.bind(this));
    image.click(me.handleMouseclick.bind(this));
};

ProfileButton.prototype.handleMouseover = function () {
    console.log("Mouseover profile button.");
    var image = $("div#profile_button img");
    image.attr("src", image.attr("src").replace("active", "mouseover"));
    image.attr("style", "border: 2px solid #98050D;");
};

ProfileButton.prototype.handleMouseout = function () {
    console.log("Mouseout profile button.");
    if (this.status != "selected") {
        var image = $("div#profile_button img");
        image.attr("src", image.attr("src").replace("mouseover", this.status));
        image.removeAttr("style");
    }
};

ProfileButton.prototype.handleMouseclick = function () {
    console.log("Mouseclick on profile button.");
    this.status = this.status == "active" ? "selected" : "active";
    console.log("Switched ProfileButton status to " + this.status);
    var image = $("div#profile_button img");
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

ReceiveCallButton.prototype.init = function () {
    var me = this;
    var receiveCallButton = $("div#receive_call_button");
    var image = $("div#receive_call_button img");
    image.mouseover(me.handleMouseover.bind(this));
    image.mouseout(me.handleMouseout.bind(this));
    image.click(me.handleMouseclick.bind(this));
};

ReceiveCallButton.prototype.handleMouseover = function () {
    console.log("Mouseover receive call button.");
    var image = $("div#receive_call_button img");
    image.attr("src", image.attr("src").replace("active", "mouseover"));
    image.attr("style", "border: 2px solid #98050D;");
};

ReceiveCallButton.prototype.handleMouseout = function () {
    console.log("Mouseout receive call button.");
    if (this.status != "selected") {
        var image = $("div#receive_call_button img");
        image.attr("src", image.attr("src").replace("mouseover", this.status));
        image.removeAttr("style");
    }
};

ReceiveCallButton.prototype.handleMouseclick = function () {
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
