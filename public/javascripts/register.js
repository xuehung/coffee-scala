$(function(){
  $("#register-btn").on("click", function(){
    sendRegisterRequest();
  });
});

function sendRegisterRequest(){
  var data = {
    email: $("#email").val(),
    password: $("#password").val(),
    name: $("#name").val()
  };
  ajaxRequest(
    "POST",
    CONSTANT.REGISTER_API_URL,
    data,
    function(data){
      if(data.code == 200) {
        var email = data.email;
        var token = data.token;
        $.cookie("email", email);
        $.cookie("token", token);
        window.location = CONSTANT.INDEX_URL;
      } else {
        alert("register failed");
      }
    }
  );
};
