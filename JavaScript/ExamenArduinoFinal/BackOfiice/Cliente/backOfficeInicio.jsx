import React from "react";
import { useNavigate } from "react-router-dom";

const BackOfficeInicio = () => {
  const navigate = useNavigate();

  const Clientes = () => {
    navigate("/paginas/clientes"); 
  };

  const Trabajadores = () => {
    navigate("/paginas/trabajadores");
  };

  const Clases = () => {
    navigate("/paginas/clases");
  };

  const RangosTemperatura = () => {
    navigate("/paginas/rangosTemperatura");
  };

  const RangosHumedad = () => {
    navigate("/paginas/rangosHumedad");
  };

  const RangosHumedadSuelo = () => {
    navigate("/paginas/rangosHumedadSuelo");
  };

  const NivelesAgua = () => {
    navigate("/paginas/nivelesAgua");
  };

  return (
    <div className="principal">
      <div>
          <button type="button" className="btn btn-warning mb-3" onClick={Clientes} > Gestión de Clientes </button>
          <button type="button" className="btn btn-success mb-3" onClick={Trabajadores}> Gestión de Trabajadores </button>
          <button type="button" className="btn btn-warning mb-3" onClick={Clases} > Gestión de Artículos</button>
          <button type="button" className="btn btn-warning mb-3" onClick={RangosTemperatura} > Rangos de la Temperatura</button>   
          <button type="button" className="btn btn-warning mb-3" onClick={RangosHumedad} > Rangos de la Humedad</button>   
          <button type="button" className="btn btn-warning mb-3" onClick={RangosHumedadSuelo} > Rangos de la Humedad del Suelo</button>   
          <button type="button" className="btn btn-warning mb-3" onClick={NivelesAgua} > Nivel del Agua</button>   
      </div>
    </div>
  );
};

export default BackOfficeInicio;
