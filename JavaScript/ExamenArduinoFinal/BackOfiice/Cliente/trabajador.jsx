import React from "react";
import axios from "axios"
import { useNavigate } from 'react-router-dom'

const Trabajador = (datos) => { 
    const navega = useNavigate()

    const IrAFormularioTrabajadorEditar = (dato) =>{
        navega("/formularioTrabajadorEditar/" + dato)
    }

    const borrarTrabajador = async (dato) =>{
        await axios.delete("http://localhost:4000/trabajadores/" + dato)
        location.reload()
    }

    return (
        <>
            <br/>
            <br/>
            <div style={{ border: "3px solid yellow", padding: "10px", margin: "10px 0" }}>
                <p className="text-body-secondary">Trabajador {datos.id}</p>
                <p className="text-body-secondary">Nombre: {datos.nombre}</p>
                <p className="text-body-secondary">Apellidos: {datos.apellidos}</p>
                <p className="text-body-secondary">Rol: {datos.rol_asociado}</p>
            </div>
            <br/>
            <div>
                <button type="button" className="btn btn-primary" onClick={()=>{IrAFormularioTrabajadorEditar(datos.id)}} name="Editar">Editar</button>
                <br/>
                <br/>
                <button type="button" className="btn btn-danger disabled" onClick={()=>{borrarTrabajador(datos.id)}} name="Borrar">Borrar</button>
            </div>
        </>
    )

}

export default Trabajador