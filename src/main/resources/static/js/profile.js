// 구독 정보 보는 함수
document.querySelector("#subscribeBtn").onclick = (e) => {
	e.preventDefault();
	document.querySelector(".modal-follow").style.display = "flex";

	// ajax 통신 후에 json 리턴 -> javaScript 오브젝트 변경 => for문 돌면서 뿌리기

	let userId = $("#userId").val();

	$.ajax({
		url: `/user/${userId}/follow`,
	}).done((res) => {

		$("#follow_list").empty();

		res.data.forEach((u) => {
			console.log(2, u);
			let item = makeSubscribeInfo(u);
			$("#follow_list").append(item);
		})
	}).fail((error) => {
		alert("오류 : " + error);
	});


};


function makeSubscribeInfo(u) {
	let item = `<div class="follower__item" id="follow-${u.userId}">`;
	item += `<div class="follower__img"><img src="/upload/{u.user.profileImageUrl}" alt="" onerror="this.src='/images/punch.png'"></div>`;
	item += `<div class="follower__text">`;
	item += `<h2>${u.username}</h2>`;
	item += `</div>`;

	if (!u.eqaulState) {
		if (u.followState) {
			item += `<div class="follower__btn"><button class="cta blue" onclick="followOrunfollow(${u.userId})">구독취소</button></div>`;
		} else {
			item += `<div class="follower__btn"><button class="cta" onclick="followOrunfollow(${u.userId})">구독하기</button></div>`;
		}
	}
	item += `</div>`;

	return item;
}

function followOrunfollow(userId) {

	let text = $(`#follow-${userId} button`).text();

	if (text == "구독취소") {

		$.ajax({
			type: "DELETE",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#follow-${userId} button`).text("구독하기");
			$(`#follow-${userId} button`).toggleClass("blue");
		});

	} else {

		$.ajax({
			type: "POST",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#follow-${userId} button`).text("구독취소");
			$(`#follow-${userId} button`).toggleClass("blue");
		});
	}
}


function followOrunfollowProfile(userId) {

	let text = $(`#profile_follow_btn button`).text();

	if (text == "구독취소") {

		$.ajax({
			type: "DELETE",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#profile_follow_btn button`).text("구독하기");
			$(`#profile_follow_btn button`).toggleClass("blue");
		});

	} else {

		$.ajax({
			type: "POST",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#profile_follow_btn button`).text("구독취소");
			$(`#profile_follow_btn button`).toggleClass("blue");
		});
	}
}


function closeFollow() {
	document.querySelector(".modal-follow").style.display = "none";
}
document.querySelector(".modal-follow").addEventListener("click", (e) => {
	if (e.target.tagName !== "BUTTON") {
		document.querySelector(".modal-follow").style.display = "none";
	}
});
function popup(obj) {
	console.log(obj);
	document.querySelector(obj).style.display = "flex";
}
function closePopup(obj) {
	console.log(2);
	document.querySelector(obj).style.display = "none";
}
document.querySelector(".modal-info").addEventListener("click", (e) => {
	if (e.target.tagName === "DIV") {
		document.querySelector(".modal-info").style.display = "none";
	}
});
document.querySelector(".modal-image").addEventListener("click", (e) => {
	if (e.target.tagName === "DIV") {
		document.querySelector(".modal-image").style.display = "none";
	}
});

// 회원정보 수정
function update(userId) {
	event.preventDefault();
	let data = $("#profile_setting").serialize();

	$.ajax({
		type: "put",
		url: "/user/" + userId,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res => {
		location.href = "/user/" + userId
	});
}