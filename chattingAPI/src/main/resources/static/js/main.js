'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');

var roomPage = document.querySelector('#rooms-page');
var roomArea = document.querySelector('#roomList');

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

var messageUri = '/app/chatting/room/';
var wsUri = '/ws';

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        //chatPage.classList.remove('hidden');
        roomPage.classList.remove('hidden');

        var socket = new SockJS(wsUri);
        stompClient = Stomp.over(socket);

        stompClient.connect({a: 'a1', b: 'b2'}, onConnected, onError);
    }
    event.preventDefault();
}



function onConnected() {
    getRooms();
}

function getRooms()
{
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/chatting/rooms');
    xhr.send();

    xhr.onreadystatechange = function () {
        // 서버 응답 완료 && 정상 응답
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status === 200) {
            console.log(xhr.responseText);

            // Deserializing (String → Object)
            var responseObject = JSON.parse(xhr.responseText);

            // JSON → HTML String
            responseObject.forEach(room => {
                var roomElement = document.createElement('li');
                roomElement.classList.add('room');
                roomElement.addEventListener('click', function(event){
                    selectRoom(room.id);
                });

                var roomNameElement = document.createElement('span');
                roomNameElement.appendChild(document.createTextNode(room.name));

                roomElement.appendChild(roomNameElement);

                roomArea.appendChild(roomElement);
            });
        } else {
            console.log(`[${xhr.status}] : ${xhr.statusText}`);
        }
    };
}

function selectRoom(roomId)
{
    roomPage.classList.add('hidden');
    chatPage.classList.remove('hidden');
    messageUri += roomId;

    // Subscribe to the Public Topic
    stompClient.subscribe(messageUri + '/chat', onMessageReceived);

    // Tell your username to the server
    stompClient.send(messageUri + "/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient.connected) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        //stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        stompClient.send(messageUri + "/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    else
    {
        onError();
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
