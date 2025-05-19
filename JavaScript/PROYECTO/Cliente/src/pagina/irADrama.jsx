import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Drama from "./drama";

const IrADrama = ({ cesta, setCesta }) => {
    const { claseAsociada} = useParams(); 
    const [peliculasDrama, setPeliculasDrama] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasDrama = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasDrama?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasDrama(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasDrama();
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
                <h4 className="alert-heading">Películas de Drama</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasDrama.map((drama) => (
                    <Drama key={drama.id} datos={drama} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrADrama;