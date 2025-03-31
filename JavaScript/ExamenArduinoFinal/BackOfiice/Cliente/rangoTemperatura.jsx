import React from "react";
import axios from "axios"
import { useNavigate } from 'react-router-dom'

const RangoTemperatura = (datos) => { 
    const navega = useNavigate()

    const IrAFormularioRangoTemperaturaEditar = (dato) =>{
        navega("/formularioRangoTemperaturaEditar/" + dato)
    }

    const borrarRango = async (dato) =>{
        await axios.delete("http://localhost:4000/rangosTemperatura/" + dato)
        location.reload()
    }

    return (
        <>
            <br/>
            <br/>
            <div style={{ border: "3px solid yellow", padding: "10px", margin: "10px 0" }}>
                <p className="text-body-secondary">Rangos ideales {datos.id}</p>
                <p className="text-body-secondary">Mínimo: {datos.idealMinimo}</p>
                <p className="text-body-secondary">Máximo: {datos.idealMaximo}</p>
            </div>
            <br/>
            <div>
                <button type="button" className="btn btn-primary" onClick={()=>{IrAFormularioRangoTemperaturaEditar(datos.id)}} name="Editar">Editar</button>
                <br/>
                <br/>
                <button type="button" className="btn btn-danger disabled" onClick={()=>{borrarRango(datos.id)}} name="Borrar">Borrar</button>
            </div>
        </>
    )

}

export default RangoTemperatura