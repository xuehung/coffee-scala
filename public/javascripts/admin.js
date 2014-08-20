$(function(){
  $("#add-product-btn").on("click", addProductHandler);
  $("#add-schedule-btn").on("click", addScheduleHandler);
  if($("#add-schedule-btn").length > 0) {
    showAllProduct();
    $( "#datepicker" ).datepicker();
  }
});

function showAllProduct() {

  ajaxRequest(
    "GET",
    CONSTANT.SHOW_PRODUCTS_API_URL,
    {},
    function(data){
      if(data.code == 200) {
        for(var i=0 ; i<data.products.length ; i++) {
          var item = data.products[i];
          $("#product-id").append(
            $('<option></option>').val(item.id).html(item.name)
          );
        }
      } else {
        alert("loading failed");
      }
    }
  );
}

function addProductHandler() {
  var data = {
    name_ch: $("#name-ch").val(),
    name_eng: $("#name-eng").val(),
    altitude: $("#altitude").val(),
    origin: $("#origin").val()
  };
  ajaxRequest(
    "POST",
    CONSTANT.ADD_PRODUCT_API_URL,
    data,
    function(data){
      if(data.code == 200) {
        location.reload();
      } else {
        alert("adding failed");
      }
    }
  );
}

function addScheduleHandler() {
  var data = {
    date: $("#datepicker").val(),
    product_id: $("#product-id").val()
  };
  ajaxRequest(
    "POST",
    CONSTANT.ADD_SCHEDULE_API_URL,
    data,
    function(data){
      if(data.code == 200) {
        location.reload();
      } else {
        alert("adding failed");
      }
    }
  );
}

