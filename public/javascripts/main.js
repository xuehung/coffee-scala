$(function(){
  navInit();
  pageInit();
  indexInit();
  $("#logout-btn").on("click", logout);
});

function navInit() {
  var isLogin = false;
  if(typeof $.cookie("email") !== "undefined" &&
    typeof $.cookie("token") !== "undefined") {
    isLogin = true;
  }
  if(isLogin) {
    $(".as-nonlogin").hide();
  } else {
    $(".as-login").hide();
  }
}

function cf() {
    var active = $('#product div.opaque');
    var next = (active.next('div.product-img').length > 0) ? active.next() : $('#product div.product-img:first');
    $("#product div.product-img").removeClass("opaque");
    next.addClass("opaque");
}

function logout(event) {
  event.preventDefault();
  $.removeCookie("email");
  $.removeCookie("token");
  window.location = CONSTANT.INDEX_URL;
}

function pageInit() {
    $(document).foundation();
}

function indexInit() {
    setInterval('cf()', 7000);
}
