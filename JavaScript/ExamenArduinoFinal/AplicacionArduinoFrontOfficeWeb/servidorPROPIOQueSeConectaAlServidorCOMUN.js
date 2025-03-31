import express from 'express';
import http from 'http';
import { initializeApp } from 'firebase/app';
import { getFirestore, collection, addDoc } from 'firebase/firestore';
import { Server } from 'socket.io';
import { io as ClientIO } from 'socket.io-client';
import cors from 'cors';

const PUERTO = 3000;
const app = express();
const server = http.createServer(app);
app.use(cors({
  origin: "*", 
  methods: ["GET", "POST"],
  credentials: true,
}));

const ioServer = new Server(server, {
  cors: {
    origin: "*", 
    methods: ["GET", "POST"],
    allowedHeaders: ["my-custom-header"],
    credentials: true,
  },
});

// Configuración de Firebase
const firebaseConfig = {
  apiKey: "AIzaSyD2lNhTeo3jXnPoo_OzgrtDrOtJq9B_PK4",
  authDomain: "ejemplo-219d9.firebaseapp.com",
  projectId: "ejemplo-219d9",
  storageBucket: "ejemplo-219d9.appspot.com",
  messagingSenderId: "729196906554",
  appId: "1:729196906554:web:b64b7764a3609fc9b54d03",
};

const appFireBase = initializeApp(firebaseConfig);
const dbFireBase = getFirestore(appFireBase);

console.log("Firebase inicializado correctamente");

const socket = ClientIO('http://dam2.colexio-karbo.com:6227', {
  reconnection: true,
  reconnectionAttempts: 5,
  reconnectionDelay: 1000,
});

socket.on('connect_error', (err) => {
  console.error('Error al conectar al servidor remoto:', err.message);
});

socket.on('connect', () => {
  console.log('Conectado al servidor remoto correctamente');
});

socket.on('disconnect', () => {
  console.log('Desconectado del servidor remoto');
});

socket.on('recibioArduino', async (data) => {
  if (
    data.humedad === undefined ||
    data.temperatura === undefined ||
    data.nivelAgua === undefined ||
    data.humedadSuelo === undefined ||
    data.indiceCalor === undefined
  ) {
    console.warn('Datos incompletos recibidos:', data);
    return;
  }

  ioServer.emit('recibioDatosPlanta', data);
  const { humedad, temperatura, nivelAgua, humedadSuelo, indiceCalor } = data;
  const jardin_id = Math.random() < 0.5 ? 1 : 2;

  try {
    const eljardin = await addDoc(collection(dbFireBase, 'arduino'), {
      jardin_id,
      humedad,
      temperatura,
      nivelAgua,
      humedadSuelo,
      indiceCalor,
      timestamp: new Date(),
    });
    console.log('guardado en Firebase:', eljardin.id);
  } catch (e) {
    console.error('Error al guardar:', e.message);
  }
});
ioServer.on('connection', (socket) => {
  console.log('Cliente conectado al servidor');

  socket.on('disconnect', () => {
    console.log('Cliente desconectado');
  });
});

server.listen(PUERTO, () => {
  console.log(`Servidor local escuchando en http://localhost:${PUERTO}`);
});
