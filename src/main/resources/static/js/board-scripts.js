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


$('#content').keyup(function(e) {
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

$("#delBtn").click(function() {
	if (!confirm("삭제하시겠습니까?")) return;

	let tdArr = [];
	const checkbox = $("input[name=delYN]:checked");

	console.log("체크박스 리스트 유무 : " + checkbox);

	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");

	checkbox.each(function(i) {
		let tr = checkbox.parent().parent().eq(i);
		let td = tr.children();

		let val = td.eq(0).val();
		let jsonObject = { "id": val };
		console.log("val : " + val);
		tdArr.push(jsonObject);
	});
	const jsonString = JSON.stringify(tdArr);

	$.ajax({
		url: "/deleteCheckedBoardFiles",
		type: "POST",
		data: jsonString,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		dataType: "text",
		contentType: "application/json",
		success: function(data) {
			alert(data);
			window.location.reload();
		},
		error: function(xhr, status, error) {
			let obj = JSON.parse(xhr.responseText);
			alert(obj.errorMessage);
		}
	});
});


$("#btn-reply-save").click(function() {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	const data = {
		boardId: $('#boardId').val(),
		content: $('#content').val()
	}
	console.log(data);
	if (!data.content || data.content.trim() === "") {
		alert("공백 또는 입력하지 않은 부분이 있습니다.");
		return false;
	} else {
		$.ajax({
			url: '/api/board/' + data.boardId + '/reply',
			type: 'POST',
			data: JSON.stringify(data),
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			contentType: 'application/json; charset=utf-8'
		}).done(function() {
			alert('댓글이 등록되었습니다.');
			window.location.reload();
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
});
