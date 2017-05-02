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
    $("div#profile_button").mouseover(me.handleMouseover.bind(this));
    $("div#profile_button img").mouseover(me.handleMouseover.bind(this));

    $("div#profile_button").mouseout(me.handleMouseout.bind(this));
    $("div#profile_button img").mouseout(me.handleMouseout.bind(this));
}

ProfileButton.prototype.handleMouseover = function() {
    console.log("Mouseover profile button.");
    var image = $("div#profile_button img");
    image.attr("src", image.attr("src").replace("active", "mouseover"));
    image.attr("style", "border: 2px solid #98050D;");
}

ProfileButton.prototype.handleMouseout = function() {
    console.log("Mouseout profile button.");
    var image = $("div#profile_button img");
    image.attr("src", image.attr("src").replace("mouseover", this.status));
    image.removeAttr("style");
}

/**
 * Implements functionality of the ReceiveCallButton
 */
function ReceiveCallButton() {
    // status can be active, inactive, mouseover or selected
    this.status = "active";
}

ReceiveCallButton.prototype.init = function() {
    var me = this;
    $("div#receive_call_button").mouseover(me.handleMouseover.bind(this));
    $("div#receive_call_button img").mouseover(me.handleMouseover.bind(this));

    $("div#receive_call_button").mouseout(me.handleMouseout.bind(this));
    $("div#receive_call_button img").mouseout(me.handleMouseout.bind(this));
}

ReceiveCallButton.prototype.handleMouseover = function() {
    console.log("Mouseover receive call button.");
    var image = $("div#receive_call_button img");
    image.attr("src", image.attr("src").replace("active", "mouseover"));
    image.attr("style", "border: 2px solid #98050D;");
}

ReceiveCallButton.prototype.handleMouseout = function() {
    console.log("Mouseout receive call button.");
    var image = $("div#receive_call_button img");
    image.attr("src", image.attr("src").replace("mouseover", this.status));
    image.removeAttr("style");
}
