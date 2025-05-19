import React, { useEffect, useState } from "react";
import { io } from "socket.io-client";
import { Chart } from "react-google-charts";

const DatosATiempoReal = () => {
  const [datos, setDatos] = useState(null);
  const [conectado, setConectado] = useState(false);
  const [errorConexion, setErrorConexion] = useState(false);
  const [dataChart, setDataChart] = useState([
    ["Variable", "Valor"],
    ["Humedad", 0],
    ["Temperatura", 0],
    ["Nivel Agua", 0],
    ["Humedad Suelo", 0],
    ["Índice Calor", 0],
  ]);

  useEffect(() => {
    const socket = io("http://localhost:3000", {
      reconnection: true,
      reconnectionAttempts: 5,
      reconnectionDelay: 1000,
    });

    socket.on("connect_error", (err) => {
      console.error("Error al conectar al servidor:", err.message);
      setErrorConexion(true);
    });

    socket.on("connect", () => {
      console.log("Conectado al servidor correctamente");
      setConectado(true);
      setErrorConexion(false);
    });

    socket.on("disconnect", () => {
      console.log("Desconectado del servidor");
      setConectado(false);
    });

    socket.on("recibioDatosPlanta", (data) => {
      console.log("Datos recibidos:", data);
      if (data.humedad != null && data.temperatura != null && data.nivelAgua != null && data.humedadSuelo != null && data.indiceCalor != null) {
        setDatos(data);
        setDataChart([["Variable", "Valor"],["Humedad", data.humedad],["Temperatura", data.temperatura],["Nivel Agua", data.nivelAgua],["Humedad Suelo", data.humedadSuelo],["Índice Calor", data.indiceCalor],]);
      }
    });

    return () => {
      socket.disconnect();
    };
  }, []);

  return (
    <div className="container mt-4">
      <div className="alert alert-dismissible alert-warning">
        <h4 className="alert-heading">Los Datos de tu planta a tiempo real</h4>
      </div>
      {errorConexion && (
        <div className="alert alert-danger">
          <p>Tremendo error</p>
        </div>
      )}
      {datos ? (
        <div className="card border-primary mb-3">
          <div className="card-header bg-primary text-white">
            <h5 className="card-title">Datos de la planta</h5>
          </div>
          <div className="card-body">
            <p className="card-text">Temperatura: {datos.temperatura}°C</p>
            <p className="card-text">Humedad: {datos.humedad}%</p>
            <p className="card-text">Humedad Suelo: {datos.humedadSuelo}%</p>
            <p className="card-text">Nivel de Agua: {datos.nivelAgua}%</p>
            <p className="card-text">Índice de Calor: {datos.indiceCalor}</p>
          </div>
        </div>
      ) : null}
      <div style={{ width: "100%", height: "400px" }}>
        <Chart chartType="BarChart" data={dataChart}options={{title: "Datos de la planta",width: "100%",height: 400,hAxis: { title: "Valores" },vAxis: { title: "Variables" },}}/>
      </div>
    </div>
  );
};

export default DatosATiempoReal;