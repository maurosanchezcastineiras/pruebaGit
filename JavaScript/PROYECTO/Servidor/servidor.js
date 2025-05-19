import http from "http";
import express from "express";
import cors from "cors";
import mysql from "mysql";

const app = express();
const server = http.createServer(app);
const puerto = 5000;

app.use(cors());
app.use(express.json());

// Inicializar Firebase
const appFireBase = initializeApp(firebaseConfig);
const dbFireBase = getFirestore(appFireBase);
//////////////////////////////////////////////
const bd = mysql.createConnection({
  host: "dam2.colexio-karbo.com",
  port: "3333",
  user: "dam2",
  password: "Ka3b0134679",
  database: "proyecto_mauro",
});

bd.connect((err) => {
  if (err) {
      console.error("Error conectando a MySQL:", err);
      return;
  }
  console.log("Conexión a MySQL establecida correctamente.");
});
///////////////////////////////////////////////////////////////////////////////////////////////
app.get('/categorias', (req, res) => {
  const sql = "SELECT * FROM generoPeliculas";
  bd.query(sql, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      return res.json(data);
  });
});

app.get('/categorias/:id', (req, res) => {
  const sql = "SELECT * FROM generoPeliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Género no encontrado" });
      return res.json(data);
  });
});

app.get('/accion', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron películas de acción" });
      return res.json(data);
  });
});

app.get('/accion/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película de acción no encontrada" });
      return res.json(data[0]); 
  });
});

app.get('/terror', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron películas de terror" });
      return res.json(data);
  });
});

app.get('/terror/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película de terror no encontrado" });
      return res.json(data[0]); 
  });
});

app.get('/cienciaFiccion', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron películas de ciencia ficción" });
      return res.json(data);
  });
});

app.get('/cienciaFiccion/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película de ciencia ficción no encontrada" });
      return res.json(data[0]); 
  });
});


app.get('/western', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron peliculas western" });
      return res.json(data);
  });
});



app.get('/western/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película western no encontrada" });
      return res.json(data[0]); 
  });
});

app.get('/suspense', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron peliculas de suspense" });
      return res.json(data);
  });
});



app.get('/suspense/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película de suspense no encontrada" });
      return res.json(data[0]); 
  });
});

app.get('/drama', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron peliculas de drama" });
      return res.json(data);
  });
});



app.get('/drama/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película de drama no encontrada" });
      return res.json(data[0]); 
  });
});

app.get('/romance', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron peliculas de romance" });
      return res.json(data);
  });
});



app.get('/romance/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película romántica no encontrada" });
      return res.json(data[0]); 
  });
});

app.get('/comedia', (req, res) => {
  const generoId = req.query.genero_asociado;
  if (!generoId) {
      return res.status(400).json({ error: "El parámetro 'genero_asociado' es requerido" });
  }

  const sql = "SELECT * FROM peliculas WHERE genero_asociado = ?";
  const valores = [generoId];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "No se encontraron peliculas de comedia" });
      return res.json(data);
  });
});



app.get('/comedia/:id', (req, res) => {
  const sql = "SELECT * FROM peliculas WHERE id = ?";
  const valores = [req.params.id];
  bd.query(sql, valores, (err, data) => {
      if (err) return res.status(500).json({ error: err.message });
      if (data.length === 0) return res.status(404).json({ error: "Película de comedia no encontrada" });
      return res.json(data[0]); 
  });
});

app.listen(puerto, () => {
  console.log("Servidor funcionando en el puerto " + puerto);
});

