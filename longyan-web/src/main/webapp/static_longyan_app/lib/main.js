$(function(){
	$('.home-slider .flexslider').flexslider({
		animation: "slide",
		touch: true
	});

	$('.product-slides .flexslider').flexslider({
		animation: "slide",
		touch: true
	});

	$(".body").bind("touchmove", function(event) {
		// event.preventDefault();
		event.stopPropagation();
	});

	$(".input-stars .ion-ios-star").bind("touchstart touchmove", function(event) {
		var stars = parseInt($(this).attr("data-stars"));

		switch(stars) {
			case 1:
				$(this).parent(".input-stars").removeClass().addClass("input-stars onestar");
				$(this).siblings("input:hidden").val(stars);
				break;
			case 2:
				$(this).parent(".input-stars").removeClass().addClass("input-stars twostar");
				$(this).siblings("input:hidden").val(stars);
				break;
			case 3:
				$(this).parent(".input-stars").removeClass().addClass("input-stars threestar");
				$(this).siblings("input:hidden").val(stars);
				break;
			case 4:
				$(this).parent(".input-stars").removeClass().addClass("input-stars fourstar");
				$(this).siblings("input:hidden").val(stars);
				break;
			case 5:
				$(this).parent(".input-stars").removeClass().addClass("input-stars fivestar");
				$(this).siblings("input:hidden").val(stars);
				break;
		}

		event.stopPropagation();
	});

	$("#button-call").bind("click", function(){
		$("#pop-backdrop").addClass("active").addClass("animated fadeIn");
		$("#pop-call").addClass("active").addClass("animated slideInUp");
	});

	$("#pop-call .button").bind("click", function(){
		$("#pop-backdrop").addClass("animated fadeOut");
		$("#pop-call").addClass("animated slideOutDown");
		setTimeout(function(){
			$("#pop-backdrop").removeClass("active animated fadeIn fadeOut");
			$("#pop-call").removeClass("active animated slideInUp slideOutDown");
		}, 300);
	});


	height = $(window).height();

	$("#welcome-slider").css("height", height + "px");
	$("#welcome-slider li").css("height", height + "px");


	$('.welcome-slider .flexslider').flexslider({
		animation: "slide",
		touch: true,
		animationLoop: false,
		slideshow: false
	});

	$("body").bind("touchmove", function(event) {
		event.preventDefault();
		event.stopPropagation();
	});

});

