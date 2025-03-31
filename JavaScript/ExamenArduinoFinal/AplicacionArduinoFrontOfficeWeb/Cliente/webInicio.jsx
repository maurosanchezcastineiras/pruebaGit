import React from "react";
import { useNavigate } from "react-router-dom";

const WebInicio = () => {
  const navigate = useNavigate();

  const DatosATiempoReal = () => {
    navigate("/datosATiempoReal"); 
  };

  const DatosHistoricosConGraficos = () => {
    navigate("/datosHistoricosConGraficos");
  };

  const TiendaCategorias = () => {
    navigate("/tiendaCategorias");
  };

  const Cesta = () => {
    navigate("/cesta");
  };

  return (
    <div className="principal">
        <button type="button" className="btn btn-warning mb-3" onClick={DatosATiempoReal} > Datos de Planta </button>
        <br/>
        <button type="button" className="btn btn-success mb-3" onClick={DatosHistoricosConGraficos}> Datos Históricos</button>
        <br/>
        <button type="button" className="btn btn-success mb-3" onClick={TiendaCategorias}> Nuestra Tienda</button>
        <br/>
        <button type="button" className="btn btn-success mb-3" onClick={Cesta}> Tu Cesta</button>
    </div>
  );
};

export default WebInicio;
