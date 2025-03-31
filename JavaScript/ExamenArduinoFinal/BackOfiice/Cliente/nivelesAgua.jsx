import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom'
import axios from "axios";
import NivelAgua from "./nivelAgua"

const NivelesAgua = () => {
  const navega = useNavigate()
  const [rangos,setRangos]=useState([])

  useEffect(() => {
    const obtenerDatos = async () => {
      try {
        const resultado = await axios.get("http://localhost:4000/nivelAgua");
        console.log(resultado.data)
        setRangos(resultado.data)
      } catch (error) {
        console.log(error)
      }
  }
  obtenerDatos()
},[])

  return (
      <>
          <div className="alert alert-dismissible alert-warning">
              <h4 className="alert-heading">Los rangos del Nivel del Agua!</h4>
              <p className="mb-0">Haga lo que quiera</p>
          </div>
          <p className="lead">Nivel</p>
          <div  style={{ border: "3px solid violet", padding: "10px", margin: "10px 0" }}>
              {rangos.map((rango)=>{
                  return (
                      <NivelAgua key={rango.id} nivelMinimo={rango.nivelMinimo}  />
                  )
              })}
              <br/>
              <br/>
              <br/>
          </div>
       </>
  )
}

export default NivelesAgua
