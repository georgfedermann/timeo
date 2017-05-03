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

ProfileButton.prototype.handleMouseclick = function() {
    console.log("Mouseclick on profile button.");
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
    var receiveCallButton = $("div#receive_call_button");
    var image = $("div#receive_call_button img"); 
    image.mouseover(me.handleMouseover.bind(this));
    image.mouseout(me.handleMouseout.bind(this));
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
