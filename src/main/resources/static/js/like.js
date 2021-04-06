function likeOrUnLike(imageId) {
  let likeIcon = $("#like_icon_"+imageId);
  if (likeIcon.hasClass("far")) {
	  $.ajax({
		  type: "POST",
		  url: `/image/${imageId}/likes`,
		  dataType: "json"
	  }).done(res=>{
		    let likeCountStr  = $(`#like_count_${imageId}`).text();
		    let likeCount = Number(likeCountStr) + 1;
		    $(`#like_count_${imageId}`).text(likeCount);
		    
		    likeIcon.addClass("fas");
		    likeIcon.addClass("active");
		    likeIcon.removeClass("far");
	  });
	  
	  

  } else {
	  $.ajax({
		  type: "DELETE",
		  url: `/image/${imageId}/likes`,
		  dataType: "json"
	  }).done(res=>{
		    let likeCountStr  = $(`#like_count_${imageId}`).text();
		    let likeCount = Number(likeCountStr) - 1;
		    $(`#like_count_${imageId}`).text(likeCount);
		    
		    likeIcon.removeClass("fas");
		    likeIcon.removeClass("active");
		    likeIcon.addClass("far");
	  });  

  }
}