import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import CienciaFiccion from "./cienciaFiccion";

const IrACienciaFiccion = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [peliculasCienciaFiccion, setPeliculasCienciaFiccion] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasCienciaFiccion = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasCienciaFiccion?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasCienciaFiccion(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasCienciaFiccion();
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
                <h4 className="alert-heading">Películas de Ciencia Ficción</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasCienciaFiccion.map((cienciaFiccion) => (
                    <CienciaFiccion key={cienciaFiccion.id} datos={cienciaFiccion} anadirCesta={anadirALaCesta} />
                ))}
            </div>
        </>
    );
};

export default IrACienciaFiccion;