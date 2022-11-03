let stompClient = null;
let notificationCount = 0;
let userId = null;
let receiver = null;


$(document).ready(function() {
    userId = $("#pmsUserId").val();
    console.log("userId : : : "+userId);
    console.log("Index page is ready");
    connect();

    $("#bsend").click(function (){
        sendMessage();
    });

    $(".nav-link").on("click", function (){
        // alert(userId);
        receiver = $(this).find($('input:hidden')).val();
        // alert("click user session : "+receiver);
    });
});


function connect() {
    let socket = new SockJS('http://localhost:7070/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // updateNotificationDisplay();

        stompClient.subscribe('/topic/pms-message/'+userId, function (message) {
            showMessage(JSON.parse(message.body).content);
        });
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage(){
    console.log("send msg 2222222");
    alert("receiver : "+receiver);
    alert("sender : "+userId);
    stompClient.send("/ws/pms/"+receiver, {},
        JSON.stringify({'messageContent': $("#msg2").val(), 'sender':userId}));
}

function updateNotificationDisplay() {
    if (notificationCount == 0) {
        $('#notifications').hide();
    } else {
        $('#notifications').show();
        $('#notifications').text(notificationCount);
    }
}

function resetNotificationCount() {
    notificationCount = 0;
    updateNotificationDisplay();
}