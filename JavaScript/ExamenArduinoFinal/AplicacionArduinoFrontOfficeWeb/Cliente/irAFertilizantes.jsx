import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; 
import Fertilizante from "./fertilizante";

const IrAFertilizantes = ({ cesta, setCesta }) => {
    const { claseAsociada } = useParams(); 
    const [fertilizantes, setFertilizantes] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const buscaFertilizantes = async () => {
            try {
                const resultado = await axios.get(`http://localhost:5000/fertilizantes?clase_asociada=${claseAsociada}`);
                console.log(resultado.data);
                setFertilizantes(resultado.data); 
            } catch (error) {
                console.log(error);
            }
        };
        buscaFertilizantes();
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
                <h4 className="alert-heading">Fertilizantes</h4>
            </div>
            <div style={{ padding: "10px", margin: "10px 0" }}>
                {fertilizantes.map((fertilizante) => (
                    <Fertilizante key={fertilizante.id} datos={fertilizante} anadirCesta={anadirALaCesta} />
                ))}
            </div>
        </>
    );
};

export default IrAFertilizantes;