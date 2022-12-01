
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
						window.location.reload();
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