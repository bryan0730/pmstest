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
    });
});