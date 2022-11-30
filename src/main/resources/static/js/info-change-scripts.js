$(document).ready(function() {

    $("#info-change-btn").on("click", function (){
        if ($("#user-pw").val() != $("#user-pw-check").val()){
            alert("비밀번호를 다시 확인해주세요.");
            return false;
        }

        if(confirm("변경하시겠습니까?")){
            return true;
        }else{
            return false;
        }
    });

    $("#user-pw-check").on("keyup", function (){
        let pw = $("#user-pw").val();
        let pwCheck = $("#user-pw-check").val();

        if (pw!="" || pwCheck !== ""){
            if (pw==pwCheck){
                $("#check-msg-div").html('비밀번호 일치');
                $("#check-msg-div").attr('class', 'true-msg');
            }else{
                $("#check-msg-div").html('비밀번호 불일치');
                $("#check-msg-div").attr('class', 'false-msg');
            }
        }
    });

});