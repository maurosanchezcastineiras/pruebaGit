import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Romance from "./romance";

const IrARomance = ({ cesta, setCesta }) => {
    const { claseAsociada} = useParams(); 
    const [peliculasRomance, setPeliculasRomance] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasRomance = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasRomance?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasRomance(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasRomance();
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
                <h4 className="alert-heading">Películas Románticas</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasRomance.map((romance) => (
                    <Romance key={romance.id} datos={romance} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrARomance;