
//게시물 삭제
const delete_elements = document.getElementsByClassName("delete");
Array.from(delete_elements).forEach(function(element) {
	element.addEventListener('click', function() {
		swal({
			title: "게시물",
			text: "삭제하시겠습니까?",
			icon: "warning",
			buttons: [
				'취소',
				'확인'
			],
			dangerMode: true,
		}).then((result) => {
			if (result) {
				location.href = this.dataset.uri;
			} else {
				return;
			};
		});
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
		$(this).val($(this).val().substring(0, 5000));
		swal('글자수는 5000자까지 입력 가능합니다.');
	};
});

$("#delBtn").click(function() {
	swal({
		title: "첨부파일",
		text: "삭제하시겠습니까?",
		icon: "warning",
		buttons: [
			'취소',
			'확인'
		],
		dangerMode: true,
	}).then((result) => {
		if (result) {
			console.log("ok");
			let tdArr = [];
			const checkbox = $("input[name=delYN]:checked");
			const token = $("meta[name='_csrf']").attr("content");
			const header = $("meta[name='_csrf_header']").attr("content");

			checkbox.each(function(i) {
				let tr = checkbox.parent().parent().eq(i);
				let td = tr.children();

				let val = td.eq(0).val();
				let jsonObject = { "id": val };
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
					swal({
						title: '첨부파일',
						text: data,
						icon: 'success',
						button: '확인'
					}).then(() => {
						 $('#fileTable').load(location.href+' #fileTable');
					});

				},
				error: function(xhr, status, error) {
					let obj = JSON.parse(xhr.responseText);
					swal("게시판", obj.errorMessage, "error");
				}
			});
		} else {
			return;
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



'use strict';

let replyIndex = {

	replyDelete: function(boardId, replyId) {
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		console.log(boardId);
		console.log(replyId);
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			dataType: "text"
		}).done(function(res) {
			alert("댓글삭제가 완료되었습니다.");
			location.href = `/pms/board/detail/${boardId}`;
		}).fail(function(err) {
			alert(JSON.stringify(err));
		});
	},
}
replyIndex.init();

