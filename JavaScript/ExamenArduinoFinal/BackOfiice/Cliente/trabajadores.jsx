import React from "react";
import axios from "axios"
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom'
import Trabajador from "./trabajador"

const Trabajadores = () =>{
    const navega = useNavigate()
    const [trabajadores,setTrabajadores]=useState([])

    useEffect(()=>{
        const buscaTrabajadores = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/trabajadores")
                console.log(resultado.data)
                setTrabajadores(resultado.data)
            } catch (error) {
                console.log(error)
            }
        }
        buscaTrabajadores()
    },[])

    const anadeTrabajador = () =>{
        navega("/formularioTrabajador")
    }

    return (
        <>
            <div className="alert alert-dismissible alert-warning">
                <h4 className="alert-heading">Nuestros maravillosos trabajadores!</h4>
                <p className="mb-0">Haga lo que quiera</p>
            </div>
            <p className="lead">Trabajadores</p>
            <div  style={{ border: "3px solid violet", padding: "10px", margin: "10px 0" }}>
                {trabajadores.map((trabajador)=>{
                    return (
                        <Trabajador key={trabajador.id} nombre={trabajador.nombre} apellidos={trabajador.apellidos} jardinesAsociados={trabajador.rol_asociado}/>
                    )
                })}
                <br/>
                <br/>
                <br/>
            </div>
            <button type="button" className="btn btn-warning" onClick={anadeTrabajador} name="anadeTrabajador">Añadir un trabajador</button>
        </>
    )
}

export default Trabajadores
