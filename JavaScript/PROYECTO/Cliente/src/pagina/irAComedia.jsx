import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Comedia from "./comedia";

const IrAComedia = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [peliculasComedia, setPeliculasComedia] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasComedia = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasComedia?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasComedia(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasComedia();
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
                <h4 className="alert-heading">Películas de Comedia</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasComedia.map((comedia) => (
                    <Comedia key={comedia.id} datos={comedia} anadirCesta={anadirALaCesta} />
                ))}
            </div>
        </>
    );
};

export default IrAComedia;