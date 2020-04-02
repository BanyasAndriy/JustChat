

jQuery(document).ready(function($){
    
$('#create').click(function(){
  

  //создаём массив для значений флажков
  var checkboxes = [];
  $('input:checkbox:checked').each(function(){


    checkboxes.push(this.value);
  });
  /*объединяем массив в строку с разделителем-запятой. Но лучше подобные вещи хранить в массиве. Для наглядности - вывод в консоль.*/
  console.log(checkboxes.join(','));

groupDate={users:checkboxes.join(','),
      groupName: $('#group-name').val()}

  $.ajax({
    type: "POST",
    contentType : 'application/json; charset=utf-8',
    dataType:'text',
    data:JSON.stringify(groupDate),
    url: '/create-group',

    success :function(response){
      window.location = 'http://localhost:8080';

    },
    error:function(response,textStatus) {


    }
  });

})
});











jQuery(document).ready(function($){

  $.ajax({
    type: "POST",
    contentType : 'application/json; charset=utf-8',
    dataType : "json",
    url: '/get-all-users',

    success :function(response){

      let select="";

      console.log(response.length);

      for (let i = 0 ;i< response.length;i++){


        select += '' +
            '      <li class="contact" id="pzd">\n'+
            '<div class="wrap">\n'+
            '<span class="contact-status online"></span>\n'+
            '<img  class = "avarar" src="./image/anonim.png" alt="" />\n'+
            '<div class="meta">\n'+
            '<p class="name">'+response[i].login+'</p>\n'+
            '<input type="checkbox" class="ch" value="'+response[i].login+'">' +"Додати до групи" + '</input>\n'
        '</div>\n'+
            '</div>\n'+
            '</li>\n';


        $('.friends').html(select);
      }
    },
    error:function(response,textStatus) {
      alert("I am in error");

    }
  });

});

