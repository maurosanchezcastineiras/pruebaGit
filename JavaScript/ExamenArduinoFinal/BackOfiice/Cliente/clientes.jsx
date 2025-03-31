import React from "react";
import axios from "axios"
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom'
import Cliente from "./cliente"

const Clientes = () =>{
    const navega = useNavigate()
    const [clientes,setClientes]=useState([])

    useEffect(()=>{
        const buscaClientes = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/clientes")
                console.log(resultado.data)
                setClientes(resultado.data)
            } catch (error) {
                console.log(error)
            }
        }
        buscaClientes()
    },[])

    const anadeCliente = () =>{
        navega("/formularioCliente")
    }

    return (
        <>
            <div className="alert alert-dismissible alert-warning">
                <h4 className="alert-heading">Nuestros maravillosos clientes!</h4>
                <p className="mb-0">Haga lo que quiera</p>
            </div>
            <p className="lead">Clientes</p>
            <div  style={{ border: "3px solid violet", padding: "10px", margin: "10px 0" }}>
                {clientes.map((cliente)=>{
                    return (
                        <Cliente key={cliente.id} usuario={cliente.usuario} contrasena={cliente.contrasena} jardinesAsociados={cliente.jardin_asociado} />
                    )
                })}
                <br/>
                <br/>
                <br/>
            </div>
            <button type="button" className="btn btn-warning" onClick={anadeCliente} name="anadeCliente">Añadir un cliente</button>
        </>
    )
}

export default Clientes
