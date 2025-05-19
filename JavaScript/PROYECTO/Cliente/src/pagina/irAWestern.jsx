import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Western from "./western";

const IrAWestern = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [peliculasWestern, setPeliculasWestern] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasWestern = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasWestern?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasWestern(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasWestern();
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
                <h4 className="alert-heading">Películas Western</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasWestern.map((western) => (
                    <Western key={western.id} datos={western} anadirCesta={anadirALaCesta} />
                ))}
            </div>
        </>
    );
};

export default IrAWestern;