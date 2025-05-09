import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom'
import axios from "axios";
import RangoTemperatura from "./rangoTemperatura"

const RangosTemperatura = () => {
  const navega = useNavigate()
  const [rangos,setRangos]=useState([])

  useEffect(() => {
    const obtenerDatos = async () => {
      try {
        const resultado = await axios.get("http://localhost:4000/rangosTemperatura");
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
              <h4 className="alert-heading">Los rangos de la Temperatura!</h4>
              <p className="mb-0">Haga lo que quiera</p>
          </div>
          <p className="lead">Rangos</p>
          <div  style={{ border: "3px solid violet", padding: "10px", margin: "10px 0" }}>
              {rangos.map((rango)=>{
                  return (
                      <RangoTemperatura key={rango.id} idealMinimo={rango.idealMinimo} idealMaximo={rango.idealMaximo} />
                  )
              })}
              <br/>
              <br/>
              <br/>
          </div>
       </>
  )
}

export default RangosTemperatura
