
$(document).ready(function() {
    $("#delBtn").click(function () {
        if (!confirm("삭제하시겠습니까?")) return;

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
                alert(data);
                window.location.replace("/admin/organization");
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + error);
            }
        });
    });
});