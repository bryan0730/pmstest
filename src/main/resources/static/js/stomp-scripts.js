let stompClient = null;
let notificationCount = 0;
// let userId = null;
let receiver = null;


$(document).ready(function() {
    // userId = $("#pmsUserId").val();
    // console.log("userId : : : "+userId);
    console.log("Index page is ready");
    connect();

    $(".nav-link").on("click", function (){
        let userName = $(this).text().trim();
        receiver = $(this).find($('input:hidden')).val();
        $('#recipient-name').val(userName);

        console.log("Message Receiver name = "+userName);
        console.log("Login User id = "+$("#pmsUserId").val());
        console.log("Message Receiver id = "+receiver);
    });

    $("#send-msg").click(function (){
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        sendMessage();
        let json = {
            "messageReceiver" : receiver,
            "messageSender" : $("#pmsUserId").val(),
            "comment" : $("#message-text").val(),
            "sendDate" : new Date()
        };
        const jsonString = JSON.stringify(json);
        console.log(jsonString);
        $.ajax({
            url: "/pms/message/send",
            type: "POST",
            data: jsonString,
            beforeSend : function(xhr)
            {
                xhr.setRequestHeader(header, token);
            },
            dataType: "text",
            contentType:"application/json",
            success: function(data){
                alert(data);
            },
            error: function(xhr, status, error){
                alert(xhr.responseText + error);
            }
        });
    });
});


function connect() {
    let socket = new SockJS('http://localhost:7070/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // updateNotificationDisplay();

        stompClient.subscribe('/topic/pms-message/'+$("#pmsUserId").val(), function (message) {
            // showNotification(JSON.parse(message.body).content);
            showNotification(message);
        });
    });
}

function showNotification(msg){
    $('#notifications').show();
    console.log(JSON.parse(msg.body));
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage(){
    console.log("send msg 2222222");
    stompClient.send("/ws/pms/"+receiver, {},
        JSON.stringify({'messageContent': $("#message-text").val(), 'sender':$("#pmsUserId").val()}));
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