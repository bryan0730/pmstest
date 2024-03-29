let stompClient = null;
let notificationCount = 0;
let receiver = null;

$(document).ready(function() {
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

    $(".modal-user-name").on("click", function (){
        let userName = $(this).text().trim();
        receiver = $(this).find($('input:hidden')).val();
        $('#recipient-name').val(userName);

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
        if (files.length>5){
            let fileArray = Array.from(files);
            fileArray.splice(4,1);
            fileArray.forEach(file => { dataTransfer.items.add(file)});
            $('#msg-file')[0].files = dataTransfer.files;
            swal('메시지', '첨부파일은 최대 5개까지만 등록가능합니다.', 'warning')
            $("#msg-file").val("");
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
                sendMessage(data);
                swal('메시지', '메시지 전송완료', 'success');
                initMsgModal();
            },
            error: function(xhr, status, error){
                let obj = JSON.parse(xhr.responseText);
                swal('메시지', obj.errorMessage, 'error');
            }
        });
    });

    $("#close-msg").click(function (){
        initMsgModal();
    });
});

function fn_checkByte(obj){
    const maxByte = 1000; //최대 1000바이트
    const text_val = obj.value; //입력한 문자
    const text_len = text_val.length; //입력한 문자수
    let text_val2 = "";
    let rlen = 0;

    let totalByte=0;
    for(let i=0; i<text_len; i++){
        const each_char = text_val.charAt(i);
        const uni_char = escape(each_char);
        if(uni_char.length>4){
            totalByte += 2;
        }else{
            totalByte += 1;
        }
        if (totalByte <= maxByte){
            rlen = i+1;
        }
    }

    if(totalByte>maxByte){
        swal('메시지', '최대 '+maxByte+'Byte까지만 입력 가능합니다.', 'warning')
        document.getElementById("nowByte").innerText = totalByte;
        document.getElementById("nowByte").style.color = "red";
        text_val2 = text_val.substring(0, rlen);
        obj.value = text_val2;
        fn_checkByte(obj);
    }else{
        document.getElementById("nowByte").innerText = totalByte;
        document.getElementById("nowByte").style.color = "green";
    }
}


function connect() {
    let socket = new SockJS('/pms-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {};
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/pms-message/'+$("#pmsUserId").val(), function (message) {
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
        " <span class='title text-truncate' onclick=location.href='/pms/message/"+JSON.parse(msg.body).messageId+"'>" +
            JSON.parse(msg.body).messageContent +
        "</span> <span class='date'>"+JSON.parse(msg.body).messageSenderName+"</span>" +
        "</a>" +
        "</div>" +
        "</div>"
    );

    $("#toast-sender").html(JSON.parse(msg.body).messageSenderName);
    $("#toast-message").html(JSON.parse(msg.body).messageContent);
    $("#liveToast").attr("onclick", "location.href='/pms/message/"+JSON.parse(msg.body).messageId+"'");

    const toastLiveExample = $("#liveToast");
    const toast = new bootstrap.Toast(toastLiveExample);
    toast.show();
}

function sendMessage(messageId){
    stompClient.send("/ws/pms/"+receiver, {},
        JSON.stringify({'messageId' : messageId,'messageContent': $("#message-text").val(), 'sender':$("#pmsUserId").val(), 'senderName':$('#pmsUserInfoName').text()}));
}

function initMsgModal(){
    $("#message-text").val("");
    $("#msg-file").val("");
}