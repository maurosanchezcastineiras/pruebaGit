import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Terror from "./terror";

const IrATerror = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [peliculasTerror, setPeliculasTerror] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaPeliculasTerror = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/peliculasTerror?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setPeliculasTerror(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaPeliculasTerror();
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
                <h4 className="alert-heading">Películas de Terror</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {peliculasTerror.map((terror) => (
                    <Terror key={terror.id} datos={terror} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrATerror;