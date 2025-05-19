import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Suspense from "./suspense";

const IrASuspense = ({ cesta, setCesta }) => {
    const { claseAsociada} = useParams(); 
    const [peliculasSuspense, setPeliculasSuspense] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasSuspense = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasSuspense?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasSuspense(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasSuspense();
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
                <h4 className="alert-heading">Películas de Suspense</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasSuspense.map((suspense) => (
                    <Suspense key={suspense.id} datos={suspense} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrASuspense;