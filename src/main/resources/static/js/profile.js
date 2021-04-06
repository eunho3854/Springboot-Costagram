// 구독자 정보  모달 보기
$("#subscribe_btn").on("click", (e) => {
  e.preventDefault();
  $(".modal-follow").css("display", "flex");

  let userId = $("#userId").val();

  $.ajax({
    url: `/user/${userId}/follow`,
  })
    .done((res) => {
      $("#follow_list").empty();
      res.data.forEach((u) => {
        let item = makeSubscribeInfo(u);
        $("#follow_list").append(item);
      });
    })
    .fail((error) => {
      alert("오류 : " + error);
    });
});

// 구독자 정보 모달에서 아이템 생성
function makeSubscribeInfo(u) {
  let item = `<div class="follower__item" id="follow-${u.userId}">`;
  item += `<div class="follower__img">`;
  item += `<img src="/upload/${u.profileImageUrl}" alt=""  onerror="this.src='/images/punch.png'"/>`;
  item += `</div>`;
  item += `<div class="follower__text">`;
  item += `<h2>${u.username}</h2>`;
  item += `</div>`;
  item += `<div class="follower__btn">`;
  if (!u.equalState) {
    if (u.followState) {
      item += `<button class="cta blue" onclick="followOrUnFollowModal(${u.userId})">구독취소</button>`;
    } else {
      item += `<button class="cta" onclick="followOrUnFollowModal(${u.userId})">구독하기</button>`;
    }
  }
  item += `</div>`;
  item += `</div>`;

  return item;
}

// 구독자 정보 모달에서 구독하기, 구독 취소하기
function followOrUnFollowModal(userId) {
  let text = $(`#follow-${userId} button`).text();

  if (text === "구독취소") {
    $.ajax({
      type: "DELETE",
      url: "/follow/" + userId,
      dataType: "json",
    }).done((res) => {
      $(`#follow-${userId} button`).text("구독하기");
      $(`#follow-${userId} button`).toggleClass("blue");
    });
  } else {
    $.ajax({
      type: "POST",
      url: "/follow/" + userId,
      dataType: "json",
    }).done((res) => {
      $(`#follow-${userId} button`).text("구독취소");
      $(`#follow-${userId} button`).toggleClass("blue");
    });
  }
}

// 구독자 정보 프로파일에서 구독하기, 구독취소
function followOrUnFollowProfile(userId) {
  let text = $(`#follow_profile_btn`).text();

  if (text === "구독취소") {
    $.ajax({
      type: "DELETE",
      url: "/follow/" + userId,
      dataType: "json",
    }).done((res) => {
      $(`#follow_profile_btn`).text("구독하기");
      $(`#follow_profile_btn`).toggleClass("blue");
    });
  } else {
    $.ajax({
      type: "POST",
      url: "/follow/" + userId,
      dataType: "json",
    }).done((res) => {
      $(`#follow_profile_btn`).text("구독취소");
      $(`#follow_profile_btn`).toggleClass("blue");
    });
  }
}

// 회원정보 수정
function update(userId) {
  event.preventDefault();
  let data = $("#profile_setting").serialize();
  console.log(data);

  $.ajax({
    type: "put",
    url: "/user/" + userId,
    data: data,
    contentType: "application/x-www-form-urlencoded; charset=utf-8",
    dataType: "json",
  }).done((res) => {
    location.href = "/user/" + userId;
  });
}

// 사용자 정보 메뉴 보기
function popup(obj) {
	$(obj).css("display", "flex");
}

// 사용자 정보 메뉴 닫기
function closePopup(obj) {
	$(obj).css("display", "none");
}

$(".modal-info").on("click", (e)=>{
	$(".modal-info").css("display", "none");	
});

$(".modal-image").on("click", (e)=>{
	$(".modal-image").css("display", "none");	
});

function closeFollow() {
	$(".modal-follow").css("display", "none");		
}

$(".modal-follow").on("click", (e) => {
	  if (e.target.tagName !== "BUTTON") {
		  $(".modal-follow").css("display", "none");		
	}
});