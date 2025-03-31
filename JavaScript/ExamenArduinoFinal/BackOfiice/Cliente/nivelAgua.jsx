import React from "react";
import axios from "axios"
import { useNavigate } from 'react-router-dom'

const NivelAgua = (datos) => { 
    const navega = useNavigate()

    const IrAFormularioNivelAguaEditar = (dato) =>{
        navega("/formularioNivelAguaEditar/" + dato)
    }

    const borrarNivel = async (dato) =>{
        await axios.delete("http://localhost:4000/nivelAgua/" + dato)
        location.reload()
    }

    return (
        <>
            <br/>
            <br/>
            <div style={{ border: "3px solid yellow", padding: "10px", margin: "10px 0" }}>
                <p className="text-body-secondary">Nivel Agua {datos.id}</p>
                <p className="text-body-secondary">Mínimo: {datos.idealMinimo}</p>
            </div>
            <br/>
            <div>
                <button type="button" className="btn btn-primary" onClick={()=>{IrAFormularioNivelAguaEditar(datos.id)}} name="Editar">Editar</button>
                <br/>
                <br/>
                <button type="button" className="btn btn-danger disabled" onClick={()=>{borrarNivel(datos.id)}} name="Borrar">Borrar</button>
            </div>
        </>
    )

}

export default NivelAgua