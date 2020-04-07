function getAllUsers() {


    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-all-users',

        success: function (response) {

            let select = "";


            for (let i = 0; i < response.length; i++) {

                if (response[i] != null) {
                    select += '' +
                        '      <li class="contact" id="pzd">\n' +
                        '<div class="wrap">\n' +
                        '<span class="contact-status online"></span>\n' +
                        '<img  class = "avarar" src="./image/anonim.png" alt="" />\n' +
                        '<div class="meta">\n' +
                        '<p class="name">' + response[i].login + '</p>\n' +
                        '<p class="preview">' + "" + '</p>\n' +
                        '</div>\n' +
                        '</div>\n' +
                        '</li>\n';


                    $('.users').html(select);

                }
            }

        },
        error: function (response, textStatus) {
            alert("I am in error");

        }
    });


}


function getAllGroups() {
    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-all-groups',

        success: function (response) {

            let select = "";

            console.log(response.length);

            for (let i = 0; i < response.length; i++) {


                select += '' +
                    '      <li class="contact" id="pzd">\n' +
                    '<div class="wrap">\n' +
                    '<span class="contact-status online"></span>\n' +
                    '<img  class = "avarar" src="./image/anonim.png" alt="" />\n' +
                    '<div class="meta">\n' +
                    '<p class="name">' + response[i].name + '</p>\n' +
                    '<p class="preview">' + "" + '</p>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '</li>\n';


                $('.users').html(select);


            }

        },
        error: function (response, textStatus) {
            alert("I am in error");

        }
    });
}


jQuery(document).ready(function ($) {
    getAllUsers();

});


$(document).ready(function () {

    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-current-user',

        success: function (response) {
            $("#current-user").html(response.login);
        },
        error: function (response, textStatus) {


        }
    });
});

$(window).on('keydown', function (e) {
    if (e.which == 13) {


        let msg = $(".message-input input").val();
        let whom = $('.chat-login').html();
        let from = $('#current-user').html();

        let messageStatus = $('#choosen').text().trim();
        sendMessage(msg, whom, from, messageStatus);


        return false;
    }
});


$(function () {
    $(document).on('click', '.choose-users-groups', function () {

        $('.choose-users-groups').attr('id', '');
        $(this).attr('id', 'choosen');


        let messageStatus = $('#choosen').text();
        console.log(messageStatus);
        if (messageStatus.trim() === "Contacts") {
            getAllUsers();
        } else if (messageStatus.trim() === "Groups") {
            getAllGroups();
        }

    })

});

function icons() {
    if ($('#choosen').text().trim() === "Groups") {
        $('.fa-user-plus').show();
        $('.fa-users').show();
        $('.fa-facebook').hide();
        $('.fa-twitter').hide();
        $('.fa-instagram').hide();


    } else {

        $('.fa-user-plus').hide();
        $('.fa-users').hide();
        $('.fa-facebook').show();
        $('.fa-twitter').show();
        $('.fa-instagram').show();
    }
}


$(document).ready(function () {
    icons();

});

$(document).ready(function () {

    $('#close-icon').click(function () {
        $('#rightPanel').hide();
    });
});


function getUsersFromGroup() {

    var data = {groupName: $('.chat-login').html()};

    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-all-users-by-group',
        data: JSON.stringify(data),
        success: function (response) {


            var select = '';

            let isAdmin=false;

            for (let i = 0; i < response.length; i++) {

                if (response[i] != null) {
                    if ($('#current-user').html() === response[i].login && response[i].admin === true) {

                        isAdmin=true;

                    }}
            }


            for (let i = 0; i < response.length; i++) {

                if (response[i] != null) {
                    if (isAdmin) {
                        select += '' +
                            '      <li class="contactByGroup" id="">\n' +
                            '<div class="wrap">\n' +
                            '<img  class = "avatar" src="./image/anonim.png" alt="" />\n' +
                            '<div class="meta">\n' +
                            '<p class="user-name">' + response[i].login   + '<span id="delete-user" >X<span></p>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</li>\n';


                        $('.usersOfGroup').html(select);
                    }else {
                        select += '' +
                            '      <li class="contactByGroup" id="">\n' +
                            '<div class="wrap">\n' +
                            '<img  class = "avatar" src="./image/anonim.png" alt="" />\n' +
                            '<div class="meta">\n' +
                            '<p class="user-name">' + response[i].login + '</p>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</li>\n';


                        $('.usersOfGroup').html(select);
                    }
                }
            }
            if (isAdmin){
                $('#set-group').html('<button id="drop-group">Видалити групу</button>');
            }

        }
    });
}

$(document).ready(function () {

    $('#groupUsers').click(function () {

        $('#rightPanel').show();





        getUsersFromGroup();

    });
});




$(function () {
    $(document).on('click', '#delete-user', function () {

let value  = $(this).closest(".user-name").html();

let userName =value.substr(0,value.lastIndexOf('<span id="delete-user">'));

var data =  { groupName : $('.chat-login').html() , users :userName};

        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: "text",
            url: '/delete-user-from-group',
            data: JSON.stringify(data),
            success: function (response) {

                    getUsersFromGroup();

            }
        });
    })
});





$(function () {
    $(document).on('click', '.contact', function () {

        connect();
        jQuery('.contact').removeClass('active');
        jQuery(this).addClass('active');


        icons();


        let messageStatus = $('#choosen').text();
        result = $(this).html();

        let urlAvatar;
        let firstIndexForLogin = result.lastIndexOf("<p class=\"name\">");
        let lastIndexForLogin = result.indexOf("</p>");
        let firstIndexForAvatar = result.lastIndexOf("<img");
        let lastIndexForAvatar = result.indexOf(" alt");


        let login = result.substring(firstIndexForLogin + 16, lastIndexForLogin);
        let avatar = result.substring(firstIndexForAvatar + 25, lastIndexForAvatar - 1);

        $(".chat-login").html(login);
        $("#chat-avatar").attr("src", avatar);

        $('.msg').html(" ");

        let currentUser = $('#current-user').html();

        data = {login: login, messageStatus: messageStatus};


        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: "json",
            url: '/get-history',
            data: JSON.stringify(data),
            success: function (response) {

                let select = "";


                console.log(response.length);
                if ($('#choosen').text().trim() === "Contacts") {
                    for (let i = 0; i < response.length; i++) {
                        if (response[i].to === $('#current-user').html()) {

                            select += '' + '<li class="replies">\n' +
                                '<img src="./image/anonim.png" alt="" />\n' +
                                '<p class="sendMessages">' + response[i].message + '</p>\n' +
                                '</li>\n';

                        } else {
                            select += '' + '<li class="sent">\n' +
                                '<img src="./image/anonim.png" alt="" />\n' +
                                '<p class="sendMessages">' + response[i].message + '</p>\n' +
                                '</li>\n';
                        }
                        $('.msg').html(select);
                    }
                } else if ($('#choosen').text().trim() === "Groups") {
                    for (let i = 0; i < response.length; i++) {

                        console.log(response[i].from + "  : " + $('#current-user').html());
                        console.log(response[i].from === $('#current-user').html());


                        if (response[i].from === $('#current-user').html()) {

                            select += '' + '<li class="sent">\n' +
                                '<img src="./image/anonim.png" alt="" />\n' +
                                '<p class="sendMessages">' + response[i].message + '</p>\n' +
                                '</li>\n';

                        } else {
                            select += '' + '<li class="replies">\n' +
                                '<img src="./image/anonim.png" alt="" />\n' +
                                '<p class="sendMessages">' + response[i].message + '</p>\n' +
                                '</li>\n';
                        }


                        $('.msg').html(select);

                    }


                }


            }
        });
    })
});


$(function () {
    $(document).on('click', '.submit', function () {

        let msg = $(".message-input input").val();

        let whom = $('.chat-login').html();
        let from = $('#current-user').html();
        let messageStatus = $('#choosen').text().trim();
        sendMessage(msg, whom, from, messageStatus);


    })
});


function getHistory(from, to) {

    data = {
        login: to,
        from: from, messageStatus: $('#choosen').text().trim()
    };


    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-history',
        data: JSON.stringify(data),
        success: function (response) {

            let select = "";


            console.log(response.length);
            if ($('#choosen').text().trim() === "Contacts") {
                for (let i = 0; i < response.length; i++) {
                    if (response[i].to === $('#current-user').html()) {

                        select += '' + '<li class="replies">\n' +
                            '<img src="./image/anonim.png" alt="" />\n' +
                            '<p class="sendMessages">' + response[i].message + '</p>\n' +
                            '</li>\n';

                    } else {
                        select += '' + '<li class="sent">\n' +
                            '<img src="./image/anonim.png" alt="" />\n' +
                            '<p class="sendMessages">' + response[i].message + '</p>\n' +
                            '</li>\n';
                    }
                    $('.msg').html(select);
                }
            } else if ($('#choosen').text().trim() === "Groups") {
                for (let i = 0; i < response.length; i++) {

                    console.log(response[i].from + "  : " + $('#current-user').html());
                    console.log(response[i].from === $('#current-user').html());


                    if (response[i].from === $('#current-user').html()) {

                        select += '' + '<li class="sent">\n' +
                            '<img src="./image/anonim.png" alt="" />\n' +
                            '<p class="sendMessages">' + response[i].message + '</p>\n' +
                            '</li>\n';

                    } else {
                        select += '' + '<li class="replies">\n' +
                            '<img src="./image/anonim.png" alt="" />\n' +
                            '<p class="sendMessages">' + response[i].message + '</p>\n' +
                            '</li>\n';
                    }


                    $('.msg').html(select);

                }


            }


        }
    });
}


function saveUrlNetwork(id, value) {

    alert("id  = " + id);
    alert("value= " + value)

    var network = {
        nameOfTheNetwork: id,
        urlOfTheNetwork: value
    }


    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "text",
        url: '/save-network',
        data: JSON.stringify(network),
        success: function (response) {
            alert("network  saved");

        }
    });

}

$(document).ready(function () {

    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        url: '/get-all-networks',

        success: function (response) {


            $("#facebook").val(response[0]);


            $("#twitter").val(response[1]);

            $("#instagram").val(response[2]);

        }
    });
});


function visitNetwork(network) {

    let login = $('.chat-login').html();

    if ($('.chat-login').html() === 'Загальний чат') {
        data = {nameOfTheNetwork: network, owner: $('#current-user').html()};
    } else data = {nameOfTheNetwork: network, owner: login};

    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: "text",
        data: JSON.stringify(data),
        url: '/visit-network',
        success: function (response) {
            if (response != null) {
                window.open(response);
            }

        }
    });


}


function searchUsers(value) {

    if (value === "") {
        $('.users').html("");
        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: "json",
            url: '/get-all-users',

            success: function (response) {

                let select = "";

                console.log(response.length);

                for (let i = 0; i < response.length; i++) {

                    if (response[i] != null) {
                        select += '' +
                            '      <li class="contact" id="pzd">\n' +
                            '<div class="wrap">\n' +
                            '<span class="contact-status online"></span>\n' +
                            '<img  class = "avarar" src="./image/anonim.png" alt="" />\n' +
                            '<div class="meta">\n' +
                            '<p class="name">' + response[i].login + '</p>\n' +
                            '<p class="preview">' + "" + '</p>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</li>\n';


                        $('.users').html(select);
                    }

                }

            },
            error: function (response, textStatus) {
                alert("I am in error");

            }
        });
    } else $('.users').html("");


    if (value != "") {
        data = {login: value}
        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: "json",
            data: JSON.stringify(data),
            url: '/search-users',

            success: function (response) {

                let select = "";

                console.log(response.length);

                for (let i = 0; i < response.length; i++) {


                    select += '' +
                        '      <li class="contact" id="pzd">\n' +
                        '<div class="wrap">\n' +
                        '<span class="contact-status online"></span>\n' +
                        '<img  class = "avarar" src="./image/anonim.png" alt="" />\n' +
                        '<div class="meta">\n' +
                        '<p class="name">' + response[i].login + '</p>\n' +
                        '<p class="preview">' + "" + '</p>\n' +
                        '</div>\n' +
                        '</div>\n' +
                        '</li>\n';


                    $('.users').html(select);


                }

            },
            error: function (response, textStatus) {
                alert("I am in error");

            }
        });
    }


}


$(function () {
    $(document).on('click', '#drop-group', function () {



let data={groupName : $('.chat-login').html()};

        $.ajax({
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : "text",
            url: '/delete-group',
            data:JSON.stringify(data),
            success :function(response) {

            if (response==="OK"){
                window.location = 'http://localhost:8080';
            }


            }
        });





    })

});






/*
jQuery(document).ready(function($){

    $.ajax({
        type: "POST",
        contentType : 'application/json; charset=utf-8',
        dataType : "json",
        url: '/get-all-groups',

        success :function(response){
            alert("I am in groups");
            let select="";

            console.log(response.length);

            for (let i = 0 ;i< response.length;i++){


                select += '' +
                    '      <li class="contact" id="pzd">\n'+
                    '<div class="wrap">\n'+
                    '<span class="contact-status online"></span>\n'+
                    '<img  class = "avarar" src="./image/anonim.png" alt="" />\n'+
                    '<div class="meta">\n'+
                    '<p class="name">'+response[i].name+'</p>\n'+
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
*/


//web sockets

function connect() {
    var socket = new SockJS('/chat-messaging');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected: " + frame);
        stompClient.subscribe('/chat/messages', function (response) {
            var data = JSON.parse(response.body);
            draw(data.message);
        });
    });
}

function draw(text) {
    //message = $(".message-input input").val();


    if (text === '') {
        return false;
    }

    if ($.trim(text) == '') {
        return false;
    }

    getHistory($('#current-user').html(), $('.chat-login').html());


    $('.message-input input').val(null);
    /*$('.contact.active .preview').html('<span>You: </span>' + text);*/
    // $(".messages").animate({ scrollTop: $(document).height() }, "fast");

    /*    var el = document.getElementsByClassName('.message');
        el.scrollTop = Math.ceil( el.scrollHeight -  el.clientHeight);*/


}

function disconnect() {
    stompClient.disconnect();
}

function sendMessage(message, whom, from, messageStatus) {
    if (message === "") {
        return false;
    }

    stompClient.send("/app/message", {}, JSON.stringify({
        'message': message,
        'to': whom,
        'from': from,
        'messageStatus': messageStatus
    }));

}


