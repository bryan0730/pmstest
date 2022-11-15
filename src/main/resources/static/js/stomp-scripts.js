let stompClient = null;
let notificationCount = 0;
let receiver = null;

$(document).ready(function() {
    console.log("Index page is ready");

    if($('#unreadCount').val()!=0){
        $('.circle-wrapper').show();
    }else{
        $('.circle-wrapper').hide();
    }

    connect();

    $(".alarm-wrap").hover(function (){
        $(".nav-modal-cover").addClass("active");
    }, function (){
        $(".nav-modal-cover").removeClass("active");
    });

    $(".message_modal_cover").hover(function () {
        $(".nav-modal-cover").addClass("active");
    }, function (){
        $(".nav-modal-cover").removeClass("active");
    });

    $(".nav-link").on("click", function (){
        let userName = $(this).text().trim();
        receiver = $(this).find($('input:hidden')).val();
        $('#recipient-name').val(userName);

        console.log("Message Receiver name = "+userName);
        console.log("Login User id = "+$("#pmsUserId").val());
        console.log("Message Receiver id = "+receiver);
    });

    $("#btn-msg-reply").on("click", function (){
        let userName = $("#exampleFormControlInput1").val();
        receiver = $("#msgSenderId").val();
        let comment = '-----Origin Message-----\n'
            +$("#exampleFormControlTextarea1").text()+'\n'
            +'---------------------------\n';
        $('#recipient-name').val(userName);
        $("#message-text").text(comment);
    });

    $("#msg-file").on("change", function () {
        const dataTransfer = new DataTransfer();
        let files = $("#msg-file")[0].files;
        console.log(files.length);
        if (files.length>5){
            let fileArray = Array.from(files);
            fileArray.splice(4,1);
            fileArray.forEach(file => { dataTransfer.items.add(file)});
            $('#msg-file')[0].files = dataTransfer.files;
            alert("첨부파일은 최대 5개까지만 등록가능합니다.");
        }
    });

    $("#send-msg").click(function (){
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        let form = new FormData();
        form.append("messageReceiver", receiver);
        form.append("messageSender", $("#pmsUserId").val());
        form.append("comment", $("#message-text").val());
        form.append("sendDate", new Date());
        let files = $("#msg-file")[0].files;
        let fileArray = Array.from(files);
        fileArray.forEach((file) => console.log(file.name));
        fileArray.forEach((file) => form.append("messageFiles", file));

        $.ajax({
            url: "/pms/message/send",
            type: "POST",
            data: form,
            beforeSend : function(xhr)
            {
                xhr.setRequestHeader(header, token);
            },
            dataType: "text",
            contentType:false,
            processData:false,
            cache:false,
            enctype:'multipart/form-data',
            success: function(data){
                sendMessage();
            },
            error: function(xhr, status, error){
                let obj = JSON.parse(xhr.responseText);
                alert(obj.errorMessage);
            }
        });
    });
});


function connect() {
    let socket = new SockJS('http://localhost:7070/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/pms-message/'+$("#pmsUserId").val(), function (message) {
            console.log("sender ::" + JSON.parse(message.body).messageSender);
            console.log("session id :: " + $("#pmsUserId").val());
            if(JSON.parse(message.body).messageSender!=$("#pmsUserId").val()){
                notificationCount = Number($('.num').text())+notificationCount+1;
                showNotification(message);
            }
        });
    });
}

function showNotification(msg){
    $('.circle-wrapper').show();
    $('.num').text(notificationCount);
    $('.list-content').prepend(
        "<div>" +
        "<div class='infd-message-cover checked unread'>" +
        "<a href='#' class='infd-message-el'>" +
        " <span class='title text-truncate' onclick='location.href=/pms/message/"+JSON.parse(msg.body).messageId+"'>" +
            JSON.parse(msg.body).messageContent +
        "</span> <span class='date'>"+JSON.parse(msg.body).messageSenderName+"</span>" +
        "</a>" +
        "</div>" +
        "</div>"
    );
    console.log(JSON.parse(msg.body));
}

function sendMessage(){
    console.log("send msg 2222222");
    stompClient.send("/ws/pms/"+receiver, {},
        JSON.stringify({'messageContent': $("#message-text").val(), 'sender':$("#pmsUserId").val(), 'senderName':$('#pmsUserInfoName').text()}));
}