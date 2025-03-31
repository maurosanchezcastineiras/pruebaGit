import React from "react";
import axios from "axios"
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom'

const FormularioTrabajador = () =>{

    const navega = useNavigate()
    const [formulario,setFormulario]=useState({
        "nombre": "",
        "apellidos": "",
        "rol_asociado": ""

    })

    const [roles,setRoles]=useState([])

    useEffect(()=>{
        const buscaRoles = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/roles")
                console.log(resultado.data)
                setRoles(resultado.data)
            } catch (error) {
                console.log(error)
            }
        }
        buscaRoles()
    },[])

    const enviarTrabajador = async (e) =>{
        e.preventDefault()
        try {
            await axios.post("http://localhost:4000/trabajadores", formulario)
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
            <h2>Agregar un Trabajador</h2>
            <br/>
            <input type="text" onChange={gestionFormulario} id="nombre" name="nombre" value={formulario.nombre}></input>
            <br/>
            <input type="text" onChange={gestionFormulario} id="apellidos" name="apellidos" value={formulario.apellidos}></input>
            <br/>
            <br/>
            <select type="button" className="btn btn-info" id="rol_asociado" name="rol_asociado" onChange={gestionFormulario} value={formulario.rol_asociado}>
                <option value="0">Elige un rol</option>
                {
                    roles.map((rol) =>{
                        return(
                            <option key={rol.id} value={rol.id}>{rol.rol}</option>
                        )
                    })
                }
            </select>
            <br/>
            <br/>
            <button type="button" className="btn btn-danger" onClick={enviarTrabajador} name="enviarTrabajador">Envia un trabajador</button>

        </>
    )
}

export default FormularioTrabajador