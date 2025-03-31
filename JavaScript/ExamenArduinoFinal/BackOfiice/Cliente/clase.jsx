import React from "react";
import axios from "axios"
import { useNavigate } from 'react-router-dom'
import Articulos from "./articulos";

const Clase = (datos) => { 
    const navega = useNavigate()

    const IrAFormularioClaseArticuloEditar = (dato) =>{
        navega("/formularioClaseArticuloEditar/" + dato)
    }

    const borrarClase = async (dato) =>{
        await axios.delete("http://localhost:4000/claseArticulos/" + dato)
        location.reload()
    }

    return (
        <>
            <br/>
            <br/>
            <div style={{ border: "3px solid yellow", padding: "10px", margin: "10px 0" }}>
                <p className="text-body-secondary">Clase de Artículo: {datos.clase}</p>
            </div>
            <br/>
            <div>
                <button type="button" className="btn btn-primary" onClick={()=>{IrAFormularioClaseArticuloEditar(datos.id)}} name="Editar">Editar</button>
                <br/>
                <br/>
                <button type="button" className="btn btn-danger disabled" onClick={()=>{borrarClase(datos.id)}} name="Borrar">Borrar</button>
            </div>
            <Articulos claseArticuloId={datos.id} />
        </>
    )

}

export default Clase