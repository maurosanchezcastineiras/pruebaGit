import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Maceta from "./maceta";

const IrAMacetas = ({ cesta, setCesta }) => {
    const { claseAsociada} = useParams(); 
    const [macetas, setMacetas] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaMacetas = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/macetas?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setMacetas(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaMacetas();
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
                <h4 className="alert-heading">Macetas</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {macetas.map((maceta) => (
                    <Maceta key={maceta.id} datos={maceta} anadirCesta={anadirALaCesta}/>
                ))}
            </div>
        </>
    );
};

export default IrAMacetas;