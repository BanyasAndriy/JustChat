









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
            '<p class="preview">'+"" +'</p>\n'+
            '</div>\n'+
            '</div>\n'+
            '</li>\n';


          $('.users').html(select);


      }

        },
        error:function(response,textStatus) {
 alert("I am in error");

        }
    });

});



$(document).ready(function () {

    $.ajax({
        type: "POST",
        contentType : 'application/json; charset=utf-8',
        dataType : "json",
        url: '/get-current-user',

        success :function(response){
            $("#current-user").html(response.login);
        },
        error:function(response,textStatus) {


        }
    });
});

$(window).on('keydown', function(e) {
    if (e.which == 13) {




let  msg =  $(".message-input input").val();


        let whom = $('.chat-login').html();
        let from  = $('#current-user').html();
sendMessage(msg,whom,from);



      /*  let data={message:msg,
            to:whom};

        $.ajax({
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : "text",
            url: '/save-message' ,
            data:JSON.stringify(data),
            success :function(response){





            },
            error:function(response,textStatus) {
                alert("I am in error");

            }
        });

*/


        return false;
    }
});




$(function () {
    $(document).on('click', '.contact', function(){

        connect();
        jQuery('.contact').removeClass('active');
        jQuery(this).addClass('active');


        result = $(this).html();

       let urlAvatar;
        let firstIndexForLogin =result.lastIndexOf("<p class=\"name\">");
        let lastIndexForLogin=result.indexOf("</p>");
        let firstIndexForAvatar =result.lastIndexOf("<img");
        let lastIndexForAvatar=result.indexOf(" alt");


        let  login =result.substring(firstIndexForLogin+16,lastIndexForLogin);
        let  avatar =result.substring(firstIndexForAvatar+25,lastIndexForAvatar-1);

        $(".chat-login").html(login);
        $("#chat-avatar").attr("src",avatar);

        $('.msg').html(" ");

        let currentUser = $('#current-user').html();

        data={login:login};



    $.ajax({
        type: "POST",
        contentType : 'application/json; charset=utf-8',
        dataType : "json",
        url: '/get-history',
        data:JSON.stringify(data),
        success :function(response){

            let select="";

            console.log(response.length);

            for (let i = 0 ;i< response.length;i++){



                if (response[i].to===currentUser){
                select +='' + '<li class="replies">\n'+
                        '<img src="./image/anonim.png" alt="" />\n'+
                        '<p class="sendMessages">'+  response[i].message  +'</p>\n'+
                    '</li>\n';

                }else {
                    select +='' + '<li class="sent">\n'+
                        '<img src=""./image/anonim.png"" alt="" />\n'+
                        '<p class="sendMessages">'+  response[i].message  +'</p>\n'+
                        '</li>\n';
                }




                $('.msg').html(select);

            }


        },
        error:function(response,textStatus) {
            alert("I am in error");

        }
    });
})});





$(function () {
    $(document).on('click', '.submit', function(){



let  msg = $(".message-input input").val();

let whom = $('.chat-login').html();
let from = $('#current-user').html();
        sendMessage(msg,whom,from);
/*let data={message:msg,
      to:whom};

        $.ajax({
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : "text",
            url: '/save-message' ,
             data:JSON.stringify(data),
            success :function(response){

            },
            error:function(response,textStatus) {
                alert("I am in error");

            }
        });*/
    })});




function getHistory(from ,  to ) {

    data = {login: to,
             from : from};


    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-history',
        data: JSON.stringify(data),
        success: function (response) {

            let select = "";

            console.log(response.length);

            for (let i = 0; i < response.length; i++) {


                if (response[i].to === $('#current-user').html()) {

                    select += '' + '<li class="replies">\n' +
                        '<img src="./image/anonim.png" alt="" />\n' +
                        '<p class="sendMessages">' + response[i].message + '</p>\n' +
                        '</li>\n';

                } else {
                    select += '' + '<li class="sent">\n' +
                        '<img src=""./image/anonim.png"" alt="" />\n' +
                        '<p class="sendMessages">' + response[i].message + '</p>\n' +
                        '</li>\n';
                }


                $('.msg').html(select);

            }

        }
    });


};










function connect() {
    var socket = new SockJS('/chat-messaging');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log("connected: " + frame);
        stompClient.subscribe('/chat/messages', function(response) {
            var data = JSON.parse(response.body);
            draw( data.message);
        });
    });
}

function draw(text) {
    //message = $(".message-input input").val();



    if(text===''){
        return false;
    }

    if ($.trim(text) == '') {
        return false;
    }

getHistory($('#current-user').html() ,$('.chat-login').html());



    $('.message-input input').val(null);
    /*$('.contact.active .preview').html('<span>You: </span>' + text);*/
   // $(".messages").animate({ scrollTop: $(document).height() }, "fast");
   // divElement.scrollTop = 9999;


// if(current_user!=to  and != from){
//}

  /*  if ($('#current-user').html()==to) {
        $('<li class="replies"><img src="./image/anonim.png" alt="" /><p class="sendMessages">' + text + '</p></li>').appendTo($('.messages ul'));

    }else{
        $('<li class="sent"><img src="./image/anonim.png" alt="" /><p class="sendMessages">' + text + '</p></li>').appendTo($('.messages ul'));


    }
    $('.message-input input').val(null);
    $('.contact.active .preview').html('<span>You: </span>' + text);
    $(".messages").animate({ scrollDown: $(document).height() }, "fast");


*/




}
function disconnect(){
    stompClient.disconnect();
}
function sendMessage(message,whom,from){
    if (message===""){
        return false;
    }

    stompClient.send("/app/message", {}, JSON.stringify({'message': message , 'to':whom,'from':from}));

}


