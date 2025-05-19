import { io } from "socket.io-client";

const s = sessionStorage.getItem("socketId");

export const socket = io("http://localhost:4000", {
    autoConnect: false,
});

if(s) {
    socket.auth = { sessionId: s };
}

socket.connect();

socket.on("connect", () => {
    sessionStorage.setItem("socketId", socket.id);
});