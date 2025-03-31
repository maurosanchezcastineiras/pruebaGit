import React from "react";
import axios from "axios"
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom'
import Clase from "./clase"

const Clases = () =>{
    const navega = useNavigate()
    const [clases,setClases]=useState([])

    useEffect(()=>{
        const buscaClase = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/claseArticulos")
                console.log(resultado.data)
                setClases(resultado.data)
            } catch (error) {
                console.log(error)
            }
        }
        buscaClase()
    },[])

    const anadeClase = () =>{
        navega("/formularioClaseArticulos")
    }

    return (
        <>
            <div className="alert alert-dismissible alert-warning">
                <h4 className="alert-heading">Nuestros maravillosos articulos!</h4>
                <p className="mb-0">Haga lo que quiera</p>
            </div>
            <p className="lead">Tipos</p>
            <div  style={{ border: "3px solid violet", padding: "10px", margin: "10px 0" }}>
                {clases.map((clase)=>{
                    return (
                        <Clase key={clase.id} id={clase.id} clase={clase.clase}/>
                    )
                })}
                <br/>
                <br/>
                <br/>
            </div>
            <button type="button" className="btn btn-warning" onClick={anadeClase} name="anadeClase">Añadir una clase</button>
        </>
    )
}

export default Clases
