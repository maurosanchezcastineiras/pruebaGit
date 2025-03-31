import http from "http";
import express from "express";
import cors from "cors";
import mysql from "mysql";

const app = express();
const server = http.createServer(app);
const puerto = 4000;

app.use(cors());
app.use(express.json());

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

app.get("/", (req, res) => {
  res.json("Servidor servido está servido el Servidor");
});
////////////////////////////////////////7777777777
app.get("/clientes", async (req, res) => {
  const sql = "select * from clientes"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get("/trabajadores", async (req, res) => {
  const sql = "select * from trabajadores"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get("/claseArticulos", async (req, res) => {
  const sql = "select * from claseArticulos"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get("/articulos", async (req, res) => {
  const sql = "select * from articulos"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get('/articulos/:id', (req, res) => {
  const sql = "select * from articulos where id=?";
  const valores = [req.params.id];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      if (data.length === 0) return res.status(404).json({ error: "Articulo no encontrada." });
      return res.json(data);
  });
});


app.get("/rangosTemperatura", async (req, res) => {
  const sql = "select * from rangosTemperatura"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get("/rangosHumedad", async (req, res) => {
  const sql = "select * from rangosHumedad"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get("/rangosHumedadSuelo", async (req, res) => {
  const sql = "select * from rangosHumedadSuelo"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.get("/nivelAgua", async (req, res) => {
  const sql = "select * from nivelAgua"

  bd.query(sql,(err,data)=>{
      if(err) return res.json(err)
      return res.json(data)
  })
});

app.post("/clientes", async (req, res) => {
  console.log("Datos recibidos:", req.body); 

  const sql = "insert into clientes set usuario=?, contrasena=?, jardin_asociado=?";
  const valores = [
      req.body.usuario,
      req.body.contrasena,
      req.body.rol_asociado
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) {
          console.log("Error al insertar:", err); 
          return res.json(err);
      }

      return res.json({ "status": "OK" });
  });
});
app.put("/clientes/:id", async (req, res) => {
  const sql = "update clientes set usuario=?, contrasena=? jardin_asociado=? where id=?"

  const valores = [
      req.body.usuario,
      req.body.contrasena,
      req.body.jardin_asociado,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.post("/trabajadores", async (req, res) => {
  console.log("Datos recibidos:", req.body); 

  const sql = "insert into trabajadores set nombre=?, apellidos=?, rol_asociado=?";
  const valores = [
      req.body.nombre,
      req.body.apellidos,
      req.body.rol_asociado
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) {
          console.log("Error al insertar:", err); 
          return res.json(err);
      }

      return res.json({ "status": "OK" });
  });
});

app.put("/trabajadores/:id", async (req, res) => {
  const sql = "update trabajadores set nombre=?, apellidos=? rol_asociado=? where id=?"

  const valores = [
      req.body.nombre,
      req.body.apellidos,
      req.body.rol_asociado,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.post("/claseArticulos", async (req, res) => {
  console.log("Datos recibidos:", req.body); 

  const sql = "insert into claseArticulos set clase=?";
  const valores = [
      req.body.clase,
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) {
          console.log("Error al insertar:", err); 
          return res.json(err);
      }

      return res.json({ "status": "OK" });
  });
});

app.put("/claseArticulos/:id", async (req, res) => {
  const sql = "update claseArticulos set clase=? where id=?"

  const valores = [
      req.body.clase,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.post("/articulos", async (req, res) => {
  console.log("Datos recibidos:", req.body); 

  const sql = "insert into articulos set articulo=?, clase_asociada=?";
  const valores = [
      req.body.articulo,
      req.body.clase_asociada
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) {
          console.log("Error al insertar:", err); 
          return res.json(err);
      }

      return res.json({ "status": "OK" });
  });
});

app.put("/articulos/:id", async (req, res) => {
  const sql = "update articulos set articulo=?, clase_asociada=? where id=?"

  const valores = [
    req.body.articulo,
      req.body.clase_asociada,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.delete('/clientes/:id', (req, res) => {
  const sql = "delete from clientes where id=?"

  const valores = [
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.delete('/trabajadores/:id', (req, res) => {
  const sql = "delete from trabajadores where id=?"

  const valores = [
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.delete('/claseArticulos/:id', (req, res) => {
  const sql = "delete from claseArticulos where id=?"

  const valores = [
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.delete('/articulos/:id', (req, res) => {
  const sql = "delete from articulos where id=?"

  const valores = [
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.put("/rangosTemperatura/:id", async (req, res) => {
  const sql = "update rangosTemperatura set idealMinimo=?, idealMaximo=? where id=?"

  const valores = [
      req.body.idealMinimo,
      req.body.idealMaximo,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.put("/rangosHumedad/:id", async (req, res) => {
  const sql = "update rangosHumedad set idealMinimo=?, idealMaximo=? where id=?"

  const valores = [
      req.body.idealMinimo,
      req.body.idealMaximo,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.put("/rangosHumedadSuelo/:id", async (req, res) => {
  const sql = "update rangosHumedadSuelo set idealMinimo=?, idealMaximo=? where id=?"

  const valores = [
      req.body.idealMinimo,
      req.body.idealMaximo,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.put("/nivelAgua/:id", async (req, res) => {
  const sql = "update nivelAgua set nivelMinimo=? where id=?"

  const valores = [
      req.body.nivelMinimo,
      req.params.id
  ];

  bd.query(sql, valores, (err, data) => {
      if (err) return res.json(err);
      return res.json({ "status": "OK" });
  });
});

app.listen(puerto, () => {
  console.log("Servidor funcionando en el puerto " + puerto);
});
