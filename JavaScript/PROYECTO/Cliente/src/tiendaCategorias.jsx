import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import TiendaCategoria from "./tiendaCategoria";

const TiendaCategorias = ({ id, clase }) => { 
    const navega = useNavigate();
    const [tiendaCategorias, setTiendaCategorias] = useState([]);

    useEffect(() => {
        const buscaCategorias = async () => {
            try {
                const resultado = await axios.get("http://localhost:5000/generos");
                console.log(resultado.data);
                setTiendaCategorias(resultado.data);
            } catch (error) {
                console.log(error);
            }
        };
        buscaCategorias();
    }, []);

    const IrAAccion = (dato) => {
        navega("/irAAccion/" + dato);
    };

    const IrATerror = (dato) => {
        navega("/irATerror/" + dato);
    };

    const IrAComedia = (dato) => {
        navega("/irAComedia/" + dato);
    };

    const IrAWestern = (dato) => {
        navega("/irAWestern/" + dato);
    };

    const IrARomance = (dato) => {
        navega("/irARomance/" + dato);
    };

    const IrADrama = (dato) => {
        navega("/irADrama/" + dato);
    };

    const IrACienciaFiccion = (dato) => {
        navega("/irACienciaFiccion/" + dato);
    };

    const IrASuspense = (dato) => {
        navega("/irASuspense/" + dato);
    };

    return (
        <>
            <p style={{ fontSize: "20px", fontWeight: "bold", textAlign: "center" }}></p>
            <br/>
            <br/>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                <p className="text-body-secondary">{clase}</p>
                <br/>
                <div style={{ padding: "10px", margin: "10px 0" }}>
                    <div style={{ display: "flex", gap: "10px", flexWrap: "wrap" }}>
                        {tiendaCategorias.map((categoria) => (
                            <button key={categoria.id} type="button" className="btn btn-primary"
                                onClick={() => {
                                    const categoriaNombre = categoria.clase.toLowerCase().trim();
                                    if (categoriaNombre === "accion") {
                                        IrAAccion(categoria.id);
                                    } else if (categoriaNombre === "terror") {
                                        IrATerror(categoria.id);
                                    } else if (categoriaNombre === "comedia") {
                                        IrAComedia(categoria.id);
                                    } else if (categoriaNombre === "western") {
                                        IrAWestern(categoria.id);
                                    } else if (categoriaNombre === "romance") {
                                        IrARomance(categoria.id);
                                    } else if (categoriaNombre === "drama") {
                                        IrADrama(categoria.id);
                                    } else if (categoriaNombre === "ciencia_ficcion") {
                                        IrACienciaFiccion(categoria.id);
                                    } else if (categoriaNombre === "suspense") {
                                        IrASuspense(categoria.id);
                                    }
                                }}>
                                {categoria.clase} 
                            </button>
                        ))}
                    </div>
                    <br/>
                    <br/>
                    <br/>
                </div>
            </div>
        </>
    );
};

export default TiendaCategorias;