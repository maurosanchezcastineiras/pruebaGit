import http from "http";
import express from "express";
import cors from "cors";
import mysql from "mysql";
import { initializeApp } from "firebase/app";
import { query, where, getFirestore, collection, getDocs, addDoc } from "firebase/firestore";

const app = express();
const server = http.createServer(app);
const puerto = 5000;

app.use(cors());
app.use(express.json());
/////////////////////////////////////////77777
// Configuración de Firebase
const firebaseConfig = {
  apiKey: "AIzaSyD2lNhTeo3jXnPoo_OzgrtDrOtJq9B_PK4",
  authDomain: "ejemplo-219d9.firebaseapp.com",
  projectId: "ejemplo-219d9",
  storageBucket: "ejemplo-219d9.appspot.com",
  messagingSenderId: "729196906554",
  appId: "1:729196906554:web:b64b7764a3609fc9b54d03",
};

// Inicializar Firebase
const appFireBase = initializeApp(firebaseConfig);
const dbFireBase = getFirestore(appFireBase);
//////////////////////////////////////////////
const bd = mysql.createConnection({
  host: "dam2.colexio-karbo.com",
  port: "3333",
  user: "dam2",
  password: "Ka3b0134679",
  database: "jardin_msanchez",
});

bd.connect((err) => {
  if (err) {
      console.error("Error conectando a MySQL:", err);
      return;
  }
  console.log("Conexión a MySQL establecida correctamente.");
});
////////////////////////////////////////////////
app.post("/login", async (req, res) => {
  const { usuario, contrasena } = req.body;

  try {
    const querySnapshot = await db.collection("clientes").where("usuario", "==", usuario).where("contrasena", "==", contrasena).get();

    if (!querySnapshot.empty) {
      res.status(200).json({ success: true });
    } else {
      res.status(401).json({ error: "Credenciales malas" });
    }
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "Error del servidor" });
  }
});
////////////////////////////////////////////////////////////////////7
app.get("/LosDatosDeLasPlantas", async (req, res) => {
  const { jardin_id } = req.query;

  try {
    if (!jardin_id) {
      return res.status(400).json({ error: "El parámetro jardin_id es obligatorio" });
    }

    const jardinId = Number(jardin_id);

    if (isNaN(jardinId)) {
      return res.status(400).json({ error: "jardin_id debe ser un número" });
    }

    const q = query(collection(dbFireBase, "arduino"), where("jardin_id", "==", jardinId));
    const querySnapshot = await getDocs(q);

    if (querySnapshot.empty) {
      return res.json([]);
    }
    const datos = querySnapshot.docs.map((doc) => ({
      id: doc.id,
      ...doc.data(),
    }));

    res.json(datos);
  } catch (error) {
    console.error("Error obteniendo datos:", error);
    res.status(500).json({ error: "Error con los datos", details: error.message });
  }
});
///////////////////////////////////////////////////////////////////////////////////////////////
app.get('/categorias', (req, res) => {
  const sql = "SELECT * FROM claseArticulos";
  bd.query(sql, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      return res.json(data);
  });
});

app.get('/categorias/:id', (req, res) => {
  const sql = "SELECT * FROM claseArticulos WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Categoría no encontrada" });
      return res.json(data);
  });
});

app.get('/herramientas', (req, res) => {
  const claseId = req.query.clase_asociada;
  if (!claseId) {
      return res.status(400).json({ error: "El parámetro 'clase_asociada' es requerido" });
  }

  const sql = "SELECT * FROM articulos WHERE clase_asociada = ?";
  const valores = [claseId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron herramientas" });
      return res.json(data);
  });
});

app.get('/herramientas/:id', (req, res) => {
  const sql = "SELECT * FROM articulos WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Herramienta no encontrada" });
      return res.json(data[0]); 
  });
});

app.get('/fertilizantes', (req, res) => {
  const claseId = req.query.clase_asociada;
  if (!claseId) {
      return res.status(400).json({ error: "El parámetro 'clase_asociada' es requerido" });
  }

  const sql = "SELECT * FROM articulos WHERE clase_asociada = ?";
  const valores = [claseId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron fertilizantes" });
      return res.json(data);
  });
});

app.get('/fertilizantes/:id', (req, res) => {
  const sql = "SELECT * FROM articulos WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Fertilizante no encontrado" });
      return res.json(data[0]); 
  });
});

app.get('/macetas', (req, res) => {
  const claseId = req.query.clase_asociada;
  if (!claseId) {
      return res.status(400).json({ error: "El parámetro 'clase_asociada' es requerido" });
  }

  const sql = "SELECT * FROM articulos WHERE clase_asociada = ?";
  const valores = [claseId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron macetas" });
      return res.json(data);
  });
});

app.get('/macetas/:id', (req, res) => {
  const sql = "SELECT * FROM articulos WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Maceta no encontrada" });
      return res.json(data[0]); 
  });
});


app.get('/accesorios', (req, res) => {
  const claseId = req.query.clase_asociada;
  if (!claseId) {
      return res.status(400).json({ error: "El parámetro 'clase_asociada' es requerido" });
  }

  const sql = "SELECT * FROM articulos WHERE clase_asociada = ?";
  const valores = [claseId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron accesorios" });
      return res.json(data);
  });
});



app.get('/accesorios/:id', (req, res) => {
  const sql = "SELECT * FROM articulos WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Accesorios no encontrada" });
      return res.json(data[0]); 
  });
});


app.listen(puerto, () => {
  console.log("Servidor funcionando en el puerto " + puerto);
});

