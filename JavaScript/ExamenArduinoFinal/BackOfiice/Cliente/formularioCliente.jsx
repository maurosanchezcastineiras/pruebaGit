import React from "react";
import axios from "axios"
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom'

const FormularioCliente = () =>{

    const navega = useNavigate()
    const [formulario,setFormulario]=useState({
        "usuario": "",
        "contrasena": "",
        "jardin_asociado": ""

    })

    const [jardines,setJardines]=useState([])

    useEffect(()=>{
        const buscaJardines = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/jardines")
                console.log(resultado.data)
                setJardines(resultado.data)
            } catch (error) {
                console.log(error)
            }
        }
        buscaJardines()
    },[])

    const enviarCliente = async (e) =>{
        e.preventDefault()
        try {
            await axios.post("http://localhost:4000/clientes", formulario)
        } catch (error) {
            console.log(error)
        }
        navega("/")
    }

    const gestionFormulario = (e) =>{
        setFormulario((anterior) => ({...anterior, [e.target.name]:e.target.value}))
    }

    return(
        <>
            <h2>Agregar un Cliente</h2>
            <br/>
            <input type="text" onChange={gestionFormulario} id="usuario" name="usuario" value={formulario.usuario}></input>
            <br/>
            <input type="text" onChange={gestionFormulario} id="contrasena" name="contrasena" value={formulario.contrasena}></input>
            <br/>
            <br/>
            <select type="button" className="btn btn-info" id="jardin_asociado" name="jardin_asociado" onChange={gestionFormulario} value={formulario.jardin_asociado}>
                <option value="0">Elige uno o varios jardines</option>
                {
                    jardines.map((jardin) =>{
                        return(
                            <option key={jardin.id} value={jardin.id}>{jardin.jardin}</option>
                        )
                    })
                }
            </select>
            <br/>
            <br/>
            <button type="button" className="btn btn-danger" onClick={enviarCliente} name="enviarCliente">Envia un cliente</button>

        </>
    )
}

export default FormularioCliente