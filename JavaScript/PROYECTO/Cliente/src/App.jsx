import { useState } from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootswatch/dist/lux/bootstrap.min.css";

import './App.css';
import DatosATiempoReal from './paginas/datosATiempoReal';
import DatosHistoricosConGraficos from './paginas/datosHistoricosConGraficos';
import WebInicio from './paginas/webInicio'; 
import WebInicioSinLogin from './paginas/webInicioSinLogin'; 
import IrAHerramientas from './paginas/irAHerramientas'; 
import IrAFertilizantes from './paginas/irAFertilizantes'; 
import IrAMacetas from './paginas/irAMacetas'; 
import IrAAccesorios from './paginas/irAAccesorios'; 
import TiendaCategorias from './tiendaCategorias'; 
import Login from './paginas/login'; 
import Cesta from './paginas/cesta'; 

function App() {
  const [cesta, setCesta] = useState([]);

  return (
      <>
          <BrowserRouter>
              <Routes>
                  <Route path="/webInicio" element={<WebInicio />} />
                  <Route path="/cesta" element={<Cesta cesta={cesta} />} /> 
                  <Route path="/login" element={<Login />} />
                  <Route path="/" element={<WebInicioSinLogin />} />
                  <Route path="/datosATiempoReal" element={<DatosATiempoReal />} />
                  <Route path="/datosHistoricosConGraficos" element={<DatosHistoricosConGraficos />} />
                  <Route path="/tiendaCategorias" element={<TiendaCategorias />} />
                  <Route path="/irAHerramientas/:claseAsociada" element={<IrAHerramientas cesta={cesta} setCesta={setCesta} />}/>
                  <Route path="/irAFertilizantes/:claseAsociada" element={<IrAFertilizantes cesta={cesta} setCesta={setCesta} />} />
                  <Route path="/irAMacetas/:claseAsociada" element={<IrAMacetas cesta={cesta} setCesta={setCesta} />}/>
                  <Route path="/irAAccesorios/:claseAsociada" element={<IrAAccesorios cesta={cesta} setCesta={setCesta} />} />
              </Routes>
          </BrowserRouter>
      </>
  );
}

export default App;
