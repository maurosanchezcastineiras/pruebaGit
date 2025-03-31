import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Herramienta from "./herramienta";

const IrAHerramientas = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [herramientas, setHerramientas] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaHerramientas = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/herramientas?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setHerramientas(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaHerramientas();
    }, [claseAsociada]); 

    const anadirALaCesta = (producto) => {
        // añade el producto actual a la cesta creando una nueva lista
        const cestaAct = [...cesta, producto]; 
        setCesta(cestaAct); 
        navigate("/cesta");
    };

    return (
        <>
            <div className="alert alert-dismissible alert-warning">
                <h4 className="alert-heading">Herramientas</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {herramientas.map((herramienta) => (
                    <Herramienta key={herramienta.id} datos={herramienta} anadirCesta={anadirALaCesta} />
                ))}
            </div>
        </>
    );
};

export default IrAHerramientas;