
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
        if ($("#rank-text").val()==""){
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

    $("#id-text").on("propertychange change keyup paste input", function() {
        $("#verify-check").val(false);
    });

    $("#delBtn").click(function () {
        if (!confirm("삭제하시겠습니까?")) return;

        let tdArr = [];
        const checkbox = $("input[name=delYN]:checked");

        console.log("체크박스 리스트 유무 : "+checkbox);

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
                alert(data);
                window.location.replace("/admin/user");
            },
            error: function (xhr, status, error) {
                let obj = JSON.parse(xhr.responseText);
                alert(obj.errorMessage);
            }
        });
    });

    $("#duplicated-btn").click(function (){

        let verifyId = $("#id-text").val();
        console.log(verifyId)
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: "/admin/user/verify",
            type: "POST",
            data: verifyId,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            // dataType: "json",
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
});