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
                const resultado = await axios.get("http://localhost:5000/categorias");
                console.log(resultado.data);
                setTiendaCategorias(resultado.data);
            } catch (error) {
                console.log(error);
            }
        };
        buscaCategorias();
    }, []);

    const IrAHerramientas = (dato) => {
        navega("/irAHerramientas/" + dato);
    };

    const IrAFertilizantes = (dato) => {
        navega("/irAFertilizantes/" + dato);
    };

    const IrAMacetas = (dato) => {
        navega("/irAMacetas/" + dato);
    };

    const IrAAccesorios = (dato) => {
        navega("/irAAccesorios/" + dato);
    };

    return (
        <>
            <p style={{ fontSize: "20px", fontWeight: "bold", textAlign: "center" }}>Nuestra Maravillosa Tienda</p>
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
                                    if (categoriaNombre === "herramientas") {
                                        IrAHerramientas(categoria.id);
                                    } else if (categoriaNombre === "fertilizantes") {
                                        IrAFertilizantes(categoria.id);
                                    } else if (categoriaNombre === "macetas") {
                                        IrAMacetas(categoria.id);
                                    } else if (categoriaNombre === "accesorios") {
                                        IrAAccesorios(categoria.id);
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