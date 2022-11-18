//게시물 삭제
const delete_elements = document.getElementsByClassName("delete");
Array.from(delete_elements).forEach(function(element) {
	element.addEventListener('click', function() {
		if (confirm("정말로 삭제하시겠습니까?")) {
			location.href = this.dataset.uri;
		}
		;
	});
});

//파일삭제
function boardDelete(fileId) {
	if (confirm("정말로 삭제하시겠습니까?")) {
		//배열생성
		const form = document.createElement('form');
		form.setAttribute('method', 'post'); //Post 메소드 적용
		form.setAttribute('action', '/boardFileDelete');

		//파일 id
		var input1 = document.createElement('input');
		input1.setAttribute("type", "hidden");
		input1.setAttribute("name", "fileId");
		input1.setAttribute("value", fileId);

		//게시판 id
		const selectedElements = document.querySelector("#boardId")
		var input2 = document.createElement('input');
		input2.setAttribute("type", "hidden");
		input2.setAttribute("name", "boardId");
		input2.setAttribute("value", selectedElements.value);

		form.appendChild(input1);
		form.appendChild(input2);
		console.log(form);
		document.body.appendChild(form);
		form.submit();
	}
}

	$('#content').keyup(function (e) {
		let content = $(this).val();
	    
	    // 글자수 세기
	    if (content.length == 0 || content == '') {
	    	$('.textCount').text('0자');
	    } else {
	    	$('.textCount').text(content.length + '자');
	    }
	    
	    // 글자수 제한
	    if (content.length > 5000) {
	        $(this).val($(this).val().substring(0, 200));
	        alert('글자수는 5000자까지 입력 가능합니다.');
	    };
	});
	