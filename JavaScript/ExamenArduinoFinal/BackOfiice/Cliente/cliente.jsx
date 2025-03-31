import React from "react";
import axios from "axios"
import { useNavigate } from 'react-router-dom'

const Cliente = (datos) => { 
    const navega = useNavigate()

    const IrAFormularioClienteEditar = (dato) =>{
        navega("/formularioClienteEditar/" + dato)
    }

    const borrarCliente = async (dato) =>{
        await axios.delete("http://localhost:4000/clientes/" + dato)
        location.reload()
    }

    return (
        <>
            <br/>
            <br/>
            <div style={{ border: "3px solid yellow", padding: "10px", margin: "10px 0" }}>
                <p className="text-body-secondary">Cliente {datos.id}</p>
                <p className="text-body-secondary">{datos.usuario}</p>
                <p className="text-body-secondary">Contraseña: {datos.contrasena}</p>
                <p className="text-body-secondary">{datos.jardin_asociado}</p>
            </div>
            <br/>
            <div>
                <button type="button" className="btn btn-primary" onClick={()=>{IrAFormularioClienteEditar(datos.id)}} name="Editar">Editar</button>
                <br/>
                <br/>
                <button type="button" className="btn btn-danger disabled" onClick={()=>{borrarCliente(datos.id)}} name="Borrar">Borrar</button>
            </div>
        </>
    )

}

export default Cliente