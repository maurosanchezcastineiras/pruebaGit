import React, { useState } from "react";
import axios from "axios";

const ActualizarRangos = () => {
  const [rangos, setRangos] = useState({
    minHumedad: 30,
    maxHumedad: 70,
    minTemperatura: 15,
    maxTemperatura: 30,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRangos({ ...rangos, [name]: parseFloat(value) });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:5000/actualizar-rangos", rangos);
      alert("Rangos actualizados correctamente");
    } catch (error) {
      console.error("Error actualizando rangos:", error);
      alert("Error al actualizar rangos");
    }
  };

  return (
    <div>
      <h4>Configuración de Rangos Ideales</h4>
      <form onSubmit={handleSubmit}>
        <label>
          Humedad Mínima:
          <input
            type="number"
            name="minHumedad"
            value={rangos.minHumedad}
            onChange={handleChange}
          />
        </label>
        <label>
          Humedad Máxima:
          <input
            type="number"
            name="maxHumedad"
            value={rangos.maxHumedad}
            onChange={handleChange}
          />
        </label>
        <label>
          Temperatura Mínima:
          <input
            type="number"
            name="minTemperatura"
            value={rangos.minTemperatura}
            onChange={handleChange}
          />
        </label>
        <label>
          Temperatura Máxima:
          <input
            type="number"
            name="maxTemperatura"
            value={rangos.maxTemperatura}
            onChange={handleChange}
          />
        </label>
        <button type="submit">Guardar</button>
      </form>
    </div>
  );
};

export default ActualizarRangos;