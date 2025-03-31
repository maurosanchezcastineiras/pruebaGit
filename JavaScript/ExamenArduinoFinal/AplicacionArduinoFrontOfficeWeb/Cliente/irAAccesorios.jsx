import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Accesorio from "./accesorio";

const IrAAccesorios = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [accesorios, setAccesorios] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaAccesorios = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/accesorios?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setAccesorios(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaAccesorios();
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
                <h4 className="alert-heading">Accesorios</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {accesorios.map((accesorio) => (
                    <Accesorio key={accesorio.id} datos={accesorio} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrAAccesorios;