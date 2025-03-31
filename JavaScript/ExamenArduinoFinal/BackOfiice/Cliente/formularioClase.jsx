import React from "react";
import axios from "axios"
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom'

const FormularioClase = () =>{

    const navega = useNavigate()
    const [clases,setClases]=useState({
        "clase": ""
    })

    const enviarClase = async (e) =>{
        e.preventDefault()
        try {
            await axios.post("http://localhost:4000/claseArticulos", clases)
        } catch (error) {
            console.log(error)
        }
        navega("/")
    }

    const gestionClases = (e) =>{
        setClases((anterior) => ({...anterior, [e.target.name]:e.target.value}))
    }

    return(
        <>
            <h2>Agregar una Clase</h2>
            <br/>
            <input type="text" onChange={gestionClases} id="clase" name="clase" value={clases.clase}></input>
            <br/>
            <br/>
            <button type="button" className="btn btn-danger" onClick={enviarClase} name="enviarClase">Envia una clase</button>

        </>
    )
}

export default FormularioClase