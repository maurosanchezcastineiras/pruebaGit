import React, { useEffect, useState } from "react";
import axios from "axios";
import { Chart } from "react-google-charts";

const DatosHistoricosConGraficos = () => {
  const [estosDatosMolanMazo, setEstosDatosMolanMazo] = useState([]);

  useEffect(() => {
    const elQueBuscaLosDatos = async () => {
      try {
        // bbtén el ID del jardín asociado desde localStorage
        const jardinAsociadoId = localStorage.getItem("jardin_asociado");
        // se hace la solicitud con el id del jardin
        const resultado = await axios.get(`http://localhost:5000/LosDatosDeLasPlantas?jardin_id=${jardinAsociadoId}`);
        setEstosDatosMolanMazo(resultado.data);
      } catch (error) {
        console.error("Error obteniendo datos:", error);
      }
    };
    elQueBuscaLosDatos();
  }, []);

  const datosHistoricosDeFIREBASE = (datos) => {
    return [
      ["Variable", "Valor"],
      ["Humedad", datos?.humedad || 0],
      ["Temperatura", datos?.temperatura || 0],
      ["Nivel Agua", datos?.nivelAgua || 0],
      ["Humedad Suelo", datos?.humedadSuelo || 0],
      ["Índice Calor", datos?.indiceCalor || 0],
    ];
  };

  return (
    <div>
      <h1>Comprueba los datos de tu planta</h1>
      <br />
      <br />
      <br />
      <br />
      {estosDatosMolanMazo.length === 0 ? (
        <p>Tu jardín murió hace tiempo ya</p>
      ) : (
        estosDatosMolanMazo.map((dato) => (
          <div key={dato.id} style={{ marginBottom: "20px" }}>
            <Chart chartType="BarChart"data={datosHistoricosDeFIREBASE(dato)} options={{title: "Datos de la planta", width: "100%", height: 400,hAxis: { title: "Valores" }, vAxis: { title: "Variables" },}}/>
          </div>
        ))
      )}
    </div>
  );
};

export default DatosHistoricosConGraficos;