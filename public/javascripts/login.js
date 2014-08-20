$(function(){
  $("#login-btn").on("click", function(){
    sendLoginRequest();
  });
});

function sendLoginRequest(){
  var data = {
    email: $("#login-email").val(),
    password: $("#login-password").val()
  };
  ajaxRequest(
    "POST",
    CONSTANT.LOGIN_API_URL,
    data,
    function(data){
      if(data.code == 200) {
        var email = data.email;
        var token = data.token;
        $.cookie("email", email);
        $.cookie("token", token);
        window.location = CONSTANT.INDEX_URL;
      } else {
        alert("Login failed");
      }
    }
  );
};
