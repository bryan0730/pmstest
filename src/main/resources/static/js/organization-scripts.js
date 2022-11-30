
$(document).ready(function() {

    $("#org-regist-btn").click(function (){
       if($("#org-name-text").val()==""){
           swal('조직', '조직명을 입력하세요', 'warning');
           return false;
       }
    });


    $("#delBtn").click(function () {
        swal({
            title : "조직",
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
                const checkbox = $("input[name=isChecked]:checked");

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
                    url: "/admin/organization/del",
                    type: "POST",
                    data: jsonString,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    dataType: "text",
                    contentType: "application/json",
                    success: function (data) {
                        swal('조직', data, 'success').then(() => {
                            window.location.replace("/admin/organization");
                        });
                    },
                    error: function (xhr, status, error) {
                        let obj = JSON.parse(xhr.responseText);
                        swal('조직', obj.errorMessage, 'error');
                    }
                });
            }else{
                return;
            }
        });
    });
});