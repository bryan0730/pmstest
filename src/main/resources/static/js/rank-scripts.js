$(document).ready(function() {


    $("#group-text").on("change", function() {
        let orgId= $("#group-text option:selected").val();
        window.location.replace("/admin/rank/"+orgId);
    });

    $("#rank-regist-btn").on("click", function (){
        if ($("#group-text option:selected").val()==""){
            alert("조직을 선택해주세요.");
            return;
        }
        if ($("#rankName").val()==""){
            alert("직급명을 입력해주세요.");
            return;
        }
        if ($("#rankWeight").val()==""){
            alert("직급순위를 입력해주세요.");
            return;
        }
    });
});