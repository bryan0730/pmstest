
$(document).ready(function() {

    $("#regist-btn").click(function (){
        if ($("#id-text").val()==""){
            alert("아이디는 필수입니다.");
            return false;
        }
        if ($("#pw-text").val()==""){
            alert("비밀번호는 필수입니다.");
            return false;
        }
        if ($("#pw-text").val() != $("#pw-text-check").val()){
            alert("비밀번호를 다시 확인해주세요.");
            return false;
        }
        if ($("#group-rank").val()==""){
            alert("직급은 필수입니다.");
            return false;
        }
        if ($("#name-text").val()==""){
            alert("이름은 필수입니다.");
            return false;
        }
        if ($("#phone-text").val()==""){
            alert("휴대폰 번호는 필수입니다.");
            return false;
        }
        if($("#verify-check").val()=="false"){
            alert("ID 중복확인이 필요합니다.");
            return false;
        }
    });

    $("#pw-text-check").on("keyup", function (){
        let pw = $("#pw-text").val();
        let pwCheck = $("#pw-text-check").val();

        if (pw!="" || pwCheck != ""){
            if (pw==pwCheck){
                $("#pw-check-msg").html('비밀번호 일치');
                $("#pw-check-msg").attr('class', 'true-msg');
            }else{
                $("#pw-check-msg").html('비밀번호 불일치');
                $("#pw-check-msg").attr('class', 'false-msg');
            }
        }
    });

    $("#id-text").on("propertychange change keyup paste input", function() {
        $("#verify-check").val(false);
    });

    $("#delBtn").click(function () {
        swal({
            title : "사용자",
            text : "삭제하시겠습니까?",
            icon: "warning",
            buttons: [
                '취소',
                '확인'
            ],
            dangerMode: true,
        }).then((result) => {
            if (result){
                let tdArr = [];
                const checkbox = $("input[name=delYN]:checked");

                const token = $("meta[name='_csrf']").attr("content");
                const header = $("meta[name='_csrf_header']").attr("content");

                checkbox.each(function (i) {
                    let tr = checkbox.parent().parent().eq(i);
                    let td = tr.children();

                    let val = td.eq(0).val();
                    let jsonObject = {"id": val};

                    tdArr.push(jsonObject);
                });
                const jsonString = JSON.stringify(tdArr);

                $.ajax({
                    url: "/admin/user/del",
                    type: "POST",
                    data: jsonString,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    dataType: "text",
                    contentType: "application/json",
                    success: function (data) {
                        swal('사용자', data, 'success').then(() => {
                            window.location.replace("/admin/user");
                        });
                    },
                    error: function (xhr, status, error) {
                        let obj = JSON.parse(xhr.responseText);
                        swal('사용자', obj.errorMessage, 'error');
                    }
                });
            }else {
                return;
            }
        });
    });

    $("#duplicated-btn").click(function (){

        if ($("#id-text").val()==""){
            swal('사용자', 'ID를 입력하세요.', 'warning');
            return;
        }

        let verifyId = $("#id-text").val();
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: "/admin/user/verify",
            type: "POST",
            data: verifyId,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            contentType: "application/json",
            success: function (data) {
                $("#verify-check").val(data.isDuplicated);
                if (data.isDuplicated){
                    $("#duplicated-msg").attr("class","duplicated-msg true-msg");
                }else {
                    $("#duplicated-msg").attr("class","duplicated-msg false-msg");
                }
                $("#duplicated-msg").text(data.responseMessage);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
                let obj = JSON.parse(xhr.responseText);
                alert(obj.errorMessage);
            }
        });
    });

    $("#org-group-text").on("change", function() {

        let orgId= $("#org-group-text option:selected").val();
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/admin/user/rank-info",
            type: "POST",
            data: orgId,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            contentType: "application/json",
            success: function (data) {
                addSelectBox(data);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
                let obj = JSON.parse(xhr.responseText);
                alert(obj.errorMessage);
            }
        });


    });

    function addSelectBox(data){
        $("#group-rank option").remove();
        data.forEach(d => {
            $("#group-rank")
                .append("<option value='"+ d.rankId +"'>"+d.rankName+"</option>");
        });
    }
});