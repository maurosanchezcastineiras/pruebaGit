import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Accion from "./accion";

const IrAAccion = ({ cesta, setCesta }) => {
    const { claseAsociada} = useParams(); 
    const [peliculasAccion, setPeliculasAccion] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasAccion = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasAccion?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasAccion(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasAccion();
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
                <h4 className="alert-heading">Películas de Acción</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasAccion.map((accion) => (
                    <Accion key={accion.id} datos={accion} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrAAccion;